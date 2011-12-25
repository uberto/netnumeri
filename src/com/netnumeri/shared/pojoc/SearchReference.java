package com.netnumeri.shared.pojoc;


import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.netnumeri.shared.pojoc.field.*;

public class SearchReference extends BAPojo implements Suggestion {
    private static final long serialVersionUID = 3170177686444862063L;

    private StringField fPojoKind = stringField("pojoKind", 100);
    private StringField fPojoName = stringField("pojoName", 30);
    private IdField fPojoId = idField("pojoId");

    public void setPojoKind(String pojoKind) {
        fPojoKind.setValue(pojoKind);
        setName();
    }

    public String getPojoKind() {
        return fPojoKind.getValue();
    }

    public void setPojoName(String pojoName) {
        fPojoName.setValue(pojoName);
    }

    public String getPojoName() {
        return fPojoName.getValue();
    }

    public void setPojoId(Long pojoId) {
        fPojoId.setValue(pojoId);
        setName();
    }

    public Long getPojoId() {
        return fPojoId.getValue();
    }

    @Override
    public String getReplacementString() {
        return fName.getValue();
    }

    @Override
    public String getDisplayString() {
        return fName.getValue();
    }

    private void setName() {
        fName.setValue(fPojoKind.getValue() + "-" + fPojoId.getValue());
    }
}
