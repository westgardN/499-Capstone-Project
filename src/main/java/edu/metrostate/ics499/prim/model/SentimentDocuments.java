package edu.metrostate.ics499.prim.model;

import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SentimentDocuments extends ArrayList<SentimentDocument> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param json
	 * @return
	 */
	static public SentimentDocuments toDocuments(String json) {
		SentimentDocuments documents = null;
		
		ObjectMapper objectMapper = new ObjectMapper();
        try {
			documents = objectMapper.readValue(json, SentimentDocuments.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return documents;
	}
}
