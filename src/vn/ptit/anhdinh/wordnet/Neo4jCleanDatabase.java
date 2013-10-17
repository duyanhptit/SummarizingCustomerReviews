package vn.ptit.anhdinh.wordnet;

import java.io.File;

import vn.ptit.anhdinh.wordnet.neo4j.Neo4JConnectionPool;

public class Neo4jCleanDatabase {

	private static final String NEO4J_PATH = Neo4JConnectionPool.NEO4J_PATH;

	public static void main(String[] args) {
		File file = new File(NEO4J_PATH);
		System.out.println("Startting clean Neo4J Database in: " + NEO4J_PATH + "...");
		deleteFileOrDirectory(file);
		System.out.println("Compleled clean Neo4J Database.");
	}

	private static void deleteFileOrDirectory(File file) {
		if (file.exists()) {
			if (file.isDirectory()) {
				for (File child : file.listFiles()) {
					deleteFileOrDirectory(child);
				}
			}
			file.delete();
			System.out.println("Deleted file: " + file.getPath());
		}
	}
}
