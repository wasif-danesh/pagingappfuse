package com.pobox.paging.webapp.controller;

import org.appfuse.webapp.controller.BaseControllerTestCase;
import com.pobox.paging.model.Authority;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

public class AuthorityFormControllerTest extends BaseControllerTestCase {
    private AuthorityFormController form;

    public void setAuthorityFormController(AuthorityFormController form) {
        this.form = form;
    }

    public void testEdit() throws Exception {
        log.debug("testing edit...");
        MockHttpServletRequest request = newGet("/authorityform.html");
        request.addParameter("id", "-1");

        ModelAndView mv = form.handleRequest(request, new MockHttpServletResponse());

        Authority authority = (Authority) mv.getModel().get(form.getCommandName());
        assertNotNull(authority);
    }

    public void testSave() throws Exception {
        MockHttpServletRequest request = newGet("/authorityform.html");
        request.addParameter("id", "-1");

        ModelAndView mv = form.handleRequest(request, new MockHttpServletResponse());

        Authority authority = (Authority) mv.getModel().get(form.getCommandName());
        assertNotNull(authority);

        request = newPost("/authorityform.html");

        // update required fields

        super.objectToRequestParameters(authority, request);

        mv = form.handleRequest(request, new MockHttpServletResponse());

        Errors errors = (Errors) mv.getModel().get(BindException.MODEL_KEY_PREFIX + "authority");
        assertNull(errors);
        assertNotNull(request.getSession().getAttribute("successMessages"));
    }

    public void testRemove() throws Exception {
        MockHttpServletRequest request = newPost("/authorityform.html");
        request.addParameter("delete", "");
        request.addParameter("id", "-2");

        form.handleRequest(request, new MockHttpServletResponse());

        assertNotNull(request.getSession().getAttribute("successMessages"));
    }
}