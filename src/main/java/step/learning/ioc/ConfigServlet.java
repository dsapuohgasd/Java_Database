package step.learning.ioc;

import com.google.inject.servlet.ServletModule;
import step.learning.servlets.*;
import step.learning.filters.CharSetFilter;
import step.learning.filters.*;

public class ConfigServlet extends ServletModule {
    @Override
    protected void configureServlets() {
        // Программная замена web.xml - конфигурация фильтров ...
        filter( "/*" ).through( CharSetFilter.class ) ;
        filter( "/*" ).through( DataFilter.class ) ;
        filter( "/*" ).through( AuthFilter.class ) ;
        filter( "/*" ).through( DemoFilter.class ) ;

        // ...  и сервлетов
        serve( "/filters" ).with( FiltersServlet.class ) ;
        serve( "/servlets" ).with( ViewServlet.class ) ;
        serve( "/registration" ).with( RegistrationServlet.class ) ;
        serve( "/register/" ).with( RegUserServlet.class ) ;
        serve("/image/*").with(DownloadServlet.class);
        serve("/profile/").with(ProfileServlet.class);
        serve("/checkmail/").with(CheckMailServlet.class);
        serve("/cars/").with(CarServlet.class);
        serve( "/" ).with( HomeServlet.class ) ;

    }
}