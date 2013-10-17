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
		words.add(new Word("đẹp", POS.ADJECTIVE, "có hình thức hoặc phẩm chất đem lại sự hứng thú đặc biệt, làm cho người ta thích nhìn ngắm hoặc kính nể"));
		words.add(new Word("xinh", POS.ADJECTIVE, "có hình dáng và những đường nét rất dễ coi, ưa nhìn (thường nói về trẻ em, phụ nữ trẻ)"));
		Synset synsetBeauty = neo4JWordNetDAO.insertSynset(new Synset(words, POS.ADJECTIVE));
		neo4JWordNetDAO.setOpinion(synsetBeauty.getmId(), Opinion.POSITIVE);

		words = new LinkedList<Word>();
		words.add(new Word("xấu", POS.ADJECTIVE, "có hình thức, vẻ ngoài khó coi, gây cảm giác khó chịu, làm cho không muốn nhìn ngắm"));
		Synset synsetUgly = neo4JWordNetDAO.insertSynset(new Synset(words, POS.ADJECTIVE));
		neo4JWordNetDAO.setOpinion(synsetUgly.getmId(), Opinion.POSITIVE);

		neo4JWordNetDAO.createRelationship(synsetBeauty.getmId(), synsetUgly.getmId(), RelationType.ANTONYM);

		Synset synset = neo4JWordNetDAO.loadSynset(new Word("đẹp", POS.ADJECTIVE, "có hình thức hoặc phẩm chất đem lại sự hứng thú đặc biệt, làm cho người ta thích nhìn ngắm hoặc kính nể"));
		for (Word word : synset.getmWords()) {
			System.out.println(word.getmLemma());
		}

		neo4JConnectionPool.shutDownDatabase();

		// System.out.println(test());
	}

	private static String test() {
		try {
			String str = "abc";
			int num = Integer.parseInt(str);
			System.out.println(num);
			return str;
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			System.out.println("finally");
		}
		return "end of function";
	}
}
