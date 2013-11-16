package vn.ptit.anhdinh.scr.data;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WPStoreReviews implements GetReviews {

	private static final String CLASS_COMMENTS = "reviewText";
	private static final String ID_PAGE_NEXT = "moreReviews";
	private static final Map<String, String> mURLApp = new HashMap<String, String>();

	public WPStoreReviews() {
		mURLApp.put("Zalo", "http://www.windowsphone.com/vi-vn/store/app/zalo/ca5b631e-1cb9-4e39-b03e-9499443afe73");
		mURLApp.put("Facebook", "http://www.windowsphone.com/vi-vn/store/app/facebook/82a23635-5bd9-df11-a844-00237de2db9e");
		mURLApp.put("ZingMP3", "http://www.windowsphone.com/vi-vn/store/app/zing-mp3/a99f4688-480e-4c46-9071-1e96c6138395");
	}

	@Override
	public List<String> getReviews(String appName) {
		List<String> comments = new LinkedList<String>();
		String url = mURLApp.get(appName);
		System.out.println("Getting comments of app: " + appName + " ...");
		while (true) {
			try {
				Document doc = Jsoup.connect(url).get();
				Elements elComments = doc.getElementsByClass(CLASS_COMMENTS);
				for (Element elComment : elComments) {
					// byte[] byteString = elComment.text().getBytes("UTF-8");
					// String comment = new String(byteString, "UTF-8");
					comments.add(elComment.text());
				}
				Element nextPage = doc.getElementById(ID_PAGE_NEXT);
				if (nextPage == null) {
					break; // out of comments
				}
				url = nextPage.attr("href");
				System.out.println("Completed get " + String.valueOf(elComments.size()) + " comments");
			} catch (IOException e) {
				System.out.println("Error get data form URL: " + url);
			}
		}
		System.out.println("Finish get comments of app: " + appName + " has: " + String.valueOf(comments.size()) + " comments.");
		return comments;
	}
}
