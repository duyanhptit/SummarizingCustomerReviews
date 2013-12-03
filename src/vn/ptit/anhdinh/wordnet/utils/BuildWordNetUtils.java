package vn.ptit.anhdinh.wordnet.utils;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import vn.ptit.anhdinh.scr.utils.FileUtils;
import vn.ptit.anhdinh.wordnet.model.Cluster;
import vn.ptit.anhdinh.wordnet.model.POS;
import vn.ptit.anhdinh.wordnet.model.RelationType;
import vn.ptit.anhdinh.wordnet.model.Synset;
import vn.ptit.anhdinh.wordnet.model.Word;

public class BuildWordNetUtils {

	private static List<String> mAdjective = new LinkedList<String>();
	private static List<String> mSynonym = new LinkedList<String>();
	private static List<String> mAntonym = new LinkedList<String>();

	public static List<String> getAllAdjective(String filePath) throws Exception {
		String inputFile = filePath;
		Document document = FileUtils.ReadFileXML(inputFile);

		XPathFactory xPathFactory = XPathFactory.newInstance();
		XPath xPath = xPathFactory.newXPath();
		XPathExpression xPathExpression = xPath.compile("//w[@pos=\"A\"]");
		NodeList nodeAdjectives = (NodeList) xPathExpression.evaluate(document, XPathConstants.NODESET);
		for (int i = 0; i < nodeAdjectives.getLength(); i++) {
			Element elAdjective = (Element) nodeAdjectives.item(i);
			NodeList nodeValue = elAdjective.getChildNodes();
			String stringValue = nodeValue.item(0).getNodeValue();
			String adjective = stringValue.toLowerCase();
			if (!mAdjective.contains(adjective)) {
				mAdjective.add(adjective);
			}
		}
		return mAdjective;
	}

	private static void findWords(List<String> synonyms, List<String> antonyms, int depth) {
		if (depth > 0) {
			List<String> tempSynonyms = new LinkedList<String>();
			List<String> tempAntonyms = new LinkedList<String>();
			for (String synonym : synonyms) {
				Map<String, List<String>> relationWord = GetRelationWord.getRelationWord(synonym);
				if (relationWord != null) {
					tempSynonyms.addAll(onlyAddNewWord(mSynonym, tempSynonyms, relationWord.get(GetRelationWord.KEY_SYNONYMS)));
					tempAntonyms.addAll(onlyAddNewWord(mAntonym, tempAntonyms, relationWord.get(GetRelationWord.KEY_ANTONYMS)));
				}
			}
			for (String antonym : antonyms) {
				Map<String, List<String>> relationWord = GetRelationWord.getRelationWord(antonym);
				if (relationWord != null) {
					tempSynonyms.addAll(onlyAddNewWord(mSynonym, tempSynonyms, relationWord.get(GetRelationWord.KEY_ANTONYMS)));
					tempAntonyms.addAll(onlyAddNewWord(mAntonym, tempAntonyms, relationWord.get(GetRelationWord.KEY_SYNONYMS)));
				}
			}
			mSynonym.addAll(tempSynonyms);
			mAntonym.addAll(tempAntonyms);
			findWords(tempSynonyms, tempAntonyms, depth - 1);
		}
	}

	private static List<String> onlyAddNewWord(List<String> mWords, List<String> tempWords, List<String> newWords) {
		List<String> words = new LinkedList<String>();
		for (String newWord : newWords) {
			if (!mWords.contains(newWord) && !tempWords.contains(newWord) && !words.contains(newWord)) {
				words.add(newWord);
			}
		}
		return words;
	}

	public static Cluster buildCluster(String keyWord) {
		return buildCluster(keyWord, 3);
	}

	public static Cluster buildCluster(String keyWord, int depth) {
		if (!GetRelationWord.checkValidWord(keyWord, POS.ADJECTIVE)) {
			return null;
		}
		mSynonym.add(keyWord.toLowerCase());
		findWords(mSynonym, mAntonym, depth);
		List<Word> synonymWords = new LinkedList<Word>();
		List<Word> antonymWords = new LinkedList<Word>();
		for (String word : mSynonym) {
			synonymWords.add(createWord(word));
		}
		mSynonym.clear();
		for (String word : mAntonym) {
			antonymWords.add(createWord(word));
		}
		mAntonym.clear();

		Synset synset1 = new Synset(synonymWords, POS.ADJECTIVE);
		Synset synset2 = new Synset(antonymWords, POS.ADJECTIVE);

		return new Cluster(RelationType.ANTONYM, synset1, synset2);
	}

	private static Word createWord(String lemma) {
		Map<String, List<String>> relationWord = GetRelationWord.getRelationWord(lemma);
		if (relationWord != null) {
			List<String> definations = relationWord.get(GetRelationWord.KEY_DEFINATION);
			String defination = definations.isEmpty() ? "" : definations.get(0);
			return new Word(lemma, POS.ADJECTIVE, defination);
		}
		return new Word(lemma, POS.ADJECTIVE, "");
	}
}
