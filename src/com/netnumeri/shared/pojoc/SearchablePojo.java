package com.netnumeri.shared.pojoc;


import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;

/**
 * Default is all string fields and date fields are searchable
 */
public abstract class SearchablePojo extends BAPojo implements Suggestion {
    private static final long serialVersionUID = -8555420061181468139L;

    @Override
    public String getReplacementString() {
        return fName.getValue();
    }

    @Override
    public String getDisplayString() {
        return fName.getValue();
    }

    public abstract String getKind();

    public abstract SearchablePojo clone();
}
