package vn.ptit.anhdinh.wordnet.utils;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

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

	public static List<String> mAdjectives = new LinkedList<String>();
	private static Queue<String> mStackSynonym = new LinkedList<String>();
	private static Queue<String> mStackAntonym = new LinkedList<String>();

	public static List<String> getAllAdjective() throws Exception {
		String inputFile = "data/commentsOfZalo.xml";
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
			if (!mAdjectives.contains(adjective)) {
				mAdjectives.add(adjective);
			}
		}
		return mAdjectives;
	}

	public static Cluster buildCluster(String keyWord, int depth) {
		List<Word> synonym = new LinkedList<Word>();
		List<Word> antonym = new LinkedList<Word>();

		int depthSynonym = depth;
		int depthAntonym = depth - 1;
		mStackSynonym.add(keyWord);
		while (!mStackSynonym.isEmpty() || !mStackAntonym.isEmpty()) {
			if (!mStackSynonym.isEmpty()) {
				String lemma = mStackSynonym.poll();
				Map<String, List<String>> relationWord = GetRelationWord.getRelationWord(lemma);
				if (relationWord != null) {
					List<String> definations = relationWord.get(GetRelationWord.KEY_DEFINATION);
					String defination = definations.isEmpty() ? "" : definations.get(0);
					synonym.add(new Word(lemma, POS.ADJECTIVE, defination));
					if (depthSynonym > 0) {
						addToStackSynonym(relationWord.get(GetRelationWord.KEY_SYNONYMS), synonym);
						addToStackAntonym(relationWord.get(GetRelationWord.KEY_ANTONYMS), antonym);
						depthSynonym--;
					}
				}
			}
			if (!mStackAntonym.isEmpty()) {
				String lemma = mStackAntonym.poll();
				Map<String, List<String>> relationWord = GetRelationWord.getRelationWord(lemma);
				if (relationWord != null) {
					List<String> definations = relationWord.get(GetRelationWord.KEY_DEFINATION);
					String defination = definations.isEmpty() ? "" : definations.get(0);
					antonym.add(new Word(lemma, POS.ADJECTIVE, defination));
					if (depthAntonym > 0) {
						addToStackAntonym(relationWord.get(GetRelationWord.KEY_SYNONYMS), antonym);
						addToStackSynonym(relationWord.get(GetRelationWord.KEY_ANTONYMS), synonym);
						depthAntonym--;
					}
				}
			}
		}

		Synset synset1 = new Synset(synonym, POS.ADJECTIVE);
		Synset synset2 = new Synset(antonym, POS.ADJECTIVE);
		return new Cluster(RelationType.ANTONYM, synset1, synset2);
	}

	private static void addToStackSynonym(List<String> words, List<Word> synonym) {
		for (String word : words) {
			if (!mStackSynonym.contains(word) && !checkContains(synonym, word)) {
				mStackSynonym.add(word);
			}
		}
	}

	private static void addToStackAntonym(List<String> words, List<Word> antonym) {
		for (String word : words) {
			if (!mStackAntonym.contains(word) && !checkContains(antonym, word)) {
				mStackAntonym.add(word);
			}
		}
	}

	private static boolean checkContains(List<Word> words, String lemma) {
		for (Word word : words) {
			if (lemma.equals(word.getmLemma())) {
				return true;
			}
		}
		return false;
	}
}
