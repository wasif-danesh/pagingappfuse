All the code is in http://code.google.com/p/pagingappfuse  go to http://code.google.com/p/pagingappfuse/source/checkout for instructions on checking out the whole project, or you can browse the source from http://code.google.com/p/pagingappfuse/source/browse

## Overview ##
I saw the blog post by Ram Gorti at http://i-work-i-blog.blogspot.com/2007/06/display-tag-and-external-pagination.html and have re-used code and adapted the implementation to fit into a web project built on appfuse. Thanks Ram!

## Getting setup ##
The following will get a new project into a state needed to build the paging and sorting:
Start with a SpringMVC basic project and generate in the same was as the appfuse quickstart guide shows here: http://appfuse.org/display/APF/AppFuse+QuickStart
e.g.

`mvn archetype:create -DarchetypeGroupId=org.appfuse.archetypes -DarchetypeArtifactId=appfuse-basic-spring -DremoteRepositories=http://static.appfuse.org/releases -DarchetypeVersion=2.0.2 -DgroupId=com.pobox.paging -DartifactId=pagingappfuse`

Run the app to download dependencies and so on - I'm using h2 as an RDBMS here:
`mvn -Ph2`
then generate eclipse project files:
`mvn eclipse:eclipse`

Now add an entity to persist then try to page and sort with. create a Hibernate annotated POJO called Authority in the model package like this: [Authority.java](http://code.google.com/p/pagingappfuse/source/browse/trunk/src/main/java/com/pobox/paging/model/Authority.java)

Generate the CRUD logic and screens - `mvn appfuse:gen -Dentity=Authority`
Because the pluralisation as generated is slightly wrong in this example, fix the bean definition for urlMapping in
` /paging/src/main/webapp/WEB-INF/dispatcher-servlet.xml`
by adding
`/authorities.html=authorityController`
and also you should rename the file
`/paging/src/main/webapp/WEB-INF/pages/authoritys.jsp`
to
`/paging/src/main/webapp/WEB-INF/pages/authorities.jsp`

Add some sample data into `/paging/src/test/resources/sample-data.xml`  (you could also add it via the web app itself but that would be tedious)  You'll need more than 25 records to trigger paging on the authorties screen Display Tag table.  The version [here](http://code.google.com/p/pagingappfuse/source/browse/trunk/src/test/resources/sample-data.xml) has over 100 records which you could re-use.

Run the app:
mvn jetty:run-war -Ph2
browse to http://localhost:8080 login and navigate via the menu to Authority List page - see the table with records in it.

## The Problem ##
With the project as is you should be able to see the problem: Display Tag allows paging and sorting, if you click on a column header to sort ascending or descending.  Note that it only sorts the records that are on the page - all the other records are excluded from this 'internal' Display Tag sort.  Similarly when the page was generated every single record was retrieved, but only 25 were shown.  With big datasets this is going to slow your web app down, (a lot), and the inconsistent sorting will confuse your users.  This is what we are going to fix.

## Proposed Solution ##
Display Tag allows you to implement a `PaginatedList` interface, if it gets passed one of these on your page to use as a model it assumes you are doing all paging and sorting externally and modifies the request URL's it send back from a page to your controller to something slightly less cryptic than the usual.  Our controller can take advantage of this to only find the number of records required to display this page full of info, and the total number of records that the query would return if we didn't limit it.  Similarly we can amend our query to get the RDBMS (via Hibernate) to sort on the users request column and then return the X number records required - fixing the sorting as well.  To make this work we will need to keep track of what page we are on, number of records per page, the total number of records etc.  and modify queries accordingly; which is easiest by adding some helper objects and a new PagingDAO similar to the existing `LookupDAO` in Appfuse.

## THE STEPS ##
### extend Paginated List ###
In the blog entry by Ram Gorti he has a nice implementation of an extended `PaginatedList`, (original is here http://javanotes.wikispaces.com/PaginatedListImpl ) keeping track of everything we need and doing the sums about pagesizes and numbers. So I reused this wholesale - the code for this is in [PaginatedListImpl.java](http://code.google.com/p/pagingappfuse/source/browse/trunk/src/main/java/com/pobox/paging/webapp/helper/PaginatedListImpl.java)

### Convenience factory method for `PaginatedList` from `HttpRequest` ###
Add a convenience creation method that unwraps the `HttpRequest` getting all the Display Tag info out of it returning a new populated object that extends `PaginatedList`, already correctly setup - all that's needed is the subset (page) of List records and the total to be set.  I put this into a helper object at [PaginateListFactory.java](http://code.google.com/p/pagingappfuse/source/browse/trunk/src/main/java/com/pobox/paging/webapp/helper/PaginateListFactory.java)
This has a null check for HttpRequest so out appfuse unit tests work outside of a web container, it has sensible defaults if it can't unwrap the http request.

### Add a Manager in the service layer ###
Similar to the exisitng `LookupDAO` in appfuse, add a manager that accepts a class , and one of our extended `PaginatedList` objects.  It will call down onto a new `PagingLookupDAO` to get the page of records, correctly sorted as requested, and the Total count of records.  It sets the results of these DAO calls into the extended `PaginateList` object itself and returns. This is implemented in [PagingLookupManagerImpl.java](http://code.google.com/p/pagingappfuse/source/browse/trunk/src/main/java/com/pobox/paging/dataaccess/PagingLookupManagerImpl.java),   there is an interface for this too.

### Add the DAO ###
This object has two methods, find the page of records required (correctly sorted) and find the total number of records.  This is implemented using Hibernate queries, I'd be interested to hear if there are ways of improving these two methods.Source is in [PagingLookupDaoHibernate.java](http://code.google.com/p/pagingappfuse/source/browse/trunk/src/main/java/com/pobox/paging/dataaccess/PagingLookupDaoHibernate.java)  there is an interface for this too.

### Amend the controller ###
`AuthorityController`  now uses the new manager instead of `GenericManager` and has the helper `PaginatedListFactory` injected too by Spring.  It uses this to created our new `PaginatedList object`, correctly populated (using the Factory method in the helper), calls the manager to get the the page required with sort options etc, then puts the `PaginatedList` into the `ModelAndView`.  When you add it to the model, give it the same name as used in your display Tag tag id on the jsp page - it didn't need to have an explicit name before because of Springs magic name discovery stuff, but now we want to explicitly name it "authorityList".  Code for this is in [AuthorityController.java ](http://code.google.com/p/pagingappfuse/source/browse/trunk/src/main/java/com/pobox/paging/webapp/controller/AuthorityController.java)

### Despatcher-servlet amend controller bean ###
add a property for the helper to be injected into `authorityController` in despatcher-servlet.xml, also change the manager property to inject `pagingLookupManager` instead of `GenericManager`. source is here [dispatcher-servlet.xml](http://code.google.com/p/pagingappfuse/source/browse/trunk/src/main/webapp/WEB-INF/dispatcher-servlet.xml)

### Add new manager, dao and helper beans to app context ###
In your applicationContext.xml add in bean definitions for the `pagingLookupDao`,` pagingLookupManager` and `paginatedListFactory`.  example source is here: [application-context.xml](http://code.google.com/p/pagingappfuse/source/browse/trunk/src/main/webapp/WEB-INF/applicationContext.xml)

### Change `AuthorityControllerTest` test ###
The last line of the unit test does an cast for a List - change this to assert an instance of `PaginatedLis`t then getList before the size assertion. see [AuthorityControllerTest.java](http://code.google.com/p/pagingappfuse/source/browse/trunk/src/test/java/com/pobox/paging/webapp/controller/AuthorityControllerTest.java)

### Run it and try new paging ###
execute `mvn jetty:run-war -Ph2`
then  login and browse to the Authority List page - try the new paging and see that sorting of columns now works.  This places much less load on the database as it only retrieves required records.  Note also the URL of the requests from Display tag for paging and sorting are now less cryptic - it has recognized that a `PaginatedList` was supplied and is handing off all calls for sorting and paging correctly.