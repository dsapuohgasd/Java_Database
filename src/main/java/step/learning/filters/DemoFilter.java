package step.learning.filters;

import com.google.inject.Singleton;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
@Singleton
public class DemoFilter implements Filter {
    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Filter Start");
        servletRequest.setAttribute("DemoFilter","Filter Works!");
        filterChain.doFilter(servletRequest,servletResponse);
        System.out.println("Filter Ends");
    }

    @Override
    public void destroy() {
        this.filterConfig = null;
    }
}
