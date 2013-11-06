package vn.ptit.anhdinh.scr;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;

import vn.ptit.anhdinh.scr.data.GetReviews;
import vn.ptit.anhdinh.scr.data.WPStoreReviews;
import vn.ptit.anhdinh.scr.model.SummaryFeature;
import vn.ptit.anhdinh.scr.utils.FileUtils;
import vn.ptit.anhdinh.wordnet.model.Opinion;
import edu.stanford.nlp.ling.WordTag;

public class RunApplication {

	private List<String> mReviews;
	private List<List<WordTag>> mReviewsTagged;
	private List<String> mFeatures;
	private final Map<List<WordTag>, String> mOpinionReviews = new HashMap<List<WordTag>, String>();
	private Map<String, Opinion> mOpinionAdjectives;
	private Map<String, SummaryFeature> mSummaryFeatures;

	private static final String SUMMARY_PATH = "data/summaryOfZalo.json";

	public static void main(String[] args) throws Exception {
		RunApplication runApp = new RunApplication();
		// runApp.downloadReviews();
		runApp.vnTagging();
		// runApp.taggingToFile();
		runApp.featureExtraction();
		runApp.opinionReviewsExtraction();
		runApp.showOpinionReviews();
		runApp.adjectiveOpinionIdentification();
		runApp.summarizingReviewsBaseOnFeature();
		runApp.generateSummaryFeature();
	}

	private void downloadReviews() {
		System.out.print("Crawl and download Reviews...");
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
		System.out.print("Vietnamese Tagging...");
		mReviews = FileUtils.ReadFile("data/reviewsOfZalo.txt");
		ReviewsPOSTagging reviewsTagging = new ReviewsPOSTagging(mReviews);
		mReviewsTagged = reviewsTagging.getReviewsTagged();
		System.out.println("OK");
	}

	private void featureExtraction() {
		System.out.print("Feature Extraction...");
		FeatureExtraction featureExtraction = new FeatureExtraction(mReviewsTagged);
		mFeatures = featureExtraction.getFrequentFeature(0.02);
		for (String nouns : mFeatures) {
			System.out.println(nouns);
		}
		System.out.println("OK");
	}

	private void opinionReviewsExtraction() {
		System.out.print("Opinion Reviews Extraction...");
		for (int i = 0; i < mReviewsTagged.size(); i++) {
			List<WordTag> reviewTagged = mReviewsTagged.get(i);
			if (checkOpinionReview(reviewTagged)) {
				mOpinionReviews.put(reviewTagged, mReviews.get(i));
			}
		}
		System.out.println("OK");
	}

	private void adjectiveOpinionIdentification() {
		System.out.print("Adjective Opinion Identification...");
		List<String> adjectives = new LinkedList<String>();
		for (List<WordTag> opinionReview : mOpinionReviews.keySet()) {
			for (WordTag wordTag : opinionReview) {
				if ("A".equals(wordTag.tag()) && !adjectives.contains(wordTag.word())) {
					adjectives.add(wordTag.word());
				}
			}
		}
		AdjectiveOrientationIdentification adjOI = new AdjectiveOrientationIdentification();
		mOpinionAdjectives = adjOI.OrientationPrediction(adjectives);
		System.out.println("OK");
	}

	private void summarizingReviewsBaseOnFeature() {
		System.out.print("Summarizing Reviews Base On Feature...");
		SummarizingOpinionReviews summarizingReviews = new SummarizingOpinionReviews(mOpinionReviews, mFeatures, mOpinionAdjectives);
		mSummaryFeatures = summarizingReviews.getSummaryFeature();
		System.out.println("OK");
	}

	private void generateSummaryFeature() {
		System.out.print("Generate Summary Feature...");
		JSONArray jsonSummary = new JSONArray();
		for (SummaryFeature summaryFeature : mSummaryFeatures.values()) {
			jsonSummary.add(summaryFeature.getJSONValue());
		}
		FileUtils.WriteJSONFile(SUMMARY_PATH, jsonSummary);
		System.out.println("OK");
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
			String review = mOpinionReviews.get(reviewTagged);
			System.out.println(review);
		}
		System.out.println("Total: " + mOpinionReviews.keySet().size());
	}

	private void taggingToFile() {
		ReviewsPOSTagging.writeFileTagged("Zalo");
	}

}
