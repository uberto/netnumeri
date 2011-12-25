package com.netnumeri.shared.finance.date;
/*
 * Copyright 2006 Robert Hanson <iamroberthanson AT gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.google.gwt.core.client.GWT;
import com.netnumeri.shared.finance.beans.ParseException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * <dl>
 * <dt><b>Title: </b><dd>SimpleDateFormat</dd>
 * <p>
 * <dt><b>Description: </b><dd>GWT does not implement any of the java.text package, so this class tries to fill the void
 * of the missing java.text.SimpleDateFormat class. This version however only supports a subset of the date and time patterns
 * supported by its java.text counterpart. The pattern symbols supported by this class are:
 * <dl>
 * <dt><b>E</b></dt><dd>TDay in a week</dd>
 * <dt><b>d</b></dt><dd>TDay of the month</dd>
 * <dt><b>y</b></dt><dd>Year</dd>
 * <dt><b>M</b></dt><dd>Month January, Jan, 01, 1</dd>
 * <dt><b>H</b></dt><dd>Hour in 24 hour format (0-23)</dd>
 * <dt><b>h</b></dt><dd>Hour in 12 hour format (1-12)</dd>
 * <dt><b>m</b></dt><dd>Minute of the hour </dd>
 * <dt><b>s</b></dt><dd>Seconds of the minute</dd>
 * <dt><b>a</b></dt><dd>am/pm</dd>
 * <dt><b>a</b></dt><dd>'Any String'</dd>
 * </dl>
 * </dd>
 * <p>
 * </dl>
 * @author <a href="mailto:jasone@greenrivercomputing.com">Jason Essington</a>
 * @version $Revision: 1.1 $
 */

/**
 * <dl>
 * <li>Modifyied to parse pattern string and conver it to a list of tokens
 * <li>ported to i18n
 * <li>created the parse method
 * </dl>
 *
 * @author <a href="mailto:andre.freller@gmail.com">Andre Freller</a>
 * @version $Revision: 1.1 $
 */
///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// ///////// /////////
public class SimpleDateFormat {
    private static SimpleDateFormatConstants CONS = (SimpleDateFormatConstants) GWT.create(SimpleDateFormatConstants.class);
    private static SimpleDateFormatMessages MSGS = (SimpleDateFormatMessages) GWT.create(SimpleDateFormatMessages.class);

    /**
     * this class holds the constants that represent the allowed chars in a pattern String
     */
    private static class Token {
        private char value = '\0';

        private Token(char value) {
            this.value = value;
        }

        public static Token DAY_OF_WEEK = new Token('E');
        public static Token DAY_OF_MONTH = new Token('d');
        public static Token MONTH = new Token('M');
        public static Token YEAR = new Token('y');
        public static Token HOUR_12 = new Token('h');
        public static Token HOUR_24 = new Token('H');
        public static Token MINUTE = new Token('m');
        public static Token SECOND = new Token('s');
        public static Token AM_PM = new Token('a');
        public static Token QUOTE = new Token('\'');
        public static Token SLASH = new Token('/');
        public static Token COLON = new Token(':');
        public static Token DASH = new Token('-');
        public static Token SPACE = new Token(' ');
        public static Token[] LIST = {
                DAY_OF_WEEK, DAY_OF_MONTH, MONTH, YEAR, HOUR_12, HOUR_24, MINUTE, SECOND, AM_PM, QUOTE, SLASH, COLON, DASH, SPACE
        };

        /*
         * Getters
         */

        public char getValue() {
            return this.value;
        }

        /*
         * Setters
         */

        private final void setValue(char value) {
        }

        /*
         * Util
         */

        public String toString() {
            return Character.toString(this.value);
        }

        /**
         * return the token that represents this value
         */
        public static Token getByValue(char value) {
            for (int cii = 0; cii < LIST.length; cii++)
                if (LIST[cii].getValue() == value)
                    return LIST[cii];
            return null;
        }
    } // end of class Token

    /**
     * this utility class is used to create a list that represents the pattern string after parsing.
     */
    private static class Item {
        private Token token = null;
        private String value = null;
        private int size = -1;

