package step.learning.servlets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import step.learning.entities.User;
import step.learning.services.MimeService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

@Singleton
public class ProfileServlet extends HttpServlet {
    @Inject
    private MimeService mimeService;

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User authUser = (User)req.getAttribute("AuthUser");
        if(authUser==null){
            //resp.sendRedirect(req.getContextPath()+"/register/");
            req.setAttribute("pageBody","profile-unauth.jsp");

        }else{
            req.setAttribute("pageBody","profile.jsp");
            req.getRequestDispatcher("/WEB-INF/_layout.jsp").forward(req,resp);
        }
    }
}
