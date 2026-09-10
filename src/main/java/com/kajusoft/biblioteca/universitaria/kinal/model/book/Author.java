package main.java.com.kajusoft.biblioteca.universitaria.kinal.model.book;

public class Author {
    
    private int idAuthor;
    private String authorName;
    private String authorLastName;

    public Author(int idAuthor, String authorName, String authorLastName) {
        this.idAuthor = idAuthor;
        this.authorName = authorName;
        this.authorLastName = authorLastName;
    }

    public int getIdAuthor() {
        return idAuthor;
    }

    public void setIdAuthor(int idAuthor) {
        this.idAuthor = idAuthor;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorLastName() {
        return authorLastName;
    }

    public void setAuthorLastName(String authorLastName) {
        this.authorLastName = authorLastName;
    }
    
    @Override
    public String toString() {
        return authorName + " " + authorLastName;
    }
    
}

