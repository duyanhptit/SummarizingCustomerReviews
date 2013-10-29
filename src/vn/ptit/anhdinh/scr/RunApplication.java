package vn.ptit.anhdinh.scr;

import java.util.LinkedList;
import java.util.List;

import vn.hus.nlp.tagger.VietnameseMaxentTagger;
import vn.ptit.anhdinh.scr.data.GetComments;
import vn.ptit.anhdinh.scr.data.WPStoreComments;
import vn.ptit.anhdinh.scr.utils.FileUtils;
import vn.ptit.anhdinh.wordnet.WordNetAPI;
import vn.ptit.anhdinh.wordnet.model.Opinion;
import edu.stanford.nlp.ling.WordTag;

public class RunApplication {

	private static VietnameseMaxentTagger mVnTagger = new VietnameseMaxentTagger();
	private static WordNetAPI mWordNetAPI = new WordNetAPI();

	public static void main(String[] args) throws Exception {
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

		SummarizingCustomerReviews(comments);
		// System.out.println("Beginning Vietnamese Tagger comments...");
		// String inFile = "data/commentsOf" + appName + ".txt";
		// String outFile = "data/commentsOf" + appName + ".xml";
		// mVnTagger.tagFile(inFile, outFile);
		// System.out.println("Ended Vietnamese tagger.");
	}

	public static void SummarizingCustomerReviews(List<String> comments) throws Exception {
		System.out.println("Starting Summarizing Customer Reviews...");
		List<List<WordTag>> commentsTagged = taggerPOS(comments);
		// List<String> topNouns = getTopNoun(commentsTagged, 0.7);

	}

	public static List<List<WordTag>> taggerPOS(List<String> comments) {
		List<List<WordTag>> commentsTagged = new LinkedList<List<WordTag>>();
		for (String comment : comments) {
			commentsTagged.add(mVnTagger.tagText2(comment));
		}
		return commentsTagged;
	}

	public static Opinion getOpinionOfComment(String comment) {
		int resultOpinion = 0;
		List<WordTag> wordTags = mVnTagger.tagText2(comment);
		for (WordTag wordTag : wordTags) {
			if ("A".equals(wordTag.tag())) {
				Opinion opinion = mWordNetAPI.getOpinion(wordTag.word());
				if (Opinion.NEGATIVE.equals(opinion)) {
					resultOpinion--;
				}
				if (Opinion.POSITIVE.equals(opinion)) {
					resultOpinion++;
				}
			}
		}
		if (resultOpinion < 0) {
			return Opinion.NEGATIVE;
		}
		if (resultOpinion > 0) {
			return Opinion.POSITIVE;
		}
		return Opinion.NEUTRAL;
	}

	public static List<String> getTopNoun(List<List<WordTag>> commentsTagged, float percent) {
		for (int i = 0; i < commentsTagged.size(); i++) {
			List<WordTag> wordTags = commentsTagged.get(i);

		}
		return null;
	}
}
