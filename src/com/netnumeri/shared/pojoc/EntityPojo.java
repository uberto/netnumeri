package com.netnumeri.shared.pojoc;


import com.netnumeri.shared.pojoc.field.*;

import java.io.Serializable;
import java.util.*;

public abstract class EntityPojo implements Serializable {
    private static final long serialVersionUID = -4293112362292442871L;

    private final static String DEFAULT_STRING = "";
    private final static int DEFAULT_INT = 0;
    private final static long DEFAULT_LONG = 0L;
    private final static boolean DEFAULT_BOOL = false;
    private final static Date DEFAULT_DATE = new Date();
    private final static double DEFAULT_DOUBLE = 0d;

    protected Map<String, OwnedListField<?>> ownedCollectionList = new LinkedHashMap<String, OwnedListField<?>>();

    protected Map<String, PojoCField<?>> fieldsList = new LinkedHashMap<String, PojoCField<?>>();

    private static final int LONGEST_SEARCHABLE_STRING = 3000;

    protected StringField stringField(String fieldName, int maxLen) {
        return stringField(fieldName, DEFAULT_STRING, maxLen);
    }

    protected StringField stringField(String fieldName, String defValue, int maxLen) {
        boolean s = maxLen <= LONGEST_SEARCHABLE_STRING;
        return stringField(fieldName, defValue, maxLen, s);
    }

    protected StringField stringField(String fieldName, String defValue, int maxLen, boolean isSearchable) {
        StringField f = new StringField(fieldName, maxLen);
        f.setValue(defValue);
        f.setSearchable(isSearchable);
        addToFieldList(f);
        return f;
    }

    private void addToFieldList(PojoCField<?> f) {
        String fieldName = f.getFieldName();
        if (fieldsList.containsKey(fieldName)) {
            throw new RuntimeException("duplicate field " + fieldName);
        }
        fieldsList.put(fieldName, f);
    }

    protected DateField dateField(String fieldName) {
        return dateField(fieldName, DEFAULT_DATE);
    }

    protected DateField dateField(String fieldName, Date defValue) {
        return dateField(fieldName, defValue, true);
    }

    protected DateField dateField(String fieldName, Date defValue, boolean isSearchable) {
        DateField f = new DateField(fieldName);
        f.setValue(defValue);
        f.setSearchable(isSearchable);
        addToFieldList(f);
        return f;
    }

	protected DateTimeField dateTimeField(String fieldName, Date defValue) {
        DateTimeField f = new DateTimeField(fieldName);
        f.setValue(defValue);
        f.setSearchable(false);
        addToFieldList(f);
        return f;
    }

    public <T extends Enum<T>> EnumField<T> enumField(String fieldName, T defValue) {
        EnumField<T> f = new EnumField<T>();
        f.setFieldName(fieldName);
        f.setDefaultValue(defValue);
        f.setValue(defValue);
        addToFieldList(f);
        return f;
    }

    protected DoubleField doubleField(String fieldName) {
        return doubleField(fieldName, DEFAULT_DOUBLE);
    }

    protected DoubleField doubleField(String fieldName, Double defValue) {
        DoubleField f = new DoubleField(fieldName);
        f.setValue(defValue);
        addToFieldList(f);
        return f;
    }

    protected BooleanField booleanField(String fieldName) {
        return booleanField(fieldName, DEFAULT_BOOL);
    }

    protected BooleanField booleanField(String fieldName, Boolean defValue) {
        BooleanField f = new BooleanField(fieldName);
        f.setValue(defValue);
        addToFieldList(f);
        return f;
    }

    protected IntegerField integerField(String fieldName) {
        return integerField(fieldName, DEFAULT_INT);
    }

    protected IntegerField integerField(String fieldName, Integer defValue) {
        IntegerField f = new IntegerField(fieldName);
        f.setValue(defValue);
        addToFieldList(f);
        return f;
    }

    protected LongField longField(String fieldName) {
        return longField(fieldName, DEFAULT_LONG);
    }

    protected LongField longField(String fieldName, Long defValue) {
        LongField f = new LongField(fieldName);
        f.setValue(defValue);
        addToFieldList(f);
        return f;
    }

    protected BlobField blobField(String fieldName) {
        BlobField f = new BlobField(fieldName);
        addToFieldList(f);
        return f;
    }

    protected IdField idField(String fieldName) {
        IdField f = new IdField(fieldName);
        f.setValue(null);
        addToFieldList(f);
        return f;
    }

    protected <T extends DetailBasePojo> OwnedListField<T> ownedListField(String fieldName) {
        OwnedListField<T> f = new OwnedListField<T>(fieldName);
        ownedCollectionList.put(f.getFieldName(), f);
        return f;
    }

    public String getAttributeAsString(String fieldName) {
        return getAttributeAsString(fieldName, null);
    }

    public String getAttributeAsString(String fieldName, String valueForNull) {
        PojoCField<?> f = getField(fieldName);
        if (f == null) {
            throw new RuntimeException("Field not present " + fieldName);
        }
        String ret = f.getValueAsString();
        ret = ret == null ? valueForNull : ret;
        return ret;
    }

    public void setAttributeAsString(String fieldName, String value) {
        PojoCField<?> f = getField(fieldName);
        if (f == null) {
            throw new RuntimeException("Field not present " + fieldName);
        }
        f.setValueAsString(value);
    }

    public List<String> getFieldNamesOrderedList() {
        List<String> l = new LinkedList<String>(getFieldNames());
        Collections.sort(l);
        return l;
    }

    public Collection<PojoCField<?>> getFields() {
        return fieldsList.values();
    }

    public PojoCField<?> getField(String name) {
        return fieldsList.get(name);
    }

    public Set<String> getFieldNames() {
        return fieldsList.keySet();
    }

    public OwnedListField<? extends DetailBasePojo> getOwnedList(String name) {
        return ownedCollectionList.get(name);
    }

    public Set<String> getOwnedListNames() {
        return ownedCollectionList.keySet();
    }

    public Collection<OwnedListField<?>> getOwnedLists() {
        return ownedCollectionList.values();
    }

    public DetailBasePojo createNewDetail(String collectionName) {
        throw new RuntimeException("asked to create a detail for " + collectionName + " but there is no such method on " + getClass());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntityPojo entityPojo = (EntityPojo) o;

        if (fieldsList == null || entityPojo.fieldsList == null) return false;
        if (fieldsList.size() != entityPojo.fieldsList.size()) return false;

        for (String fieldName : fieldsList.keySet()) {
            String value = fieldsList.get(fieldName).getValueAsString();
            String other = entityPojo.fieldsList.get(fieldName).getValueAsString();
            if (value == null) {
                if (other == null)
                    continue;
                else {
//                    System.err.println("!!!!" + fieldName + " " + value + " " + other);
                    return false;
                }
            } else if (!value.equals(other)) {
                //               System.err.println("!!!!" + fieldName + " " + value + " " + other);
                return false;
            }
        }
        return true;
    }

    public void copyFrom(EntityPojo ep) {
        for (String fieldName : ep.fieldsList.keySet()) {
            String value = ep.fieldsList.get(fieldName).getValueAsString();
            fieldsList.get(fieldName).setValueAsString(value);
        }
    }

    @Override
    public int hashCode() {
        return fieldsList != null ? fieldsList.hashCode() : 0;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (PojoCField<?> pojoCField : fieldsList.values()) {
            sb.append(pojoCField.getFieldName());
            sb.append("=");
            sb.append(pojoCField.getValueAsString());
            sb.append(", ");
        }
        return sb.toString();
    }
}
