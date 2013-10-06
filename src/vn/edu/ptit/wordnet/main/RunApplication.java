package vn.edu.ptit.wordnet.main;

import java.util.List;

import vn.edu.ptit.wordnet.data.FileUtils;
import vn.edu.ptit.wordnet.data.GetComments;
import vn.edu.ptit.wordnet.data.WPStoreComments;

public class RunApplication {

	public static void main(String[] args) {
		String appName = "Zalo";
		GetComments wpscm = new WPStoreComments();
		List<String> comments = wpscm.getComments(appName);
		FileUtils.WriteFile("data/commentsOf" + appName + ".txt", comments, false);
	}
}
