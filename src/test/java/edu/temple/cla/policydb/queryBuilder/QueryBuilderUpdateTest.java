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

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Paul
 */
public class QueryBuilderUpdateTest {
    
    @Test
    public void simpleUpdateTest() {
        QueryBuilder queryBuilder = new QueryBuilder();
        queryBuilder.setTable("NewsClips");
        queryBuilder.addSetClause(new SetClause("CAPCode", 4));
        queryBuilder.addSetClause(new SetClause("CAPOk", 0));
        queryBuilder.addToSelectCriteria(new Comparison("Code", "=", "7"));
        queryBuilder.addToSelectCriteria(FreeTextParser.parse("Abstract", "aquaculture or \"fish farm\""));
        String updateQuery = queryBuilder.buildUpdate();
        String expected = "UPDATE NewsClips SET CAPCode=4, CAPOk=0 "
                + "WHERE Code=7 AND (Abstract LIKE('%aquaculture%') OR Abstract LIKE(\'%fish farm%'))";
        assertEquals(expected, updateQuery);
    }
    
    @Test
    public void criteriaTestWithFilter() {
        QueryBuilder queryBuilder = new QueryBuilder();
        queryBuilder.setTable("NewsClips");
        queryBuilder.addSetClause(new SetClause("CAPCode", 1));
        queryBuilder.addSetClause(new SetClause("CAPOk", 0));
        queryBuilder.addToSelectCriteria(new Comparison("Code", "=", "24"));
        queryBuilder.addToSelectCriteria(FreeTextParser.parse("Abstract", "taxes or revenue"));
        queryBuilder.addFilter(new Comparison("tax", "<>", "0"));
        String expected = "UPDATE NewsClips SET CAPCode=1, CAPOk=0 WHERE "
                + "Code=24 AND (Abstract LIKE('%taxes%') OR Abstract LIKE('%revenue%')) AND tax<>0";
        assertEquals(expected, queryBuilder.buildUpdate());
    } 
    
}
