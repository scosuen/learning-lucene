package com.ying.learning_lucene.framework2;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;

import com.ying.learning_lucene.framework.AnalyzerProvider;
import com.ying.learning_lucene.framework.AnalyzerProvider.AnalyzerName;
import com.ying.learning_lucene.framework.DirectoryProvider;
import com.ying.learning_lucene.framework.IndexName;

public class RAMIndexWriter extends IndexWriter {

	private static volatile RAMIndexWriter INSTANCE;

	private RAMIndexWriter(Directory d, IndexWriterConfig conf) throws IOException {
		super(d, conf);
	}

	public static RAMIndexWriter getInstance() throws IOException {
		if (INSTANCE == null) {
			synchronized (RAMIndexWriter.class) {
				if (INSTANCE == null) {
					IndexWriterConfig indexWriterConfig = new IndexWriterConfig(AnalyzerProvider.getAnalyzer(AnalyzerName.STANDARD));
					indexWriterConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
					INSTANCE = new RAMIndexWriter(DirectoryProvider.getDirectory(IndexName.RAM), indexWriterConfig);
				}
			}
		}

		return INSTANCE;
	}

	public Document getDocument(RAMDocument ramDocument) {
		Document document = new Document();
		document.add(new Field("id", ramDocument.getId(), new FieldType(){
			{
				setStored(true);
				setIndexOptions(IndexOptions.NONE);
				setTokenized(false);
			}
		}));
		document.add(new Field("title", ramDocument.getTitle(), new FieldType() {
			{
				setStored(true);
				setIndexOptions(IndexOptions.DOCS_AND_FREQS);
				setTokenized(false);
			}
		}));

		document.add(new Field("body", ramDocument.getBody(), new FieldType() {
			{
				setStored(true);
				setIndexOptions(IndexOptions.DOCS_AND_FREQS);
				setTokenized(true);
			}
		}));

		return document;
	}
	
	public void index (RAMDocument ramDocument) throws IOException {
		addDocument(getDocument(ramDocument));
		commit();
	}
	
	public void index (List<RAMDocument> ramDocuments) throws IOException {
		
		for (RAMDocument doc : ramDocuments) {
			addDocument(getDocument(doc));
		}
		commit();
	}
	
	public void update (RAMDocument ramDocument) throws IOException {
		updateDocument(new Term("id", ramDocument.getId()), getDocument(ramDocument));
	}
	
	public void delete (RAMDocument ramDocument) throws IOException {
		deleteDocuments(new Term("id", ramDocument.getId()));
	}
}
