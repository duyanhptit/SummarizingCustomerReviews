package vn.ptit.anhdinh.wordnet.neo4j;

import java.util.LinkedList;
import java.util.List;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.RelationshipIndex;

import vn.ptit.anhdinh.wordnet.model.POS;
import vn.ptit.anhdinh.wordnet.model.RelationType;
import vn.ptit.anhdinh.wordnet.model.Synset;
import vn.ptit.anhdinh.wordnet.model.Word;

public class Neo4JWordNetDAO implements WordNetDAO {

	private final GraphDatabaseService mGraphDatabaseService;
	private final ExecutionEngine mExecutionEngine;
	private ExecutionResult mExecutionResult;
	private final Index<Node> mWordIndex;
	private final Index<Node> mSynsetIndex;
	private final RelationshipIndex mAntonymIndex;

	private static final String SYNSET_KEY = "synsets";
	private static final String WORD_KEY = "words";
	private static final String ANTONYM_KEY = "antonym";

	private static final String TYPE = "type";
	private static final String LEMMA = "lemma";
	private static final String POS_LABEL = "pos";
	// private static final String OPINION = "opinion";
	private static final String DEFINATION = "defination";
	private static final String SYNSET_ID = "synsetId";
	private static final String TAGET_SYNSET_ID = "tagetSynsetId";

	private static final String SYNSET = "synset";
	private static final String WORD = "word";

	public Neo4JWordNetDAO(GraphDatabaseService graphDatabaseService) {
		mGraphDatabaseService = graphDatabaseService;
		mExecutionEngine = new ExecutionEngine(mGraphDatabaseService);
		mWordIndex = mGraphDatabaseService.index().forNodes(WORD_KEY);
		mSynsetIndex = mGraphDatabaseService.index().forNodes(SYNSET_KEY);
		mAntonymIndex = mGraphDatabaseService.index().forRelationships(ANTONYM_KEY);
	}

	private Transaction getTransaction() {
		return mGraphDatabaseService.beginTx();
	}

	@Override
	public Word insertWord(long synsetId, Word word) {
		if (wordIsExist(word)) {
			System.out.println("Word: " + word.getmLemma() + " is exist.");
			return null;
		}
		Transaction transaction = getTransaction();
		try {
			Node headSynset = mGraphDatabaseService.getNodeById(synsetId);
			Word insertedWord = insertNode(headSynset, word);
			transaction.success();
			return insertedWord;
		} catch (Exception e) {
			System.out.println("Error when insert word is exist: " + e.toString());
			return null;
		} finally {
			transaction.finish();
		}
	}

	@Override
	public Synset insertSynset(Synset synset) {
		List<Word> words = synset.getmWords();
		if (words == null | words.isEmpty()) {
			System.out.println("Synset hasn't word.");
			return null;
		}
		if (synsetIsExist(synset)) {
			System.out.println("Synset is exist!");
			return insertSynsetIsExist(synset);
		}
		return insertSynsetNotExist(synset);
	}

	private Synset insertSynsetIsExist(Synset synset) {
		Transaction transaction = getTransaction();
		try {
			List<Word> words = synset.getmWords();
			Node headSynset = null;
			List<Word> addWords = new LinkedList<Word>();
			for (Word word : words) {
				if (wordIsExist(word) && headSynset == null) {
					Node nodeWord = mWordIndex.get(LEMMA, word.getmLemma()).getSingle();
					long idSynset = (long) nodeWord.getProperty(SYNSET_ID);
					headSynset = mGraphDatabaseService.getNodeById(idSynset);
				}
				if (!wordIsExist(word)) {
					addWords.add(word);
				}
			}
			if (!addWords.isEmpty()) {
				for (Word word : addWords) {
					insertNode(headSynset, word);
				}
			}
			transaction.success();
			return new Synset(headSynset.getId(), synset.getmWords(), synset.getmPOS());
		} catch (Exception e) {
			System.out.println("Error when insert synset is exist: " + e.toString());
			return null;
		} finally {
			transaction.finish();
		}
	}

	private Synset insertSynsetNotExist(Synset synset) {
		Transaction transaction = getTransaction();
		try {
			List<Word> words = synset.getmWords();
			Node headSynset = insertHeadSynset(synset);
			List<Word> insertedWords = new LinkedList<Word>();
			for (Word word : words) {
				insertedWords.add(insertNode(headSynset, word));
			}
			transaction.success();
			return new Synset(headSynset.getId(), insertedWords, POS.valueOf((String) headSynset.getProperty(POS_LABEL)));
		} catch (Exception e) {
			System.out.println("Error when insert synset isn't exist: " + e.toString());
			return null;
		} finally {
			transaction.finish();
		}
	}

	// @Override
	// public boolean setOpinion(long synsetId, Opinion opinion) {
	// Transaction transaction = getTransaction();
	// try {
	// Node synsetNode = mGraphDatabaseService.getNodeById(synsetId);
	// synsetNode.setProperty(OPINION, opinion.name());
	// mSynsetIndex.add(synsetNode, OPINION, synsetNode.getProperty(OPINION));
	// transaction.success();
	// System.out.println("Set opinion for nodeId(" + String.valueOf(synsetId) + ") is " + opinion.name());
	// return true;
	// } catch (Exception e) {
	// System.out.println("Error when set opinion: " + e.toString());
	// return false;
	// } finally {
	// transaction.finish();
	// }
	// }

