package vn.ptit.anhdinh.wordnet.neo4j;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class Neo4JConnectionPool {
	public static final String NEO4J_PATH = "C:/neo4j-enterprise-1.9.4/data/graph.db";
	private GraphDatabaseService mGrapDatabaseService;

	public Neo4JConnectionPool() {
		startDatabase();
	}

	private void startDatabase() {
		System.out.println("Starting Neo4J Database ...");
		mGrapDatabaseService = new GraphDatabaseFactory().newEmbeddedDatabase(NEO4J_PATH);
		registerShutdownHook(mGrapDatabaseService);
		System.out.println("Connect Neo4J Database is completed.");
	}

	public void shutDownDatabase() {
		System.out.println("Shutting down Neo4J Database ...");
		mGrapDatabaseService.shutdown();
		System.out.println("Neo4J Database is shutdown.");
	}

	public Neo4JWordNetDAO getNeo4JConnection() {
		return new Neo4JWordNetDAO(mGrapDatabaseService);
	}

	private static void registerShutdownHook(final GraphDatabaseService graphDatabaseService) {
		// Registers a shutdown hook for the Neo4j instance so that it
		// shuts down nicely when the VM exits (even if you "Ctrl-C" the running application).
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				graphDatabaseService.shutdown();
			}
		});
	}
}
