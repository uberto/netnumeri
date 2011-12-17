package com.netnumeri.javaonly.client.presenter;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.netnumeri.client.presenter.BugFormPresenter;
import com.netnumeri.client.service.GetBugServiceAsync;
import com.netnumeri.client.view.BugFormView;
import com.netnumeri.shared.entity.Bug;
import com.netnumeri.shared.entity.BugEnum;
import com.netnumeri.shared.service.GetEntitiesResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class BugFormPresenterTest {
    private BugFormPresenter pres;
    public BugFormView view = mock(BugFormView.class);
    public GetBugServiceAsync service = mock(GetBugServiceAsync.class);


    @Before
    public void setUp() throws Exception {
        pres = new BugFormPresenter(view, service);
    }


    @After
    public void dropDown() throws Exception {
        Mockito.verifyNoMoreInteractions(view);
        Mockito.verifyNoMoreInteractions(service);
    }

    @Test
    public void activateTest(){
        pres.activate();

        verify(view).show();
        verify(view).setTitle("Edit bug");

        verify(service).getEntity(anyString(), any(AsyncCallback.class));

    }

    @Test
    public void success(){
        GetEntitiesResponse resp = new GetEntitiesResponse();
        Bug dummyBug = createDummyBug();
        resp.add(dummyBug);
        resp.add(dummyBug);
        resp.add(dummyBug);
//        pres.populateForm(resp);
//
//        verify(view).clearBugGrid();
//        verify(view, times(3)).addBug(dummyBug.getId(), dummyBug.getDesc(), dummyBug.getStatus(), dummyBug.getUser());

    }

    private Bug createDummyBug() {
        return new Bug(101, "test desc", BugEnum.WORKING, "uberto");
    }

}
