package vn.ptit.anhdinh.wordnet;

import vn.ptit.anhdinh.wordnet.model.Cluster;
import vn.ptit.anhdinh.wordnet.model.Synset;
import vn.ptit.anhdinh.wordnet.neo4j.Neo4JConnectionPool;
import vn.ptit.anhdinh.wordnet.neo4j.WordNetDAO;

public class WordNetAPI {

	public Cluster insertCluster(Cluster cluster) {
		Neo4JConnectionPool neo4jConnectionPool = Neo4JConnectionPool.getInstance();
		WordNetDAO wordNetDAO = neo4jConnectionPool.getNeo4JConnection();
		Synset synset1 = wordNetDAO.insertSynset(cluster.getmSynset1());
		Synset synset2 = wordNetDAO.insertSynset(cluster.getmSynset2());
		boolean insertRelation = false;
		if (synset1 != null && synset2 != null) {
			insertRelation = wordNetDAO.createRelationship(synset1.getmId(), synset2.getmId(), cluster.getmRelationType());
		} else {
			return null;
		}
		if (insertRelation) {
			return cluster;
		}
		return null;
	}

	public static void main(String[] args) {

	}

}
