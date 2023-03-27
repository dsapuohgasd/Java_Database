package step.learning.servlets;
import com.google.inject.Singleton;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Singleton
public class RegistrationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding( "UTF-8" ) ;
        HttpSession session = req.getSession() ;
        String email = (String) session.getAttribute( "email" ) ;
        String psw = (String) session.getAttribute( "psw" ) ;
        String pswrepeat = (String) session.getAttribute( "psw-repeat" ) ;
        req.setAttribute( "email", email ) ;
        req.setAttribute( "psw", psw ) ;
        req.setAttribute( "psw-repeat", pswrepeat ) ;
        if( email != null ) {
            session.removeAttribute( "email" ) ;
        }
        if( psw != null ) {
            session.removeAttribute( "psw" ) ;
        }
        if( pswrepeat != null ) {
            session.removeAttribute( "psw-repeat" ) ;
        }
        req.getRequestDispatcher( "WEB-INF/registration.jsp" ).forward( req, resp ) ;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding( "UTF-8" ) ;
        String email = req.getParameter( "email" ) ;
        String psw = req.getParameter( "psw" ) ;
        String pswrepeat = req.getParameter( "psw-repeat" ) ;

        req.getSession().setAttribute( "email", email ) ;
        req.getSession().setAttribute( "psw", psw ) ;
        req.getSession().setAttribute( "psw-repeat", pswrepeat ) ;
        resp.sendRedirect( req.getRequestURI() ) ;
    }
}
