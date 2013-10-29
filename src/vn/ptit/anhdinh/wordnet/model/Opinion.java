package vn.ptit.anhdinh.wordnet.model;

public enum Opinion {
	POSITIVE("tích cực"), //
	NEGATIVE("tiêu cực"), //
	UNDEFINED("chưa xác định"), //
	NEUTRAL("trung lập");

	private String mName;

	private Opinion(String name) {
		mName = name;
	}

	public String getmName() {
		return mName;
	}

}
