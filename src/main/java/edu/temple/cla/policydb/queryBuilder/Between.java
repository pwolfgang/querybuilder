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
/**
 * The Between class represents a range.  It generates the
 * expression BETWEEN lowValue AND hiValue
 * @author Paul Wolfgang
 */
public class Between implements Expression {
    
    private String column;
    private String lowValue;
    private String hiValue;
    
    /**
     * Create a Between instance
     * @param column the column to be examined.
     * @param lowValue the minimum value
     * @param hiValue the maximum value
     */
    public Between(String column, int lowValue, int hiValue) {
        this.column = column;
        this.lowValue = Integer.toString(lowValue);
        this.hiValue = Integer.toString(hiValue);
    }
    
    /**
     * return a String representation
     * @return column BETWEEN lowValue AND hiValue
     */
    @Override
    public String toString() {
        return column + " BETWEEN " + lowValue + " AND " + hiValue;
    }
    
    /**
     * Return a String representation without parentheses
     * @return column BETWEEN lowValue and hiValue
     */
    @Override
    public String toStringNoParen() {
        return toString();
    }
    
    /**
     * Indicate whether this expression is empty
     * @return false
     */
    @Override
    public boolean isEmpty() {
        return false;
    }

}
