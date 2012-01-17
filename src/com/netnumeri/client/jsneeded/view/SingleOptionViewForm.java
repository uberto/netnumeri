package com.netnumeri.client.jsneeded.view;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;

public class SingleOptionViewForm extends ViewAbstractRoot implements SingleOptionView{


    @Override
    protected void placeInPanel(Panel panel) {

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
}
