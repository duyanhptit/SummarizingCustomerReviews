package vn.ptit.anhdinh.wordnet.model;

public class Word {
	private long mId;
	private String mLemma;
	private POS mPOS;
	private String mDefination;

	public Word(long id, String lemma, POS pos, String defination) {
		mId = id;
		mLemma = lemma;
		mPOS = pos;
		mDefination = defination;
	}

	public Word(String lemma, POS pos, String defination) {
		mLemma = lemma;
		mPOS = pos;
		mDefination = defination;
	}

	public long getmId() {
		return mId;
	}

	public String getmLemma() {
		return mLemma;
	}

	public POS getmPOS() {
		return mPOS;
	}

	public String getmDefination() {
		return mDefination;
	}
}
