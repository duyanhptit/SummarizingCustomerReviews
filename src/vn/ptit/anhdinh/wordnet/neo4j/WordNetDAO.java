package vn.ptit.anhdinh.wordnet.neo4j;

import vn.ptit.anhdinh.wordnet.model.Opinion;
import vn.ptit.anhdinh.wordnet.model.POS;
import vn.ptit.anhdinh.wordnet.model.RelationType;
import vn.ptit.anhdinh.wordnet.model.Synset;
import vn.ptit.anhdinh.wordnet.model.Word;

public interface WordNetDAO {

	public Word insertWord(Word word);

	public Synset insertSynset(Synset synset);

	public boolean setOpinion(long synsetId, Opinion opinion);

	public boolean createRelationship(long synsetId1, long synsetId2, RelationType relationType);

	public Synset loadSynset(POS pos, String lemma);

}
