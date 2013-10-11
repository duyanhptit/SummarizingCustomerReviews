package vn.ptit.anhdinh.wordnet.neo4j;

import java.util.List;

import vn.ptit.anhdinh.wordnet.model.Word;

public interface WordNetDAO {

	public Word createOneNode(Word word);

	public List<Word> createMutiNode(List<Word> words);

}
