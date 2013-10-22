package vn.ptit.anhdinh.wordnet;

import vn.ptit.anhdinh.wordnet.model.Cluster;
import vn.ptit.anhdinh.wordnet.model.Synset;
import vn.ptit.anhdinh.wordnet.neo4j.Neo4JConnectionPool;
import vn.ptit.anhdinh.wordnet.neo4j.WordNetDAO;

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

	public void shutDown() {
		mNeo4JConnectionPool.shutDownDatabase();
	}

	public static void main(String[] args) {

	}

}
