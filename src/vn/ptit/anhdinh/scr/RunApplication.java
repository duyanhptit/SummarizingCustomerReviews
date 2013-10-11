package vn.ptit.anhdinh.scr;

import java.util.List;

import vn.hus.nlp.tagger.VietnameseMaxentTagger;
import vn.ptit.anhdinh.scr.data.GetComments;
import vn.ptit.anhdinh.scr.data.WPStoreComments;
import vn.ptit.anhdinh.scr.utils.FileUtils;

public class RunApplication {

	public static void main(String[] args) {
		String appName = "Zalo";
		GetComments wpscm = new WPStoreComments();
		List<String> rawComments = wpscm.getComments(appName);

		PreprocessorData preprocessorData = new PreprocessorData(rawComments);
		preprocessorData.convertComposeUnicode();
		preprocessorData.deleteSpecialCharacter();
		List<String> comments = preprocessorData.getComments();

		System.out.print("Write comments to file: commentsOf" + appName + ".txt... ");
		FileUtils.WriteFile("data/commentsOf" + appName + ".txt", comments, false);
		System.out.println("OK");

		System.out.println("Beginning Vietnamese Tagger comments...");
		String inFile = "data/commentsOf" + appName + ".txt";
		String outFile = "data/commentsOf" + appName + ".xml";
		VietnameseMaxentTagger tagger = new VietnameseMaxentTagger();
		tagger.tagFile(inFile, outFile);
		System.out.println("Ended Vietnamese tagger.");
	}
}
