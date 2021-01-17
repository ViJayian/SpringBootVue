package org.vijayian.support;

import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * servlet filter listener整合
 *
 * @author ViJay
 * @date 2021-01-17
 */
public class Web3Support {
    //>> TODO 这种方式配置必须是静态内部类.
    @WebServlet("/my")
    static class MyServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            System.out.println(req.getParameter("name"));
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            doGet(req, resp);
        }
    }

    @WebFilter("/*")
    static class MyFilter implements Filter {

        @Override
        public void init(FilterConfig filterConfig) throws ServletException {
            System.out.println("init");
        }

        @Override
        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
            System.out.println("doFilter");
            filterChain.doFilter(servletRequest, servletResponse);
        }

        @Override
        public void destroy() {
            System.out.println("destory");
        }
    }

    @WebListener
    static class MyListener implements ServletRequestListener{

        @Override
        public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
            System.out.println("destroy");
        }

        @Override
        public void requestInitialized(ServletRequestEvent servletRequestEvent) {
            System.out.println("event");
        }
    }
}
