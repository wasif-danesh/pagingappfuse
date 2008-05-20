package com.pobox.paging.dataaccess;

import com.pobox.paging.webapp.helper.ExtendedPaginatedList;

public interface PagingLookupManager {
    ExtendedPaginatedList getAllRecordsPage(Class clazz,
            ExtendedPaginatedList paginatedList);
}
