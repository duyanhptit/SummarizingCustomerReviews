package vn.ptit.anhdinh.scr;

import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONArray;

public class Test {

	public static void main(String[] args) {
		testJSONArraytoList();
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
}
