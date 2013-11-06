package vn.ptit.anhdinh.scr;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import vn.ptit.anhdinh.wordnet.model.POS;
import vn.ptit.anhdinh.wordnet.utils.GetRelationWord;
import edu.stanford.nlp.ling.WordTag;

public class FeatureExtraction {

	private final List<List<WordTag>> mReviewsTagged;
	private final Map<String, Integer> mNounOccurrences = new TreeMap<String, Integer>();

	public FeatureExtraction(List<List<WordTag>> reviewsTagged) {
		mReviewsTagged = reviewsTagged;
		computingFrequenceNoun();
	}

	public Map<String, Integer> getNounOccurrences() {
		return mNounOccurrences;
	}

	private void computingFrequenceNoun() {
		for (List<WordTag> reviewTagged : mReviewsTagged) {
			List<String> nouns = new LinkedList<String>();
			for (WordTag wordTag : reviewTagged) {
				String word = wordTag.word();
				if ("N".equals(wordTag.tag()) && !nouns.contains(word)) {
					nouns.add(word);
				}
			}
			updateOccurrences(nouns);
		}
	}

	private void updateOccurrences(List<String> nouns) {
		for (String noun : nouns) {
			Integer oldCount = mNounOccurrences.get(noun);
			if (oldCount == null) {
				oldCount = 0;
			}
			mNounOccurrences.put(noun, oldCount + 1);
		}
	}

	public List<String> getFrequentFeature(double percent) {
		List<String> topNouns = new LinkedList<String>();
		int numOfReviews = mReviewsTagged.size();
		for (String noun : mNounOccurrences.keySet()) {
			double probability = (double) mNounOccurrences.get(noun) / numOfReviews;
			if (probability >= percent) {
				topNouns.add(noun);
			}
		}
		return removeInvalidWord(topNouns);
	}

	private List<String> removeInvalidWord(List<String> words) {
		List<String> validWords = new LinkedList<String>();
		for (String word : words) {
			if (GetRelationWord.checkValidWord(word, POS.NOUN)) {
				validWords.add(word);
			}
		}
		return validWords;
	}
}
