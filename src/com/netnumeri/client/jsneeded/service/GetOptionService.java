package com.netnumeri.client.jsneeded.service;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.netnumeri.shared.finance.finpojo.derivative.equity.Option;
import com.netnumeri.shared.service.GetEntitiesResponse;
import com.netnumeri.shared.service.GetEntityResponse;

@RemoteServiceRelativePath("GetOptionService")
public interface GetOptionService extends RemoteService {

    GetEntitiesResponse<Option> getEntities(String query);

    GetEntityResponse<Option> getEntity(String query);

    public static class App {
        private static final GetOptionServiceAsync ourInstance =  GWT.create(GetOptionService.class);

        public static GetOptionServiceAsync getInstance() {
            return ourInstance;
        }
    }
}
