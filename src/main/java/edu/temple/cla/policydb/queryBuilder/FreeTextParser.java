/* 
 * Copyright (c) 2019, Temple University
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 * * All advertising materials features or use of this software must display 
 *   the following  acknowledgement
 *   This product includes software developed by Temple University
 * * Neither the name of the copyright holder nor the names of its 
 *   contributors may be used to endorse or promote products derived 
 *   from this software without specific prior written permission. 
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package edu.temple.cla.policydb.queryBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class FreeTextParser {

    /**
     * Create a Like expression to select a given key word.
     * @param textColumn The column in the table to search.
     * @param token The key word or phrase to be sought.
     * @return
     */
    static Expression createLike(String textColumn, String token) {
        if (token.startsWith("\"")) {
            int length = token.length();
            if (length < 2) {
                return new EmptyExpression();
            }
            token = token.substring(1, token.length() - 1);
        } 
        return new Like(textColumn, "%" + token + "%");
    }

    /**
     * Method to parse the free text and generate the query condition
     *
     * @param textColumn The name of the text column
     * @param freeText the free text string
     * @return The expressing to select the free text
     */
    public static Expression parse(String textColumn, String freeText) {
        Pattern p = Pattern.compile("(\\\"[^\\\"]+\\\")|([^\\p{javaWhitespace}]+)");
        Scanner scan = new Scanner(freeText);
        List<String> list = new ArrayList<>();
        String t;
        while ((t = scan.findInLine(p)) != null) {
            list.add(t);
        }
        Expression result;
        if (list.size() == 1) {
            result = FreeTextParser.createLike(textColumn, list.get(0));
        } else {
            result = new Conjunction();
            for (String token : list) {
                switch (token.toLowerCase()) {
                    case "and":
                        if (result.getClass() != Conjunction.class) {
                            Expression lhs = result;
                            Conjunction c = new Conjunction();
                            c.addTerm(lhs);
                            result = c;
                        }
                        break;
                    case "or":
                        if (result.getClass() != Disjunction.class) {
                            Expression lhs = result;
                            Disjunction d = new Disjunction();
                            d.addTerm(lhs);
                            result = d;
                        }
                        break;
                    default:
                        Composite c = (Composite) result;
                        c.addTerm(FreeTextParser.createLike(textColumn, token));
                }
            }
        }
        return result;
    }

}
