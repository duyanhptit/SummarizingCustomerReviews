package vn.ptit.anhdinh.scr.data;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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
}
