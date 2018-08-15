package edu.metrostate.ics499.prim.test;

import edu.metrostate.ics499.prim.service.SentimentService;
import edu.metrostate.ics499.prim.model.Document;
import edu.metrostate.ics499.prim.model.Documents;
import edu.metrostate.ics499.prim.model.Interaction;
import edu.metrostate.ics499.prim.model.SentimentDocuments;
import edu.metrostate.ics499.prim.service.SentimentServiceImpl;

public class SentimentTestHelper {
	
	static final int ID = 1;
	static final String MESSAGE = "Hello world. This is some input text that I love.";
	
	static final String EXPECTEDJSON = "[ {\r\n" + 
			"  \"language\" : \"en\",\r\n" + 
			"  \"id\" : \"" + ID + "\",\r\n" + 
			"  \"text\" : \"" + MESSAGE + "\"\r\n" + 
			"} ]";
	
	static Interaction testInteraction = new Interaction();
	static SentimentServiceImpl SC = new SentimentServiceImpl();
	
	static boolean failed = false;

	public SentimentTestHelper() {
		
	}
	
	public String JSONTest() {
		testInteraction.setId(ID);
		testInteraction.setMessage(MESSAGE);
		
		Documents testDocuments = new Documents();
		Document testDocument = new Document(testInteraction);
		
		testDocuments.add(testDocument);
		
		String JSON;
		
		JSON = Documents.toJSON(testDocuments);
		
		System.out.println(JSON);
		
		if(JSON.equals(EXPECTEDJSON)) {
			System.out.println("JSON test Passed!");
			return JSON;
		}else {
			System.out.println("JSON test Failed!");
			failed = true;
			return null;
		}
	}
	
	public SentimentDocuments testDataSent(String data) {
		SentimentDocuments sentimentDocuments = SC.sendData(data);
		
		String sentimentId = sentimentDocuments.get(0).getId();
		double sentimentValue = sentimentDocuments.get(0).getScore();
		
		if(sentimentId.equals(ID + "") && sentimentValue >= 0.0 && sentimentValue <= 100.0 && !failed) {
			System.out.print("Data Sent test passed!");
		}else {
			System.out.print("Data Sent test failed!");
			failed = true;
		}
		
		return sentimentDocuments;
	}

}
