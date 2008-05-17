package com.pobox.paging.dataaccess;

import java.util.List;

import org.appfuse.service.impl.UniversalManagerImpl;

import com.pobox.paging.webapp.helper.ExtendedPaginatedList;

public class PagingLookupManagerImpl extends UniversalManagerImpl implements
        PagingLookupManager {
    private PagingLookupDao pagingDao;

    /**
     * Method that allows setting the DAO to talk to the data store with.
     * 
     * @param dao
     *            the dao implementation
     */
    public void setLookupDao(PagingLookupDao pagingDao) {
        super.dao = pagingDao;
        this.pagingDao = pagingDao;
    }

    @SuppressWarnings("unchecked")
    public ExtendedPaginatedList getAllRecordsPage(Class clazz,
            ExtendedPaginatedList paginatedList) {
        List results = pagingDao.getAllRecordsPage(clazz, paginatedList
                .getFirstRecordIndex(), paginatedList.getPageSize(), paginatedList
                .getSortDirection(), paginatedList.getSortCriterion());
        paginatedList.setList(results);
        paginatedList.setTotalNumberOfRows(pagingDao.getAllRecordsCount(clazz));
        return paginatedList;
    }

    public PagingLookupDao getPagingDao() {
        return pagingDao;
    }

    public void setPagingDao(PagingLookupDao pagingDao) {
        this.pagingDao = pagingDao;
    }

}
