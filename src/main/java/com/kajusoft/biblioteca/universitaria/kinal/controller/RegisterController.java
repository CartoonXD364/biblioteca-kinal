package main.java.com.kajusoft.biblioteca.universitaria.kinal.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.java.com.kajusoft.biblioteca.universitaria.kinal.dao.user.RoleDAO;
import main.java.com.kajusoft.biblioteca.universitaria.kinal.dao.user.UserDAO;
import main.java.com.kajusoft.biblioteca.universitaria.kinal.model.user.Role;
import main.java.com.kajusoft.biblioteca.universitaria.kinal.util.SceneManager;

public class RegisterController implements Initializable {

    private UserDAO userDAO;
    private RoleDAO roleDAO;
    private SceneManager sceneManager;

    @FXML
    private TextField txtFieldName;
    @FXML
    private TextField txtFieldLastName;
    @FXML
    private TextField txtFieldEmail;
    @FXML
    private PasswordField txtFieldPassword;
    @FXML
    private ComboBox<Role> cmbRole;

    public RegisterController(UserDAO userDAO, RoleDAO roleDAO, SceneManager sceneManager) {
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
        this.sceneManager = sceneManager;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (roleDAO != null) {
            cmbRole.setItems(roleDAO.list());
        }
    }

    @FXML
    private void handleRegister() {

        String name = txtFieldName.getText().trim();
        String lastName = txtFieldLastName.getText().trim();
        String email = txtFieldEmail.getText().trim();
        String password = txtFieldPassword.getText();
        Role selectedRole = cmbRole.getValue();

        try {
            if (name.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                sceneManager.showAlertInfo(Alert.AlertType.WARNING, "Campos requeridos", "Información incompleta", "Por favor, completa todos los campos.");
                return;
            }

            if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                sceneManager.showAlertInfo(Alert.AlertType.WARNING, "Correo inválido", "Ingrese un correo válido.", "");
                txtFieldEmail.requestFocus();
                return;
            }

            if (selectedRole == null) {
                sceneManager.showAlertInfo(Alert.AlertType.WARNING, "Rol requerido", "Selección de rol", "Por favor, selecciona un rol para el usuario.");
                return;
            }

            boolean isSaved = userDAO.saveUser(name, lastName, email, password, selectedRole);

            if (isSaved) {
                sceneManager.showAlertInfo(Alert.AlertType.INFORMATION, "Éxito", "Usuario registrado", "La cuenta se creó correctamente. Ahora puedes iniciar sesión.");
                sceneManager.showLoginView();
            } else {
                sceneManager.showAlertInfo(Alert.AlertType.ERROR, "Error", "Fallo al registrar", "No se pudo crear el usuario.");
            }

        } catch (Exception e) {
            sceneManager.showAlertInfo(Alert.AlertType.ERROR, "Error inesperado", "Ocurrió un error", e.getMessage());
        }
    }

    @FXML
    private void handleGoToLogin() {
        try {
            sceneManager.showLoginView();
        } catch (Exception e) {
            sceneManager.showAlertInfo(Alert.AlertType.ERROR, "Error", "No se pudo volver al inicio de sesión", e.getMessage());
        }
    }
}