package edu.metrostate.ics499.prim.model;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Documents implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    private List<Document> documents;

    public Documents() {
        this.documents = new ArrayList<>();
    }

    public void add(Interaction interaction) {
        this.add(new Document(interaction));
    }

    public void add(Document document) {
        this.documents.add(document);
    }

    public boolean isEmpty() {
        return this.documents.isEmpty();
    }

    public int size() {
        return this.documents.size();
    }

    public void clear() {
        this.documents.clear();
    }

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

    /**
     * Gets documents
     *
     * @return value of documents
     */
    public List<Document> getDocuments() {
        return documents;
    }

    /**
     * Sets documents to the specified value in documents
     *
     * @param documents the new value for documents
     */
    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }
}
