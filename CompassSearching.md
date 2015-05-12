## Searching in Appfuse 2.0.2 with Compass 2.0 and Lucene 2.3.2 ##
There are a few of tutorials for getting Compass (and therefore Lucene) to work with Appfuse
around; however with the advent of Compass 2.0 release, I thought I'd update and give a working
example.

Compass 2.0 has a lot of nice features, including annotations, default AND of query terms
(used to be OR), and built in result paging.

This example project  also shows the use of the Compass built in paging, and also 'external' paging via Displaytag.

## See Also ##
More complex searching - multiple models: http://code.google.com/p/pagingappfuse/wiki/MoreCompassSearching

## Projects ##
  * Appfuse http://appfuse.org/display/APF/Home
  * Compass http://www.compass-project.org/
  * Lucene http://lucene.apache.org/java/docs/index.html
  * Luke http://www.getopt.org/luke/
  * Displaytag  http://displaytag.sourceforge.net/11/
## Code ##
This extends the external PagingSorting example, all the source code is available as a SVN branch.
see instructions here : http://code.google.com/p/pagingappfuse/source/checkout you want to checkout the **branches/search** tree (the trunk has the PagingSorting code only )
e.g.: https://pagingappfuse.googlecode.com/svn/branches/search/
or you can browse the source at:
http://code.google.com/p/pagingappfuse/source/browse in the branches/search subdirectory

Changes to the previous external PagingSorting project are as follows:

## STEPS ##

1) edit pom.xml and add a new repository (it's not in the main repo yet see
http://forum.compass-project.org/thread.jspa?messageID=294522&tstart=0 ):
```
        <repository>
            <id>compass</id>
            <url>http://repo.compass-project.org</url>
        </repository>
```

2) edit the pom.xml and add in the new dependencies:
```
  <dependency>
            <groupId>org.compass-project</groupId>
            <artifactId>compass</artifactId>
            <version>${compass.version}</version>
        </dependency>
     <dependency>
          <groupId>org.apache.lucene</groupId>
          <artifactId>lucene-core</artifactId> 
          <version>${lucene.version}</version>
      </dependency>
      <dependency>
          <groupId>org.apache.lucene</groupId>
          <artifactId>lucene-analyzers</artifactId> 
          <version>${lucene.version}</version>
      </dependency>
      <dependency>
          <groupId>org.apache.lucene</groupId>
          <artifactId>lucene-highlighter</artifactId> 
          <version>${lucene.version}</version>
      </dependency>
      <dependency>
          <groupId>org.apache.lucene</groupId>
          <artifactId>lucene-queries</artifactId> 
          <version>${lucene.version}</version>
      </dependency>
      <dependency>
          <groupId>org.apache.lucene</groupId>
          <artifactId>lucene-snowball</artifactId> 
          <version>${lucene.version}</version>
      </dependency>
```

3) add new properties to the foot of your pom.xml:
```
        <!-- Compass Settings -->
        <compass.version>2.0.0</compass.version>
        <lucene.version>2.3.2</lucene.version>
```

4) Add annotations to your model - have a look at [Authority.java](http://code.google.com/p/pagingappfuse/source/browse/branches/search/src/main/java/com/pobox/paging/model/Authority.java), the annotations
available to you are documented here http://www.compass-project.org/docs/2.0.0/reference/html/core-osem.html

5) Add a [reindex.jsp](http://code.google.com/p/pagingappfuse/source/browse/branches/search/src/main/webapp/WEB-INF/pages/reindex.jsp) and a [search.jsp](http://code.google.com/p/pagingappfuse/source/browse/branches/search/src/main/webapp/WEB-INF/pages/search.jsp)

6) Add a new controller [CompassSearchController.java](http://code.google.com/p/pagingappfuse/source/browse/branches/search/src/main/java/com/pobox/paging/webapp/controller/SearchController.java) - this is almost the same as that supplied with Compass, however in
the handle method there are a couple of extra lines that decode `DisplayTag`'s page request and set it into the Command, if you are not using
`DisplayTag` then just reuse the `CompassSearchController` that comes with Compass itself.

7) Add the compass beans to [applicationContext.xml](http://code.google.com/p/pagingappfuse/source/browse/branches/search/src/main/webapp/WEB-INF/applicationContext.xml) - these wire up Compass to Hibernate using the new Compass `HibernateGpsDevice` so that the index will
be updated when Hibernate commits data to the RDBMS.  This is also where you declare your annotated class to compass.  Note that a number of non-default properties are set on the Compass bean - for instance the use of the Snowball analyzer (for English).

8) URLMappings - add search and reindex to the URLMapping - mapping them to their respective controllers:
see [dispatcher-servlet.xml](http://code.google.com/p/pagingappfuse/source/browse/branches/search/src/main/webapp/WEB-INF/dispatcher-servlet.xml)
```
  /search.html=searchController
  /reindex.html=indexController
```

9) add in beans for the new controllers (see [dispatcher-servlet.xml](http://code.google.com/p/pagingappfuse/source/browse/branches/search/src/main/webapp/WEB-INF/dispatcher-servlet.xml) ):

```
    <!-- ====  COMPASS SEARCH CONTROLLER ==== -->
    <bean id="searchController" class="com.pobox.paging.webapp.controller.SearchController">
        <property name="compass"><ref bean="compass"/></property>
        <property name="searchView"><value>search</value></property>
        <property name="searchResultsView"><value>search</value></property>
        <property name="pageSize"><value>25</value></property>
    </bean> 

    <bean id="indexController" class="org.compass.spring.web.mvc.CompassIndexController">
        <property name="compassGps"><ref bean="compassGps"/></property>
        <property name="indexView"><value>reindex</value></property>
        <property name="indexResultsView"><value>reindex</value></property>
    </bean>
```

10) Add search to the main menu, and reindex to the admin menu : [menu-config.xml](http://code.google.com/p/pagingappfuse/source/browse/branches/search/src/main/webapp/WEB-INF/menu-config.xml) and [menu.jsp](http://code.google.com/p/pagingappfuse/source/browse/branches/search/src/main/webapp/common/menu.jsp)

11) fire it up with mvn jetty:run-war , log in as admin and select re-index from the main menu - build the index (it's in ${user.home}/compass if you wish to browse it with [Luke](http://www.getopt.org/luke/))

12) try a search for the term
`2008*`
it should find all 106 records, (matching on the version date year), showing only the first 25.

13) The search page is a hybrid - raw HTML at the bottom (with paging courtesy of Compass), and Displaytag at the top (sorting is disabled - but you could add this functionality in).  Displaytag is doing external sorting and paging correctly, however the header says "Displaying 1-25" regardless of what page you are on - this is a Displaytag bug I think :
  * http://jira.codehaus.org/browse/DISPL-303
  * http://jira.codehaus.org/browse/DISPL-304
  * http://jira.codehaus.org/browse/DISPL-469

## Further Improvement? ##
1) Sorting - sort search results; a `CompassQuery` can be sorted eg: compassQuery.addSort("propertyName",SortPropertyType.STRING, SortDirection.REVERSE);

2) Fix DisplayTag bug "Display 1 to 25" by implementing a PaginatedList instead