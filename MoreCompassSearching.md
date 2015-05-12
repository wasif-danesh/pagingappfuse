# More Complex Searching with Compass #

## Code Location ##
This project is on a branch called weblogs see: http://code.google.com/p/pagingappfuse/source/browse/branches/weblogs/

See http://code.google.com/p/pagingappfuse/source/checkout for how to check it out

### Overview ###
Using Appfuse ( http://www.appfuse.org ) and Compass ( http://www.compass-project.org/ ) to build a persistent model that is searchable.
Here is  a slightly more complex example of using Compass with Hibernate; with relationships between 3
entities and enabled searchable references across them.

This is a POJO model roughly based on Matt Raibles page at:
http://raibledesigns.com/wiki/Wiki.jsp?page=HibernateRelationships

I have just updated it with Annotations etc. (which I can never remember so I
used http://tadtech.blogspot.com/2007_09_01_archive.html as a quick reference guide)

## Running Steps ##
  1. run it with
    * mvn jetty:run-war -Dmaven.test.skip=true
    * (it is currently assuming an Apache Derby database on localhost)
  1. login (admin/admin)
  1. navigate to Admin menu then re-Index (we must rebuild the index for new searchable properties)
  1. On menu click weblogs
  1. search for the query term:  **sp`*`**
  1. Hopefully you can see, (screenshot attached), examples on the results screen of:
    * Clickable HREF links to the Authority Detail page for any ID's associated with an Authority search result - this will go off to the database and retrieve the Authority you want by its ID (I haven't built detail pages for the other entities hence only hyperlinks on Authority Objects)
    * Second column of search results is a toString() on the search result object - you can see that the search engine has returned a complete object, ready for normal processing use, there is actually no need to go off to the database to retrieve it.

## Code Pointers ##
Have a look at the model POJO for Category, Weblog, Entry and see the Many-To-One Hibernate
mappings and the way Compass annotations enable you to do @`SearchableRef` - this enables you
to navigate to component objects of your search result object if you wish.

## Screenshot ##

![http://pagingappfuse.googlecode.com/svn/branches/weblogs/src/test/resources/AppfuseWeblogSearchResults.png](http://pagingappfuse.googlecode.com/svn/branches/weblogs/src/test/resources/AppfuseWeblogSearchResults.png)