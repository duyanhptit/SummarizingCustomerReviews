package vn.ptit.anhdinh.wordnet.neo4j;

import java.util.List;

import vn.ptit.anhdinh.wordnet.model.Opinion;
import vn.ptit.anhdinh.wordnet.model.RelationType;
import vn.ptit.anhdinh.wordnet.model.Synset;
import vn.ptit.anhdinh.wordnet.model.Word;

public interface WordNetDAO {

	public Word insertWord(long synsetId, Word word);

	public Synset insertSynset(Synset synset);

	public boolean setOpinion(long synsetId, Opinion opinion);

	public boolean createRelationship(long synsetId1, long synsetId2, RelationType relationType);

	public Opinion getOpinion(Word word);

	public Synset loadSynsetByWord(Word word);

	public Synset loadSynsetById(long id);

	public List<Synset> loadSynsetByOpinion(Opinion opinion);

}
