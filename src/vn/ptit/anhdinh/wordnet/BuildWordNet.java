package vn.ptit.anhdinh.wordnet;

import java.util.List;

import vn.ptit.anhdinh.wordnet.model.Cluster;
import vn.ptit.anhdinh.wordnet.model.Synset;
import vn.ptit.anhdinh.wordnet.model.Word;
import vn.ptit.anhdinh.wordnet.utils.BuildWordNetUtils;

public class BuildWordNet {
	private static int mSum = 0;

	public static void main(String[] args) throws Exception {
		WordNetAPI wordNetAPI = new WordNetAPI();
		List<String> adjectives = BuildWordNetUtils.getAllAdjective("data/commentsOfZalo.xml");
		for (String adjective : adjectives) {
			System.out.println(adjective);
		}
		for (int i = 0; i < adjectives.size(); i++) {
			Cluster cluster = BuildWordNetUtils.buildCluster(adjectives.get(i), 3);
			// wordNetAPI.insertCluster(cluster);
			System.out.println("(" + String.valueOf(i + 1) + "/" + String.valueOf(adjectives.size()) + " )");
			System.out.println("ĐỒNG NGHĨA:");
			printSynset(cluster.getmSynset1());
			System.out.println("TRÁI NGHĨA:");
			printSynset(cluster.getmSynset2());
			System.out.println("==============================================================================");
		}
		System.out.println("Summarizing has: " + String.valueOf(mSum) + " words.");
		wordNetAPI.shutDown();
		// testBuildCluster();
	}

	public static void testBuildCluster() {
		Cluster cluster = BuildWordNetUtils.buildCluster("đẹp", 5);
		System.out.println("ĐỒNG NGHĨA:");
		printSynset(cluster.getmSynset1());
		System.out.println("TRÁI NGHĨA:");
		printSynset(cluster.getmSynset2());
	}

	public static void printSynset(Synset synset) {
		List<Word> words = synset.getmWords();
		mSum += words.size();
		for (Word word : words) {
			System.out.print(word.getmLemma() + ", ");
		}
		System.out.println("");
	}

}
