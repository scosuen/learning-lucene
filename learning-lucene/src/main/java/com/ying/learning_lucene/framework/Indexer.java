package com.ying.learning_lucene.framework;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;

import com.ying.learning_lucene.test1.File;

public class Indexer {
	private static volatile Indexer INDEXER;
	private volatile Map<IndexName, IndexWriter> indexWriterMap = new HashMap<IndexName, IndexWriter>();

	private Indexer() { }

	public static Indexer getIntance() {
		if (INDEXER == null) {
			synchronized (INDEXER) {
				if (INDEXER == null) {
					INDEXER = new Indexer();
				}
			}
		}

		return INDEXER;
	}
	
	private IndexWriter getIndexWriter(IndexName indexName) throws IOException {
		if (indexWriterMap.get(indexName) == null) {
			synchronized (indexWriterMap) {
				if (indexWriterMap.get(indexName) == null) {
					if (IndexName.RAM.equals(indexName)) {
						IndexWriterConfig indexWriterConfig = new IndexWriterConfig(AnalyzerProvider.getAnalyzer());
						indexWriterConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
						indexWriterMap.put(IndexName.RAM, new IndexWriter(DirectoryProvider.getDirectory(indexName), indexWriterConfig));
					}
				}
			}
		}

		return indexWriterMap.get(indexName);
	}

	private IndexWriter getIndexWriter() throws IOException {
		return getIndexWriter(IndexName.RAM);
	}

	public Document getDocument(File file) {
		Document document = new Document();
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
	
	public boolean indexFile (IndexName indexName, File file) throws IOException {
		IndexWriter indexWriter = getIndexWriter(indexName);
		if (indexWriter != null) {
			indexWriter.addDocument(getDocument(file));
			return true;
		}
		return false;
	}
	
	public boolean commit (IndexName indexName) throws IOException {
		IndexWriter indexWriter = getIndexWriter(indexName);
		if (indexWriter != null) {
			indexWriter.commit();
			return true;
		}
		return false;
	}
	
	public void close(IndexName indexName) throws IOException {
		IndexWriter indexWriter = getIndexWriter(indexName);
		if (indexWriter != null)
			indexWriter.close();
	}
	
}
