package com.netnumeri.client.jsneeded.view;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;

public class SingleOptionViewForm extends ViewAbstractWrapper implements SingleOptionView {


    private Button submitButton = new Button("Submit");
    private Button cancelButton = new Button("Cancel");
    private TextBox name = new TextBox();


    @Override
    protected void placeInPanel(Panel panel) {

        panel.add(name);
        panel.add(submitButton);
        panel.add(cancelButton);
    }

    /*
    ci dovrebbe essere una view generica (dentro yuzu) per edit-data-form, che e' generica per VO. L'utente deve implementare un interfaccia con i metodi: createVOFromForm, populateFormFromVO, e gli hook per gli eventi Submit, Cancel
    ShowNew e ShowEdit dovrebbero chiedere all'iunterfaccia l'html per new e edit

    SingleOptionView  -> FormViewVanilla
    OptionListView  -> GridViewVanilla


    uibinder -> special brick from yuzu (with explicit VO) but single view concrete devono crearsi il loro

    dovrebbe accorgersi se serve cambiare header/footer/menu/body, forse la cosa migliore e' controllare l'id dal html. se l'id e' uguale si potrebbe solo cambiare la parte varaibile (p.es. title on menu, selected tab on tabs)


     */

    @Override
    public void showNew() {
        RootPanel.get("title-label").getElement().setInnerText("New Option");
        show();

    }

    @Override
    public void showEdit(Object entity) {
        RootPanel.get("title-label").getElement().setInnerText("Edit Option");
        show();

    }

    @Override
    public void addButtonsHandlers(ClickHandler submitHandler, ClickHandler cancelHandler) {

        submitButton.addClickHandler(submitHandler);
        cancelButton.addClickHandler(cancelHandler);
    }

}
