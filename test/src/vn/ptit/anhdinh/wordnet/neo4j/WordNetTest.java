package vn.ptit.anhdinh.wordnet.neo4j;

import java.util.LinkedList;
import java.util.List;

import vn.ptit.anhdinh.wordnet.model.POS;
import vn.ptit.anhdinh.wordnet.model.RelationType;
import vn.ptit.anhdinh.wordnet.model.Synset;
import vn.ptit.anhdinh.wordnet.model.Word;

public class WordNetTest {

	public static void main(String[] args) {
		Neo4JConnectionPool neo4JConnectionPool = Neo4JConnectionPool.getInstance();
		WordNetDAO wordNetDAO = neo4JConnectionPool.getNeo4JConnection();

		List<Word> words = new LinkedList<Word>();
		words.add(new Word("đẹp", POS.ADJECTIVE, "có hình thức hoặc phẩm chất đem lại sự hứng thú đặc biệt, làm cho người ta thích nhìn ngắm hoặc kính nể"));
		words.add(new Word("xinh", POS.ADJECTIVE, "có hình dáng và những đường nét rất dễ coi, ưa nhìn (thường nói về trẻ em, phụ nữ trẻ)"));
		Synset synsetBeauty = wordNetDAO.insertSynset(new Synset(words, POS.ADJECTIVE));

		words = new LinkedList<Word>();
		words.add(new Word("xấu", POS.ADJECTIVE, "có hình thức, vẻ ngoài khó coi, gây cảm giác khó chịu, làm cho không muốn nhìn ngắm"));
		Synset synsetUgly = wordNetDAO.insertSynset(new Synset(words, POS.ADJECTIVE));

		wordNetDAO.createRelationship(synsetBeauty.getmId(), synsetUgly.getmId(), RelationType.ANTONYM);

		wordNetDAO.insertSynset(synsetBeauty);

		Synset synset = wordNetDAO.loadSynsetByWord(new Word("đẹp", POS.ADJECTIVE, "có hình thức hoặc phẩm chất đem lại sự hứng thú đặc biệt, làm cho người ta thích nhìn ngắm hoặc kính nể"));
		for (Word word : synset.getmWords()) {
			System.out.println(word.getmLemma());
		}

		neo4JConnectionPool.shutDownDatabase();
	}

	public void BuildClusterFastandSlow() {
		Neo4JConnectionPool neo4JConnectionPool = Neo4JConnectionPool.getInstance();
		WordNetDAO wordNetDAO = neo4JConnectionPool.getNeo4JConnection();

		List<Word> words = new LinkedList<Word>();
		words.add(new Word("nhanh", POS.ADJECTIVE, ""));
		words.add(new Word("mau", POS.ADJECTIVE, ""));
		words.add(new Word("chóng", POS.ADJECTIVE, ""));
		Synset synsetBeauty = wordNetDAO.insertSynset(new Synset(words, POS.ADJECTIVE));

		words = new LinkedList<Word>();
		words.add(new Word("lâu", POS.ADJECTIVE, ""));
		words.add(new Word("lâu la", POS.ADJECTIVE, ""));
		words.add(new Word("chậm", POS.ADJECTIVE, ""));
		Synset synsetUgly = wordNetDAO.insertSynset(new Synset(words, POS.ADJECTIVE));

		wordNetDAO.createRelationship(synsetBeauty.getmId(), synsetUgly.getmId(), RelationType.ANTONYM);

		neo4JConnectionPool.shutDownDatabase();
	}
}
