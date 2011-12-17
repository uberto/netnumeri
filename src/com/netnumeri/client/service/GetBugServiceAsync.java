package com.netnumeri.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.netnumeri.shared.service.GetEntitiesResponse;
import com.netnumeri.shared.service.GetEntityResponse;

public interface GetBugServiceAsync {
    void getEntities(String query, AsyncCallback<GetEntitiesResponse> async);

    void getEntity(String query, AsyncCallback<GetEntityResponse> async);
}