	@Override
	public boolean createRelationship(long synsetId1, long synsetId2, RelationType relationType) {
		Transaction transaction = getTransaction();
		if (RelationType.ANTONYM.equals(relationType) && (relationIsExist(synsetId1) || relationIsExist(synsetId2))) {
			return true;
		}
		try {
			Node synsetNode1 = mGraphDatabaseService.getNodeById(synsetId1);
			Node synsetNode2 = mGraphDatabaseService.getNodeById(synsetId2);
			Relationship relation1 = synsetNode1.createRelationshipTo(synsetNode2, relationType);
			Relationship relation2 = synsetNode2.createRelationshipTo(synsetNode1, relationType);
			mAntonymIndex.add(relation1, TAGET_SYNSET_ID, synsetId2);
			mAntonymIndex.add(relation2, TAGET_SYNSET_ID, synsetId1);
			transaction.success();
			System.out.println("Create relationship for nodeId(" + String.valueOf(synsetId1) + ") and nodeId(" + String.valueOf(synsetId2) + ") is " + relationType.name());
			return true;
		} catch (Exception e) {
			System.out.println("Error when create relationship isn't exist: " + e.toString());
			return false;
		} finally {
			transaction.finish();
		}
	}

	private boolean relationIsExist(long tagetSynsetId) {
		Relationship relation = mAntonymIndex.get(TAGET_SYNSET_ID, tagetSynsetId).getSingle();
		if (relation != null) {
			return true;
		}
		return false;
	}

	// @Override
	// public Opinion getOpinion(Word word) {
	// if (!wordIsExist(word)) {
	// return null;
	// }
	// String query = "START word=node:words(lemma=\"" + word.getmLemma() + "\") " //
	// + "MATCH word-[:" + RelationType.SIMILARITY.name() + "]->synset " //
	// + "WHERE synset.pos = \"" + POS.ADJECTIVE.name() + "\" " //
	// + "RETURN synset";
	// mExecutionResult = mExecutionEngine.execute(query);
	// Node synsetNode = (Node) mExecutionResult.columnAs("synset").next();
	// String opinion = (String) synsetNode.getProperty(OPINION);
	// return Opinion.valueOf(opinion);
	// }

	@Override
	public Synset loadSynsetByWord(Word word) {
		if (!wordIsExist(word)) {
			return null;
		}
		Node nodeWord = mWordIndex.get(LEMMA, word.getmLemma()).getSingle();
		return loadSynsetById(Long.parseLong(nodeWord.getProperty(SYNSET_ID).toString()));
	}

	@Override
	public Synset loadSynsetById(long id) {
		Node headWord = mGraphDatabaseService.getNodeById(id);
		List<Word> words = new LinkedList<Word>();
		for (Node node : mWordIndex.get(SYNSET_ID, headWord.getId())) {
			words.add(new Word(node.getId(), (String) node.getProperty(LEMMA), POS.valueOf((String) node.getProperty(POS_LABEL)), (String) node.getProperty(DEFINATION)));
		}
		return new Synset(headWord.getId(), words, POS.valueOf((String) headWord.getProperty(POS_LABEL)));
	}

	// @Override
	// public List<Synset> loadSynsetByOpinion(Opinion opinion) {
	// List<Synset> synsets = new LinkedList<Synset>();
	// for (Node node : mSynsetIndex.get(OPINION, opinion.name())) {
	// synsets.add(loadSynsetById(node.getId()));
	// }
	// return synsets;
	// }

	private boolean wordIsExist(Word word) {
		Node nodeWord = mWordIndex.get(LEMMA, word.getmLemma()).getSingle();
		if (nodeWord == null) {
			return false;
		}
		if (word.getmPOS().name().equals(nodeWord.getProperty(POS_LABEL))) {
			return true;
		}
		return false;
	}

	private boolean synsetIsExist(Synset synset) {
		List<Word> words = synset.getmWords();
		for (Word word : words) {
			if (wordIsExist(word)) {
				return true;
			}
		}
		return false;
	}

	private Node insertHeadSynset(Synset synset) {
		Node headSynset = mGraphDatabaseService.createNode();
		headSynset.setProperty(TYPE, SYNSET);
		headSynset.setProperty(POS_LABEL, synset.getmPOS().name());

		Node node0 = mGraphDatabaseService.getNodeById(0);
		headSynset.createRelationshipTo(node0, RelationType.SYNSET_OF);
		System.out.println("Added head synset: \"" + String.valueOf(headSynset.getId()) + "\"");
		return headSynset;
	}

	private Word insertNode(Node headSynset, Word word) {
		Node nodeWord = mGraphDatabaseService.createNode();
		nodeWord.setProperty(TYPE, WORD);
		nodeWord.setProperty(SYNSET_ID, headSynset.getId());
		nodeWord.setProperty(LEMMA, word.getmLemma());
		nodeWord.setProperty(POS_LABEL, word.getmPOS().name());
		nodeWord.setProperty(DEFINATION, word.getmDefination());
		nodeWord.createRelationshipTo(headSynset, RelationType.SIMILARITY);

		mWordIndex.add(nodeWord, LEMMA, nodeWord.getProperty(LEMMA));
		mWordIndex.add(nodeWord, SYNSET_ID, nodeWord.getProperty(SYNSET_ID));
		System.out.println("Added word: \"" + word.getmLemma() + "\"");
		return new Word(nodeWord.getId(), (String) nodeWord.getProperty(LEMMA), POS.valueOf((String) nodeWord.getProperty(POS_LABEL)), (String) nodeWord.getProperty(DEFINATION));
	}
}
