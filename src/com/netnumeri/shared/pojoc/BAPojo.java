package com.netnumeri.shared.pojoc;


import com.netnumeri.shared.pojoc.field.*;

import java.util.Date;

public abstract class BAPojo extends EntityPojo {
    private static final long serialVersionUID = 8225039372534866199L;

    private static final int DB_VERSION = 1;
    public static final String FIELD_DB_VERSION = "dbVersion";
    public static final String FIELD_ID = "id";
    public static final String FIELD_CREATED = "created";
    public static final String FIELD_LAST_UPDATED = "lastUpdated";

    public static final String FIELD_NAME = "name";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_BUSINESS_ACCOUNT_URI = "businessAccountUri";

    public StringField fBusinessAccountUri = stringField(FIELD_BUSINESS_ACCOUNT_URI, "", 30, false);
    public IntegerField fDbVersion = integerField(FIELD_DB_VERSION, DB_VERSION);
    public IdField fId = idField(FIELD_ID);
    public DateTimeField fCreated = dateTimeField(FIELD_CREATED, null);
    public DateTimeField fLastUpdated = dateTimeField(FIELD_LAST_UPDATED, null);

    public StringField fName = stringField(FIELD_NAME, 30);
    public StringField fDescription = stringField(FIELD_DESCRIPTION, 200);

    public String getCode() {
        return fId.getValueAsString();
    }

    public Long getId() {
        return fId.getValue();
    }

    public void setId(Long id) {
        fId.setValue(id);
    }

    public Date getCreated() {
        return fCreated.getValue();
    }

    public void setCreated(Date created) {
        fCreated.setValue(created);
    }

    public String getDescription() {
        return fDescription.getValue();
    }

    public String getName() {
        return fName.getValue();
    }

    public void setDescription(String description) {
        fDescription.setValue(description);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((fName.getValue() == null) ? 0 : fName.getValue().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        BAPojo other = (BAPojo) obj;
        if (fName.getValue() == null) {
            if (other.fName.getValue() != null)
                return false;
        } else if (!fName.getValue().equals(other.fName.getValue()))
            return false;
        return true;
    }
}
