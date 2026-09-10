package main.java.com.kajusoft.biblioteca.universitaria.kinal.dao.book;

import javafx.collections.ObservableList;
import main.java.com.kajusoft.biblioteca.universitaria.kinal.model.book.Book;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import main.java.com.kajusoft.biblioteca.universitaria.kinal.config.DataBaseConnection;
import main.java.com.kajusoft.biblioteca.universitaria.kinal.model.book.Author;

public class BookDAO {
    
    public ObservableList<Book> list(){
        
        String sql = "select b.*, a.author_name, a.author_last_name from books b inner join authors a on b.id_author = a.id_author;";
        
        try(PreparedStatement pstm = DataBaseConnection.getDBConnection().prepareStatement(sql); ResultSet rs = pstm.executeQuery();){
            
            ObservableList<Book> list = FXCollections.observableArrayList();
            
            while(rs.next()){
                
                Author author = new Author(
                        rs.getInt("id_author"),
                        rs.getString("author_name"),
                        rs.getString("author_last_name")
                );
                
                list.add(new Book(
                        rs.getInt("id_book"),
                        rs.getString("isbn"),
                        rs.getString("title"),
                        author,
                        rs.getString("editorial"),
                        rs.getInt("publication_year"),
                        rs.getInt("copies_available")
                ));
                
            }
            
            return list;
        }catch(SQLException e){
            throw new RuntimeException("Error en la consulta");
        }
        
    }
    
    // Método sobrecargado para guardar directamente desde el objeto Book mandando su ID
    public boolean saveBook(Book book){
        return saveBook(book.getIdBook(), book.getIsbn(), book.getTitle(), book.getIdAuthor(), book.getEditorial(), book.getPublicationYear(), book.getCopiesAvailable());
    }
    
    // Recibe el idBook explícitamente e inserta la columna id_book en la BD
    public boolean saveBook(int idBook, String isbn, String title, Author author, String editorial, int publicationYear, int copiesAvailable){
        
        String sql = "insert into books (id_book, isbn, title, id_author, editorial, publication_year, copies_available) values (?, ?, ?, ?, ?, ?, ?);";
        
        try(PreparedStatement pstm = DataBaseConnection.getDBConnection().prepareStatement(sql);){
            
            pstm.setInt(1, idBook);
            pstm.setString(2, isbn);
            pstm.setString(3, title);
            pstm.setInt(4, author.getIdAuthor());
            pstm.setString(5, editorial);
            pstm.setInt(6, publicationYear);
            pstm.setInt(7, copiesAvailable);
            
            int affectedRows = pstm.executeUpdate();
            
            return affectedRows > 0;
        }catch(SQLException e){
            throw new RuntimeException("Error en la consulta");
        }
        
    }
    
    public boolean deleteBookById(int id){
        
        String sql = "delete from books where id_book = ?;";
        
        try(PreparedStatement pstm = DataBaseConnection.getDBConnection().prepareStatement(sql);){
            
            pstm.setInt(1, id);
            
            int affectedRow = pstm.executeUpdate();
            
            return affectedRow > 0;
        }catch(SQLException e){
            throw new RuntimeException("Error en la consulta");
        }
    }
    
    // Sobrecarga para actualizar recibiendo directamente un objeto Book
    public boolean updateBookById(Book book){
        return updateBookById(book.getIdBook(), book.getIsbn(), book.getTitle(), book.getIdAuthor(), book.getEditorial(), book.getPublicationYear(), book.getCopiesAvailable());
    }

    // Actualizar pasando el idBook al inicio como parámetro principal
    public boolean updateBookById(int idBook, String isbn, String title, Author author, String editorial, int publicationYear, int copiesAvailable){
        
        String sql = "update books set isbn = ?, title = ?, id_author = ?, editorial = ?, publication_year = ?, copies_available = ? where id_book = ?;";
        
        try(PreparedStatement pstm = DataBaseConnection.getDBConnection().prepareStatement(sql)){
            
            pstm.setString(1, isbn);
            pstm.setString(2, title);
            pstm.setInt(3, author.getIdAuthor());
            pstm.setString(4, editorial);
            pstm.setInt(5, publicationYear);
            pstm.setInt(6, copiesAvailable);
            pstm.setInt(7, idBook);
            
            int affectedRows = pstm.executeUpdate();
            
            return affectedRows > 0;
        }catch(SQLException e){
            throw new RuntimeException("Error en la consulta");
        }
        
    }
    
}