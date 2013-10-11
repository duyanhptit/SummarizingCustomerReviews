package vn.ptit.anhdinh.scr.data;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import vn.ptit.anhdinh.scr.utils.ComposeUnicodeUtils;

public class WPStoreComments implements GetComments {

	private static final String RESOURCE_URL_ZALO = "http://www.windowsphone.com/vi-vn/store/app/zalo/ca5b631e-1cb9-4e39-b03e-9499443afe73";
	private static final String CLASS_COMMENTS = "reviewText";
	private static final String ID_PAGE_NEXT = "moreReviews";

	@Override
	public List<String> getComments(String appName) {
		List<String> comments = new LinkedList<String>();
		String url = RESOURCE_URL_ZALO;
		ComposeUnicodeUtils decomposeUnicode = new ComposeUnicodeUtils();
		System.out.println("Getting comments of app: " + appName + " ...");
		while (true) {
			try {
				Document doc = Jsoup.connect(url).get();
				Elements elComments = doc.getElementsByClass(CLASS_COMMENTS);
				for (Element elComment : elComments) {
					byte[] byteString = elComment.text().getBytes("UTF-8");
					String comment = new String(byteString, "UTF-8");
					comments.add(decomposeUnicode.getComposedUnicode(comment));
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
