package vn.ptit.anhdinh.wordnet.model;

public class Word {
	private long mId;
	private String mLemma;
	private String mDefination;

	public Word(long id, String lemma, String defination) {
		mId = id;
		mLemma = lemma;
		mDefination = defination;
	}

	public Word(String lemma, String defination) {
		mLemma = lemma;
		mDefination = defination;
	}

	public long getmId() {
		return mId;
	}

	public String getmLemma() {
		return mLemma;
	}

	public String getmDefination() {
		return mDefination;
	}
}
