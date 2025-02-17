/**
 * 
 */
package vn.hus.nlp.tagger.test;

import vn.hus.nlp.tagger.VietnameseMaxentTagger;

/**
 * @author LE HONG Phuong, phuonglh@gmail.com <br>
 *         Apr 9, 2009, 7:34:28 PM <br>
 *         Test class for the Vietnamese tagger.
 */
public class VietnameseTaggerTest {

	public static void main(String[] args) {
		// test();
		taggerComments();
	}

	public static void test() {
		String data = "data/testvnTagger.txt";
		String[] sentences = vn.hus.nlp.utils.UTF8FileUtility.getLines(data);
		VietnameseMaxentTagger tagger = new VietnameseMaxentTagger();
		for (String sentence : sentences) {
			try {
				System.out.println(tagger.tagText(sentence));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void taggerComments() {
		String inFile = "data/commentsOfZalo.txt";
		String outFile = "data/commentsOfZalo.xml";

		VietnameseMaxentTagger tagger = new VietnameseMaxentTagger();
		tagger.tagFile(inFile, outFile);
	}
}
