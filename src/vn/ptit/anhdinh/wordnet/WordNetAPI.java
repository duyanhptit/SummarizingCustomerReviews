package vn.ptit.anhdinh.wordnet;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import vn.ptit.anhdinh.wordnet.model.Cluster;
import vn.ptit.anhdinh.wordnet.model.POS;
import vn.ptit.anhdinh.wordnet.model.RelationType;
import vn.ptit.anhdinh.wordnet.model.Synset;
import vn.ptit.anhdinh.wordnet.model.Word;
import vn.ptit.anhdinh.wordnet.neo4j.Neo4JConnectionPool;
import vn.ptit.anhdinh.wordnet.neo4j.WordNetDAO;
import vn.ptit.anhdinh.wordnet.utils.BuildWordNetUtils;

public class WordNetAPI {

	private final Neo4JConnectionPool mNeo4JConnectionPool;
	private final WordNetDAO mWordNetDAO;

	public WordNetAPI() {
		mNeo4JConnectionPool = Neo4JConnectionPool.getInstance();
		mWordNetDAO = mNeo4JConnectionPool.getNeo4JConnection();
	}

	public Cluster insertCluster(Cluster cluster) {
		Synset synset1 = mWordNetDAO.insertSynset(cluster.getmSynset1());
		Synset synset2 = mWordNetDAO.insertSynset(cluster.getmSynset2());
		boolean insertRelation = false;
		if (synset1 != null && synset2 != null) {
			insertRelation = mWordNetDAO.createRelationship(synset1.getmId(), synset2.getmId(), cluster.getmRelationType());
		} else {
			return null;
		}
		if (insertRelation) {
			return cluster;
		}
		return null;
	}

	public void insertToWordNet(String word) {
		Cluster cluster = BuildWordNetUtils.buildCluster(word);
		insertCluster(cluster);
	}

	public Map<String, List<String>> getSynonymAndAntonym(String lemma) {
		Word word = new Word(lemma.toLowerCase(), POS.ADJECTIVE, "");
		Map<String, List<String>> mapResult = new HashMap<String, List<String>>();
		List<Word> synonym = mWordNetDAO.loadSynonym(word);
		mapResult.put(RelationType.SIMILARITY.getmKey(), getLemmaFromListWord(synonym));
		List<Word> antonym = mWordNetDAO.loadAntonym(word);
		mapResult.put(RelationType.ANTONYM.getmKey(), getLemmaFromListWord(antonym));
		return mapResult;
	}

	private List<String> getLemmaFromListWord(List<Word> words) {
		List<String> lemmas = new LinkedList<String>();
		for (Word word : words) {
			lemmas.add(word.getmLemma());
		}
		return lemmas;
	}

	public void shutDown() {
		mNeo4JConnectionPool.shutDownDatabase();
	}

	public static void main(String[] args) {
		Neo4JConnectionPool mNeo4JConnectionPool = Neo4JConnectionPool.getInstance();
		WordNetDAO mWordNetDAO = mNeo4JConnectionPool.getNeo4JConnection();
	}

}
