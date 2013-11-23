package vn.ptit.anhdinh.scr;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import vn.ptit.anhdinh.scr.model.SummaryFeature;
import vn.ptit.anhdinh.scr.utils.FileUtils;
import vn.ptit.anhdinh.wordnet.model.Opinion;
import edu.stanford.nlp.ling.WordTag;

public class SummarizingOpinionReviews {
	private final Map<List<WordTag>, String> mOpinionReviews;
	private final List<String> mFeatures;
	private final Map<String, Opinion> mOpinionAdjectives;
	private final Map<String, Opinion> mOpinionReviewsWithOrientation = new HashMap<String, Opinion>();
	private final Map<String, SummaryFeature> mSummaryFeatures = new TreeMap<String, SummaryFeature>();

	private final String[] mNegationWord = { "không", "ko", "k", "chưa", "chẳng" };

	public SummarizingOpinionReviews(Map<List<WordTag>, String> opinionReviews, List<String> features, Map<String, Opinion> opinionAdjectives) {
		mOpinionReviews = opinionReviews;
		mFeatures = features;
		mOpinionAdjectives = opinionAdjectives;
		for (String feature : mFeatures) {
			SummaryFeature summaryFeature = new SummaryFeature(feature, new LinkedList<String>(), new LinkedList<String>());
			mSummaryFeatures.put(feature, summaryFeature);
		}
		reviewOrientation();
		summarizingReviews();
	}

	private void summarizingReviews() {
		for (String review : mOpinionReviewsWithOrientation.keySet()) {
			Opinion opinion = mOpinionReviewsWithOrientation.get(review);
			if (Opinion.POSITIVE.equals(opinion)) {
				addPositiveReview(review);
			}
			if (Opinion.NEGATIVE.equals(opinion)) {
				addNegativeReview(review);
			}
		}
	}

	public Map<String, SummaryFeature> getSummaryFeature() {
		return mSummaryFeatures;
	}

	private void reviewOrientation() {
		for (List<WordTag> taggedReview : mOpinionReviews.keySet()) {
			int orientation = 0;
			for (String adjective : getAdjectiveInReview(taggedReview)) {
				orientation += wordOrientation(adjective, taggedReview);
			}
			if (orientation > 0) {
				// addPositiveReview(mOpinionReviews.get(taggedReview));
				mOpinionReviewsWithOrientation.put(mOpinionReviews.get(taggedReview), Opinion.POSITIVE);
			} else if (orientation < 0) {
				// addNegativeReview(mOpinionReviews.get(taggedReview));
				mOpinionReviewsWithOrientation.put(mOpinionReviews.get(taggedReview), Opinion.NEGATIVE);
			} else {
				for (String feature : getFeatureInside(taggedReview)) {
					String adjClosely = getAdjectiveClosely(taggedReview, feature);
					if (adjClosely == null) {
						break;
					}
					int subOrientation = wordOrientation(adjClosely, taggedReview);
					if (subOrientation > 0) {
						SummaryFeature summaryFeature = mSummaryFeatures.get(feature);
						summaryFeature.addPositiveReview(mOpinionReviews.get(taggedReview));
						mSummaryFeatures.put(feature, summaryFeature);
					}
					if (subOrientation < 0) {
						SummaryFeature summaryFeature = mSummaryFeatures.get(feature);
						summaryFeature.addNegativeReview(mOpinionReviews.get(taggedReview));
						mSummaryFeatures.put(feature, summaryFeature);
					}
				}
			}
		}
	}

	private void addPositiveReview(String review) {
		for (String feature : mFeatures) {
			if (review.toLowerCase().contains(feature)) {
				SummaryFeature summaryFeature = mSummaryFeatures.get(feature);
				summaryFeature.addPositiveReview(review);
				mSummaryFeatures.put(feature, summaryFeature);
			}
		}
	}

	private void addNegativeReview(String review) {
		for (String feature : mFeatures) {
			if (review.toLowerCase().contains(feature)) {
				SummaryFeature summaryFeature = mSummaryFeatures.get(feature);
				summaryFeature.addNegativeReview(review);
				mSummaryFeatures.put(feature, summaryFeature);
			}
		}
	}

