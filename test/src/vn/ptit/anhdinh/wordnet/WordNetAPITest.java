package vn.ptit.anhdinh.wordnet;

import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import vn.ptit.anhdinh.wordnet.model.RelationType;

public class WordNetAPITest {
	private static WordNetAPI mWordNetAPI;

	@BeforeClass
	public static void startTest() {
		mWordNetAPI = new WordNetAPI();
	}

	@AfterClass
	public static void finishTest() {
		mWordNetAPI.shutDown();
	}

	@Test
	public void TestGetSynonymAndAntonym() {
		Map<String, List<String>> mapResult = mWordNetAPI.getSynonymAndAntonym("đẹp");
		String synonymKey = RelationType.SIMILARITY.getmKey();
		String antonymKey = RelationType.ANTONYM.getmKey();

		Assert.assertTrue(mapResult.get(synonymKey).contains("xinh"));
		Assert.assertTrue(mapResult.get(antonymKey).contains("xấu"));

		System.out.print(synonymKey + ": ");
		printList(mapResult.get(synonymKey));
		System.out.print(antonymKey + ": ");
		printList(mapResult.get(antonymKey));
	}

	public void printList(List<String> words) {
		for (String word : words) {
			System.out.print(word + ", ");
		}
		System.out.println("");
	}
}
