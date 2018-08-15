package edu.metrostate.ics499.prim.model;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Document {
	
	private final String ENGLISH = "en";
	
	private String language;
	private String id;
	private String text;
	
	public Document() {
		this.setLanguage(ENGLISH);
	}
	
	/**
	 * Creates a Document with the id and message of the given Interaction.
	 * 
	 * @param interaction
	 */
	public Document(Interaction interaction) {
		String interactionId = "" + interaction.getId();
		String interactionText = interaction.getMessage();
		
		this.setLanguage(ENGLISH);
		this.setId(interactionId);
		this.setText(interactionText);
	}

	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * 
	 * @param document
	 * @return
	 */
	static public String toJSON(Document document) {
		String jsonInString = "";
		
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            jsonInString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(document);
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
	static public Document toDocument(String json) {
		Document document = null;
		
		ObjectMapper objectMapper = new ObjectMapper();
        try {
			document = objectMapper.readValue(json, Document.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return document;
	}

	public String toString() {
		String data = "";
		data = data + this.getText(); 
		data = data + this.getId();
		data = data + this.getLanguage();
		return data;
	}
}
