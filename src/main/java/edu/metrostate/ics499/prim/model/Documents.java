package edu.metrostate.ics499.prim.model;

import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Documents extends ArrayList<Document> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param documents
	 * @return
	 */
	static public String toJSON(Documents documents) {
		String jsonInString = "";
		
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            jsonInString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(documents);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return jsonInString;
	}
	
	/**
	 * 
	 * @param json
	 * @return
	 */
	static public Documents toDocuments(String json) {
		Documents documents = null;
		
		ObjectMapper objectMapper = new ObjectMapper();
        try {
			documents = objectMapper.readValue(json, Documents.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return documents;
	}

}
