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
		for (String comment : mReviews) {
			mReviewsTagged.add(mVnTagger.tagText2(comment));
		}
	}

	public static void writeFileTagged(String appName) {
		System.out.println("Beginning Vietnamese Tagger comments...");
		String inFile = "data/reviewsOf" + appName + ".txt";
		String outFile = "data/reviewsOf" + appName + ".xml";
		mVnTagger.tagFile(inFile, outFile);
		System.out.println("Ended Vietnamese tagger.");
	}
}
