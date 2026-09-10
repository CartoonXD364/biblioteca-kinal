package main.java.com.kajusoft.biblioteca.universitaria.kinal.model.user;

public class User {
    
    private String idUser;
    private String userName;
    private String userLastName;
    private String email;
    private String password;
    private Role role; // Cambiado de 'idRol' a 'role' para representar el objeto

    public User() {
    }

    public User(String idUser, String userName, String userLastName, String email, String password, Role role) {
        this.idUser = idUser;
        this.userName = userName;
        this.userLastName = userLastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}