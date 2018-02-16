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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A composite is an expression that contains multiple terms
 * 
 * @author Paul Wolfgang
 */
public abstract class  Composite implements Expression {

    protected List<Expression> terms;

    /**
     * Add a term
     *
     * @param e the term to be added
     */
    public void addTerm(Expression e) {
        if (e != null) {
            if (terms == null) {
                terms = new ArrayList<>();
            }
            if (this.getClass() == e.getClass()) { // Both same kind of Composite
                Composite composite = (Composite) e;
                if (!composite.isEmpty()) {
                    terms.addAll(composite.getTerms());
                }
            } else {
                terms.add(e);
            }
        }
    }

    /**
     * Determine if composite is empty.
     * @return true if the composite is null or empty.
     */
    @Override
    public boolean isEmpty() {
        return terms == null || terms.isEmpty();
    }

    /**
     * Get the list of terms.
     * @return the list of terms.
     */
    public List<Expression> getTerms() {
        return terms;
    }
    
    /**
     * Return a string representation. If there are no terms, return an empty
     * string. If there is only one term, return that term. Otherwise return the
     * terms separated by operator
     * @param operator The operator
     * @noParen if true, then surrounding parentheses are not applied.
     * @return
     */
    public String toString(String operator, boolean noParen) {
        if (terms == null) {
            return "";
        } else {
            for (Iterator<Expression> itr = terms.iterator(); itr.hasNext();) {
                Expression e = itr.next();
                if (e.isEmpty()) {
                    itr.remove();
                }
            }
            if (terms.size() == 1) {
                return terms.get(0).toString();
            } else {
                StringBuilder sb = new StringBuilder();
                if (!noParen) {
                    sb.append("(");
                }
                sb.append(terms.get(0));
                for (int i = 1; i < terms.size(); i++) {
                    sb.append(operator);
                    sb.append(terms.get(i));
                }
                if (!noParen) {
                    sb.append(")");
                }
                return sb.toString();
            }
        }
    }
    
}
