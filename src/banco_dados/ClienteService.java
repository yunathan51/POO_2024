package banco_dados;

import banco_dados.model.Cidade;
import banco_dados.model.Cliente;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import static banco_dados.CidadeService.insereCidade;

public class ClienteService {

    public static int insereCliente(Cliente cliente) {
        try {
            Connection conn = Conexao.conectaMySql();
            String sql = "INSERT INTO cliente (nome, idade, sexo, idCidade) VALUES (?, ?, ?, ?)";
            PreparedStatement pr = conn.prepareStatement(sql);
            pr.setString(1, cliente.getNome());
            pr.setInt(2, cliente.getIdade());
            pr.setString(3, cliente.getSexo());
            pr.setInt(4, cliente.getCidade().getId());
            int total = pr.executeUpdate();
            conn.close();
            return total;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static int alteraCliente(Cliente cliente) {
        try {
            Connection conn = Conexao.conectaMySql();
            String sql = "UPDATE cliente SET nome = ?, idade = ?, sexo = ?, cidade = ? WHERE idCliente = ?";
            PreparedStatement pr = conn.prepareStatement(sql);
            pr.setString(1, cliente.getNome());
            pr.setInt(2, cliente.getIdade());
            pr.setString(3, cliente.getSexo());
            pr.setInt(4, cliente.getCidade().getId());
            pr.setInt(5, cliente.getIdCliente());
            int total = pr.executeUpdate();
            conn.close();
            return total;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static int excluiCliente(Cliente cliente) {
        try {
            Connection conn = Conexao.conectaMySql();
            String sql = "DELETE FROM cliente WHERE idCliente=?";
            PreparedStatement pr = conn.prepareStatement(sql);
            pr.setInt(1, cliente.getIdCliente());
            int total = pr.executeUpdate();
            conn.close();
            return total;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static int limpaTabelaCliente() {
        try {
            Connection conn = Conexao.conectaMySql();
            String sql = "DELETE FROM cliente WHERE idCliente >0";
            PreparedStatement pr = conn.prepareStatement(sql);
            int total = pr.executeUpdate();

            sql = "ALTER TABLE cliente AUTO_INCREMENT = 0";
            pr = conn.prepareStatement(sql);
            total = pr.executeUpdate();

            conn.close();
            return total;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static int listaTodosCliente() {
        try {
            Connection conn = Conexao.conectaMySql();
            String sql = "SELECT * FROM cliente";
            PreparedStatement pr = conn.prepareStatement(sql);
            int total = pr.executeUpdate();
            conn.close();
            return total;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static ArrayList<Cliente> listAll(){
        ArrayList<Cliente> lista = new ArrayList<Cliente>();
        try {
            String sql = "select * from cliente";
            Connection conn = Conexao.conectaMySql();
            PreparedStatement pr = conn.prepareStatement(sql);
            ResultSet rs = pr.executeQuery();
            while(rs.next()) {
                Cliente c = new Cliente();
                c.setIdCliente(rs.getInt("idCliente"));
                c.setNome(rs.getString("nome"));
                c.setSexo(rs.getString("sexo"));
                c.setIdade(rs.getInt("idade"));
                c.setCidade(CidadeService.findById(rs.getInt("IdCidade")));
                lista.add(c);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
    //|-----------------------------------------|
    //|não mudar nada acima dessa linha          |
    //|-----------------------------------------|

    public static int lerArquivoListaClientes() {
        int resultadoTotal = 0;

        try (BufferedReader br = new BufferedReader(new FileReader("src/banco_dados/clientes.txt"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] vl = linha.split(",");

                String nome = vl[0].trim();
                int idade = Integer.parseInt(vl[1].trim());
                String sexo = vl[2].trim();
                int idCidade = Integer.parseInt(vl[3].trim());

                Cliente cliente = new Cliente();
                cliente.setNome(nome);
                cliente.setIdade(idade);
                cliente.setSexo(sexo);

                Cidade cidade = CidadeService.findById(idCidade);
                cliente.setCidade(cidade);

                resultadoTotal += insereCliente(cliente);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return resultadoTotal;
    }

    public static void escreverClientes(ArrayList<Cliente> listaClientes, String nomeArquivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            for (Cliente cliente : listaClientes) {
                writer.write(cliente.getIdCliente() + " - " + cliente.getNome() + " - " + cliente.getIdade() + " - " +  cliente.getSexo() + " - " + cliente.getCidade() + "\n");
            }
            System.out.println("Arquivo " + nomeArquivo + " criado com sucesso.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }










}