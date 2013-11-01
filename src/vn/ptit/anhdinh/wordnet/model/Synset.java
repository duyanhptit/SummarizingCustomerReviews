package vn.ptit.anhdinh.wordnet.model;

import java.util.List;

public class Synset {
	private long mId;
	private final List<Word> mWords;
	private final POS mPOS;

	public Synset(long id, List<Word> words, POS pos) {
		mId = id;
		mWords = words;
		mPOS = pos;
	}

	public Synset(List<Word> words, POS pos) {
		mWords = words;
		mPOS = pos;
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

	public void addWord(Word word) {
		mWords.add(word);
	}
}
