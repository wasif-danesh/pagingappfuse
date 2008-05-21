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

/**
 * <p>
 * A general Spring's MVC Controller that perform the search operation of
 * <code>Compass</code>.
 * 
 * <p>
 * Will perform the search operation on the <code>Compass</code> instance
 * using the query supplied by the {@link CompassSearchCommand}.
 * 
 * <p>
 * Pagination can be enabled by setting the <code>pageSize</code> property on
 * the controller, as well as providing the <code>page</code> number property
 * on the {@link CompassSearchCommand}.
 * 
 * <p>
 * The controller has two views to be set, the <code>searchView</code>, which
 * is the view that holds the screen which the user will initiate the search
 * operation, and the <code>searchResultsView</code>, which will show the
 * results of the search operation (they can be the same page).
 * 
 * <p>
 * The results of the search operation will be saved under the
 * <code>searchResultsName<code>,
 * which defaults to "searchResults".
 *
 * <p>In order to extend and execute additional operations during the search process
 * {@link org.compass.core.support.search.CompassSearchHelper} should be extended and
 * set using {@link #setSearchHelper(org.compass.core.support.search.CompassSearchHelper)}.
 *
 * @author kimchy
 */
public class SearchController extends AbstractCompassCommandController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(SearchController.class);

    private String searchView;

    private String searchResultsView;

    private String searchResultsName = "searchResults";

    private Integer pageSize;

    private CompassSearchHelper searchHelper;

    public SearchController() {
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
        logger.debug("**** incoming page Parm:"+searchCommand.getPage());
        String pageParm = request.getParameter((new ParamEncoder(
                "row")
                .encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
        logger.debug("**** found pageParm:"+pageParm);
        if (pageParm != null) {
            Integer pageRequested = Integer.parseInt(pageParm) -1;
            searchCommand.setPage(pageRequested);
        }
        CompassSearchResults searchResults = searchHelper.search(searchCommand);
        HashMap data = new HashMap();

        data.put(getCommandName(), searchCommand);
        data.put(getSearchResultsName(), searchResults);
        ModelAndView returnModelAndView = new ModelAndView(getSearchResultsView(), data);
        logger.debug("handle(HttpServletRequest, HttpServletResponse, Object, BindException) - end");
        return returnModelAndView;
    }

    /**
     * Returns the view that holds the screen which the user will initiate the
     * search operation.
     */
    public String getSearchView() {
        return searchView;
    }

    /**
     * Sets the view that holds the screen which the user will initiate the
     * search operation.
     */
    public void setSearchView(String searchView) {
        this.searchView = searchView;
    }

    /**
     * Returns the name of the results that the
     * {@link org.compass.core.support.search.CompassSearchResults} will be
     * saved under. Defaults to "searchResults".
     */
    public String getSearchResultsName() {
        return searchResultsName;
    }

    /**
     * Sets the name of the results that the
     * {@link org.compass.core.support.search.CompassSearchResults} will be
     * saved under. Defaults to "searchResults".
     */
    public void setSearchResultsName(String searchResultsName) {
        this.searchResultsName = searchResultsName;
    }

    /**
     * Returns the view which will show the results of the search operation.
     */
    public String getSearchResultsView() {
        return searchResultsView;
    }

    /**
     * Sets the view which will show the results of the search operation.
     */
    public void setSearchResultsView(String resultsView) {
        this.searchResultsView = resultsView;
    }

    /**
     * Sets the page size for the pagination of the results. If not set, not
     * pagination will be used.
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * Returns the page size for the pagination of the results. If not set, not
     * pagination will be used.
     * 
     * @param pageSize
     *            The page size when using paginated results
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * <p>
     * The search helper is used to execute teh actual search. By default (if
     * not set) the search controller will create a new search helper. If
     * provided, the search controller will use it to perform the search.
     * 
     * <p>
     * Mainly used to extend the search helper and execute additional operation
     * within specific calbacks the search helper exposes.
     * 
     * @param searchHelper
     *            A specific search helper to use
     */
    public void setSearchHelper(CompassSearchHelper searchHelper) {
        this.searchHelper = searchHelper;
    }
}
