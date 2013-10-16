package vn.ptit.anhdinh.wordnet.model;

public class Relation {
	private long mId;
	private RelationType mRelationType;
	private Synset mSourceSynset;
	private Synset mTargetSynset;

	public Relation(long id, RelationType relationType, Synset sourceSynset, Synset targetSynset) {
		mId = id;
		mRelationType = relationType;
		mSourceSynset = sourceSynset;
		mTargetSynset = targetSynset;
	}

	public Relation(RelationType relationType, Synset sourceSynset, Synset targetSynset) {
		mRelationType = relationType;
		mSourceSynset = sourceSynset;
		mTargetSynset = targetSynset;
	}

	public long getmId() {
		return mId;
	}

	public RelationType getmRelationType() {
		return mRelationType;
	}

	public Synset getmSourceSynset() {
		return mSourceSynset;
	}

	public Synset getmTargetSynset() {
		return mTargetSynset;
	}
}
