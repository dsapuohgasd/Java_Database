package step.learning.filters;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import step.learning.services.DataService;
import step.learning.services.MysqlDataService;

import javax.servlet.*;
import java.io.IOException;
@Singleton
public class DataFilter implements Filter{
    private FilterConfig filterConfig;
    @Inject
    DataService dataService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (dataService.getConnection() == null) {
            servletRequest.getRequestDispatcher("WEB-INF/static.jsp").forward(servletRequest,servletResponse);

        }else {
            filterChain.doFilter(servletRequest,servletResponse);
        }

    }
    @Override
    public void destroy() {
        this.filterConfig = null;
    }
}

