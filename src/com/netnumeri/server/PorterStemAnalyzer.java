package com.netnumeri.server;

import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.util.Version;

import java.io.Reader;
import java.util.Set;

public class PorterStemAnalyzer extends Analyzer {
    private Set<?> stopSet;

    public PorterStemAnalyzer() {
        this(StopAnalyzer.ENGLISH_STOP_WORDS_SET);   //TODO put the countrycode here
    }

    public PorterStemAnalyzer(Set<?> stopSet) {
        this.stopSet = stopSet;
    }

    public TokenStream tokenStream(String fieldName, Reader reader) {
        TokenStream result = new StandardTokenizer(Version.LUCENE_CURRENT, reader);
        result = new StandardFilter(result);
        result = new LowerCaseFilter(result);
        result = new PorterStemFilter(result);
        result = new StopFilter(false, result, stopSet);
        return result;
    }
}
