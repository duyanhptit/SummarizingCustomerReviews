package vn.ptit.anhdinh.wordnet.model;

import org.neo4j.graphdb.RelationshipType;

public enum RelationType implements RelationshipType {
	SYNONYM("Đồng nghĩa"), //
	ANTONYM("Trái nghĩa"), //
	HYPERNYM(""), //
	HYPONYM(""), //
	ENTAILMENT("");

	private String mKey;

	private RelationType(String key) {
		mKey = key;
	}

	public String getmKey() {
		return mKey;
	}
}
