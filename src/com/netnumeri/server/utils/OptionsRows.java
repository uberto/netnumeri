package com.netnumeri.server.utils;

import org.w3c.dom.Document;

import java.util.ArrayList;

public class OptionsRows {

    public ArrayList<String> callsRows = new ArrayList<String>();
    public ArrayList<String> putsRows = new ArrayList<String>();

    public Document callsDocument;
    public Document putsDocument;

    public void setCallsDocument(Document callsDocument) {
        this.callsDocument = callsDocument;
    }

    public void setPutsDocument(Document putsDocument) {
        this.putsDocument = putsDocument;
    }
}
