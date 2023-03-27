package step.learning.dao;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import step.learning.entities.User;
import step.learning.hash.HashService;
import step.learning.services.DataService;
import step.learning.services.EmailService;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Singleton
public class UserDAO {
    private final Connection connection;
    private final DataService dataService;
    private final HashService hashService;
    private final EmailService emailService;

    public boolean incEmailCodeAttempts(User user){
        if( user == null || user.getId() == null ) return false ;
        String sql = "UPDATE users u SET u.`email_code_attempts` = u.`email_code_attempts` + 1 WHERE u.`id` = ?";
        try(PreparedStatement statement = dataService.getConnection().prepareStatement(sql)){
            statement.setString(1,user.getId());
            statement.executeUpdate();
        }
        catch( SQLException ex ) {
            System.out.println( "UserDAO::incEmailCodeAttempts" + ex.getMessage() ) ;
            return false ;
        }
        return true;
    }

    public boolean confirmEmail(User user) {
        if (user.getId() == null) return false;

        String sql = "UPDATE Users SET email_code = NULL WHERE id = ?";

        try( PreparedStatement prep = dataService.getConnection().prepareStatement( sql ) ) {
            prep.setString(1, user.getId());
            prep.executeUpdate();
        }
        catch( SQLException ex ) {
            System.out.println( ex.getMessage() ) ;
            return false;
        }
        return true;
    }

    @Inject
    public UserDAO(DataService dataService, HashService hashService, EmailService emailService) {
        this.dataService = dataService;
        this.hashService = hashService;
        this.emailService = emailService;

        this.connection = dataService.getConnection();
    }

    public boolean updateUser( User user ) {
        if( user == null || user.getId() == null ) return false ;

        // Задание: сформировать запрос, учитывая только те данные, которые не null (в user)
        Map<String, String> data = new HashMap<>() ;
        Map<String, Integer> dataNumeric = new HashMap<>() ;
        if( user.getName() != null ) data.put( "name", user.getName() ) ;
        if( user.getLogin() != null ) data.put( "login", user.getLogin() ) ;
        if( user.getAvatar() != null ) data.put( "avatar", user.getAvatar() ) ;
        if( user.getEmail() != null ) {
            // Обновление почты + новый код подтверждения
            user.setEmailCode( UUID.randomUUID().toString().substring( 0, 6 ) ) ;
            data.put( "email", user.getEmail() ) ;
            data.put( "email_code", user.getEmailCode() ) ;
            dataNumeric.put("email_code_attempts",0);
        }if(user.getPass()!=null){ // изменение паролья
            // генерируем соль
            String salt = hashService.hash(UUID.randomUUID().toString());
            // генерируем хеш пароль
            String passHash=this.hashPassword(user.getPass(), salt);
            data.put("pass",passHash);
            data.put("salt",salt);
        }

        String sql = "UPDATE Users u SET " ;
        boolean needComma = false ;
        for( String fieldName : data.keySet() ) {
            sql += String.format( "%c u.`%s` = ?", ( needComma ? ',' : ' ' ), fieldName ) ;
            needComma = true ;
        }
        for (String fieldName:dataNumeric.keySet()){
            sql += String.format( "%c u.`%s` = %d", ( needComma ? ',' : ' ' ), fieldName, dataNumeric.get(fieldName ));
            needComma = true ;
        }
        sql += " WHERE u.`id` = ? " ;
        if( ! needComma ) {  // не было ни одного параметра
            return false ;
        }
        try( PreparedStatement prep = dataService.getConnection().prepareStatement( sql ) ) {
            int n = 1;
            for( String fieldName : data.keySet() ) {
                prep.setString( n, data.get( fieldName ) ) ;
                ++n ;
            }
            prep.setString( n, user.getId() ) ;
            prep.executeUpdate() ;
        }
        catch( SQLException ex ) {
            System.out.println( "UserDAO::updateUser" + ex.getMessage() ) ;
            return false ;
        }
        // Запрос к БД выполнен успешно, если нужно, отправляем код на почту
        if( user.getEmailCode() != null ) {
            String text = String.format(
                    "Hello! Your code is <b>%s</b>",
                    user.getEmailCode() ) ;
            emailService.send( user.getEmail(), "Email confirmation", text ) ;
        }
        return true ;
    }

