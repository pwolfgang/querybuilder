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
 * The Like expression determines if a value matches or does not match
 * a pattern. It is used to match a major code to one or more minor codes
 * or in the key-word search.
 * @author Paul Wolfgang
 */
public class Like implements Expression {
    
    private String column;
    private String what;
    private boolean not;
    
    /**
     * Construct a Like expression
     * @param column The column to be tested
     * @param code The major code
     * @param not True if NOT should be preappended.
     */
    public Like(String column, int code, boolean not) {
        this.column = column;
        this.what = code + "__";
        this.not = not;
    }
    
    /**
     * Construct a Like expression with not set to false
     * @param column The column to be tested
     * @param code The major code
     */
    public Like(String column, int code) {
        this(column, code, false);
    }
    
    /**
     * Construct a general Like expression
     * @param column The column to be tested
     * @param what The pattern to be matched
     * @param not True if NOT should be preapppended
     */
    public Like(String column, String what, boolean not) {
        this.column = column;
        this.what = what;
        this.not = not;
    }
    
    /**
     * Construct a general Like expression with not set to false
     * @param column The column to be tested
     * @param what The pattern to be matched
     */
    public Like(String column, String what) {
        this(column, what, false);
    }
    
    /**
     * Return the expression as a string
     * @return [NOT] LIKE('what')
     */
    @Override
    public String toString() {
        return column + (not ? " NOT ": " ") + "LIKE('" + what + "')";
    }
    
    /**
     * Return the expression as a string without parentheses.
     * @return [NOT] LIKE('what')
     */
    @Override
    public String toStringNoParen() {
        return toString();
    }
    
    /**
     * Indicate whether this expression is empty.
     * @return false
     */
    @Override
    public boolean isEmpty() {return false;}

}
