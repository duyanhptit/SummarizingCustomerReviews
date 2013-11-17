package vn.ptit.anhdinh.scr;

import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONArray;

import vn.ptit.anhdinh.scr.utils.ComposeUnicodeUtils;
import vn.ptit.anhdinh.scr.utils.FileUtils;

public class Test {

	public static void main(String[] args) {
		tagReviewVN();
	}

	private static void testJSONArraytoList() {
		JSONArray ja = new JSONArray();
		ja.add("Một");
		ja.add("Hai");
		ja.add("Ba");
		List<String> strs = new LinkedList<String>();
		for (int i = 0; i < ja.size(); i++) {
			strs.add((String) ja.get(i));
		}
		for (String str : strs) {
			System.out.println(str);
		}
	}

	private static void tagReviewVN() {
		List<String> reviews = FileUtils.ReadFile("data/zalo/rawReviewsOfZalo.txt");
		List<String> reviewTaged = new LinkedList<String>();
		ComposeUnicodeUtils utils = new ComposeUnicodeUtils();
		for (String review : reviews) {
			if (utils.checkVietnameseReviews(review)) {
				review = review + " <vn>";
			}
			reviewTaged.add(review);
		}
		// FileUtils.WriteFile("data/zalo/rawReviewsOfZalo - tagTV.txt", reviewTaged, false);
	}
}
