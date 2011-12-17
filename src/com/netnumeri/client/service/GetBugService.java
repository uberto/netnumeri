package com.netnumeri.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.core.client.GWT;
import com.netnumeri.shared.service.GetEntitiesResponse;
import com.netnumeri.shared.service.GetEntityResponse;

@RemoteServiceRelativePath("GetBugService")
public interface GetBugService extends RemoteService {

    GetEntitiesResponse getEntities(String query);

    GetEntityResponse getEntity(String query);

    /**
     * Utility/Convenience class.
     * Use GetBugService.App.getInstance() to access static instance of getBugServiceAsync
     */
    public static class App {
        private static final GetBugServiceAsync ourInstance = (GetBugServiceAsync) GWT.create(GetBugService.class);

        public static GetBugServiceAsync getInstance() {
            return ourInstance;
        }
    }
}
