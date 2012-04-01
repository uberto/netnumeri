package com.netnumeri.server.utils;


import org.dom4j.Document;

public class OptionsDocuments {

    String ticker;

    public Document callsDocument;
    public Document putsDocument;

    public OptionsDocuments(String ticker) {
        this.ticker = ticker;
    }

    public void setCallsDocument(Document callsDocument) {
        this.callsDocument = callsDocument;
    }

    public void setPutsDocument(Document putsDocument) {
        this.putsDocument = putsDocument;
    }
}
