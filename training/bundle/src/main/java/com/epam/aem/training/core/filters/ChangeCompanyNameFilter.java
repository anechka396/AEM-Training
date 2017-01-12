package com.epam.aem.training.core.filters;

import java.io.IOException;
import java.util.Dictionary;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Modified;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.sling.SlingFilter;
import org.apache.felix.scr.annotations.sling.SlingFilterScope;
import org.apache.jackrabbit.oak.commons.PropertiesUtil;
import org.osgi.service.component.ComponentContext;

import com.adobe.acs.commons.util.BufferingResponse;

@SlingFilter(generateComponent=true, generateService=true, order = -700, scope = SlingFilterScope.REQUEST)
@Properties({
	@Property(name="sling.filter.pattern", value="/content/geometrixx/.*", propertyPrivate=false),
	@Property(name=ChangeCompanyNameFilter.PROPERTY_NAME, value=ChangeCompanyNameFilter.DEFAULT_NAME)
})
public class ChangeCompanyNameFilter implements Filter {
    
    public static final String OLD_NAME = "Geometrixx"; 
    public static final String DEFAULT_NAME = "Geometrio, LLC";
    public static final String PROPERTY_NAME = "com.epam.company.name";
    
    private String newName;

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response,
            final FilterChain filterChain) throws IOException, ServletException {        
        BufferingResponse bufferingResponse = new BufferingResponse((HttpServletResponse) response);        
        filterChain.doFilter(request, bufferingResponse);    
        response.getWriter().print(bufferingResponse.getContents().replaceAll(OLD_NAME, newName));
    }

    @Activate
    @Modified
    protected void activate(final ComponentContext context) throws Exception {
    	Dictionary<String, Object> props = context.getProperties();
    	newName = PropertiesUtil.toString(props.get(PROPERTY_NAME), DEFAULT_NAME);
    }
    
    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void destroy() {}

}