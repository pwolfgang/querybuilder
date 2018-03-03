/* 
 * Copyright (c) 2018, Temple University
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

import edu.temple.cla.policydb.queryBuilder.FreeTextParser;
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