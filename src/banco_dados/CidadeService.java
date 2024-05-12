package banco_dados;

import banco_dados.model.Cidade;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class CidadeService {

    public static int insereCidade(Cidade cidade) {

        try {
            Connection conn = Conexao.conectaMySql();
            String sql = "INSERT INTO cidade (nome, uf) VALUES (?, ?)";
            PreparedStatement pr = conn.prepareStatement(sql);
            pr.setString(1, cidade.getNome());
            pr.setString(2, cidade.getUf());
            int total = pr.executeUpdate();
            conn.close();
            return total;

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static int alteraCidade(Cidade c) {
        try {
            Connection conn = Conexao.conectaMySql();
            String sql = "update cidade set nome=?, uf=? where idCidade=?";
            PreparedStatement pr = conn.prepareStatement(sql);
            pr.setString(1, c.getNome());
            pr.setString(2, c.getUf());
            pr.setInt(3, c.getId());
            int total = pr.executeUpdate();
            conn.close();
            return total;
        }catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static int deletaCidade(int idCidade) {
        try {
            Connection conn = Conexao.conectaMySql();
            String sql = "DELETE FROM cidade WHERE idCidade = ?";
            PreparedStatement pr = conn.prepareStatement(sql);
            pr.setInt(1, idCidade);
            int total = pr.executeUpdate();
            conn.close();
            return total;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static Cidade findById(int idCidade){
        Cidade c = new Cidade();
        try {
            String sql = "select * from cidade where idCidade = ?";
            Connection conn = Conexao.conectaMySql();
            PreparedStatement pr = conn.prepareStatement(sql);
            pr.setInt(1, idCidade);
            ResultSet rs = pr.executeQuery();
            while(rs.next()) {
                c.setId(rs.getInt("idCidade"));
                c.setNome(rs.getString("nome"));
                c.setUf(rs.getString("uf"));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }

    public static ArrayList<Cidade> listAll(){
        ArrayList<Cidade> lista = new ArrayList<Cidade>();
        try {
            String sql = "select * from cidade";
            Connection conn = Conexao.conectaMySql();
            PreparedStatement pr = conn.prepareStatement(sql);
            ResultSet rs = pr.executeQuery();
            while(rs.next()) {
                Cidade c = new Cidade();
                c.setId(rs.getInt("idCidade"));
                c.setNome(rs.getString("nome"));
                c.setUf(rs.getString("uf"));
                lista.add(c);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public static int limpaTabelaCidade() {
        try {
            Connection conn = Conexao.conectaMySql();
            String sql = "DELETE FROM cidade WHERE idCidade>0";
            PreparedStatement pr = conn.prepareStatement(sql);
            sql = "ALTER TABLE cidade AUTO_INCREMENT = 0";
            pr = conn.prepareStatement(sql);
            int total = pr.executeUpdate();
            conn.close();
            return total;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }









    //|-----------------------------------------|
    //|não mudar nada acima dessa linha          |
    //|-----------------------------------------|

    public static int lerArquivoListaCidades() {
        int resultadoTotal = 0;

        try (BufferedReader br = new BufferedReader(new FileReader("src/banco_dados/cidades.txt"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] vl = linha.split(",");

                String nome = vl[1];
                String uf = vl[2].trim();

                Cidade cidade = new Cidade();
                cidade.setNome(nome);
                cidade.setUf(uf);
                resultadoTotal += insereCidade(cidade);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return resultadoTotal;
    }

    public static void escreverCidades(ArrayList<Cidade> listaCidades, String nomeArquivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            for (Cidade cidade : listaCidades) {
                writer.write(cidade.getId() + " - " + cidade.getNome() + " - " + cidade.getUf() + "\n");
            }
            System.out.println("Arquivo " + nomeArquivo + " criado com sucesso.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
