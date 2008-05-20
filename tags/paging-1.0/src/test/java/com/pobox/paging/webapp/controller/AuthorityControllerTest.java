package com.pobox.paging.webapp.controller;

import org.appfuse.webapp.controller.BaseControllerTestCase;
import org.displaytag.pagination.PaginatedList;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

public class AuthorityControllerTest extends BaseControllerTestCase {
    private AuthorityController controller;

    public void setAuthorityController(AuthorityController controller) {
        this.controller = controller;
    }

    public void testHandleRequest() throws Exception {
        ModelAndView mav = controller.handleRequest(null, null);
        ModelMap m = mav.getModelMap();
        assertNotNull(m.get("authorityList"));
        assertTrue(((PaginatedList) m.get("authorityList")).getList().size() > 0);
    }
}