package vn.ptit.anhdinh.wordnet;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import vn.ptit.anhdinh.wordnet.utils.GetRelationWord;

public class GetRelationWordTest {

	@Test
	public void TestgetRelationWord() {
		GetRelationWord getRelationWord = new GetRelationWord();
		String test = "đẹp";
		Map<String, List<String>> relationWords = getRelationWord.getRelationWord(test);

		List<String> definations = relationWords.get(GetRelationWord.KEY_DEFINATION);
		List<String> synonyms = relationWords.get(GetRelationWord.KEY_SYNONYMS);
		List<String> antonyms = relationWords.get(GetRelationWord.KEY_ANTONYMS);
		Assert.assertTrue("có hình thức hoặc phẩm chất đem lại sự hứng thú đặc biệt, làm cho người ta thích nhìn ngắm hoặc kính nể".equals(definations.get(0)));
		Assert.assertTrue("có sự hài hoà, tương xứng".equals(definations.get(1)));
		Assert.assertTrue("có cảm giác thích thú".equals(definations.get(2)));
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
