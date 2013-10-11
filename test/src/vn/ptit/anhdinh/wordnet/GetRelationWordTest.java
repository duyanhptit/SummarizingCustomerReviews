package vn.ptit.anhdinh.wordnet;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class GetRelationWordTest {

	@Test
	public void TestgetRelationWord() {
		GetRelationWord getRelationWord = new GetRelationWord();
		String test = "đẹp";
		Map<String, List<String>> relationWords = getRelationWord.getRelationWord(test);

		List<String> synonyms = relationWords.get(GetRelationWord.KEY_SYNONYMS);
		List<String> antonyms = relationWords.get(GetRelationWord.KEY_ANTONYMS);
		Assert.assertTrue("xinh".equals(synonyms.get(0)));
		Assert.assertTrue("xấu".equals(antonyms.get(0)));

		test = "buồn";
		relationWords = getRelationWord.getRelationWord(test);
		synonyms = relationWords.get(GetRelationWord.KEY_SYNONYMS);
		antonyms = relationWords.get(GetRelationWord.KEY_ANTONYMS);
		Assert.assertTrue("rầu".equals(synonyms.get(0)));
		Assert.assertTrue("sầu".equals(synonyms.get(1)));
		Assert.assertTrue("mừng".equals(antonyms.get(0)));
		Assert.assertTrue("vui".equals(antonyms.get(1)));

		test = "long lanh";
		relationWords = getRelationWord.getRelationWord(test);
		synonyms = relationWords.get(GetRelationWord.KEY_SYNONYMS);
		antonyms = relationWords.get(GetRelationWord.KEY_ANTONYMS);
		Assert.assertTrue(synonyms.isEmpty());
		Assert.assertTrue(antonyms.isEmpty());
	}
}
