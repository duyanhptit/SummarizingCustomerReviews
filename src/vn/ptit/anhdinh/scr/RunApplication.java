package vn.ptit.anhdinh.scr;

import java.util.List;

import vn.ptit.anhdinh.scr.data.FileUtils;
import vn.ptit.anhdinh.scr.data.GetComments;
import vn.ptit.anhdinh.scr.data.WPStoreComments;

public class RunApplication {

	public static void main(String[] args) {
		String appName = "Zalo";
		GetComments wpscm = new WPStoreComments();
		List<String> comments = wpscm.getComments(appName);
		FileUtils.WriteFile("data/commentsOf" + appName + ".txt", comments, false);
	}
}
