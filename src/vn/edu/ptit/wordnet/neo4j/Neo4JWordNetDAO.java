package vn.edu.ptit.wordnet.neo4j;

import java.util.List;
import java.util.Map;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.UniqueFactory;

import vn.edu.ptit.wordnet.model.Word;

public class Neo4JWordNetDAO implements WordNetDAO {

	private GraphDatabaseService mGraphDatabaseService = null;
	// private final ExecutionEngine mExecutionEngine;
	private UniqueFactory<Node> mUniqueFactory = null;

	private static final String NAME = "name";
	private static final String NAME_INDEX = "words";

	public Neo4JWordNetDAO(GraphDatabaseService graphDatabaseService) {
		mGraphDatabaseService = graphDatabaseService;
		// mExecutionEngine = new ExecutionEngine(mGraphDatabaseService);
		mUniqueFactory = getUniqueFactory();
	}

	private UniqueFactory<Node> getUniqueFactory() {
		Transaction transaction = getTransaction();
		try {
			UniqueFactory<Node> uniqueFactory = new UniqueFactory.UniqueNodeFactory(mGraphDatabaseService, NAME_INDEX) {

				@Override
				protected void initialize(Node created, Map<String, Object> properties) {
					created.setProperty(NAME, properties.get(NAME));
				}
			};
			return uniqueFactory;
		} finally {
			transaction.finish();
		}
	}

	private Transaction getTransaction() {
		return mGraphDatabaseService.beginTx();
	}

	@Override
	public Word createOneNode(Word word) {
		Transaction transaction = getTransaction();
		try {
			Node node = mUniqueFactory.getOrCreate(NAME, word.getmName());
			transaction.success();
			System.out.println("Added word: \"" + word.getmName() + "\"");
			return new Word(node.getProperty(NAME).toString());
		} finally {
			transaction.finish();
		}
	}

	@Override
	public List<Word> createMutiNode(List<Word> words) {
		return null;
	}

}
