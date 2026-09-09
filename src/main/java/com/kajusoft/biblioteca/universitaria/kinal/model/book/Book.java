package main.java.com.kajusoft.biblioteca.universitaria.kinal.model.book;

public class Book {
    
    private int idBook;
    private String isbn;
    private String title;
    private Author idAuthor;
    private String editorial;
    private int publicationYear;
    private int copiesAvailable;

    public Book(int idBook, String isbn, String title, Author idAuthor, String editorial, int publicationYear, int copiesAvailable) {
        this.idBook = idBook;
        this.isbn = isbn;
        this.title = title;
        this.idAuthor = idAuthor;
        this.editorial = editorial;
        this.publicationYear = publicationYear;
        this.copiesAvailable = copiesAvailable;
    }

    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getIdAuthor() {
        return idAuthor;
    }

    public void setIdAuthor(Author idAuthor) {
        this.idAuthor = idAuthor;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public int getCopiesAvailable() {
        return copiesAvailable;
    }

    public void setCopiesAvailable(int copiesAvailable) {
        this.copiesAvailable = copiesAvailable;
    }
    
}
