package vn.edu.ptit.wordnet.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class TGDDComments implements GetComments {
	private static final String RESOURCE_URL_TEMPLATE = "http://www.thegioididong.com/comment/Services/CommentSvc.asmx/GetCommentsByFilterPage?lDetailId=%s&strCMTCategoryId=2&intWatchBy=-1&intOrderBy=1&intCache=1&intPage=%s";

	private static final String KEY_CONTENT = "Content";

	public static void main(String args[]) {
		TGDDComments getComments = new TGDDComments();
		getComments.getComments("55872");
	}

	@Override
	public List<String> getComments(String productID) {
		System.out.println("Starting get comments of product id: " + productID + " form The Gioi Di Dong...");
		List<String> comments = new LinkedList<String>();
		int page = 0;
		while (true) {
			String url = String.format(RESOURCE_URL_TEMPLATE, productID, String.valueOf(page));
			String stringData = getDataFromURL(url);
			JSONArray jsonData = convertStringToJSON(stringData);
			List<String> subComments = getListComments(jsonData);
			if (subComments.isEmpty()) {
				break; // Stop when end of list comments
			}
			FileUtils.WriteFile("data/commetsOfProductID" + productID + ".txt", subComments, true);
			System.out.printf("Completed get comments of page %d has: %d comments\n", page, subComments.size());
			comments.addAll(subComments);
			page++;
		}
		System.out.println("Completed get comments of product id: " + productID + ". Total comments: " + String.valueOf(comments.size()));
		return comments;
	}

	private String getDataFromURL(String url) {
		try {
			URL resourceURL = new URL(url);
			URLConnection urlConnection = resourceURL.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

			StringBuilder stringData = new StringBuilder();
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				stringData.append(inputLine);
			}
			in.close();
			return stringData.toString();
		} catch (IOException e) {
			System.out.println("Error get data form URL: " + url);
		}
		return null;
	}

	private List<String> getListComments(JSONArray jsonData) {
		List<String> comments = new LinkedList<String>();
		for (int i = 0; i < jsonData.size(); i++) {
			JSONObject json = (JSONObject) jsonData.get(i);
			String comment = (String) json.get(KEY_CONTENT);
			comments.add(comment);
		}
		return comments;
	}

	private JSONArray convertStringToJSON(String data) {
		JSONParser jsonParser = new JSONParser();
		JSONArray jsonData = null;
		try {
			jsonData = (JSONArray) jsonParser.parse(data);
		} catch (ParseException e) {
			System.out.println("Error parser String to JSONObject" + e.getMessage());
		}
		return jsonData;
	}
}
