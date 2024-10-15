Project requirements
The desktop GUI should support the following basic functions:
1. Add/remove students to/from the database (each student should have at least
name, ID, date of birth, and their current semester as attributes)
2. Add/remove modules to/from the database (each module should at least have
name, code, and semester as attributes)
3. Enter/modify/delete student grades (each student should have grades for all
modules completed until their current semester; grades can be a percentage,
or NP if the module was not completed)
4. Search for students in the database to display their information and the
modules they passed (>=40%).
In addition to the basic functionality:
5. Implement an extra Button in your GUI to simulate a memory leak by creating
new Student objects (in Heap space) in an infinite loop until the application
runs out of memory. Note how long it took and the memory at the point of
exception. Set the VM size to half of normal then use the same button and
observe what happens/how long it takes to get the out of memory exception.
Attach a Word or PDF document which shows your analysis of this memory
leak and explain in your own words what is happening.
Your solution should:
- Apply OOP principles and implement a clear package structure
- Implement appropriate design patterns, in particular MVC, where applicable
- Use the JDBC API to access the SQLite database
- Include JavaDoc documentation and Unit test documentation for all classes
Also make sure that:
- appropriate objects are used in your code (not just String) to represent
attributes (e.g. dates) and validate user inputs accordingly
- the database is always kept updated and synchronised with the desktop
application
- queries to access the underlying database do not retrieve any unnecessary
information (e.g. modules not passed for the student search)
- when deleting any record from that database, that all associated information is
removed as well
