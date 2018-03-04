# Package to create SQL queries

This package contains classes used by the Pennsylvania Policy Database project
analysis tool (PAPolicy) to create the SQL queries.  PA Policy Database contains
several tables and the tool allows users to select from those tables based upon
filters, year ranges, and keyword search of text.

The basic form of the generated query is:
   SELECT ‹columns› from ‹table› WHERE ‹filter criteria› AND ‹year range› 
        GROUP BY ‹columns› ORDER BY ‹columns›
The filter criteria, year range, and group by are all optional.
The filter criteria is a conjunction of one or more comparisons or disjunctions
of comparisons.

This package is specific to PAPolicy, but was developed as a separate project
for testing purposes.
