package banco_dados;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class Conexao {

    public static Connection conectaMySql() {
        Connection conn = null;

        try{
            Properties props = new Properties();
            props.load(new FileInputStream("C:\\Users\\yunat\\IdeaProjects\\projetoClavison\\src\\banco_dados\\login.properties"));
            conn = DriverManager.getConnection(
                    props.getProperty("url"),
                    props.getProperty("username"),
                    props.getProperty("password")
            );

        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de configuração do banco de dados não encontrado");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
