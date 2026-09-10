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
                sceneManager.showAlertInfo(Alert.AlertType.WARNING, "Campo requerido", "Ingrese su Correo.", "");
                txtFieldEmail.requestFocus();
                return;
            }

            // 2. Validar formato del correo
            if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                sceneManager.showAlertInfo(Alert.AlertType.WARNING, "Correo invalido", "Ingrese un correo valido.", "");
                txtFieldEmail.requestFocus();
                return;
            }

            // 3. Validar contraseña vacía
            if (password.isEmpty()) {
                sceneManager.showAlertInfo(Alert.AlertType.WARNING, "Campo requerido", "Ingrese su contraseña.", "");
                txtFieldPassword.requestFocus();
                return;
            }

            // 4. Validar credenciales contra la base de datos (devuelve boolean)
            boolean isValidUser = userDAO.searchUserByEmail(email, password);

            if (isValidUser) {
                sceneManager.showAlertInfo(Alert.AlertType.INFORMATION, "Bienvenido", "Acceso concedido", "Inicio de sesión exitoso!");
                sceneManager.showDashboardView();
            } else {
                sceneManager.showAlertInfo(Alert.AlertType.ERROR, "Inicio de sesión fallido", "Credenciales invalidas", "Correo o contraseñas incorrectos.");
                txtFieldPassword.clear();
                txtFieldPassword.requestFocus();
            }

        } catch(Exception e) {
            sceneManager.showAlertInfo(Alert.AlertType.ERROR, "Error", "Un error inesperado ocurrido", "");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TODO
    }
}