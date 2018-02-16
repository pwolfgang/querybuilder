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
