package com.pobox.paging.webapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.pobox.paging.dataaccess.PagingLookupManager;
import com.pobox.paging.model.Authority;
import com.pobox.paging.webapp.helper.ExtendedPaginatedList;
import com.pobox.paging.webapp.helper.PaginateListFactory;

public class AuthorityController implements Controller {
    private PagingLookupManager manager;
    private PaginateListFactory paginateListFactory;

    public void setManager(PagingLookupManager manager) {
        this.manager = manager;
    }

    public ModelAndView handleRequest(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ExtendedPaginatedList paginatedList = paginateListFactory
                .getPaginatedListFromRequest(request);
        manager.getAllRecordsPage(Authority.class, paginatedList);
        return new ModelAndView().addObject("authorityList", paginatedList);
    }

    public PaginateListFactory getPaginateListFactory() {
        return paginateListFactory;
    }

    public void setPaginateListFactory(PaginateListFactory paginateListFactory) {
        this.paginateListFactory = paginateListFactory;
    }

}