        public Item(Token token, int size) {
            this.token = token;
            this.size = size;
        }

        public Item(Token token, String value) {
            this.token = token;
            this.value = value;
        }


        /*
         * Getters
         */

        public Token getToken() {
            return this.token;
        }

        public String getValue() {
            return this.value;
        }

        public int getSize() {
            return this.size;
        }

        public String toString() {
            return getValue();
        }

        /*
         * Setters
         */

        public void setToken(Token token) {
            this.token = token;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public void setSize(int size) {
            this.size = size;
        }
    } // end of class Item


    private String pattern = null;
    private List tokenList = new ArrayList();

    public SimpleDateFormat(String pattern)
            throws ParseException {
        setPattern(pattern);
    }

    /*
     * Getters
     */

    public String getPattern() {
        return this.pattern;
    }

    public static String[] getWeekDayShort() {
        return SimpleDateFormat.CONS.weekDayShort();
    }

    public static String[] getWeekDayLong() {
        return SimpleDateFormat.CONS.weekDayLong();
    }

    public static String[] getMonthShort() {
        return SimpleDateFormat.CONS.monthShort();
    }

    public static String[] getMonthLong() {
        return SimpleDateFormat.CONS.monthLong();
    }


    /*
     * Setters
     */

    public void setPattern(String pattern)
            throws ParseException {

        this.pattern = pattern;
        parsePattern();
    }

    /*
     * Util
     */

    /**
     * read the pattern and convert it to a list of tokens (tokenList)
     */
    private void parsePattern()
            throws ParseException {

        if (this.pattern == null)
            throw new RuntimeException(MSGS.invalidPattern(this.pattern));

        Token lastToken = null;
        int size = 0;
        StringBuffer buf = null;
        for (int cii = 0; cii < this.pattern.length(); cii++) {
            char currentChar = this.pattern.charAt(cii);
            Token currentToken = Token.getByValue(currentChar);

            if (lastToken == Token.QUOTE) {
                if (currentToken == Token.QUOTE) {
                    this.tokenList.add(new Item(lastToken, buf.toString()));
                    lastToken = null;
                } else {
                    buf.append(currentChar);
                }
                continue;
            }
            if (currentToken == null)
                throw new ParseException(MSGS.invalidCharInPattern(currentChar, cii + 1));

            if (currentToken == Token.QUOTE)
                buf = new StringBuffer();


            if (currentToken == lastToken) {
                size++;
                continue;
            }

            if (lastToken != null)
                this.tokenList.add(new Item(lastToken, size));
            lastToken = currentToken;
            size = 1;

        }
        if (lastToken != null)
            this.tokenList.add(new Item(lastToken, size));
    }


