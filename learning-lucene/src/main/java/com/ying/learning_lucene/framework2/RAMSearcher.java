package com.ying.learning_lucene.framework2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;

import com.ying.learning_lucene.framework.AnalyzerProvider;
import com.ying.learning_lucene.framework.AnalyzerProvider.AnalyzerName;
import com.ying.learning_lucene.framework.DirectoryProvider;
import com.ying.learning_lucene.framework.IndexName;

public class RAMSearcher extends IndexSearcher {

	private static volatile RAMSearcher INSTANCE;

	private RAMSearcher(IndexReader r) {
		super(r);
	}

	public static RAMSearcher getInstance() throws IOException {
		if (INSTANCE == null) {
			synchronized (RAMSearcher.class) {
				if (INSTANCE == null) {
					Directory directory = DirectoryProvider.getDirectory(IndexName.RAM);
					if (directory == null)
						return null;
					INSTANCE = new RAMSearcher(DirectoryReader.open(directory));
				}
			}
		}
		return INSTANCE;
	}
	
	public List<RAMDocument> searchByBody (String keyword, int number) throws ParseException, IOException {
		QueryParser parser = new QueryParser("body", AnalyzerProvider.getAnalyzer(AnalyzerName.STANDARD));
		Query query = parser.parse(keyword);
		ScoreDoc[] hits = search(query, number).scoreDocs;

		List<RAMDocument> docs = new ArrayList<RAMDocument>();
		for (int i = 0; i < hits.length; i++) {
			Document hitDoc = doc(hits[i].doc);
			RAMDocument doc = new RAMDocument();
			doc.setId(hitDoc.get("id"));
			doc.setTitle(hitDoc.get("title"));
			doc.setBody(hitDoc.get("body"));
			docs.add(doc);
		}
		
		return docs;
	}

}
