package com.netnumeri.shared.datacargo;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListCommandResponse<T extends Suggestion> extends SuggestOracle.Response implements CommandResponse, IsSerializable {
    private List<T> entityList = new ArrayList<T>();
    private Collection<CommandItem> commandItems;
    private boolean isFirstPage = true; //default don't show
    private boolean isLastPage = true;

    public List<T> getEntityList() {
        return entityList;
    }

    public void setEntityList(List<T> entityList) {
        this.entityList = entityList;
    }

    public void setPaginatedList(PaginatedList<T> pl) {
        setEntityList(pl.getList());
        setFirstPage(pl.isFirstPage());
        setLastPage(pl.isLastPage());
    }

    public void addItem(T item) {
        entityList.add(item);
    }

    @Override
    public Collection<? extends Suggestion> getSuggestions() {
        List<Suggestion> ret = new ArrayList<Suggestion>();
        ret.addAll(getEntityList());
        ret.addAll(commandItems);
        return ret;
    }

    public void setCommandItems(List<CommandItem> commandItems) {
        this.commandItems = commandItems;
    }

    public boolean isFirstPage() {
        return isFirstPage;
    }

    public void setFirstPage(boolean firstPage) {
        isFirstPage = firstPage;
    }

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ListCommandResponse that = (ListCommandResponse) o;

        if (isFirstPage != that.isFirstPage) {
            return false;
        }
        if (isLastPage != that.isLastPage) {
            return false;
        }
        if (commandItems != null ? !commandItems.equals(that.commandItems) : that.commandItems != null) {
            return false;
        }
        if (entityList != null ? !entityList.equals(that.entityList) : that.entityList != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = entityList != null ? entityList.hashCode() : 0;
        result = 31 * result + (commandItems != null ? commandItems.hashCode() : 0);
        result = 31 * result + (isFirstPage ? 1 : 0);
        result = 31 * result + (isLastPage ? 1 : 0);
        return result;
    }
}
