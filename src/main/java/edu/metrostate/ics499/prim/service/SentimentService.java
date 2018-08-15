package edu.metrostate.ics499.prim.service;

public interface SentimentService {
    /**
     * When called, processes all unprocessed SentimentQueueItems
     * based on their priority and sets a sentiment for them.
     */
    void getSentiment();
}
