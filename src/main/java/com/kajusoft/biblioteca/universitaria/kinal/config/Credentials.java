package main.java.com.kajusoft.biblioteca.universitaria.kinal.config;

public class Credentials {
    
    public static final String URL_DATA_BASE = System.getenv("URL_MYSQL_DB") + "/biblioteca_kinal_in4bv";
    public static final String USER_DATA_BASE = System.getenv("USER_MYSQL_DB");
    public static final String PASS_DATA_BASE = System.getenv("PASS_MYSQL_DB");
    
    private Credentials(){}
    
}
