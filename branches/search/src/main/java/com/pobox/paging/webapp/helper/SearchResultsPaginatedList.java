package com.pobox.paging.webapp.helper;

import java.util.Arrays;
import java.util.List;

import org.displaytag.pagination.PaginatedList;
import org.displaytag.properties.SortOrderEnum;
import org.compass.core.support.search.*;

public class SearchResultsPaginatedList implements PaginatedList {

	CompassSearchResults compassSearchResults;
	CompassSearchCommand command;
	
	public int getFullListSize() {
		return compassSearchResults.getTotalHits();
	}

	public List getList() {
		return Arrays.asList(compassSearchResults.getHits());
	}

	public int getObjectsPerPage() {
		return 25;
	}

	public int getPageNumber() {
		return command.getPage().intValue();
	}

	public String getSearchId() {
		throw new RuntimeException("not implemented/required");
	}

	public String getSortCriterion() {
		throw new RuntimeException("Sorting not implemented");
	}

	public SortOrderEnum getSortDirection() {
		throw new RuntimeException("Sorting not implemented");
	}

}
