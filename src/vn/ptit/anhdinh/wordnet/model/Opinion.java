package vn.ptit.anhdinh.wordnet.model;

public enum Opinion {
	POSITIVE("tích cực", 1), //
	NEGATIVE("tiêu cực", -1), //
	UNDEFINED("chưa xác định", 0), //
	NEUTRAL("trung lập", 0);

	private String mName;
	private int mValue;

	private Opinion(String name, int value) {
		mName = name;
		mValue = value;
	}

	public String getmName() {
		return mName;
	}

	public int getmValue() {
		return mValue;
	}

	public static Opinion opposite(Opinion opinion) {
		if (POSITIVE.equals(opinion)) {
			return NEGATIVE;
		}
		if (NEGATIVE.equals(opinion)) {
			return POSITIVE;
		}
		return opinion;
	}

}
