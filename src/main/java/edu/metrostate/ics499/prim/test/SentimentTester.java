package edu.metrostate.ics499.prim.test;

import edu.metrostate.ics499.prim.model.SentimentDocuments;

public class SentimentTester {
	
	public static void main(String args[]) {
		SentimentTestHelper test = new SentimentTestHelper();
		
		String JSON = test.JSONTest();
		SentimentDocuments sentimentDocuments = new SentimentDocuments();
		
		
			sentimentDocuments = test.testDataSent(JSON);		
		
	}

}
