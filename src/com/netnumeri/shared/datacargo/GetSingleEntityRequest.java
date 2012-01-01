package com.netnumeri.shared.datacargo;


public abstract class GetSingleEntityRequest extends CommandRequest {

	protected Long entityId;

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        GetSingleEntityRequest that = (GetSingleEntityRequest) o;

        if (entityId != null ? !entityId.equals(that.entityId) : that.entityId != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (entityId != null ? entityId.hashCode() : 0);
        return result;
    }

}
