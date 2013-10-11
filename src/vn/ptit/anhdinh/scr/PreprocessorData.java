package vn.ptit.anhdinh.scr;

import java.util.List;

import vn.ptit.anhdinh.scr.utils.ComposeUnicodeUtils;

public class PreprocessorData {
	private final List<String> mComments;
	private final String[] mSpecialCharacters = { "@@", "<", ">", "\"", "&" };

	public PreprocessorData(List<String> comments) {
		mComments = comments;
	}

	public List<String> getComments() {
		return mComments;
	}

	public void convertComposeUnicode() {
		System.out.print("Encoding comments to compose Unicode... ");
		ComposeUnicodeUtils composeUnicodeUtils = new ComposeUnicodeUtils();
		for (int i = 0; i < mComments.size(); i++) {
			String composeStr = composeUnicodeUtils.getComposedUnicode(mComments.get(i));
			mComments.set(i, composeStr);
		}
		System.out.println("OK");
	}

	public void deleteSpecialCharacter() {
		System.out.print("Deleting specital character of comments... ");
		for (int i = 0; i < mComments.size(); i++) {
			String comment = mComments.get(i);
			for (String specChar : mSpecialCharacters) {
				comment = comment.replace(specChar, "");
			}
			mComments.set(i, comment);
		}
		System.out.println("OK");
	}
}
