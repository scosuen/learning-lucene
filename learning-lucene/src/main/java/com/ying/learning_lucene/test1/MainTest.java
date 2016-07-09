package com.ying.learning_lucene.test1;

import java.io.IOException;
import java.sql.Time;
import java.time.Clock;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

public class MainTest {

	private Indexer indexer;
	private Searcher searcher;
	private Directory directory = new RAMDirectory();

	private List<File> testFiles = new ArrayList<File>() {
		{
			add(new File() {
				{
					setTitle("article 1");
					setBody("org.apache.lucene.analysis defines an abstract Analyzer API for converting text from a Reader into a TokenStream, an enumeration of token Attributes.  A TokenStream can be composed by applying TokenFilters to the output of a Tokenizer.  Tokenizers and TokenFilters are strung together and applied with an Analyzer.  analyzers-common provides a number of Analyzer implementations, including StopAnalyzer and the grammar-based StandardAnalyzer.");
				}
			});

			add(new File() {
				{
					setTitle("article 2");
					setBody("This query matches the term [hello] within the body field (recall that this is where we put the contents of the document). We must also provide a function to compute the final payload score from all term matches, so we plug in AveragePayloadFunction, which averages all payload scores. For example, if the term [hello] occurs inside dialogue twice and outside dialogue once, the final payload score will be ²⁄₃. This final payload score is multiplied with the one provided by DefaultSimilarity for the entire document.");
				}
			});

			add(new File() {
				{
					setTitle("article 3");
					setBody("Dimensional points, replacing legacy numeric fields, provides fast and space-efficient support for both single- and multi-dimension range and shape filtering. This includes numeric (int, float, long, double), InetAddress, BigInteger and binary range filtering, as well as geo-spatial shape search over indexed 2D LatLonPoints. See this blog post for details. Dependent classes and modules (e.g., MemoryIndex, Spatial Strategies, Join module) have been refactored to use new point types.");
				}
			});
		}
	};

	public MainTest() throws IOException {
		indexer = new Indexer(directory);
	}

	public static void main(String[] args) throws Exception {
		MainTest t = new MainTest();
//		t.test1
		t.test2();

	}

	public void test2() throws ParseException, IOException, InterruptedException, ExecutionException {
		ExecutorService es = Executors.newCachedThreadPool();
		CompletionService<String> completionServcie = new ExecutorCompletionService<String>(es);
		int threads = 50;

		for (int t = 0 ; t < threads ; t++) {
			completionServcie.submit(() -> {
				System.out.println("Ying Thread " + Thread.currentThread().getName() + " is running.");
				for (int i = 0; i < 100; i++) {
					try {
						indexer.indexFile(new File() {
							{
								setTitle("Ying Thread " + Thread.currentThread().getName() + ", " + Clock.systemDefaultZone().millis());
								setBody("Ying Thread " + Thread.currentThread().getName() + ", " + Clock.systemDefaultZone().millis());
							}
						});
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
//				indexer.close();
				
				return Thread.currentThread().getName();
			});
		}
		
		es.shutdown();
		
		for (int i = 0 ; i < threads ; i ++) {
			System.out.println(completionServcie.take().get());
		}
		
		searcher = new Searcher(directory);
		List<File> files = searcher.search("Thread", 1000000);

		if (files.size() <= 0) {
			System.out.println("Thread A no result");
			return;
		}

		int count = 0;
		for (File file : files) {
//			System.out.println(count ++ + ", " + file.getTitle());
		}
	}

	public void test1() throws IOException, ParseException {

		for (File file : testFiles) {
			indexer.indexFile(file);
		}

		searcher = new Searcher(directory);
		List<File> files = searcher.search("points", 1000);
		if (files.size() <= 0) {
			System.out.println("no result");
			return;
		}

		for (File file : files) {
			System.out.println(file.getTitle() + ", " + file.getBody());
		}
	}
}
