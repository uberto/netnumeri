package com.netnumeri.shared.pojoc.field;


public class BlobField extends PojoCField<byte[]> {
	private static final long serialVersionUID = -1826585609253198145L;
	private static final byte[] EMPTY_BYTES = new byte[0];

	public BlobField(String name) {
        super(name);
        setEmptyValue();
    }

    public BlobField() {
        setEmptyValue();
    }

    public boolean isEmpty() {
    	return getValue() == null || getValue().length == 0;
    }

	@Override
    public void setValue(byte[] value) {
		if (value == null) {
			setEmptyValue();
		} else {
			super.setValue(value);
		}
    }

	public void setEmptyValue() {
        setValue(EMPTY_BYTES);
	}

	@Override
    public void setValueAsString(String value) {
        throw new RuntimeException("Trying to create a blob from a string!");
    }

    @Override
    public String getValueAsString() {
        return null; //TODO proper xmlzation 
    }

    @Override
    public FieldDsTypeEnum getDsType() {
        return FieldDsTypeEnum.blob;
    }
}
