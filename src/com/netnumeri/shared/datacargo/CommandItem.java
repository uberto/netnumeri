package com.netnumeri.shared.datacargo;


import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.gwt.user.client.ui.SuggestOracle;

public class CommandItem implements SuggestOracle.Suggestion, IsSerializable {
    private final static CommandItem ADD_NEW = new CommandItem(
            CommandItem.addKey, "New" + "...");
    private final static CommandItem MORE = new CommandItem(
            CommandItem.moreKey, "more" + "...");

    public static final String addKey = "+";
    public static final String moreKey = "*";

    private String key;
    private String value;

    public CommandItem() {
    }

    public CommandItem(String key, String value) {
        this.key = key;
        this.value = "<b>" + value + "</b>";
    }


    @Override
    public String getDisplayString() {
        return value;
    }

    @Override
    public String getReplacementString() {
        return key;
    }
}