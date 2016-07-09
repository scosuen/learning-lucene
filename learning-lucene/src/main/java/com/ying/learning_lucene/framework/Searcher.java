package com.ying.learning_lucene.framework;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;

public class Searcher {

	private static volatile Searcher SEARCHER;
	private volatile Map<IndexName, IndexSearcher> indexSearcherMap = new HashMap<IndexName, IndexSearcher>();

	private Searcher() {
	}

	public static Searcher getInstance() {
		if (SEARCHER == null) {
			synchronized (SEARCHER) {
				if (SEARCHER == null) {
					SEARCHER = new Searcher();
				}
			}
		}
		return SEARCHER;
	}

	public IndexSearcher getIndexSearcher(IndexName indexName) throws IOException {
		if (indexSearcherMap.get(indexName) == null) {
			synchronized (indexSearcherMap) {
				if (indexSearcherMap.get(indexName) == null) {
					Directory directory = IndexDirectoryProvider.getDirectory(indexName);
					if (directory == null)
						return null;
					indexSearcherMap.put(indexName, new IndexSearcher(DirectoryReader.open(directory)));
				}
			}
		}

		return indexSearcherMap.get(indexName);
	}
}
