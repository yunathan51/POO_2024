package mercado;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class Conexao {

    public static Connection conectaMySQL() {
        Connection conn = null;
        try {
            Properties props = new Properties();
            props.load(new FileInputStream("src/mercado/login.properties"));
            conn = DriverManager.getConnection(
                    props.getProperty("url"),
                    props.getProperty("username"),
                    props.getProperty("password")
            );
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
