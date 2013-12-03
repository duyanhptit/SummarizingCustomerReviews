package vn.ptit.anhdinh.wordnet.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import vn.ptit.anhdinh.scr.utils.FileUtils;
import vn.ptit.anhdinh.wordnet.model.POS;
import vn.ptit.anhdinh.wordnet.model.RelationType;

public class GetRelationWord {
	private static final String URL_TEMPLATE = "http://tratu.soha.vn/index.php?title=#&dict=vn_vn&action=edit";
	private static final String ID_CONTENT = "wpTextbox1";

	private static final String KEY_GET_DEFINATION = "=====";
	public static final String KEY_DEFINATION = "Định nghĩa";
	public static final String KEY_SYNONYMS = RelationType.SIMILARITY.getmKey();
	public static final String KEY_ANTONYMS = RelationType.ANTONYM.getmKey();

	private static final Map<String, String> mResource = getResource();
	private static final String PATH_RESOURCE = "data/tratuSohaVn.txt";
	private static final String WORD_PROPERTY = "word";
	private static final String CONTENT_PROPERTY = "content";

	private static Map<String, String> getResource() {
		Map<String, String> resource = new TreeMap<String, String>();
		JSONArray jsonArray = FileUtils.ReadJSONFile(PATH_RESOURCE);
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jo = (JSONObject) jsonArray.get(i);
			String word = (String) jo.get(WORD_PROPERTY);
			String content = (String) jo.get(CONTENT_PROPERTY);
			if ("".equals(content)) {
				resource.put(word, null);
			} else {
				resource.put(word, content);
			}
		}
		return resource;
	}

	public static Map<String, List<String>> getRelationWord(String word) {
		String content = getContentFormSoHa(word);
		if (content == null) {
			return null;
		}
		return processContent(content);
	}

	private static void saveResource() {
		JSONArray jsonResource = new JSONArray();
		for (String word : mResource.keySet()) {
			JSONObject jo = new JSONObject();
			jo.put(WORD_PROPERTY, word);
			String content = mResource.get(word) == null ? "" : mResource.get(word);
			jo.put(CONTENT_PROPERTY, content);
			jsonResource.add(jo);
		}
		FileUtils.WriteJSONFile(PATH_RESOURCE, jsonResource);
	}

	private static String getContentFormSoHa(String word) {
		if (mResource.containsKey(word)) {
			return mResource.get(word);
		}
		String url = createURL(word);
		while (true) {
			try {
				Document doc = Jsoup.connect(url).get();
				Element element = doc.getElementById(ID_CONTENT);
				if (element == null) {
					mResource.put(word, null);
					return null;
				}
				mResource.put(word, element.text());
				saveResource();
				return element.text();
			} catch (IOException e) {
				System.out.println("Error get data form URL: " + url);
			}
		}
	}

	public static boolean checkValidWord(String word, POS pos) {
		String content = getContentFormSoHa(word);
		if (content == null) {
			return false;
		}
		String[] lines = content.split("\n");
		for (String line : lines) {
			if (POS.ADJECTIVE.equals(pos) && line.contains("=== Tính từ ===")) {
				return true;
			}
			if (POS.NOUN.equals(pos) && line.contains("=== Danh từ ===")) {
				return true;
			}
		}
		return false;
	}

	private static Map<String, List<String>> processContent(String content) {
		Map<String, List<String>> relationWords = new HashMap<String, List<String>>();
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
		if (strWords.contains("<")) {
			return words;
		}
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
