package main.java.com.kajusoft.biblioteca.universitaria.kinal.controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.com.kajusoft.biblioteca.universitaria.kinal.dao.book.AuthorDAO;
import main.java.com.kajusoft.biblioteca.universitaria.kinal.dao.book.BookDAO;
import main.java.com.kajusoft.biblioteca.universitaria.kinal.model.book.Author;
import main.java.com.kajusoft.biblioteca.universitaria.kinal.model.book.Book;
import main.java.com.kajusoft.biblioteca.universitaria.kinal.util.SceneManager;

public class DashboardController implements Initializable {

    private BookDAO bookDAO;
    private AuthorDAO authorDAO;
    private SceneManager sceneManager;

    @FXML
    private ComboBox<String> cmbAction;
    @FXML
    private Button btnExecuteAction;

    @FXML
    private Label lblIdBook;
    @FXML
    private TextField txtIdBook;
    @FXML
    private Label lblIsbn;
    @FXML
    private TextField txtIsbn;
    @FXML
    private Label lblTitle;
    @FXML
    private TextField txtTitle;
    @FXML
    private Label lblAuthor;
    @FXML
    private ComboBox<Author> cmbAuthor;
    @FXML
    private Label lblEditorial;
    @FXML
    private TextField txtEditorial;
    @FXML
    private Label lblPublicationYear;
    @FXML
    private TextField txtPublicationYear;
    @FXML
    private Label lblCopiesAvailable;
    @FXML
    private TextField txtCopiesAvailable;

    @FXML
    private TableView<Book> tableBook;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // 1. Configurar ComboBox de acciones
        cmbAction.setItems(FXCollections.observableArrayList("Crear", "Actualizar", "Eliminar"));
        cmbAction.setValue("Actualizar");

