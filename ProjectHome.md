Demonstrate Display Tag External Paging and Sorting via paging queries to an RDBMS.  Implemented using Hibernate Criteria generated from calls in the web interface by Display Tag.  This is an Appfuse java project.  Solution uses Display Tags Paginated List extension.

## Overview ##
Display Tag allows paging and sorting, if you click on a column header to sort ascending or descending. Note that it only sorts the records that are on the page - all the other records are excluded from this 'internal' Display Tag sort. Similarly when the page was generated every single record was retrieved, but only 25 were shown. With big datasets this is going to slow your web app down, (a lot), and the inconsistent sorting will confuse your users. This is what we are going to fix using Appfuse, Hibernate and Display Tag.

## Implementation steps ##
See this wiki page for details of how the implementation was achieved: PagingSorting

## Sample Code ##
All the code is in http://code.google.com/p/pagingappfuse  go to http://code.google.com/p/pagingappfuse/source/checkout for instructions on checking out the whole project, or you can browse the source from http://code.google.com/p/pagingappfuse/source/browse

## Projects ##

  * Appfuse:  http://www.appfuse.org
  * Hibernate: http://www.hibernate.org
  * Display Tag: http://displaytag.sourceforge.net/11
  * Display Tag External Paging and Sorting: http://displaytag.sourceforge.net/11/tut_externalSortAndPage.html