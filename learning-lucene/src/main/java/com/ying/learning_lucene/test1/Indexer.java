package com.ying.learning_lucene.test1;

import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

public class Indexer {
	private IndexWriter writer;

	public Indexer(Directory directory) throws IOException {
		// Directory index = FSDirectory.open(new File("index-dir"));
		StandardAnalyzer analyzer = new StandardAnalyzer();
//		analyzer.setVersion(Version.LUCENE_6_1_0);
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		writer = new IndexWriter(directory, config);
	}

	public void close() throws IOException {
		if (writer != null)
			writer.close();
	}

	public Document getDocument(File file) {
		Document document = new Document();

//		Field field = new Field("", "", TextField.TYPE_STORED);
		document.add(new Field("title", file.getTitle(), new FieldType() {
			{
				setStored(true);
				setIndexOptions(IndexOptions.DOCS_AND_FREQS);
			}
		}));

		document.add(new Field("body", file.getBody(), new FieldType() {
			{
				setStored(true);
				setIndexOptions(IndexOptions.DOCS_AND_FREQS);
			}
		}));

		return document;
	}
	
	public void indexFile (File file) throws IOException {
		writer.addDocument(getDocument(file));
		writer.commit();
	}
}
