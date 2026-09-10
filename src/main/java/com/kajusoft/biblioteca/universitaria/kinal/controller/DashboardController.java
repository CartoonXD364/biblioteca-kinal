package main.java.com.kajusoft.biblioteca.universitaria.kinal.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.com.kajusoft.biblioteca.universitaria.kinal.dao.book.AuthorDAO;
import main.java.com.kajusoft.biblioteca.universitaria.kinal.dao.book.BookDAO;
import main.java.com.kajusoft.biblioteca.universitaria.kinal.model.book.Author;
import main.java.com.kajusoft.biblioteca.universitaria.kinal.model.book.Book;
import main.java.com.kajusoft.biblioteca.universitaria.kinal.model.user.Role;
import main.java.com.kajusoft.biblioteca.universitaria.kinal.util.SceneManager;

public class DashboardController implements Initializable {
    
    private BookDAO bookDAO;
    private AuthorDAO authorDAO;
    
    private SceneManager sceneManager;  
    
    @FXML
    private TextField txtIsbn;
    @FXML
    private TextField txtTitle;
    @FXML
    private ComboBox<Author> cmbAuthor;
    @FXML
    private TextField txtEditorial;
    @FXML
    private TextField txtPublicationYear;
    @FXML
    private TextField txtCopiesAvailable;
    
    @FXML
    private TableView<Book>tableBook;
    @FXML
    private TableColumn<Book, Integer> idBookColumn;
    @FXML
    private TableColumn<Book, String> isbnColumn;
    @FXML
    private TableColumn<Book, String> titleColumn;
    @FXML
    private TableColumn<Book, String> authorColumn;
    @FXML
    private TableColumn<Book, String> editorialColumn;
    @FXML
    private TableColumn<Book, Integer> publicationYearColumn;
    @FXML 
    private TableColumn<Book, Integer> copiesAvailableColumn;
    
    public DashboardController(BookDAO bookDAO, AuthorDAO authorDAO, SceneManager sceneManager) {
        this.bookDAO = bookDAO;
        this.authorDAO = authorDAO;
        this.sceneManager = sceneManager;
    }
    
    @FXML
    private void handleLoadDataTableView(){
        
        idBookColumn.setCellValueFactory(
                new PropertyValueFactory<>("idBook"));
        
        isbnColumn.setCellValueFactory(
                new PropertyValueFactory<>("isbn"));
        
        titleColumn.setCellValueFactory(
                new PropertyValueFactory<>("title"));
        
        authorColumn.setCellValueFactory(
                new PropertyValueFactory<>("idAuthor"));
        
        editorialColumn.setCellValueFactory(
                new PropertyValueFactory<>("editorial"));
        
        publicationYearColumn.setCellValueFactory(
                new PropertyValueFactory<>("publicationYear"));
        
        copiesAvailableColumn.setCellValueFactory(
                new PropertyValueFactory<>("copiesAvailable"));
        
        tableBook.setItems(bookDAO.list());
    }
    
    private void handleSelectBook() {
        // Escucha la selección de la tabla y rellena los inputs de la izquierda
        tableBook.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedBook) -> {
            if (selectedBook != null) {
                txtIsbn.setText(selectedBook.getIsbn());
                txtTitle.setText(selectedBook.getTitle());
                txtEditorial.setText(selectedBook.getEditorial());
                txtPublicationYear.setText(String.valueOf(selectedBook.getPublicationYear()));
                txtCopiesAvailable.setText(String.valueOf(selectedBook.getCopiesAvailable()));
                
                if (cmbAuthor != null) {
                    cmbAuthor.setValue(selectedBook.getIdAuthor());
                }
            }
        });
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        handleLoadDataTableView();
        handleSelectBook();
    }    
    
}
