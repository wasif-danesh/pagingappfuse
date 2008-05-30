package com.pobox.paging.webapp.controller;

import org.apache.log4j.Logger;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.compass.core.support.search.CompassSearchCommand;
import org.compass.core.support.search.CompassSearchHelper;
import org.compass.core.support.search.CompassSearchResults;
import org.compass.spring.web.mvc.AbstractCompassCommandController;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;


public class SearchWeblogsController extends AbstractCompassCommandController {

    private static final Logger logger = Logger.getLogger(SearchWeblogsController.class);
    private String searchView;
    private String searchResultsView;
    private String searchResultsName = "searchResults";
    private Integer pageSize;
    private CompassSearchHelper searchHelper;

    public SearchWeblogsController() {
        setCommandClass(CompassSearchCommand.class);
    }

    public void afterPropertiesSet() throws Exception {
        logger.debug("afterPropertiesSet() - start");

        super.afterPropertiesSet();
        if (searchView == null) {
            throw new IllegalArgumentException(
                    "Must set the searchView property");
        }
        if (searchResultsView == null) {
            throw new IllegalArgumentException(
                    "Must set the serachResultsView property");
        }
        if (searchHelper == null) {
            searchHelper = new CompassSearchHelper(getCompass(), getPageSize());
        }

        logger.debug("afterPropertiesSet() - end");
    }

    protected ModelAndView handle(HttpServletRequest request,
            HttpServletResponse response, Object command, BindException errors)
            throws Exception {
        logger.debug("handle(HttpServletRequest, HttpServletResponse, Object, BindException) - start");

        final CompassSearchCommand searchCommand = (CompassSearchCommand) command;
        if (!StringUtils.hasText(searchCommand.getQuery())) {
            ModelAndView returnModelAndView = new ModelAndView(getSearchView(), getCommandName(), searchCommand);
            logger.debug("handle(HttpServletRequest, HttpServletResponse, Object, BindException) - end");
            return returnModelAndView;
        }
      
        CompassSearchResults searchResults = searchHelper.search(searchCommand);

        HashMap data = new HashMap();

        data.put(getCommandName(), searchCommand);
        data.put(getSearchResultsName(), searchResults);
        ModelAndView returnModelAndView = new ModelAndView(getSearchResultsView(), data);
        logger.debug("handle(HttpServletRequest, HttpServletResponse, Object, BindException) - end");
        return returnModelAndView;
    }


    public String getSearchView() {
        return searchView;
    }

    public void setSearchView(String searchView) {
        this.searchView = searchView;
    }

    public String getSearchResultsName() {
        return searchResultsName;
    }


    public void setSearchResultsName(String searchResultsName) {
        this.searchResultsName = searchResultsName;
    }

    public String getSearchResultsView() {
        return searchResultsView;
    }


    public void setSearchResultsView(String resultsView) {
        this.searchResultsView = resultsView;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }


    public void setSearchHelper(CompassSearchHelper searchHelper) {
        this.searchHelper = searchHelper;
    }
}
