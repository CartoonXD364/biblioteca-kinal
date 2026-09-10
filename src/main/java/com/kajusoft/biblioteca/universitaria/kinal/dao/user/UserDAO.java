package main.java.com.kajusoft.biblioteca.universitaria.kinal.dao.user;

import javafx.collections.ObservableList;
import main.java.com.kajusoft.biblioteca.universitaria.kinal.model.user.User;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import main.java.com.kajusoft.biblioteca.universitaria.kinal.config.DataBaseConnection;
import main.java.com.kajusoft.biblioteca.universitaria.kinal.model.user.Role;

public class UserDAO {

    public boolean searchUserByEmail(String email, String password) {

        String sql = "select email from users where email = ? and password_hash = ?";

        try (PreparedStatement pstm = DataBaseConnection.getDBConnection().prepareStatement(sql)) {

            pstm.setString(1, email);
            pstm.setString(2, password);

            // Usamos executeQuery() para lecturas SELECT
            try (ResultSet rs = pstm.executeQuery()) {
                return rs.next(); // Retorna true si encontró coincidencia
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error en la consulta: " + e.getMessage(), e);
        }
    }

    // Obtiene el nombre del rol asociado a un correo, usado para autorizar el acceso a módulos.
    public String getRoleNameByEmail(String email) {

        String sql = "select r.role_name from users u inner join roles r on u.id_role = r.id_role where u.email = ?;";

        try (PreparedStatement pstm = DataBaseConnection.getDBConnection().prepareStatement(sql)) {

            pstm.setString(1, email);

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("role_name");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error en la consulta", e);
        }

        return null;
    }

    public ObservableList<User> list() {

        String sql = "select u.*, r.role_name from users u inner join roles r on u.id_role = r.id_role;";

        try (PreparedStatement pstm = DataBaseConnection.getDBConnection().prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {

            ObservableList<User> list = FXCollections.observableArrayList();

            while (rs.next()) {

                Role role = new Role(
                        rs.getInt("id_role"),
                        rs.getString("role_name")
                );

                list.add(new User(
                        rs.getString("id_user"),
                        rs.getString("user_name"),
                        rs.getString("user_last_name"),
                        rs.getString("email"),
                        rs.getString("password_hash"),
                        role));
            }

            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Error en la consulta", e);
        }
    }

    public boolean saveUser(String userName, String userLastName, String email, String password, Role role) {

        String sql = "insert into users (id_user, user_name, user_last_name, email, password_hash, id_role) values(UUID(), ?, ?, ?, ?, ?)";

        try (PreparedStatement pstm = DataBaseConnection.getDBConnection().prepareStatement(sql)) {

            pstm.setString(1, userName);
            pstm.setString(2, userLastName);
            pstm.setString(3, email);
            pstm.setString(4, password);
            pstm.setInt(5, role.getIdRole());

            int affectedRows = pstm.executeUpdate();

            return affectedRows > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error en la consulta", e);
        }
    }

    public boolean deleteUserByEmail(String email) {

        String sql = "delete from users where email = ?;";

        try (PreparedStatement pstm = DataBaseConnection.getDBConnection().prepareStatement(sql)) {

            pstm.setString(1, email);

            int affectedRow = pstm.executeUpdate();

            return affectedRow > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error en la consulta", e);
        }
    }

    public boolean updateUserByEmail(String userName, String userLastName, String password, Role role, String email) {

        String sql = "update users set user_name = ?, user_last_name = ?, password_hash = ?, id_role = ? where email = ?";

        try (PreparedStatement pstm = DataBaseConnection.getDBConnection().prepareStatement(sql)) {

            pstm.setString(1, userName);
            pstm.setString(2, userLastName);
            pstm.setString(3, password);
            pstm.setInt(4, role.getIdRole());
            pstm.setString(5, email);

            int affectedRows = pstm.executeUpdate();

            return affectedRows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error en la consulta", e);
        }

    }
}