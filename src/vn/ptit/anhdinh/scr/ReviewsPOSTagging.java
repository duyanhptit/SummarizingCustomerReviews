package vn.ptit.anhdinh.scr;

import java.util.LinkedList;
import java.util.List;

import vn.hus.nlp.tagger.VietnameseMaxentTagger;
import edu.stanford.nlp.ling.WordTag;

public class ReviewsPOSTagging {

	private static VietnameseMaxentTagger mVnTagger = new VietnameseMaxentTagger();
	private List<String> mReviews = new LinkedList<String>();
	private final List<List<WordTag>> mReviewsTagged = new LinkedList<List<WordTag>>();

	public ReviewsPOSTagging(List<String> reviews) {
		mReviews = reviews;
		reviewTagging();
	}

	public List<List<WordTag>> getReviewsTagged() {
		return mReviewsTagged;
	}

	private void reviewTagging() {
		for (String reiview : mReviews) {
			List<WordTag> taggedReview = mVnTagger.tagText2(reiview);
			mReviewsTagged.add(taggedReviewToLowCase(taggedReview));
		}
	}

	private List<WordTag> taggedReviewToLowCase(List<WordTag> taggedReview) {
		List<WordTag> taggedReviewLowCase = new LinkedList<WordTag>();
		for (WordTag wordTag : taggedReview) {
			taggedReviewLowCase.add(new WordTag(wordTag.word().toLowerCase(), wordTag.tag()));
		}
		return taggedReviewLowCase;
	}

	public static void writeFileTagged(String appName) {
		System.out.println("Beginning Vietnamese Tagger comments...");
		String inFile = "data/" + appName.toLowerCase() + "/reviewsOf" + appName + ".txt";
		String outFile = "data/" + appName.toLowerCase() + "/reviewsOf" + appName + ".xml";
		mVnTagger.tagFile(inFile, outFile);
		System.out.println("Ended Vietnamese tagger.");
	}
}
