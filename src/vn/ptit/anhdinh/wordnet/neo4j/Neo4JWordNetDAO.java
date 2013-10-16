package vn.ptit.anhdinh.wordnet.neo4j;

import java.util.LinkedList;
import java.util.List;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;

import vn.ptit.anhdinh.wordnet.model.Opinion;
import vn.ptit.anhdinh.wordnet.model.POS;
import vn.ptit.anhdinh.wordnet.model.RelationType;
import vn.ptit.anhdinh.wordnet.model.Synset;
import vn.ptit.anhdinh.wordnet.model.Word;

public class Neo4JWordNetDAO implements WordNetDAO {

	private GraphDatabaseService mGraphDatabaseService;
	private ExecutionEngine mExecutionEngine;
	private Index<Node> wordIndex;
	private Index<Node> synsetIndex;
	// private final UniqueFactory<Node> mUniqueFactory = null;

	private static final String SYNSET_KEY = "synsets";
	private static final String WORD_KEY = "words";

	private static final String TYPE = "type";
	private static final String LEMMA = "lemma";
	private static final String POS_LABEL = "pos";
	private static final String OPINION = "opinion";
	private static final String DEFINATION = "defination";
	private static final String SYNSET_ID = "synsetId";

	private static final String SYNSET = "synset";
	private static final String WORD = "word";

	public Neo4JWordNetDAO(GraphDatabaseService graphDatabaseService) {
		mGraphDatabaseService = graphDatabaseService;
		mExecutionEngine = new ExecutionEngine(mGraphDatabaseService);
		synsetIndex = mGraphDatabaseService.index().forNodes(SYNSET_KEY);
		wordIndex = mGraphDatabaseService.index().forNodes(WORD_KEY);
		// mUniqueFactory = getUniqueFactory();
	}

	// private UniqueFactory<Node> getUniqueFactory() {
	// Transaction transaction = getTransaction();
	// try {
	// UniqueFactory<Node> uniqueFactory = new UniqueFactory.UniqueNodeFactory(mGraphDatabaseService, NAME_INDEX) {
	//
	// @Override
	// protected void initialize(Node created, Map<String, Object> properties) {
	// created.setProperty(LEMMA, properties.get(LEMMA));
	// }
	// };
	// return uniqueFactory;
	// } finally {
	// transaction.finish();
	// }
	// }

	private Transaction getTransaction() {
		return mGraphDatabaseService.beginTx();
	}

	@Override
	public Word insertWord(Word word) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Synset insertSynset(Synset synset) {
		Transaction transaction = getTransaction();
		try {
			Node headSynset = insertHeadSynset(synset);
			List<Word> words = synset.getmWords();
			if (words == null | words.isEmpty()) {
				transaction.success();
				return new Synset(headSynset.getId(), words, POS.valueOf((String) headSynset.getProperty(POS_LABEL)), Opinion.valueOf((String) headSynset.getProperty(OPINION)));
			}
			List<Word> addedWords = new LinkedList<Word>();
			for (Word word : words) {
				addedWords.add(insertNode(headSynset, word));
			}
			transaction.success();
			return new Synset(headSynset.getId(), addedWords, POS.valueOf((String) headSynset.getProperty(POS_LABEL)), Opinion.valueOf((String) headSynset.getProperty(OPINION)));
		} finally {
			transaction.finish();
		}
	}

	@Override
	public boolean setOpinion(long synsetId, Opinion opinion) {
		Transaction transaction = getTransaction();
		try {
			Node synsetNode = mGraphDatabaseService.getNodeById(synsetId);
			synsetNode.setProperty(OPINION, opinion.name());
			synsetIndex.add(synsetNode, OPINION, synsetNode.getProperty(OPINION));
			transaction.success();
			System.out.println("Set opinion for nodeId(" + String.valueOf(synsetId) + ") is " + opinion.name());
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			transaction.finish();
		}
	}

	@Override
	public boolean createRelationship(long synsetId1, long synsetId2, RelationType relationType) {
		Transaction transaction = getTransaction();
		try {
			Node synsetNode1 = mGraphDatabaseService.getNodeById(synsetId1);
			Node synsetNode2 = mGraphDatabaseService.getNodeById(synsetId2);
			synsetNode1.createRelationshipTo(synsetNode2, relationType);
			synsetNode2.createRelationshipTo(synsetNode1, relationType);
			transaction.success();
			System.out.println("Create relationship for nodeId(" + String.valueOf(synsetId1) + ") and nodeId(" + String.valueOf(synsetId2) + ") is " + relationType.name());
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			transaction.finish();
		}
	}

	@Override
	public Synset loadSynset(POS pos, String lemma) {
		// TODO Auto-generated method stub
		return null;
	}

	private Node insertHeadSynset(Synset synset) {
		Node headSynset = mGraphDatabaseService.createNode();
		headSynset.setProperty(TYPE, SYNSET);
		headSynset.setProperty(POS_LABEL, synset.getmPOS().name());
		if (synset.getmOpinion() != null) {
			headSynset.setProperty(OPINION, synset.getmOpinion().name());
		} else {
			headSynset.setProperty(OPINION, Opinion.UNDEFINED.name());
		}
		synsetIndex.add(headSynset, POS_LABEL, headSynset.getProperty(POS_LABEL));
		System.out.println("Added head synset: \"" + String.valueOf(headSynset.getId()) + "\"");
		return headSynset;
	}

	private Word insertNode(Node headSynset, Word word) {
		Node nodeWord = mGraphDatabaseService.createNode();
		nodeWord.setProperty(TYPE, WORD);
		nodeWord.setProperty(SYNSET_ID, headSynset.getId());
		nodeWord.setProperty(LEMMA, word.getmLemma());
		nodeWord.setProperty(DEFINATION, word.getmDefination());
		nodeWord.createRelationshipTo(headSynset, RelationType.SYNONYM);

		wordIndex.add(nodeWord, LEMMA, nodeWord.getProperty(LEMMA));
		System.out.println("Added word: \"" + word.getmLemma() + "\"");
		return new Word(nodeWord.getId(), (String) nodeWord.getProperty(LEMMA), (String) nodeWord.getProperty(DEFINATION));
	}
}
