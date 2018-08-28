package edu.metrostate.ics499.prim.service;

import java.util.List;

import edu.metrostate.ics499.prim.model.Document;
import edu.metrostate.ics499.prim.model.Documents;
import edu.metrostate.ics499.prim.model.Interaction;
import edu.metrostate.ics499.prim.model.SentimentDocument;
import edu.metrostate.ics499.prim.model.SentimentDocuments;
import edu.metrostate.ics499.prim.model.SentimentQueueItem;
import edu.metrostate.ics499.prim.provider.InteractionProvider;
import edu.metrostate.ics499.prim.service.AzureService;
import edu.metrostate.ics499.prim.service.SentimentQueueItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

@Service("sentimentService")
public class SentimentServiceImpl implements SentimentService {
	
	private List<SentimentQueueItem> sentimentItems;

    @Autowired
    SentimentQueueItemService sentimentQueueItemService;

    @Autowired
    InteractionService interactionService;

    public static final int MAX_ITEMS = 1000;			// the maximum number of documents in a request is 1,000.
	public static final int MAX_CHARACTERS = 5000;	// the maximum size of a single document is 5,000 characters.
	
	private final String urlAddress = "https://westcentralus.api.cognitive.microsoft.com/text/analytics/v2.0/sentiment";
	private final String subscriptionKey = "d9faff7692614982a34bb5f826c5c409";

	/**
	 * When called, processes all unprocessed SentimentQueueItems 
	 * based on their priority and sets a sentiment for them.
	 */
	@Override
    public void getSentiment() {
		SentimentDocuments sentimentDocuments;

		sentimentItems = sentimentQueueItemService.findUnprocessed();
		
        String data = prepareData();

        if (data != null) {
            sentimentDocuments = sendData(data);
            this.processData(sentimentDocuments);
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
//                    document.setText(HtmlUtils.htmlEscape(message));
                    document.setText(message);
				}
				
				documents.add(document);
				itemCount++;
				
			} else {
				returnString = documents.isEmpty() ? null : Documents.toJSON(documents);
				return returnString;
			}
				
		}
		
		returnString = documents.isEmpty() ? null : Documents.toJSON(documents);
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
		AzureService azureService = new AzureService();
		SentimentDocuments sentimentDocuments = new SentimentDocuments();
		
		azureService.sendData(urlAddress, subscriptionKey, data);
		data = azureService.retrieveData();

		if (data != null) {
            data = data.substring(13);
            data = (String) data.subSequence(0, data.length() - 1);
            sentimentDocuments = SentimentDocuments.toDocuments(data);
        }

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
			Interaction interaction = new Interaction();
			interaction.setId(id);

			this.updateSentiment(interaction, sentiment);
		}
	}
	
	/**
	 * Finds the SentimentQueueItem that correlates to the id given,
	 * and updates it with the given sentiment.
	 * 
	 * @param interaction
	 * @param sentiment
	 */
	public void updateSentiment(Interaction interaction, double sentiment){
		
		List<SentimentQueueItem> sentimentQueueItems = sentimentQueueItemService.findByInteraction(interaction);

		for (SentimentQueueItem sentimentQueueItem : sentimentQueueItems) {
		    if (!sentimentQueueItem.isProcessed()) {
                Interaction interact = sentimentQueueItem.getInteraction();
                int convertedSentiment = convertSentimentToInt(sentiment);
                interact.setSentiment(convertedSentiment);

                interactionService.update(interact);
                sentimentQueueItem.setProcessed(true);
                sentimentQueueItemService.update(sentimentQueueItem);
            }
        }
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