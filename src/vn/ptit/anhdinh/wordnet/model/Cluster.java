package vn.ptit.anhdinh.wordnet.model;

public class Cluster {
	private long mId;
	private RelationType mRelationType;
	private Synset mSynset1;
	private Synset mSynset2;

	public Cluster(long id, RelationType relationType, Synset synset1, Synset synset2) {
		mId = id;
		mRelationType = relationType;
		mSynset1 = synset1;
		mSynset2 = synset2;
	}

	public Cluster(RelationType relationType, Synset synset1, Synset synset2) {
		mRelationType = relationType;
		mSynset1 = synset1;
		mSynset2 = synset2;
	}

	public long getmId() {
		return mId;
	}

	public RelationType getmRelationType() {
		return mRelationType;
	}

	public Synset getmSynset1() {
		return mSynset1;
	}

	public Synset getmSynset2() {
		return mSynset2;
	}
}