	private int wordOrientation(String adjective, List<WordTag> taggedReview) {
		Opinion opinion = mOpinionAdjectives.get(adjective);
		if (opinion == null) {
			return 0;
		}
		if (adjectiveWithNagationWord(adjective, taggedReview)) {
			return Opinion.opposite(opinion).getmValue();
		}
		return opinion.getmValue();
	}

	private boolean adjectiveWithNagationWord(String adjective, List<WordTag> taggedReview) {
		int adjIndex = 0;
		for (int i = 0; i < taggedReview.size(); i++) {
			if (adjective.equals(taggedReview.get(i).word())) {
				adjIndex = i;
			}
		}
		if (adjIndex == 0) {
			return false;
		}
		int startSenIndex = 0;
		for (int i = 0; i < taggedReview.size(); i++) {
			if (",".equals(taggedReview.get(i).word()) && i < adjIndex) {
				startSenIndex = i;
			} else if (".".equals(taggedReview.get(i).word()) && i < adjIndex) {
				startSenIndex = i;
			} else if ("...".equals(taggedReview.get(i).word()) && i < adjIndex) {
				startSenIndex = i;
			}
		}
		for (int i = startSenIndex + 1; i < adjIndex; i++) {
			for (int j = 0; j < mNegationWord.length; j++) {
				if (mNegationWord[j].equals(taggedReview.get(i).word())) {
					// printlnSenAndAdj(taggedReview, adjective);
					return true;
				}
			}
		}
		return false;
	}

	private void printlnSenAndAdj(List<WordTag> taggedReview, String adjective) {
		for (WordTag wordTag : taggedReview) {
			System.out.print(wordTag.word() + " ");
		}
		System.out.println(": " + adjective);
	}

	private List<String> getAdjectiveInReview(List<WordTag> taggedReview) {
		List<String> adjectives = new LinkedList<String>();
		for (WordTag wordTag : taggedReview) {
			if ("A".equals(wordTag.tag())) {
				adjectives.add(wordTag.word());
			}
		}
		return adjectives;
	}

	private List<String> getFeatureInside(List<WordTag> taggedReview) {
		List<String> features = new LinkedList<String>();
		for (WordTag wordTag : taggedReview) {
			if ("N".equals(wordTag.tag()) && mFeatures.contains(wordTag.word())) {
				features.add(wordTag.word());
			}
		}
		return features;
	}

	private String getAdjectiveClosely(List<WordTag> reviews, String feature) {
		int pFeature = -1;
		int pAdjective = 1000;
		for (int i = 0; i < reviews.size(); i++) {
			WordTag wordTag = reviews.get(i);
			if (feature.equals(wordTag.word())) {
				pFeature = i;
			}
		}
		for (int i = 0; i < reviews.size(); i++) {
			WordTag wordTag = reviews.get(i);
			if ("A".equals(wordTag.tag())) {
				int oldIndex = absoluteValue(pFeature, pAdjective);
				int newIndex = absoluteValue(pFeature, i);
				if ((newIndex < oldIndex) && mOpinionAdjectives.containsKey(wordTag.word())) {
					pAdjective = i;
				}
			}
		}
		if (pAdjective == 1000) {
			return null;
		}
		return reviews.get(pAdjective).word();
	}

	private int absoluteValue(int n1, int n2) {
		return (n1 - n2) > 0 ? (n1 - n2) : (n2 - n1);
	}

	private void utilsTagOrientation() {
		List<String> strs = new LinkedList<String>();
		for (String review : mOpinionReviews.values()) {
			String value = "";
			if (mOpinionReviewsWithOrientation.containsKey(review)) {
				Opinion opinion = mOpinionReviewsWithOrientation.get(review);
				value = review + " $" + opinion.getmName();
				for (String feature : mFeatures) {
					if (review.contains(feature)) {
						value = value + " <" + feature + ":$" + opinion.getmName() + ">";
					}
				}
			} else {
				value = review + " $" + Opinion.NEUTRAL.getmName();
			}
			strs.add(value);
		}
		FileUtils.WriteFile("data/zalo/opinionReviewsOfZalo - tagOrientation.txt", strs, false);
	}
}
