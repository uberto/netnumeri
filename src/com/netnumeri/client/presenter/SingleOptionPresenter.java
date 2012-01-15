package com.netnumeri.client.presenter;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.netnumeri.client.events.RestUrl;
import com.netnumeri.client.service.GetOptionServiceAsync;
import com.netnumeri.client.view.SingleOptionView;
import com.netnumeri.shared.entity.Option;
import com.netnumeri.shared.service.GetEntityResponse;

public class SingleOptionPresenter extends PresenterWithView<SingleOptionView> {
    private GetOptionServiceAsync optionServiceAsync;

    public SingleOptionPresenter(SingleOptionView view, GetOptionServiceAsync service) {
        super(view);
        this.optionServiceAsync = service;
    }


    @Override
    public void activate(RestUrl url) {

        if (url.getId() == null) {
        getView().showNew(); //setTitle "New Option"

        } else {

            optionServiceAsync.getEntity("" + url.getId(), new AsyncCallback<GetEntityResponse<Option>>() {

                @Override
                public void onFailure(Throwable caught) {
                    getView().alert("Failure on server!");
                }

                @Override
                public void onSuccess(GetEntityResponse response) {
                    //if entity is ok then
                    getView().showEdit(response.getEntity());  //setTitle "Edit Option"

                }
            });
        }



    }

    /*
    il presenter generico<pojo> per edit-form dovrebbe eessree in yuzu. gli viene passata la view specifica (con il collegamento utente.

    responsabilita' del presenter e' di passare i VO alla view e riprenderseli. Eventualmente tutto quello che serve per preparare i VO e per comunicare con i Backend.
    Il presenter deve anche occuparsi di cambiar stato alla view (readonly, edit, create) a seconda della BL e dell'url richiesto. Eventuale stato serverside deve stare sul P.


    La View deve prendersi carico di tutta la UI e UX, senza sapere nulla di logica Business, deve solo sapere come tradurre visualmente i VO. Non deve avere stato, ogni metodo pubblico deve essere chiamabile in qualsiasi sequenza.


     */
}
