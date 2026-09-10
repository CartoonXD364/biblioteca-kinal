package main.java.com.kajusoft.biblioteca.universitaria.kinal.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import main.java.com.kajusoft.biblioteca.universitaria.kinal.controller.DashboardController;
import main.java.com.kajusoft.biblioteca.universitaria.kinal.controller.LoginController;
import main.java.com.kajusoft.biblioteca.universitaria.kinal.dao.book.AuthorDAO;
import main.java.com.kajusoft.biblioteca.universitaria.kinal.dao.book.BookDAO;
import main.java.com.kajusoft.biblioteca.universitaria.kinal.dao.user.UserDAO;

public class SceneManager {
    
    private final Stage stage;
    private final String FXML_PATH = "/main/resources/view/";
    
    public SceneManager(Stage stage){
        this.stage = stage;
    }
    
    public void showLoginView()throws Exception{
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_PATH + "login-view.fxml"));
        
        loader.setControllerFactory(
        clazz -> {
            if(clazz == LoginController.class){
                UserDAO userDAO = new UserDAO();
                return new LoginController(userDAO, this);
            }
            
            try{
                return clazz.getDeclaredConstructor().newInstance();
            }catch(Exception e){
                throw new RuntimeException("Error al generar el constructor");
            }
            
        });
        
        Parent root = loader.load();
        Scene scene = new Scene(root, 600, 600);
        stage.setMinHeight(500);
        stage.setMinWidth(500);
        stage.setScene(scene);  
        stage.centerOnScreen();
        stage.show();
        
    }
    
    public void showDashboardView()throws Exception{
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML_PATH + "dashboard-view.fxml"));
        
        loader.setControllerFactory(
        clazz -> {
            if(clazz == DashboardController.class){
                BookDAO bookDAO = new BookDAO();
                AuthorDAO authorDAO = new AuthorDAO();
                return new DashboardController(bookDAO, authorDAO, this);
            }
            
            try{
                return clazz.getDeclaredConstructor().newInstance();
            }catch(Exception e){
                throw new RuntimeException("Error al generar el constructor");
            }
            
        });
        
        Parent root = loader.load();
        Scene scene = new Scene(root, 925, 600);
        stage.setMinHeight(500);
        stage.setMinWidth(500);
        stage.setScene(scene);  
        stage.centerOnScreen();
        stage.show();
        
    }
    
    public void showAlertInfo(Alert.AlertType type, String head, String title, String content){
        Alert alert = new Alert(type);
        alert.initOwner(this.stage);
        alert.setTitle(title);
        alert.setHeaderText(head);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
}
