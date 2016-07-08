package com.ying.learning_lucene.test1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;

public class Searcher {
	private IndexSearcher indexSearcher;

	public Searcher(Directory directory) throws IOException {
		IndexReader reader = DirectoryReader.open(directory);
		indexSearcher = new IndexSearcher(reader);

	}

	public List<File> search(String keyword, int number) throws ParseException, IOException {
		QueryParser parser = new QueryParser("body", new StandardAnalyzer());
		Query query = parser.parse(keyword);
		ScoreDoc[] hits = indexSearcher.search(query, number).scoreDocs;

		List<File> files = new ArrayList<File>();
		for (int i = 0; i < hits.length; i++) {
			Document hitDoc = indexSearcher.doc(hits[i].doc);
			File file = new File();
			file.setTitle(hitDoc.get("title"));
			file.setBody(hitDoc.get("body"));
			files.add(file);
		}
		
		return files;
	}

}
