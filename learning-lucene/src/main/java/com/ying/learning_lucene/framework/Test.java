package com.ying.learning_lucene.framework;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Test {

	public static void main(String[] args) {
		// Analyzer analyzer =
		// AnalyzerProvider.getAnalyzer(AnalyzerName.SIMPLE);

		Path path = Paths.get("C:\\pov\\StoreLogos");

		System.out.format("toString: %s%n", path.toString());
		System.out.format("getName: %s%n", path.getFileName());
		System.out.format("getName(0): %s%n", path.getName(0));
		System.out.format("getNameCount: %d%n", path.getNameCount());
		System.out.format("subpath(0,2): %d%n", path.subpath(0, 2));
		System.out.format("getParent: %s%n", path.getParent());
		System.out.format("getRoot: %s%n", path.getRoot());
	}
}
