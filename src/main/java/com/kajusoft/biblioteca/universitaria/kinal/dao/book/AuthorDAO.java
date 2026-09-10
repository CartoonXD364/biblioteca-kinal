package main.java.com.kajusoft.biblioteca.universitaria.kinal.dao.book;

import javafx.collections.ObservableList;
import main.java.com.kajusoft.biblioteca.universitaria.kinal.model.book.Author;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import javafx.collections.FXCollections;
import main.java.com.kajusoft.biblioteca.universitaria.kinal.config.DataBaseConnection;

public class AuthorDAO {
    
    public ObservableList<Author> list(){
        
        String sql = "select * from authors;";
        
        try(PreparedStatement pstm = DataBaseConnection.getDBConnection().prepareStatement(sql); ResultSet rs = pstm.executeQuery();){
            
            ObservableList<Author> list = FXCollections.observableArrayList();
            
            while(rs.next()){
                
                list.add(new Author(
                        rs.getInt("id_author"),
                        rs.getString("author_name"),
                        rs.getString("author_last_name")
                ));
                
            }
            
            return list;
        }catch(SQLException e){
            throw new RuntimeException("Error en la consulta");
        }
        
    }
    
    public boolean saveAuthor(String authorName, String authorLastName){
        
        String sql = "insert into authors (author_name, author_last_name) values(?, ?);";
        
        try(PreparedStatement pstm = DataBaseConnection.getDBConnection().prepareStatement(sql);){
            
            pstm.setString(1, authorName);
            pstm.setString(2, authorLastName);
            
            int affectedRows = pstm.executeUpdate();
            
            return affectedRows > 0;
        }catch(SQLException e){
            throw new RuntimeException("Error en la consulta");
        }
        
    }
    
    public boolean updateAuthorById(String authorName, String authorLastName, int idAuthor){
        
        String sql = "update authors set author_name = ?, author_last_name = ? where id_author = ?;";
        
        try(PreparedStatement pstm = DataBaseConnection.getDBConnection().prepareStatement(sql);){
            
            pstm.setString(1, authorName);
            pstm.setString(2, authorLastName);
            pstm.setInt(3, idAuthor);
            
            int affectedRows = pstm.executeUpdate();
            
            return affectedRows > 0;
        }catch(SQLException e){
            throw new RuntimeException("Error en la consulta");
        }
        
    }
    
}