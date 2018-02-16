/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.temple.cla.policydb.queryBuilder;

import edu.temple.cla.policydb.queryBuilder.Comparison;
import edu.temple.cla.policydb.queryBuilder.Expression;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Paul Wolfgang
 */
public class ComparisonTest {
    
    public ComparisonTest() {
    }

    @Test
    public void testToString() {
        Expression e = new Comparison("foo", "<>", "0");
        assertEquals("foo<>0", e.toString());
    }
}
