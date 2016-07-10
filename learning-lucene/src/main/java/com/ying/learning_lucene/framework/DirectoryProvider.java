package com.ying.learning_lucene.framework;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;

public class DirectoryProvider {

	private static volatile Map<IndexName, Directory> DIRECTORY_NAME = new HashMap<IndexName, Directory>();

	private DirectoryProvider() { }

	public static Directory getDirectory (IndexName indexName) throws IOException {
		if (DIRECTORY_NAME.get(indexName) == null) {
			synchronized (DIRECTORY_NAME) {
				if (DIRECTORY_NAME.get(indexName) == null) {
					if (IndexName.RAM.equals(indexName)) {
						DIRECTORY_NAME.put(IndexName.RAM, new RAMDirectory());
					} else if (IndexName.ENGLISH.equals(indexName)) {
						DIRECTORY_NAME.put(IndexName.ENGLISH, FSDirectory.open(Paths.get(IndexName.ENGLISH.getDir())));
					}
				}
			}
		}
		
		return DIRECTORY_NAME.get(indexName);
	}

	public static Directory getDirectory() throws IOException {
		return getDirectory(IndexName.RAM);
	}

}
