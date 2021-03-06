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

import edu.temple.cla.policydb.queryBuilder.Comparison;
import edu.temple.cla.policydb.queryBuilder.Composite;
import edu.temple.cla.policydb.queryBuilder.Disjunction;
import edu.temple.cla.policydb.queryBuilder.Expression;
import edu.temple.cla.policydb.queryBuilder.Conjunction;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Paul Wolfgang
 */
public class DisjunctionTest {
    
    public DisjunctionTest() {
    }

    @Test
    public void emptyDisjunctionReturnsEmptyString() {
        Expression e = new Disjunction();
        assertEquals("", e.toString());
    }
    
    @Test
    public void singleTermReturnTerm() {
        Composite c = new Disjunction();
        c.addTerm(new Comparison("foo", "<>", "0"));
        assertEquals("foo<>0", c.toString());
    }
    
    @Test
    public void multipleTerms() {
        Composite d = new Disjunction();
        d.addTerm(new Comparison("foo", "<>", "0"));
        d.addTerm(new Comparison("bar", "<>", "0"));
        d.addTerm(new Comparison("baz", "<>", "0"));
        assertEquals("(foo<>0 OR bar<>0 OR baz<>0)", d.toString());
    }
    
    @Test
    public void nestedExpressions() {
        Composite d = new Disjunction();
        Composite c1 = new Conjunction();
        Composite c2 = new Conjunction();
        c1.addTerm(new Comparison("foo", "<>", "0"));
        c1.addTerm(new Comparison("bar", "=", "0"));
        c2.addTerm(new Comparison("foo", "=", "0"));
        c2.addTerm(new Comparison("bar", "<>", "0"));
        d.addTerm(c1);
        d.addTerm(c2);
        assertEquals("((foo<>0 AND bar=0) OR (foo=0 AND bar<>0))", d.toString());
    }    
}
