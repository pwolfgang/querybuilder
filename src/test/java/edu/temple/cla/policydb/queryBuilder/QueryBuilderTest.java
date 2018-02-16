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

import edu.temple.cla.policydb.queryBuilder.QueryBuilder;
import edu.temple.cla.policydb.queryBuilder.Comparison;
import edu.temple.cla.policydb.queryBuilder.Expression;
import edu.temple.cla.policydb.queryBuilder.Between;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

/**
 *
 * @author Paul Wolfgang
 */
public class QueryBuilderTest {
    
    private QueryBuilder qb;
    
    public QueryBuilderTest() {
    }
    
    @Before
    public void setUp() {
        qb = new QueryBuilder();
        qb.setTable("BooBar");
    }
    
    @Test
    public void shouldSelectFromDatabase() {
        assertEquals("SELECT * FROM BooBar", qb.build());
    }
    
    @Test
    public void shouldSelectNamedColumn() {
        qb.addColumn("a");
        assertEquals("SELECT a FROM BooBar", qb.build());
    }
    
    @Test
    public void shouldSelectSeveralColumns() {
        qb.addColumn("a");
        qb.addColumn("b");
        qb.addColumn("c");
        assertEquals("SELECT a, b, c FROM BooBar", qb.build());
    }
    
    @Test
    public void shouldSelectBasedOnCriteria() {
        qb.addColumn("a");
        Expression criteria = new Comparison("a", "=", "5");
        qb.addToSelectCriteria(criteria);
        assertEquals("SELECT a FROM BooBar WHERE a=5", qb.build());
    }
    
    @Test
    public void shouldSelectBasedOnFilter() {
        qb.addColumn("a");
        Expression filter = new Comparison("a", "=", "5");
        qb.addFilter(filter);
        assertEquals("SELECT a FROM BooBar WHERE a=5", qb.build());
    }

    @Test
    public void shouldSelectBasedOnCriteriaAndFilter() {
        qb.addColumn("a");
        qb.addColumn("b");
        Expression criteria = new Comparison("a", "=", "5");
        qb.addToSelectCriteria(criteria);        
        Expression filter = new Comparison("b", "<>", "5");
        qb.addFilter(filter);
        assertEquals("SELECT a, b FROM BooBar WHERE a=5 AND b<>5", qb.build());
    }
    
    @Test
    public void shouldSelectBasedOnCriteriaAndFilterGroupBy() {
        qb.addColumn("a");
        qb.addColumn("b");
        Expression criteria = new Comparison("a", "=", "5");
        qb.addToSelectCriteria(criteria);        
        Expression filter = new Comparison("b", "<>", "5");
        qb.addFilter(filter);
        qb.setGroupBy("a");
        qb.setOrderBy("a");
        assertEquals("SELECT a, b FROM BooBar WHERE a=5 AND b<>5 GROUP BY a ORDER BY a", qb.build());
    }
    
    @Test
    public void shouldSelectSeveralColumnsGroupBy() {
        qb.addColumn("a");
        qb.addColumn("b");
        qb.addColumn("c");
        qb.setGroupBy("a");
        qb.setOrderBy("a");
       assertEquals("SELECT a, b, c FROM BooBar GROUP BY a ORDER BY a", qb.build());
    }

    @Test
    public void shouldSelectBasedOnCriteriaAndFilterAndBetweenGroupBy() {
        qb.addColumn("a");
        qb.addColumn("b");
        Expression criteria = new Comparison("a", "=", "5");
        qb.addToSelectCriteria(criteria);        
        Expression filter = new Comparison("b", "<>", "5");
        qb.addFilter(filter);
        qb.setBetween(new Between("a",3,7));
        qb.setGroupBy("a");
        qb.setOrderBy("a");
        assertEquals("SELECT a, b FROM BooBar WHERE a=5 AND b<>5 AND a BETWEEN 3 AND 7 GROUP BY a ORDER BY a", qb.build());
    }
    
}
