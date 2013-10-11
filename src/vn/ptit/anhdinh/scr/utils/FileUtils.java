package vn.ptit.anhdinh.scr.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

public class FileUtils {

	public static void WriteFile(String path, List<String> data, boolean append) {
		try {
			FileWriter fileWriter = new FileWriter(path, append);
			PrintWriter out = new PrintWriter(new BufferedWriter(fileWriter));
			for (String stringLine : data) {
				out.println(stringLine);
			}
			out.close();
		} catch (IOException e) {
			System.out.println("Error write file " + e.getMessage());
		}
	}

	public static Document ReadFileXML(String path) throws Exception {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(new File(path));
		document.getDocumentElement().normalize();
		return document;
	}
}
