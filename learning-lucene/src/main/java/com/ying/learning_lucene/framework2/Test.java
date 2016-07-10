package com.ying.learning_lucene.framework2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.queryparser.classic.ParseException;

public class Test {
	
	public static void main(String[] args) throws IOException, ParseException {
		RAMIndexWriter writer = RAMIndexWriter.getInstance();
		
		List<RAMDocument> docs = new ArrayList<RAMDocument>();
		RAMDocument doc = new RAMDocument();
		doc.setId("1");
		doc.setTitle("article 1");
		doc.setBody("org.apache.lucene.analysis defines an abstract Analyzer API for converting text from a Reader into a TokenStream, an enumeration of token Attributes.  A TokenStream can be composed by applying TokenFilters to the output of a Tokenizer.  Tokenizers and TokenFilters are strung together and applied with an Analyzer.  analyzers-common provides a number of Analyzer implementations, including StopAnalyzer and the grammar-based StandardAnalyzer.");
		docs.add(doc);
		
		doc = new RAMDocument();
		doc.setId("2");
		doc.setTitle("article 2");
		doc.setBody("This query matches the term [hello] within the body field (recall that this is where we put the contents of the document). We must also provide a function to compute the final payload score from all term matches, so we plug in AveragePayloadFunction, which averages all payload scores. For example, if the term [hello] occurs inside dialogue twice and outside dialogue once, the final payload score will be ²⁄₃. This final payload score is multiplied with the one provided by DefaultSimilarity for the entire document.");
		docs.add(doc);
		
		doc = new RAMDocument();
		doc.setId("3");
		doc.setTitle("article 3");
		doc.setBody("Dimensional points, replacing legacy numeric fields, provides fast and space-efficient support for both single- and multi-dimension range and shape filtering. This includes numeric (int, float, long, double), InetAddress, BigInteger and binary range filtering, as well as geo-spatial shape search over indexed 2D LatLonPoints. See this blog post for details. Dependent classes and modules (e.g., MemoryIndex, Spatial Strategies, Join module) have been refactored to use new point types.");
		docs.add(doc);
		
		writer.index(docs);
		
		RAMSearcher searcher = RAMSearcher.getInstance();
		System.out.println(searcher.searchByBody("article", 10));
	}
	
	

}
