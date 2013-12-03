package vn.ptit.anhdinh.scr.model;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class SummaryFeature {
	private final String mName;
	private final List<String> mPositiveReviews;
	private final List<String> mNegativeReviews;

	public SummaryFeature(String name, List<String> positiveReiviews, List<String> negativeReviews) {
		mName = name;
		mPositiveReviews = positiveReiviews;
		mNegativeReviews = negativeReviews;
	}

	public String getmName() {
		return mName;
	}

	public List<String> getmPositiveReviews() {
		return mPositiveReviews;
	}

	public List<String> getmNegativeReviews() {
		return mNegativeReviews;
	}

	public void addPositiveReview(String review) {
		mPositiveReviews.add(review);
	}

	public void addNegativeReview(String review) {
		mNegativeReviews.add(review);
	}

	public JSONObject getJSONValue() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("feature", mName);
		jsonObject.put("positive", convertToJSONArray(mPositiveReviews));
		jsonObject.put("negative", convertToJSONArray(mNegativeReviews));
		return jsonObject;
	}

	private JSONArray convertToJSONArray(List<String> reviews) {
		JSONArray jsonArray = new JSONArray();
		for (String review : reviews) {
			jsonArray.add(review);
		}
		return jsonArray;
	}
}
