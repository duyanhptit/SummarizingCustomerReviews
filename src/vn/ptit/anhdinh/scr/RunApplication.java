package vn.ptit.anhdinh.scr;

import vn.hus.nlp.tagger.VietnameseMaxentTagger;

public class RunApplication {

	public static void main(String[] args) {
		String appName = "Zalo";
		// GetComments wpscm = new WPStoreComments();
		// List<String> rawComments = wpscm.getComments(appName);
		//
		// System.out.println("Starting encode comments to compose Unicode...");
		// ComposeUnicodeUtils decomposeUnicode = new ComposeUnicodeUtils();
		// List<String> comments = decomposeUnicode.convertComposeUnicode(rawComments);
		// System.out.println("Ended encode comment to compose Unicode.");
		//
		// System.out.print("Write comments to file: commentsOf" + appName + ".txt... ");
		// FileUtils.WriteFile("data/commentsOf" + appName + ".txt", comments, false);
		// System.out.println("OK");

		System.out.println("Beginning Vietnamese Tagger comments...");
		String inFile = "data/commentsOf" + appName + ".txt";
		String outFile = "data/commentsOf" + appName + ".xml";
		VietnameseMaxentTagger tagger = new VietnameseMaxentTagger();
		tagger.tagFile(inFile, outFile);
		System.out.println("Ended Vietnamese tagger.");
	}
}