        // 2. Listener para alternar el formulario según la acción
        cmbAction.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                configureFormView(newVal);
            }
        });

        // 3. Cargar lista de autores e inicializar la tabla
        if (authorDAO != null) {
            cmbAuthor.setItems(authorDAO.list());
        }
        handleLoadDataTableView();
        handleSelectBook();
    }

    @FXML
    private void handleLoadDataTableView() {
        idBookColumn.setCellValueFactory(new PropertyValueFactory<>("idBook"));
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("idAuthor"));
        editorialColumn.setCellValueFactory(new PropertyValueFactory<>("editorial"));
        publicationYearColumn.setCellValueFactory(new PropertyValueFactory<>("publicationYear"));
        copiesAvailableColumn.setCellValueFactory(new PropertyValueFactory<>("copiesAvailable"));

        if (bookDAO != null) {
            tableBook.setItems(bookDAO.list());
        }
    }

    private void handleSelectBook() {
        tableBook.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedBook) -> {
            if (selectedBook != null) {
                txtIdBook.setText(String.valueOf(selectedBook.getIdBook()));
                txtIsbn.setText(selectedBook.getIsbn());
                txtTitle.setText(selectedBook.getTitle());
                txtEditorial.setText(selectedBook.getEditorial());
                txtPublicationYear.setText(String.valueOf(selectedBook.getPublicationYear()));
                txtCopiesAvailable.setText(String.valueOf(selectedBook.getCopiesAvailable()));

                if (cmbAuthor != null && selectedBook.getIdAuthor() != null) {
                    cmbAuthor.setValue(selectedBook.getIdAuthor());
                }
            }
        });
    }

    @FXML
    private void handleExecuteAction() {
        String currentAction = cmbAction.getValue();

        if ("Crear".equals(currentAction)) {
            handleSaveBook();
        } else if ("Actualizar".equals(currentAction)) {
            handleUpdateBook();
        } else if ("Eliminar".equals(currentAction)) {
            handleDeleteBook();
        }
    }

    @FXML
    private void handleSaveBook() {
        try {
            String idText = txtIdBook.getText().trim();
            String isbn = txtIsbn.getText().trim();
            String title = txtTitle.getText().trim();
            String editorial = txtEditorial.getText().trim();
            String yearText = txtPublicationYear.getText().trim();
            String copiesText = txtCopiesAvailable.getText().trim();
            Author selectedAuthor = cmbAuthor.getValue();

            if (idText.isEmpty() || isbn.isEmpty() || title.isEmpty() || editorial.isEmpty() || yearText.isEmpty() || copiesText.isEmpty()) {
                sceneManager.showAlertInfo(Alert.AlertType.WARNING, "Campos requeridos", "Información incompleta", "Por favor, completa todos los campos.");
                return;
            }

            if (selectedAuthor == null) {
                sceneManager.showAlertInfo(Alert.AlertType.WARNING, "Autor requerido", "Selección de autor", "Por favor, selecciona un autor.");
                return;
            }

            int idBook = Integer.parseInt(idText);
            int publicationYear = Integer.parseInt(yearText);
            int copiesAvailable = Integer.parseInt(copiesText);

            // Guardado utilizando el ID ingresado manualmente
            boolean isSaved = bookDAO.saveBook(idBook, isbn, title, selectedAuthor, editorial, publicationYear, copiesAvailable);

            if (isSaved) {
                sceneManager.showAlertInfo(Alert.AlertType.INFORMATION, "Éxito", "Libro registrado", "El libro se guardó correctamente.");
                tableBook.setItems(bookDAO.list());
                clearFormFields();
            } else {
                sceneManager.showAlertInfo(Alert.AlertType.ERROR, "Error", "Fallo al registrar", "No se pudo guardar el libro.");
            }

        } catch (NumberFormatException e) {
            sceneManager.showAlertInfo(Alert.AlertType.ERROR, "Error de formato", "Número inválido", "El ID, Año y Copias deben ser valores enteros.");
        } catch (Exception e) {
            sceneManager.showAlertInfo(Alert.AlertType.ERROR, "Error inesperado", "Ocurrió un error", e.getMessage());
        }
    }

    @FXML
    private void handleUpdateBook() {
        Book selectedBook = tableBook.getSelectionModel().getSelectedItem();

        if (selectedBook == null) {
            sceneManager.showAlertInfo(Alert.AlertType.WARNING, "Selección requerida", "Ningún libro seleccionado", "Por favor, selecciona un libro de la tabla.");
            return;
        }

        try {
            String idText = txtIdBook.getText().trim();
            String isbn = txtIsbn.getText().trim();
            String title = txtTitle.getText().trim();
            String editorial = txtEditorial.getText().trim();
            String yearText = txtPublicationYear.getText().trim();
            String copiesText = txtCopiesAvailable.getText().trim();
            Author selectedAuthor = cmbAuthor.getValue();

            if (idText.isEmpty() || isbn.isEmpty() || title.isEmpty() || editorial.isEmpty() || yearText.isEmpty() || copiesText.isEmpty()) {
                sceneManager.showAlertInfo(Alert.AlertType.WARNING, "Campos requeridos", "Información incompleta", "Por favor, completa todos los campos.");
                return;
            }

            if (selectedAuthor == null) {
                sceneManager.showAlertInfo(Alert.AlertType.WARNING, "Autor requerido", "Selección de autor", "Por favor, selecciona un autor.");
                return;
            }

            int idBook = Integer.parseInt(idText);
            int publicationYear = Integer.parseInt(yearText);
            int copiesAvailable = Integer.parseInt(copiesText);

            boolean isUpdated = bookDAO.updateBookById(idBook, isbn, title, selectedAuthor, editorial, publicationYear, copiesAvailable);

            if (isUpdated) {
                sceneManager.showAlertInfo(Alert.AlertType.INFORMATION, "Éxito", "Libro actualizado", "Los datos se modificaron correctamente.");
                tableBook.setItems(bookDAO.list());
            } else {
                sceneManager.showAlertInfo(Alert.AlertType.ERROR, "Error", "Fallo al actualizar", "No se pudo modificar el libro.");
            }

        } catch (NumberFormatException e) {
            sceneManager.showAlertInfo(Alert.AlertType.ERROR, "Error de formato", "Número inválido", "El ID, Año y Copias deben ser números enteros.");
        } catch (Exception e) {
            sceneManager.showAlertInfo(Alert.AlertType.ERROR, "Error inesperado", "Ocurrió un error", e.getMessage());
        }
    }

    @FXML
    private void handleDeleteBook() {
        Book selectedBook = tableBook.getSelectionModel().getSelectedItem();

        if (selectedBook == null) {
            sceneManager.showAlertInfo(Alert.AlertType.WARNING, "Selección requerida", "Ningún libro seleccionado", "Por favor, selecciona el libro a eliminar.");
            return;
        }

        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirmar eliminación");
        confirmAlert.setHeaderText("¿Estás seguro de eliminar este libro?");
        confirmAlert.setContentText("Libro: " + selectedBook.getTitle() + "\nEsta acción no se puede deshacer.");

        Optional<ButtonType> result = confirmAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                boolean isDeleted = bookDAO.deleteBookById(selectedBook.getIdBook());

                if (isDeleted) {
                    sceneManager.showAlertInfo(Alert.AlertType.INFORMATION, "Éxito", "Libro eliminado", "El libro se eliminó correctamente.");
                    tableBook.setItems(bookDAO.list());
                    clearFormFields();
                } else {
                    sceneManager.showAlertInfo(Alert.AlertType.ERROR, "Error", "No se pudo eliminar", "No fue posible eliminar el registro.");
                }

            } catch (Exception e) {
                sceneManager.showAlertInfo(Alert.AlertType.ERROR, "Error de BD", "No se puede eliminar", e.getMessage());
            }
        }
    }

    private void configureFormView(String action) {
        switch (action) {
            case "Crear":
                setFormFieldsVisible(true);
                btnExecuteAction.setText("Guardar");
                clearFormFields();
                break;
            case "Actualizar":
                setFormFieldsVisible(true);
                btnExecuteAction.setText("Actualizar");
                break;
            case "Eliminar":
                setFormFieldsVisible(false);
                btnExecuteAction.setText("Eliminar");
                break;
            default:
                break;
        }
    }

    private void setFormFieldsVisible(boolean visible) {
        lblIdBook.setVisible(visible);            lblIdBook.setManaged(visible);
        txtIdBook.setVisible(visible);            txtIdBook.setManaged(visible);
        lblIsbn.setVisible(visible);              lblIsbn.setManaged(visible);
        txtIsbn.setVisible(visible);              txtIsbn.setManaged(visible);
        lblTitle.setVisible(visible);             txtTitle.setManaged(visible);
        lblAuthor.setVisible(visible);            cmbAuthor.setManaged(visible);
        lblEditorial.setVisible(visible);         txtEditorial.setManaged(visible);
        lblPublicationYear.setVisible(visible);   txtPublicationYear.setManaged(visible);
        lblCopiesAvailable.setVisible(visible);   txtCopiesAvailable.setManaged(visible);
        txtTitle.setVisible(visible);
        cmbAuthor.setVisible(visible);
        txtEditorial.setVisible(visible);
        txtPublicationYear.setVisible(visible);
        txtCopiesAvailable.setVisible(visible);
    }

    private void clearFormFields() {
        txtIdBook.clear();
        txtIsbn.clear();
        txtTitle.clear();
        txtEditorial.clear();
        txtPublicationYear.clear();
        txtCopiesAvailable.clear();
        cmbAuthor.setValue(null);
    }
}