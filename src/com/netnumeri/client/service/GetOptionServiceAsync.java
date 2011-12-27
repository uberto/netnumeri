package com.netnumeri.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.netnumeri.shared.entity.Option;
import com.netnumeri.shared.service.GetEntitiesResponse;
import com.netnumeri.shared.service.GetEntityResponse;

public interface GetOptionServiceAsync {
    void getEntities(String query, AsyncCallback<GetEntitiesResponse<Option>> async);

    void getEntity(String query, AsyncCallback<GetEntityResponse<Option>> async);
}
