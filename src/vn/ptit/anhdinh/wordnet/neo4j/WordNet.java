package vn.ptit.anhdinh.wordnet.neo4j;

import vn.ptit.anhdinh.wordnet.model.Word;

public class WordNet {

	public static void main(String[] args) {
		Neo4JConnectionPool neo4JConnectionPool = new Neo4JConnectionPool();
		Neo4JWordNetDAO neo4JWordNetDAO = neo4JConnectionPool.getNeo4JConnection();

		Word word = new Word("đẹp");
		neo4JWordNetDAO.createOneNode(word);
		word = new Word("xấu");
		neo4JWordNetDAO.createOneNode(word);
		word = new Word("tốt");
		neo4JWordNetDAO.createOneNode(word);

		neo4JConnectionPool.shutDownDatabase();
	}

}
