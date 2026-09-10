package main.java.com.kajusoft.biblioteca.universitaria.kinal.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.java.com.kajusoft.biblioteca.universitaria.kinal.dao.user.UserDAO;
import main.java.com.kajusoft.biblioteca.universitaria.kinal.util.SceneManager;

public class LoginController implements Initializable {

    private static final String ROL_BIBLIOTECARIO = "Bibliotecario";

    private UserDAO userDAO;
    private SceneManager sceneManager;

    @FXML
    private TextField txtFieldEmail;
    @FXML
    private PasswordField txtFieldPassword;

    public LoginController(UserDAO userDAO, SceneManager sceneManager) {
        this.userDAO = userDAO;
        this.sceneManager = sceneManager;
    }
    @FXML
    private void handleLogin() {

        String email = txtFieldEmail.getText().trim();
        String password = txtFieldPassword.getText();

        try {
            // 1. Validar correo vacío
            if (email.isEmpty()) {
                sceneManager.showAlertInfo(Alert.AlertType.WARNING, "Campo requerido", "Ingrese su correo.", "");
                txtFieldEmail.requestFocus();
                return;
            }

            // 2. Validar formato del correo
            if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                sceneManager.showAlertInfo(Alert.AlertType.WARNING, "Correo inválido", "Ingrese un correo válido.", "");
                txtFieldEmail.requestFocus();
                return;
            }

            // 3. Validar contraseña vacía
            if (password.isEmpty()) {
                sceneManager.showAlertInfo(Alert.AlertType.WARNING, "Campo requerido", "Ingrese su contraseña.", "");
                txtFieldPassword.requestFocus();
                return;
            }

            // 4. Validar credenciales contra la base de datos
            boolean isValidUser = userDAO.searchUserByEmail(email, password);

            if (!isValidUser) {
                sceneManager.showAlertInfo(Alert.AlertType.ERROR, "Inicio de sesión fallido", "Credenciales inválidas", "Correo o contraseña incorrectos.");
                txtFieldPassword.clear();
                txtFieldPassword.requestFocus();
                return;
            }

            // 5. Autorizar el acceso según el rol del usuario.
            // Caso 5 (Biblioteca Universitaria): solo el Bibliotecario administra el catálogo de libros.
            String roleName = userDAO.getRoleNameByEmail(email);

            if (ROL_BIBLIOTECARIO.equalsIgnoreCase(roleName)) {
                sceneManager.showAlertInfo(Alert.AlertType.INFORMATION, "Bienvenido", "Acceso concedido", "¡Inicio de sesión exitoso!");
                sceneManager.showDashboardView();
            } else {
                sceneManager.showAlertInfo(Alert.AlertType.WARNING, "Acceso restringido", "Permisos insuficientes",
                        "Solo el rol Bibliotecario puede gestionar el catálogo bibliográfico.");
                txtFieldPassword.clear();
            }

        } catch (Exception e) {
            sceneManager.showAlertInfo(Alert.AlertType.ERROR, "Error", "Ocurrió un error inesperado", e.getMessage());
        }
    }

    @FXML
    private void handleGoToRegister() {
        try {
            sceneManager.showRegisterView();
        } catch (Exception e) {
            sceneManager.showAlertInfo(Alert.AlertType.ERROR, "Error", "No se pudo abrir el registro", e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Sin inicialización adicional requerida.
    }
}