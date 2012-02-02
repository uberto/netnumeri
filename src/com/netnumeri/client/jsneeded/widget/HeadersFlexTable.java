package com.netnumeri.client.jsneeded.widget;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class HeadersFlexTable extends FlexTable {

    public HeadersFlexTable() {

        setCellSpacing(0);
        addStyleName("FlexTable");
    }

    public void addHeaders(String... headers) {

        insertRow(0);
        getRowFormatter().addStyleName(0, "FlexTable-Header");
        for (String header : headers) {
            addColumn(header);
        }
    }

    private void addColumn(String header) {

        Widget widget = createCellWidget(header);
        int colNo = getCellCount(0);

        widget.setWidth("100%");
        widget.addStyleName("FlexTable-ColumnLabel");

        setWidget(0, colNo, widget);

        getCellFormatter().addStyleName(0, colNo, "FlexTable-ColumnLabelCell");
    }

    private Widget createCellWidget(String header) {
        return new Label(header);
    }

    public void addRow(String... cellTexts) {

        int rowIndex = getRowCount();
        for (int cell = 0; cell < cellTexts.length; cell++) {
            setText(rowIndex, cell, cellTexts[cell]);
            getCellFormatter().addStyleName(rowIndex, cell, "FlexTable-Cell");
        }
        HTMLTable.RowFormatter rf = getRowFormatter();


        if ((rowIndex % 2) != 0) {
            rf.addStyleName(rowIndex, "FlexTable-OddRow");
        } else {
            rf.addStyleName(rowIndex, "FlexTable-EvenRow");
        }


    }

}
