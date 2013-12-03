package vn.ptit.anhdinh.scr.model;

import vn.ptit.anhdinh.wordnet.model.Opinion;

public class OpinionAdjective {
	private final String mLemma;
	private final Opinion mOpinion;

	public OpinionAdjective(String lemma, Opinion opinion) {
		mLemma = lemma;
		mOpinion = opinion;
	}

	public String getmLemma() {
		return mLemma;
	}

	public Opinion getmOpinion() {
		return mOpinion;
	}
}
