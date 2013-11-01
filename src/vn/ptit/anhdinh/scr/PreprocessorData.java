package vn.ptit.anhdinh.scr;

import java.util.LinkedList;
import java.util.List;

import vn.ptit.anhdinh.scr.utils.ComposeUnicodeUtils;

public class PreprocessorData {
	private List<String> mReviews;
	private final String[] mSpecialCharacters = { "@@", "<", ">", "\"", "&" };

	public PreprocessorData(List<String> reviews) {
		mReviews = reviews;
	}

	public List<String> getReviews() {
		return mReviews;
	}

	public void convertComposeUnicode() {
		System.out.print("Encoding reviews to compose Unicode... ");
		ComposeUnicodeUtils composeUnicodeUtils = new ComposeUnicodeUtils();
		for (int i = 0; i < mReviews.size(); i++) {
			String composeStr = composeUnicodeUtils.getComposedUnicode(mReviews.get(i));
			mReviews.set(i, composeStr);
		}
		System.out.println("OK");
	}

	public void deleteSpecialCharacter() {
		System.out.print("Deleting specital character of reiviews... ");
		for (int i = 0; i < mReviews.size(); i++) {
			String review = mReviews.get(i);
			for (String specChar : mSpecialCharacters) {
				review = review.replace(specChar, "");
			}
			mReviews.set(i, review);
		}
		System.out.println("OK");
	}

	public void removeReviewsNotVietnamese() {
		System.out.print("Remove reviews isn't Vietnamese... ");
		List<String> vietnameseReviews = new LinkedList<String>();
		ComposeUnicodeUtils composeUnicodeUtils = new ComposeUnicodeUtils();
		for (String review : mReviews) {
			if (composeUnicodeUtils.checkVietnameseReviews(review)) {
				vietnameseReviews.add(review);
			}
		}
		mReviews = vietnameseReviews;
		System.out.println("OK");
	}
}
