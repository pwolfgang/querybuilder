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
import java.util.List;

/**
 * Class to build SQL queries.
 * The query consists of a table, a list of column names, a selection
 * criteria, groupBy column expression, and orderBy column expression.
 * The selection criteria may contain a filter list, a topic, free text,
 * and a year range. These are all anded together.
 * @author Paul Wolfgang
 */
public class QueryBuilder implements Cloneable {
    
    private String table;
    private List<String> columnNames;
    private Conjunction selectCriteria;
    private Conjunction filters;
    private Expression topic;
    private Expression freeText;
    private Between between;
    private String groupBy;
    private String orderBy;
    
    /**
     * Build query
     * @return The SQL query string
     */
    public String build() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        if (columnNames == null || columnNames.isEmpty()) {
            sb.append("* ");
        } else {
            for (int i = 0; i < columnNames.size(); i++) {
                sb.append(columnNames.get(i));
                if (i == columnNames.size()-1) {
                    sb.append(" ");
                } else {
                    sb.append(", ");
                }
            }
        }
        sb.append("FROM ");
        sb.append(table);
        Conjunction selectAndFilters = new Conjunction();
        selectAndFilters.addTerm(selectCriteria);
        selectAndFilters.addTerm(filters);
        selectAndFilters.addTerm(topic);
        selectAndFilters.addTerm(freeText);
        selectAndFilters.addTerm(between);
        if (!selectAndFilters.isEmpty()) {
            sb.append(" WHERE ");
            sb.append(selectAndFilters.toStringNoParen());
        }
        if (groupBy != null) {
            sb.append(" GROUP BY ");
            sb.append(groupBy);
        }
        if (orderBy != null) {
            sb.append(" ORDER BY ");
            sb.append(orderBy);
        }
        return sb.toString();
    }
    
    /**
     * Set the table name
     * @param table the table name.
     */
    public void setTable(String table) {
        this.table = table;
    }
    
    /**
     * Add a column name to the list of columns.
     * @param columnName to be added.
     */
    public void addColumn(String columnName) {
        if (columnNames == null) {
            columnNames = new ArrayList<>();
        }
        columnNames.add(columnName);
    }
    
    /**
     * Clear the list of columns.
     */
    public void clearColumns() {
        columnNames = null;
    }
    
    /**
     * Add a condition to the select criteria.
     * @param e The expression to be added.
     */
    public void addToSelectCriteria(Expression e) {
        if (selectCriteria == null) {
            selectCriteria = new Conjunction();
        }
        selectCriteria.addTerm(e);
    }
    
    /**
     * Add a filter to the filters list.
     * @param e The expression to be added.
     */
    public void addFilter(Expression e) {
        if (filters == null) {
            filters = new Conjunction();
        }
        filters.addTerm(e);
    }
    
    /**
     * Clear the filter list.
     */
    public void clearFilters() {
        filters = null;
    }
    
    /**
     * Set the topic.
     * @param e The topic selection expression
     */
    public void setTopic(Expression e) {
        topic = e;
    };
    
    /**
     * Set Free Text.
     * @param e The free text selection expression.
     */
    public void setFreeText(Expression e) {
        freeText = e;
    }
    
    /**
     * Add a between criteria.
     * @param between The between criteria to be added.
     */
    public void setBetween(Between between) {
        this.between = between;
    }
    
    /**
     * Clear between.
     */
    public void clearBetween() {
        between = null;
    }
    
    /**
     * Set the group by column.
     * @param groupBy the group by column
     */
    public void setGroupBy(String groupBy) {
        this.groupBy = groupBy;
    }
    
    /**
     * Clear the groupBy.
     */
    public void clearGroupBy() {
        this.groupBy = null;
    }
    
    /**
     * Set the order by column.
     * @param orderBy the order by column
     */
    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
    
    /**
     * Clear the orderBy.
     */
    public void clearOrderBy() {
        this.orderBy = null;
    }
    
    /**
     * Make a copy of this QueryBuilder object.
     * Since all fields are immutable, a shallow copy is adequate.
     * @return A copy of this object.
     */
    @Override
    public QueryBuilder clone() {
        try {
            QueryBuilder theClone = (QueryBuilder)super.clone();
            return theClone;
        } catch (CloneNotSupportedException ex) {
            throw new Error("This is an impossible situation", ex);
        }
    }
}