    /**
     * <b>Obs:</b>
     * <dl>
     * <li>On numerical input for month days and month will only work on 2 digits numbers
     * <li>It will only consider Year Month TDay Hour Minute and Second to convert to Date
     * but will validate de hole input string.
     * <li>Case will be ignored
     * </dl>
     */
    public Date parse(String input)
            throws ParseException {

        int curPos = 0;
        int newYear = 0;
        int newMonth = 0;
        int newDay = 0;
        int newHour = 0;
        int newMinute = 0;
        int newSecond = 0;
        boolean newIs24h = false;
        boolean newIsPM = false;

        input = input.toLowerCase();

        for (int cii = 0; cii < this.tokenList.size(); cii++) {
            Item item = (Item) this.tokenList.get(cii);
            boolean found = false;
            if (item.getToken() == Token.DAY_OF_WEEK) {
                if (item.getSize() == 1) {
                    for (int cjj = 0; cjj < CONS.weekDayShort().length && !found; cjj++) {
                        String letter = CONS.weekDayShort()[cjj].toLowerCase().substring(0, 1);
                        if (input.indexOf(letter, curPos) == curPos) {
                            curPos += 1;
                            found = true;
                        }
                    }
                } else if (item.getSize() <= 3) {
                    for (int cjj = 0; cjj < CONS.weekDayShort().length && !found; cjj++)
                        if (input.indexOf(CONS.weekDayShort()[cjj].toLowerCase(), curPos) == curPos) {
                            curPos += CONS.weekDayShort()[cjj].length();
                            found = true;
                        }
                } else {
                    for (int cjj = 0; cjj < CONS.weekDayLong().length && !found; cjj++)
                        if (input.indexOf(CONS.weekDayLong()[cjj].toLowerCase(), curPos) == curPos) {
                            curPos += CONS.weekDayLong()[cjj].length();
                            found = true;
                        }
                }

            } else if (item.getToken() == Token.DAY_OF_MONTH) {
                int expectedSize = 2;
                try {
                    newDay = Integer.parseInt(input.substring(curPos, curPos + expectedSize));
                    curPos += expectedSize;
                    found = true;
                } catch (java.lang.NumberFormatException exc) {
                } // if !found exceptions will be raised at end

            } else if (item.getToken() == Token.MONTH) {
                switch (item.getSize()) {
                    case 1:
                    case 2:
                        int expectedSize = 2;
                        try {
                            newMonth = Integer.parseInt(input.substring(curPos, curPos + expectedSize));
                            curPos += expectedSize;
                            found = true;
                        } catch (java.lang.NumberFormatException exc) {
                        } // if !found exceptions will be raised at end
                        break;
                    case 3:
                        for (int cjj = 0; cjj < CONS.monthShort().length && !found; cjj++)
                            if (input.indexOf(CONS.monthShort()[cjj].toLowerCase(), curPos) == curPos) {
                                found = true;
                                curPos += CONS.monthShort()[cjj].length();
                                newMonth = cjj + 1;
                            }
                        break;
                    default:
                        for (int cjj = 0; cjj < CONS.monthLong().length && !found; cjj++)
                            if (input.indexOf(CONS.monthLong()[cjj].toLowerCase(), curPos) == curPos) {
                                found = true;
                                curPos += CONS.monthLong()[cjj].length();
                                newMonth = cjj + 1;
                            }
                        break;
                }

            } else if (item.getToken() == Token.YEAR) {
                int expectedSize = 2;
                int extraYears = 2000; // Year 2100 bug
                if (item.getSize() > 2) {
                    expectedSize = 4;
                    extraYears = 0;
                }
                try {
                    newYear = extraYears + Integer.parseInt(input.substring(curPos, curPos + expectedSize));
                    curPos += expectedSize;
                    found = true;
                } catch (java.lang.NumberFormatException exc) {
                } // if !found exceptions will be raised at end

            } else if (item.getToken() == Token.HOUR_12) {
                int expectedSize = 2;
                try {
                    newHour = Integer.parseInt(input.substring(curPos, curPos + expectedSize));
                    curPos += expectedSize;
                    found = true;
                    newIs24h = false;
                } catch (java.lang.NumberFormatException exc) {
                } // if !found exceptions will be raised at end

            } else if (item.getToken() == Token.HOUR_24) {
                int expectedSize = 2;
                try {
                    newHour = Integer.parseInt(input.substring(curPos, curPos + expectedSize));
                    curPos += expectedSize;
                    found = true;
                    newIs24h = true;
                } catch (java.lang.NumberFormatException exc) {
                } // if !found exceptions will be raised at end

            } else if (item.getToken() == Token.MINUTE) {
                int expectedSize = 2;
                try {
                    newMinute = Integer.parseInt(input.substring(curPos, curPos + expectedSize));
                    curPos += expectedSize;
                    found = true;
                } catch (java.lang.NumberFormatException exc) {
                } // if !found exceptions will be raised at end

            } else if (item.getToken() == Token.SECOND) {
                int expectedSize = 2;
                try {
                    newSecond = Integer.parseInt(input.substring(curPos, curPos + expectedSize));
                    curPos += expectedSize;
                    found = true;
                } catch (java.lang.NumberFormatException exc) {
                } // if !found exceptions will be raised at end

            } else if (item.getToken() == Token.AM_PM) {
                if (input.indexOf(CONS.am().toLowerCase(), curPos) == curPos) {
                    curPos += CONS.am().length();
                    found = true;
                    newIsPM = false;
                } else if (input.indexOf(CONS.pm().toLowerCase(), curPos) == curPos) {
                    curPos += CONS.pm().length();
                    found = true;
                    newIsPM = true;
                }


            } else if (item.getToken() == Token.QUOTE) {
                if (input.indexOf(item.getValue().toLowerCase(), curPos) == curPos) {
                    curPos += item.getValue().length();
                    found = true;
                }

            } else if (input.indexOf(item.getToken().getValue(), curPos) == curPos) {  // default use literal token
                curPos += 1;
                found = true;
            }


            if (!found)
                throw new ParseException(MSGS.invalidInputString(input, curPos + 1,
                        (item.getToken() == Token.QUOTE)
                                ? item.getValue()
                                : item.getToken().toString()));
        }

        /*
         * Now try to convert it
         */
        if (!newIs24h && newIsPM)
            newHour += 12;

        return new Date(newYear - 1900, newMonth - 1, newDay, newHour, newMinute, newSecond);
    } // end of parse


