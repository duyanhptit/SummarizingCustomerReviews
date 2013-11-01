package vn.ptit.anhdinh.wordnet.neo4j;

import vn.ptit.anhdinh.wordnet.model.RelationType;
import vn.ptit.anhdinh.wordnet.model.Synset;
import vn.ptit.anhdinh.wordnet.model.Word;

public interface WordNetDAO {

	public Word insertWord(long synsetId, Word word);

	public Synset insertSynset(Synset synset);

	public boolean createRelationship(long synsetId1, long synsetId2, RelationType relationType);

	public Synset loadSynsetByWord(Word word);

	public Synset loadSynsetById(long id);

}
