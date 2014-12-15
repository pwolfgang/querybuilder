/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.temple.cla.papolicy.queryBuilder;

import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import static org.junit.Assert.*;

/**
 *
 * @author Paul Wolfgang
 */
@RunWith(value=Parameterized.class)
public class FreeTextParseTest {

    private final String expected;
    private final String textColumn;
    private final String freeText;

    @Parameters
    public static Collection<String[]> getTextParameters() {
        return Arrays.asList(new String[][] {
            {"text LIKE(\'%foo%\')", "text", "foo"},
            {"text LIKE(\'%foo%\') AND text LIKE(\'%bar%\')", "text", "foo bar"},
            {"text LIKE(\'%foo%\') AND text LIKE(\'%bar%\')", "text", "foo and bar"},
            {"(text LIKE(\'%foo%\') OR text LIKE(\'%bar%\'))", "text", "foo or bar"},
            {"text LIKE(\'%foo bar%\')", "text", "\"foo bar\""},
            {"(text LIKE(\'%foo bar%\') OR text LIKE(\'%baz%\'))", "text", "\"foo bar\" or baz"},
            {"text LIKE(\'%art%\')", "text", "\" art "},
            {"text LIKE(\'%art%\')", "text", " art \""},
            
        });
    }

    public FreeTextParseTest(String expected, String textColumn, String freeText) {
        this.expected = expected;
        this.textColumn = textColumn;
        this.freeText = freeText;
    }

    @Test
    public void testSingleToken() {
        String result = FreeTextParser.parse(textColumn, freeText).toStringNoParen();
        assertEquals(expected, result);
    }

}