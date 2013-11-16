package vn.ptit.anhdinh.scr.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;

public class FileUtils {

	public static void WriteFile(String path, List<String> data, boolean append) {
		try {
			File file = new File(path);
			file.getParentFile().mkdirs();
			FileWriter fileWriter = new FileWriter(file, append);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			PrintWriter out = new PrintWriter(bufferedWriter);
			for (String stringLine : data) {
				out.println(stringLine);
			}
			out.close();
		} catch (IOException e) {
			System.out.println("Error write file. " + e.getMessage());
		}
	}

	public static List<String> ReadFile(String path) {
		List<String> strs = new LinkedList<String>();
		try {
			FileReader fileReader = new FileReader(path);
			BufferedReader in = new BufferedReader(fileReader);
			String line = null;
			while ((line = in.readLine()) != null) {
				strs.add(line);
			}
		} catch (IOException e) {
			System.out.println("Error read file. " + e.getMessage());
		}
		return strs;
	}

	public static void WriteJSONFile(String path, JSONArray jsonArray) {
		String content = jsonArray.toJSONString();
		try {
			File file = new File(path);
			file.getParentFile().mkdirs();
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(content);
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			System.out.println("Error write file. " + e.getMessage());
		}
	}

	public static JSONArray ReadJSONFile(String path) {
		JSONArray jsonArray = new JSONArray();
		JSONParser jsonParser = new JSONParser();
		try {
			FileReader fileReader = new FileReader(path);
			jsonArray = (JSONArray) jsonParser.parse(fileReader);
		} catch (IOException e) {
			System.out.println("Error read JSON file. " + e.getMessage());
		} catch (ParseException ex) {
			System.out.println("Error parser file to JSONObject. " + ex.getMessage());
		}
		return jsonArray;
	}

	public static Document ReadFileXML(String path) throws Exception {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(new File(path));
		document.getDocumentElement().normalize();
		return document;
	}
}
