package com.netnumeri.shared.finance.beans;
/*
 * Created on Jul 30, 2006
*
* Copyright 2005 CafeSip.org
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*	http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*
 */

import java.util.ArrayList;

/**
 * A poor man's StringTokenizer since the GWT emulation library does not provide one yet.
 *
 * @author Amit Chatterjee
 */
public class StringTokenizer {
    ArrayList<String> tokens = new ArrayList<String>();

    /**
     * A constructor for this class.
     *
     * @param text      text to be tokenized.
     * @param delimiter the delimiter character.
     */
    public StringTokenizer(String text, char delimiter) {
        char[] chars = text.toCharArray();

        int sindex = 0;
        int i;
        for (i = 0; i < chars.length; i++) {
            if (chars[i] == delimiter) {
                tokens.add(text.substring(sindex, i));
                sindex = i + 1;
            }
        }

        if (sindex < i) {
            tokens.add(text.substring(sindex));
        }
    }

    /**
     * Returns the token count.
     *
     * @return the number of tokens
     */
    public int countTokens() {
        return tokens.size();
    }

    /**
     * Returns the token at a given index.
     *
     * @param index index starts with 0
     * @return the token at the specified index
     */
    public String tokenAt(int index) {
        return tokens.get(index);
    }
}
