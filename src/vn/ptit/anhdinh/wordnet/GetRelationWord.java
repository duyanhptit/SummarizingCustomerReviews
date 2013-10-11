package vn.ptit.anhdinh.wordnet;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class GetRelationWord {
	private static final String URL_TEMPLATE = "http://tratu.soha.vn/index.php?title=#&dict=vn_vn&action=edit";
	private static final String ID_CONTENT = "wpTextbox1";

	public static final String KEY_SYNONYMS = "Đồng nghĩa";
	public static final String KEY_ANTONYMS = "Trái nghĩa";

	public Map<String, List<String>> getRelationWord(String word) {
		String url = createURL(word);
		try {
			Document doc = Jsoup.connect(url).get();
			Element element = doc.getElementById(ID_CONTENT);
			String content = element.text();
			return processContent(content);
		} catch (IOException e) {
			System.out.println("Error get data form URL: " + url);
		}
		return null;
	}

	private Map<String, List<String>> processContent(String content) {
		Map<String, List<String>> relationWords = new HashMap<>();
		List<String> synonyms = new LinkedList<String>();
		List<String> antonyms = new LinkedList<String>();
		String[] lines = content.split("\n");
		for (String line : lines) {
			if (line.contains(KEY_SYNONYMS)) {
				synonyms.addAll(getWordInLine(line));
			}
			if (line.contains(KEY_ANTONYMS)) {
				antonyms.addAll(getWordInLine(line));
			}
		}
		relationWords.put(KEY_SYNONYMS, synonyms);
		relationWords.put(KEY_ANTONYMS, antonyms);
		return relationWords;
	}

	private List<String> getWordInLine(String line) {
		List<String> words = new LinkedList<String>();
		String strWords = line.split("</font>")[1];
		strWords = strWords.replace(" ", "");
		String[] arrayStr = strWords.split(",");
		words.addAll(Arrays.asList(arrayStr));
		return words;
	}

	private String createURL(String word) {
		word = word.replace(" ", "_");
		String url = URL_TEMPLATE.replace("#", word);
		return url;
	}
}
