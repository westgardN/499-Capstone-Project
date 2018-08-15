package edu.metrostate.ics499.prim.controller;

import java.util.List;

import edu.metrostate.ics499.prim.model.Document;
import edu.metrostate.ics499.prim.model.Documents;
import edu.metrostate.ics499.prim.model.Interaction;
import edu.metrostate.ics499.prim.model.SentimentDocument;
import edu.metrostate.ics499.prim.model.SentimentDocuments;
import edu.metrostate.ics499.prim.model.SentimentQueueItem;
import edu.metrostate.ics499.prim.service.AzureService;
import edu.metrostate.ics499.prim.service.SentimentQueueItemService;

public class SentimentController {
	
	List<SentimentQueueItem> sentimentItems;
	SentimentQueueItemService queueItemsService;
	
	final int MAX_ITEMS = 1000;			// the maximum number of documents in a request is 1,000.
	final int MAX_CHARACTERS = 5000;	// the maximum size of a single document is 5,000 characters.
	
	private final String urlAddress = "https://westcentralus.api.cognitive.microsoft.com/text/analytics/v2.0/sentiment";
	private final String subscriptionKey = "d9faff7692614982a34bb5f826c5c409";
	private final String JSONHeader = "{\r\n" + "  \"documents\": ";
	
	public SentimentController() {
		
	}
	
	/**
	 * When called, processes all unprocessed SentimentQueueItems 
	 * based on their priority and sets a sentiment for them.
	 */
	public void getSentiment() {
		SentimentDocuments sentimentDocuments;

		sentimentItems = queueItemsService.findUnprocessed();
		
		while (sentimentItems != null) {
			
			String data = prepareData();
			sentimentDocuments = sendData(data);
			this.processData(sentimentDocuments);
			
			sentimentItems = queueItemsService.findUnprocessed();
		}
		
	}
	
	/**
	 * Retrieves the data from the sentiment queue and prepares it to be 
	 * processed by azure. The data is then passed to method sendData.
	 * 
	 * @return A String representation of a JSON.
	 */
	public String prepareData(){
		String returnString;
		Documents documents = new Documents();
		Document document = null;
		
		int itemCount = 0; 
		String message;
		
		for(SentimentQueueItem item: sentimentItems) {
			Interaction currentInteraction = item.getInteraction();
			message = currentInteraction.getMessage();
			
			/*
			 * If the itemCount is not at least one less than MAX_ITEMS
			 * than Documents is returned.
			 */
			if (itemCount < MAX_ITEMS) {
				document = new Document(currentInteraction);
				
				/*
				 * Shortens messages length to be within MAX_CHARACTERS 
				 * if it is not already.
				 */
				if(message.length() > MAX_CHARACTERS) {
					message.substring(0, MAX_CHARACTERS);
					document.setText(message);
				}
				
				documents.add(document);
				itemCount++;
				
			} else {
				returnString = Documents.toJSON(documents);
				return returnString;
			}
				
		}
		
		returnString = Documents.toJSON(documents);
		return returnString;
	}
	
	
	/**
	 * Sends the received JSON formated String to Azure to be analyzed and then receives 
	 * the replies in the form of a SentimentDocuments Object and returns that Object.
	 * 
	 * @param a JSON formated String
	 * @return
	 */
	public SentimentDocuments sendData(String data) {
		AzureService sentimentService = new AzureService();
		SentimentDocuments sentimentDocuments = new SentimentDocuments();
		
		data =  JSONHeader + data + "}";
		sentimentService.sendData(urlAddress, subscriptionKey, data);
		data = sentimentService.retrieveData();
		
		data = data.substring(13);
		data = (String) data.subSequence(0, data.length()-1);
		sentimentDocuments = SentimentDocuments.toDocuments(data);
		
		return sentimentDocuments;
	}
	
	/**
	 * Goes through each SentimentDocument and updates the sentiment 
	 * of the Interaction with its matching id.
	 * @param sentimentDocuments
	 */
	public void processData(SentimentDocuments sentimentDocuments) {
		
		for(SentimentDocument sentimentDocument: sentimentDocuments) {
			int id = Integer.parseInt(sentimentDocument.getId());
			Double sentiment = sentimentDocument.getScore();
			
			this.updateSentiment(id, sentiment);
		}
	}
	
	/**
	 * Finds the SentimentQueueItem that correlates to the id given,
	 * and updates it with the given sentiment.
	 * 
	 * @param id
	 * @param sentiment
	 */
	public void updateSentiment(int id, double sentiment){
		
		SentimentQueueItem sentimentQueueItem = queueItemsService.findById(id);
		Interaction interaction = sentimentQueueItem.getInteraction();
		int convertedSentiment = convertSentimentToInt(sentiment);
		interaction.setSentiment(convertedSentiment);
		
		sentimentQueueItem.setProcessed(true);
	}
	
	/**
	 * Takes in Sentiment as a decimal percent and converts it 
	 * into an Integer.
	 * 
	 * @param sentiment
	 * @return
	 */
	public int convertSentimentToInt(double sentiment) {
		sentiment = sentiment * 100;
		int convertedSentiment = (int) Math.round(sentiment);
		
		return convertedSentiment;
	}
	
}