    public String format(Date date) {

        StringBuffer buf = new StringBuffer();

        for (int cii = 0; cii < this.tokenList.size(); cii++) {
            Item item = (Item) this.tokenList.get(cii);

            if (item.getToken() == Token.DAY_OF_WEEK) {
                if (item.getSize() == 1)
                    buf.append(CONS.weekDayShort()[date.getDay()].substring(0, 1));
                else if (item.getSize() <= 3)
                    buf.append(CONS.weekDayShort()[date.getDay()]);
                else
                    buf.append(CONS.weekDayLong()[date.getDay()]);

            } else if (item.getToken() == Token.DAY_OF_MONTH) {
                if (item.getSize() == 1)
                    buf.append(Integer.toString(date.getDate()));
                else
                    buf.append(formatTwoDigits(date.getDate()));

            } else if (item.getToken() == Token.MONTH) {
                switch (item.getSize()) {
                    case 1:
                        buf.append(Integer.toString(date.getMonth() + 1));
                        break;
                    case 2:
                        buf.append(formatTwoDigits(date.getMonth() + 1));
                        break;
                    case 3:
                        buf.append(CONS.monthShort()[date.getMonth()]);
                        break;
                    default:
                        buf.append(CONS.monthLong()[date.getMonth()]);
                        break;
                }

            } else if (item.getToken() == Token.YEAR) {
                if (item.getSize() > 2)
                    buf.append(Integer.toString(date.getYear() + 1900));
                else
                    buf.append(formatTwoDigits(date.getYear() + 1900));

            } else if (item.getToken() == Token.HOUR_12) {
                int hour = date.getHours();
                if (hour == 0)
                    hour = 12;
                else if (hour > 12)
                    hour -= 12;
                if (item.getSize() > 1)
                    buf.append(formatTwoDigits(hour));
                else
                    buf.append(Integer.toString(hour));

            } else if (item.getToken() == Token.HOUR_24) {
                if (item.getSize() > 1)
                    buf.append(formatTwoDigits(date.getHours()));
                else
                    buf.append(Integer.toString(date.getHours()));

            } else if (item.getToken() == Token.MINUTE) {
                if (item.getSize() > 1)
                    buf.append(formatTwoDigits(date.getMinutes()));
                else
                    buf.append(Integer.toString(date.getMinutes()));

            } else if (item.getToken() == Token.SECOND) {
                if (item.getSize() > 1)
                    buf.append(formatTwoDigits(date.getSeconds()));
                else
                    buf.append(Integer.toString(date.getSeconds()));

            } else if (item.getToken() == Token.AM_PM) {
                int hour = date.getHours();
                if (hour > 11)
                    buf.append(CONS.pm());
                else
                    buf.append(CONS.am());

            } else if (item.getToken() == Token.QUOTE) {
                buf.append(item.getValue());

            } else {
                // default is a token that represents a literal, just add it's value
                buf.append(item.getToken().toString());
            }
        }
        return buf.toString();
    } // end of format


    /**
     * This is to guarantee that our 1 or 2 digit numbers come out as a 2 character string.
     *
     * @param num
     * @return
     */
    private String formatTwoDigits(int num) {
        String res = Integer.toString(num);
        if (res.length() == 1)
            res = "0" + res;
        return res;
    }
}
