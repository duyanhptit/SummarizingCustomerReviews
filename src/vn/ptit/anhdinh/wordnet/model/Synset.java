package vn.ptit.anhdinh.wordnet.model;

import java.util.List;

public class Synset {
	private long mId;
	private List<Word> mWords;
	private POS mPOS;
	private Opinion mOpinion;

	public Synset(long id, List<Word> words, POS pos, Opinion opinion) {
		mId = id;
		mWords = words;
		mPOS = pos;
		mOpinion = opinion;
	}

	public Synset(List<Word> words, POS pos) {
		mWords = words;
		mPOS = pos;
		mOpinion = Opinion.UNDEFINED;
	}

	public long getmId() {
		return mId;
	}

	public List<Word> getmWords() {
		return mWords;
	}

	public POS getmPOS() {
		return mPOS;
	}

	public Opinion getmOpinion() {
		return mOpinion;
	}

	public void addWord(Word word) {
		mWords.add(word);
	}
}
