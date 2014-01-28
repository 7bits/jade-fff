package com.recruiters.web.utils;

import de.neuland.jade4j.JadeConfiguration;
import de.neuland.jade4j.spring.view.JadeView;
import org.springframework.beans.BeanUtils;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Locale;

/**
 * Custom Jade View Resolver
 * Created because Jade is using UrlBasedViewResolver directly
 * ignoring exposing model attributes settings
 */

public class CustomJadeViewResolver extends InternalResourceViewResolver {

    private JadeConfiguration configuration;
    private boolean renderExceptions = false;
    private String contentType = "text/html;charset=UTF-8";

    private Boolean exposePathVariables = false;

    public CustomJadeViewResolver() {
        setViewClass(requiredViewClass());
    }

    @Override
    @SuppressWarnings("rawtypes")
    protected Class requiredViewClass() {
        return JadeView.class;
    }

    /**
     * Building Jade View
     * @param viewName    View Name
     * @return View
     * @throws Exception if something goes wrong
     */
    @Override
    protected AbstractUrlBasedView buildView(final String viewName) throws Exception {
        JadeView view = (JadeView) buildViewJob(viewName);
        view.setConfiguration(this.configuration);
        view.setContentType(contentType);
        view.setRenderExceptions(renderExceptions);
        return view;
    }

    /**
     * Creating View
     * We are setting default exposing model behaviour for redirect
     * @param viewName    View Name
     * @param locale      Locale
     * @return View
     * @throws Exception if something goes wrong
     */
    @Override
    protected View createView(final String viewName, final Locale locale) throws Exception {
        View view = super.createView(viewName, locale);
        if (view instanceof RedirectView) {
            ((RedirectView) view).setExposeModelAttributes(exposePathVariables);
        }
        return view;
    }

    /**
     * Copied from
     * @link org.springframework.web.servlet.view.UrlBasedViewResolver
     * @param viewName    View Name
     * @return View
     * @throws Exception if something goes wrong
     */
    private AbstractUrlBasedView buildViewJob(final String viewName) throws Exception {
        AbstractUrlBasedView view = (AbstractUrlBasedView) BeanUtils.instantiateClass(getViewClass());
        view.setUrl(getPrefix() + viewName + getSuffix());
        String contentType = getContentType();
        if (contentType != null) {
            view.setContentType(contentType);
        }
        view.setRequestContextAttribute(getRequestContextAttribute());
        view.setAttributesMap(getAttributesMap());
        if (this.exposePathVariables != null) {
            view.setExposePathVariables(exposePathVariables);
        }
        return view;
    }

    public boolean isExposePathVariables() {
        return exposePathVariables;
    }

    public void setExposePathVariables(final boolean exposePathVariables) {
        this.exposePathVariables = exposePathVariables;
    }

    public void setConfiguration(final JadeConfiguration configuration) {
        this.configuration = configuration;
    }

    public void setRenderExceptions(final boolean renderExceptions) {
        this.renderExceptions = renderExceptions;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(final String contentType) {
        this.contentType = contentType;
    }
}