package vn.ptit.anhdinh.wordnet.neo4j;

import java.util.LinkedList;
import java.util.List;

import vn.ptit.anhdinh.wordnet.model.Opinion;
import vn.ptit.anhdinh.wordnet.model.POS;
import vn.ptit.anhdinh.wordnet.model.RelationType;
import vn.ptit.anhdinh.wordnet.model.Synset;
import vn.ptit.anhdinh.wordnet.model.Word;

public class WordNet {

	public static void main(String[] args) {
		Neo4JConnectionPool neo4JConnectionPool = new Neo4JConnectionPool();
		Neo4JWordNetDAO neo4JWordNetDAO = neo4JConnectionPool.getNeo4JConnection();

		List<Word> words = new LinkedList<Word>();
		words.add(new Word("đẹp", "có hình thức hoặc phẩm chất đem lại sự hứng thú đặc biệt, làm cho người ta thích nhìn ngắm hoặc kính nể"));
		words.add(new Word("xinh", "có hình dáng và những đường nét rất dễ coi, ưa nhìn (thường nói về trẻ em, phụ nữ trẻ)"));
		Synset synsetBeauty = neo4JWordNetDAO.insertSynset(new Synset(words, POS.ADJECTIVE));
		neo4JWordNetDAO.setOpinion(synsetBeauty.getmId(), Opinion.POSITIVE);

		words = new LinkedList<Word>();
		words.add(new Word("xấu", "có hình thức, vẻ ngoài khó coi, gây cảm giác khó chịu, làm cho không muốn nhìn ngắm"));
		Synset synsetUgly = neo4JWordNetDAO.insertSynset(new Synset(words, POS.ADJECTIVE));
		neo4JWordNetDAO.setOpinion(synsetUgly.getmId(), Opinion.POSITIVE);

		neo4JWordNetDAO.createRelationship(synsetBeauty.getmId(), synsetUgly.getmId(), RelationType.ANTONYM);

		neo4JConnectionPool.shutDownDatabase();
	}
}