    public User getUserById(String userId){
        String sql = "SELECT * FROM users u WHERE u.`id` = ?";
        try (PreparedStatement prep = dataService.getConnection().prepareStatement(sql)){
            prep.setString(1,userId);
            ResultSet res = prep.executeQuery();
            if(res.next()){
                return new User(res);
            }

        } catch (Exception e) {
            System.out.println("UserDAO: "+ e.getMessage());
        }
        return null;
    }

    public  String add(User user){
        // генерируем id для новой записи
        String id = UUID.randomUUID().toString();
        // генерируем соль
        String salt = hashService.hash(UUID.randomUUID().toString());
        // генерируем хеш пароль
        String passHash=this.hashPassword(user.getPass(), salt);
        // код на почту
        if (user.getEmail() != null) {
            user.setEmailCode(UUID.randomUUID().toString().substring(0,6));
            if (user.getEmailCode()!= null){
                String text = String.format(
                        "<h2>Hello!</h2><p>Your code is <b>%s</b></p><p>Follow <a href='http://localhost:8080/Webbasics_war_exploded/checkmail/?userid=%s&confirm=%s'>link</a> to confirm email</p>",
                        user.getEmailCode(), user.getId(), user.getEmailCode() ) ;emailService.send(user.getEmail(), "Email confirmation", text);
            }
        }
        String sql = " INSERT INTO users(`id`, `login`, `pass`, `name`,`salt`,`avatar`,`email`,`email_code`) VALUES(?,?,?,?,?,?,?,?)";
        try (PreparedStatement prep = connection.prepareStatement(sql)){
            prep.setString(1,id);
            prep.setString(2,user.getLogin());
            prep.setString(3,passHash);
            prep.setString(4,user.getName());
            prep.setString(5,salt);
            prep.setString(6, user.getAvatar());
            prep.setString(7, user.getEmail());
            prep.setString(8, user.getEmailCode());
            prep.executeUpdate();
        }
        catch (SQLException ex){
            System.out.println(ex.getMessage());
           return null;
        }
        return id;
    }

    public  boolean findLogin(String login){
        String findlogin = "SELECT COUNT(u.`id`) FROM users u WHERE u. `login`=?";
        try (PreparedStatement prep = connection.prepareStatement(findlogin)){
            prep.setString(1,login);
            ResultSet res = prep.executeQuery();
            res.next();
            return res.getInt(1)>0;
        } catch (SQLException e) {
            System.out.println("DB connection error! "+ e.getMessage());
        }
        return false;
    }

    /**
     * Calculates hash (and salt) from password
     * @param password Open password string
     * @return hash for DB table
     */
    public String hashPassword(String password, String salt){
        return hashService.hash(salt + password + salt);
    }

    public User getUserByCredentials(String login, String pass){
        String getUser = "SELECT u.* FROM users u WHERE u.`login`=?";
        try (PreparedStatement prep = connection.prepareStatement(getUser)){
            prep.setString(1, login);
            ResultSet res = prep.executeQuery();
            if(res.next()){
                User user = new User(res);
                System.out.println(user.getSalt());
                if(user.getSalt()!=null){   // с солью
                                            // pass - open, user.pass - hash
                    String expectedHash = this.hashPassword(pass,user.getSalt());
                    if(expectedHash.equals(user.getPass())){
                        return user;
                    }
                }
                else{                       // без соли
                    if(user.getPass().equals(hashService.hash(pass))){
                        return user;
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("DB connection error! "+ e.getMessage());
        }
        return null;
    }

    public boolean isLoginUsed( String login ) {
        String sql = "SELECT COUNT(u.`id`) FROM users u WHERE u.`login`=?" ;
        try( PreparedStatement prep = connection.prepareStatement( sql ) ) {
            prep.setString( 1, login ) ;
            ResultSet res = prep.executeQuery() ;
            res.next() ;
            return res.getInt(1) > 0 ;
        }
        catch( SQLException ex ) {
            System.out.println( ex.getMessage() ) ;
            System.out.println( sql ) ;
            return true ;
        }
    }

    public User getUserByCredentialsOld(String login, String pass){
        String getUser = "SELECT u.* FROM users u WHERE u.`login`=? AND u.`pass`=?";
        try (PreparedStatement prep = connection.prepareStatement(getUser)){
            prep.setString(1, login);
            prep.setString(2, "");
            ResultSet res = prep.executeQuery();
            if(res.next()){
                return new User(res);
            }

        } catch (SQLException e) {
            System.out.println("DB connection error! "+ e.getMessage());
        }
        return null;
    }

}




