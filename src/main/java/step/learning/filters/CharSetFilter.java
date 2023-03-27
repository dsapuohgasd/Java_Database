package step.learning.filters;

import com.google.inject.Singleton;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@Singleton
public class CharSetFilter implements Filter {
    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletResponse.setCharacterEncoding("UTF-8");
        servletRequest.setCharacterEncoding("UTF-8");
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("set UTF-8");
    }

    @Override
    public void destroy() {
        this.filterConfig = null;
    }
}