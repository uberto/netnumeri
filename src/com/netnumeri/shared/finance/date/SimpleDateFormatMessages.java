package com.netnumeri.shared.finance.date;

import com.google.gwt.i18n.client.Messages;

public interface SimpleDateFormatMessages extends Messages {

    String invalidPattern(String pattern);

    String invalidCharInPattern(char character, int position);

    String invalidInputString(String input, int position, String expected);
}

