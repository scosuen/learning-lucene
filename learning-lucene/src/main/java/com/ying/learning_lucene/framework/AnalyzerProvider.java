package com.ying.learning_lucene.framework;

import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;

public class AnalyzerProvider {

	public enum AnalyzerName {
		STANDARD, WHITESPACE, SIMPLE, STOP
	}

	private static volatile Map<AnalyzerName, Analyzer> ANALYZER_MAP = new HashMap<AnalyzerName, Analyzer>() {
		{
			put(AnalyzerName.STANDARD, new StandardAnalyzer());
		}
	};

	private AnalyzerProvider() { }

	public static Analyzer getAnalyzer(AnalyzerName analyzerName) {
		if (ANALYZER_MAP.get(analyzerName) == null) {
			synchronized (ANALYZER_MAP) {
				if (ANALYZER_MAP.get(analyzerName) == null) {
					if (AnalyzerName.WHITESPACE.equals(analyzerName)) {
						ANALYZER_MAP.put(AnalyzerName.WHITESPACE, new WhitespaceAnalyzer());
					} else if (AnalyzerName.SIMPLE.equals(analyzerName)) {
						ANALYZER_MAP.put(AnalyzerName.SIMPLE, new SimpleAnalyzer());
					} else if (AnalyzerName.STOP.equals(analyzerName)) {
						ANALYZER_MAP.put(AnalyzerName.STOP, new StopAnalyzer());
					}
				}
			}
		}
		
		return ANALYZER_MAP.get(analyzerName);
	}
	
	public static Analyzer getAnalyzer () {
		return getAnalyzer(AnalyzerName.STANDARD);
	}
}
