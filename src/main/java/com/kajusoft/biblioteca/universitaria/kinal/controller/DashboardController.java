package main.java.com.kajusoft.biblioteca.universitaria.kinal.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import main.java.com.kajusoft.biblioteca.universitaria.kinal.dao.book.AuthorDAO;
import main.java.com.kajusoft.biblioteca.universitaria.kinal.dao.book.BookDAO;
import main.java.com.kajusoft.biblioteca.universitaria.kinal.util.SceneManager;

public class DashboardController implements Initializable {
    
    private BookDAO bookDAO;
    private AuthorDAO authorDAO;
    
    private SceneManager sceneManager;  

    public DashboardController(BookDAO bookDAO, AuthorDAO authorDAO, SceneManager sceneManager) {
        this.bookDAO = bookDAO;
        this.authorDAO = authorDAO;
        this.sceneManager = sceneManager;
    }
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
