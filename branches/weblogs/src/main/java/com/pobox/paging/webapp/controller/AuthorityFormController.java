package com.pobox.paging.webapp.controller;

import org.apache.commons.lang.StringUtils;
import org.appfuse.service.GenericManager;
import com.pobox.paging.model.Authority;
import org.appfuse.webapp.controller.BaseFormController;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class AuthorityFormController extends BaseFormController {
    private GenericManager<Authority, Long> authorityManager = null;

    public void setAuthorityManager(GenericManager<Authority, Long> authorityManager) {
        this.authorityManager = authorityManager;
    }

    public AuthorityFormController() {
        setCommandClass(Authority.class);
        setCommandName("authority");
    }

    protected Object formBackingObject(HttpServletRequest request)
    throws Exception {
        String id = request.getParameter("id");

        if (!StringUtils.isBlank(id)) {
            return authorityManager.get(new Long(id));
        }

        return new Authority();
    }

    public ModelAndView onSubmit(HttpServletRequest request,
                                 HttpServletResponse response, Object command,
                                 BindException errors)
    throws Exception {
        log.debug("entering 'onSubmit' method...");

        Authority authority = (Authority) command;
        boolean isNew = (authority.getId() == null);
        String success = getSuccessView();
        Locale locale = request.getLocale();

        if (request.getParameter("delete") != null) {
            authorityManager.remove(authority.getId());
            saveMessage(request, getText("authority.deleted", locale));
        } else {
            authorityManager.save(authority);
            String key = (isNew) ? "authority.added" : "authority.updated";
            saveMessage(request, getText(key, locale));

            if (!isNew) {
                success = "redirect:authorityform.html?id=" + authority.getId();
            }
        }

        return new ModelAndView(success);
    }
}
