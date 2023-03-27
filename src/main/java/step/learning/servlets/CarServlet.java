package step.learning.servlets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.checkerframework.checker.units.qual.C;
import step.learning.dao.CarDAO;
import step.learning.dao.UserDAO;
import step.learning.entities.Car;
import step.learning.entities.User;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Singleton
public class CarServlet extends HttpServlet {
    @Inject private CarDAO carDAO ;



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Проверяем, есть ли сохраненные в сессии данные от предыдущей обработки
        HttpSession session = req.getSession() ;
        String addError = (String) session.getAttribute( "addError" ) ;

        // Есть сообщение об ошибке
        if( addError != null ) {
            req.setAttribute( "addError", addError ) ;
            session.removeAttribute( "addError" ) ;  // удаляем сообщение из сессии
        }

        // Загрузка списка машин
        try {
           List<Car> cars = carDAO.loadCars();
           req.setAttribute("cars",cars);
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }

        req.setAttribute( "pageBody", "cars.jsp" ) ;
        req.getRequestDispatcher( "/WEB-INF/_layout.jsp" )
                .forward( req, resp ) ;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Car buffCar = new Car();
        HttpSession session = req.getSession() ;
        String errorMessage = null ;

        // Прием данных от формы регистрации
        String carModel= req.getParameter( "carModel" ) ;
        String carBrand = req.getParameter( "carBrand" ) ;
        String carYear = req.getParameter( "carYear" ) ;
        String carColor = req.getParameter( "carColor" ) ;
        String carPrice = req.getParameter( "carPrice" ) ;
        String carDescription = req.getParameter( "carDescription" ) ;

        // Валидация данных
        try {
            if( carModel == null || carModel.isEmpty() ) {
                throw new Exception( "Model could not be empty" ) ;
            }
            if( carBrand == null || carBrand.isEmpty() ) {
                throw new Exception( "Brand could not be empty" ) ;
            }
            if( carYear == null || carYear.isEmpty() ) {
                throw new Exception( "Year could not be empty" ) ;
            }
            if( carColor == null || carColor.isEmpty() ) {
                throw new Exception( "Year could not be empty" ) ;
            }
            if( carPrice == null || carPrice.isEmpty() ) {
                throw new Exception( "Price could not be empty" ) ;
            }
            if( carDescription == null || carDescription.isEmpty() ) {
                throw new Exception( "Description could not be empty" ) ;
            }

            buffCar.setModel(carModel);
            buffCar.setBrand(carBrand);
            buffCar.setYear(Integer.parseInt(carYear));
            buffCar.setColor(carColor);
            buffCar.setPrice(Double.parseDouble(carPrice));
            buffCar.setDescription(carDescription);
            if( !carDAO.add( buffCar ) ) {
                throw new Exception( "Server error, try later" ) ;
            }
        }
        catch( Exception ex ) {
           errorMessage = ex.getMessage();
        }
        System.out.println(errorMessage);
        // Проверяем успешность валидации
        if(errorMessage!=null){
            session.setAttribute( "addError", errorMessage);
        }
        resp.sendRedirect( req.getRequestURI()) ;
    }
}

