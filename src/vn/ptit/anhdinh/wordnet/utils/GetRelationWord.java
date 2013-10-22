package vn.ptit.anhdinh.wordnet.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import vn.ptit.anhdinh.wordnet.model.RelationType;

public class GetRelationWord {
	private static final String URL_TEMPLATE = "http://tratu.soha.vn/index.php?title=#&dict=vn_vn&action=edit";
	private static final String ID_CONTENT = "wpTextbox1";

	private static final String KEY_GET_DEFINATION = "=====";
	public static final String KEY_DEFINATION = "Định nghĩa";
	public static final String KEY_SYNONYMS = RelationType.SIMILARITY.getmKey();
	public static final String KEY_ANTONYMS = RelationType.ANTONYM.getmKey();

	public static Map<String, List<String>> getRelationWord(String word) {
		String url = createURL(word);
		while (true) {
			try {
				Document doc = Jsoup.connect(url).get();
				Element element = doc.getElementById(ID_CONTENT);
				if (element == null) {
					return null;
				}
				String content = element.text();
				return processContent(content);
			} catch (IOException e) {
				System.out.println("Error get data form URL: " + url);
			}
		}
	}

	private static Map<String, List<String>> processContent(String content) {
		Map<String, List<String>> relationWords = new HashMap<>();
		List<String> definations = new LinkedList<String>();
		List<String> synonyms = new LinkedList<String>();
		List<String> antonyms = new LinkedList<String>();
		boolean isAdjective = false;
		String[] lines = content.split("\n");
		for (String line : lines) {
			if (line.contains("=== Tính từ ===")) {
				isAdjective = true;
			}
			if (line.contains("=== Phụ từ ===")) {
				break;
			}
			if (line.contains(KEY_GET_DEFINATION)) {
				String[] result = line.split(KEY_GET_DEFINATION);
				if (result.length >= 1) {
					definations.add(result[1].trim());
				}
			}
			if (line.contains(KEY_SYNONYMS)) {
				synonyms.addAll(getWordInLine(line));
			}
			if (line.contains(KEY_ANTONYMS)) {
				antonyms.addAll(getWordInLine(line));
			}
		}
		if (!isAdjective) {
			return null;
		}
		relationWords.put(KEY_DEFINATION, definations);
		relationWords.put(KEY_SYNONYMS, synonyms);
		relationWords.put(KEY_ANTONYMS, antonyms);
		return relationWords;
	}

	private static List<String> getWordInLine(String line) {
		List<String> words = new LinkedList<String>();
		if (!line.contains("</font>")) {
			return words;
		}
		String strWords = line.split("</font>")[1];
		String[] arrayStr = strWords.split(",");
		for (String str : arrayStr) {
			words.add(str.trim());
		}
		return words;
	}

	private static String createURL(String word) {
		word = word.replace(" ", "_");
		String url = URL_TEMPLATE.replace("#", word);
		return url;
	}
}
