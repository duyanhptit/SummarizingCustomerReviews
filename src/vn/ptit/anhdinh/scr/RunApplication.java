package vn.ptit.anhdinh.scr;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import vn.ptit.anhdinh.scr.data.GetReviews;
import vn.ptit.anhdinh.scr.data.WPStoreReviews;
import vn.ptit.anhdinh.scr.utils.FileUtils;
import vn.ptit.anhdinh.wordnet.model.Opinion;
import edu.stanford.nlp.ling.WordTag;

public class RunApplication {

	// private static WordNetAPI mWordNetAPI = new WordNetAPI();
	private List<String> mReviews = new LinkedList<String>();
	private List<List<WordTag>> mReviewsTagged = new LinkedList<List<WordTag>>();
	private List<String> mFeatures = new LinkedList<String>();
	private final Map<List<WordTag>, Integer> mOpinionReviews = new HashMap<List<WordTag>, Integer>();

	public static void main(String[] args) throws Exception {
		RunApplication runApp = new RunApplication();
		// runApp.downloadReviews();
		runApp.vnTagging();
		// runApp.taggingToFile();
		runApp.featureExtraction();
		runApp.opinionReviewsExtraction();
		runApp.showOpinionReviews();
	}

	private void downloadReviews() {
		String appName = "Zalo";
		GetReviews wpsrs = new WPStoreReviews();
		List<String> rawReviews = wpsrs.getReviews(appName);

		PreprocessorData preprocessorData = new PreprocessorData(rawReviews);
		preprocessorData.convertComposeUnicode();
		preprocessorData.deleteSpecialCharacter();
		preprocessorData.removeReviewsNotVietnamese();
		List<String> reviews = preprocessorData.getReviews();

		System.out.print("Write reiviews to file: reviewsOf" + appName + ".txt... ");
		FileUtils.WriteFile("data/reviewsOf" + appName + ".txt", reviews, false);
		System.out.println("OK");
	}

	private void vnTagging() {
		mReviews = FileUtils.ReadFile("data/reviewsOfZalo.txt");
		ReviewsPOSTagging reviewsTagging = new ReviewsPOSTagging(mReviews);
		mReviewsTagged = reviewsTagging.getReviewsTagged();
	}

	private void featureExtraction() {
		FeatureExtraction featureExtraction = new FeatureExtraction(mReviewsTagged);
		mFeatures = featureExtraction.getFrequentFeature(0.02);
		for (String nouns : mFeatures) {
			System.out.println(nouns);
		}
	}

	private void opinionReviewsExtraction() {
		for (int i = 0; i < mReviewsTagged.size(); i++) {
			List<WordTag> reviewTagged = mReviewsTagged.get(i);
			if (checkOpinionReview(reviewTagged)) {
				mOpinionReviews.put(reviewTagged, i);
			}
		}
	}

	private boolean checkOpinionReview(List<WordTag> reviewTagged) {
		boolean hasAdjective = false;
		boolean hasFeature = false;
		for (WordTag wordTag : reviewTagged) {
			if ("A".equals(wordTag.tag())) {
				hasAdjective = true;
			} else if (mFeatures.contains(wordTag.word())) {
				hasFeature = true;
			}
		}
		if (hasAdjective && hasFeature) {
			return true;
		}
		return false;
	}

	private void showOpinionReviews() {
		for (List<WordTag> reviewTagged : mOpinionReviews.keySet()) {
			String review = mReviews.get(mOpinionReviews.get(reviewTagged));
			System.out.println(review);
		}
		System.out.println("Total: " + mOpinionReviews.keySet().size());
	}

	public Opinion getOpinionOfComment(String comment) {
		int resultOpinion = 0;
		// List<WordTag> wordTags = mVnTagger.tagText2(comment);
		// for (WordTag wordTag : wordTags) {
		// if ("A".equals(wordTag.tag())) {
		// Opinion opinion = mWordNetAPI.getOpinion(wordTag.word());
		// if (Opinion.NEGATIVE.equals(opinion)) {
		// resultOpinion--;
		// }
		// if (Opinion.POSITIVE.equals(opinion)) {
		// resultOpinion++;
		// }
		// }
		// }
		if (resultOpinion < 0) {
			return Opinion.NEGATIVE;
		}
		if (resultOpinion > 0) {
			return Opinion.POSITIVE;
		}
		return Opinion.NEUTRAL;
	}

	private void taggingToFile() {
		ReviewsPOSTagging.writeFileTagged("Zalo");
	}

}
