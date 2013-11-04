package vn.ptit.anhdinh.scr;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import vn.ptit.anhdinh.scr.utils.FileUtils;
import vn.ptit.anhdinh.wordnet.WordNetAPI;
import vn.ptit.anhdinh.wordnet.model.Cluster;
import vn.ptit.anhdinh.wordnet.model.Opinion;
import vn.ptit.anhdinh.wordnet.model.POS;
import vn.ptit.anhdinh.wordnet.model.RelationType;
import vn.ptit.anhdinh.wordnet.utils.BuildWordNetUtils;
import vn.ptit.anhdinh.wordnet.utils.GetRelationWord;

public class AdjectiveOrientationIdentification {
	private Map<String, Opinion> mSeedList = new TreeMap<String, Opinion>();
	private WordNetAPI mWordNetAPI;
	private static final String PATH_SEED_LIST = "data/seedList.txt";

	private static final String LEMMA_KEY = "lemma";
	private static final String OPINION_KEY = "opinion";

	public AdjectiveOrientationIdentification() {
		mSeedList = loadSeedList(PATH_SEED_LIST);
	}

	private Map<String, Opinion> loadSeedList(String path) {
		Map<String, Opinion> seedList = new TreeMap<String, Opinion>();
		JSONArray jsonArray = FileUtils.ReadJSONFile(path);
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject jsonObject = (JSONObject) jsonArray.get(i);
			String lemma = (String) jsonObject.get(LEMMA_KEY);
			Opinion opinion = Opinion.valueOf((String) jsonObject.get(OPINION_KEY));
			seedList.put(lemma, opinion);
		}
		return seedList;
	}

	public Map<String, Opinion> OrientationPrediction(List<String> adjectives) {
		mWordNetAPI = new WordNetAPI();
		List<String> validAdjectives = removeInvalidAdjective(adjectives);
		int sizeBefore = mSeedList.size();
		int sizeAfter = 0;
		while (sizeBefore != sizeAfter) {
			sizeBefore = mSeedList.size();
			OrientationSearch(validAdjectives);
			sizeAfter = mSeedList.size();
		}
		saveSeedList();
		mWordNetAPI.shutDown();
		return loadOpinionAdjectives(validAdjectives);
	}

	private List<String> removeInvalidAdjective(List<String> adjectives) {
		List<String> validAdjective = new LinkedList<String>();
		for (String adjective : adjectives) {
			if (GetRelationWord.checkValidWord(adjective, POS.ADJECTIVE)) {
				validAdjective.add(adjective);
			}
		}
		return validAdjective;
	}

	private Map<String, Opinion> loadOpinionAdjectives(List<String> validAdjectives) {
		Map<String, Opinion> opinionAdjectives = new TreeMap<String, Opinion>();
		for (String adjective : validAdjectives) {
			opinionAdjectives.put(adjective, mSeedList.get(adjective));
		}
		return opinionAdjectives;
	}

	private void OrientationSearch(List<String> adjectives) {
		for (String adjective : adjectives) {
			Map<String, List<String>> synonymAndAntonym = mWordNetAPI.getSynonymAndAntonym(adjective);
			List<String> synonym = synonymAndAntonym.get(RelationType.SIMILARITY.getmKey());
			List<String> antonym = synonymAndAntonym.get(RelationType.ANTONYM.getmKey());
			if (synonym.isEmpty() && antonym.isEmpty()) {
				buildClusterAndInsertToWordNet(adjective);
				System.out.println(adjective);
				mSeedList.put(adjective.toLowerCase(), Opinion.UNDEFINED);
			}
			String word;
			if ((word = checkHasItemInSeeList(synonym)) != null) {
				mSeedList.put(adjective.toLowerCase(), mSeedList.get(word));
			} else if ((word = checkHasItemInSeeList(antonym)) != null) {
				mSeedList.put(adjective.toLowerCase(), oppositeOponion(mSeedList.get(word)));
			}
		}
	}

	private void buildClusterAndInsertToWordNet(String word) {
		Cluster cluster = BuildWordNetUtils.buildCluster(word);
		mWordNetAPI.insertCluster(cluster);
	}

	private String checkHasItemInSeeList(List<String> words) {
		for (String word : words) {
			if (mSeedList.containsKey(word)) {
				return word;
			}
		}
		return null;
	}

	private Opinion oppositeOponion(Opinion opinion) {
		if (Opinion.POSITIVE.equals(opinion)) {
			return Opinion.NEGATIVE;
		} else if (Opinion.NEGATIVE.equals(opinion)) {
			return Opinion.POSITIVE;
		}
		return opinion;
	}

	private void saveSeedList() {
		JSONArray jsonArray = new JSONArray();
		for (String lemma : mSeedList.keySet()) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put(LEMMA_KEY, lemma);
			jsonObject.put(OPINION_KEY, mSeedList.get(lemma).name());
			jsonArray.add(jsonObject);
		}
		FileUtils.WriteJSONFile(PATH_SEED_LIST, jsonArray);
	}

	// public static void main(String[] args) {
	// AdjectiveOrientationIdentification AOI = new AdjectiveOrientationIdentification();
	// mWordNetAPI = new WordNetAPI();
	// Map<String, List<String>> synonymAndAntonym = mWordNetAPI.getSynonymAndAntonym("trời ơi");
	// List<String> synonym = synonymAndAntonym.get(RelationType.SIMILARITY.getmKey());
	// List<String> antonym = synonymAndAntonym.get(RelationType.ANTONYM.getmKey());
	// mWordNetAPI.shutDown();
	// }
}
