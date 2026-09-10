package main.java.com.kajusoft.biblioteca.universitaria.kinal.dao.user;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.com.kajusoft.biblioteca.universitaria.kinal.config.DataBaseConnection;
import main.java.com.kajusoft.biblioteca.universitaria.kinal.model.user.Role;

public class RoleDAO {

    public ObservableList<Role> list() {

        String sql = "select * from roles;";

        try (PreparedStatement pstm = DataBaseConnection.getDBConnection().prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {

            ObservableList<Role> list = FXCollections.observableArrayList();

            while (rs.next()) {

                list.add(new Role(
                        rs.getInt("id_role"),
                        rs.getString("role_name")
                ));

            }

            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Error en la consulta", e);
        }

    }

    public String getRoleById(int id) {

        String sql = "select role_name from roles where id_role = ?;";

        // Se agrega el ResultSet al try-with-resources para evitar fuga de recursos.
        try (PreparedStatement pstm = DataBaseConnection.getDBConnection().prepareStatement(sql)) {

            pstm.setInt(1, id);

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

}