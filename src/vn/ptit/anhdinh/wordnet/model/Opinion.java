package vn.ptit.anhdinh.wordnet.model;

public enum Opinion {
	POSITIVE("tích cực"), //
	NEGATIVE("tiêu cực"), //
	UNDEFINED("chưa xác định"), //
	UNKNOWN("không xác định");

	private String mName;

	private Opinion(String name) {
		mName = name;
	}

	public String getmName() {
		return mName;
	}

}
