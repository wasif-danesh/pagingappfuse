package com.pobox.paging.dataaccess;

import java.util.List;

import org.appfuse.dao.UniversalDao;
import org.displaytag.properties.SortOrderEnum;

public interface PagingLookupDao extends UniversalDao {
     List getAllRecordsPage(Class clazz, int firstResult, int maxResults,
            SortOrderEnum sortDirection, String sortCriterion);
     int getAllRecordsCount(Class clazz);
}
