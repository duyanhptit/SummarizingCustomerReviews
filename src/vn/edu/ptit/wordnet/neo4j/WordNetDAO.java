package vn.edu.ptit.wordnet.neo4j;

import java.util.List;

import vn.edu.ptit.wordnet.model.Word;

public interface WordNetDAO {

	public Word createOneNode(Word word);

	public List<Word> createMutiNode(List<Word> words);

}
