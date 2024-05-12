package mercado;

import banco_dados.CidadeService;
import banco_dados.model.Cidade;
import mercado.model.Produtos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ProdutosService {

    public static int insereProduto(Produtos produtos) {
        try {
            Connection conn = Conexao.conectaMySQL();
            String sql = "INSERT INTO produtos (nome, estoque, preco) VALUES (?,?,?)";
            PreparedStatement pr = conn.prepareStatement(sql);
            pr.setString(1, produtos.getNome());
            pr.setInt(2, produtos.getEstoque());
            pr.setDouble(3, produtos.getPreco());
            int total = pr.executeUpdate();
            conn.close();
            return total;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static int removeProduto(int id) {
        try {
            Connection conn = Conexao.conectaMySQL();
            String sql = "DELETE FROM produtos WHERE id = ?";
            PreparedStatement pr = conn.prepareStatement(sql);
            pr.setInt(1, id);
            int total = pr.executeUpdate();
            conn.close();
            return total;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static int alteraProduto(Produtos produtos) {
        try {
            Connection conn = Conexao.conectaMySQL();
            String sql = "UPDATE produtos set nome=?, estoque=?, preco=? WHERE id = ?";
            PreparedStatement pr = conn.prepareStatement(sql);
            pr.setString(1, produtos.getNome());
            pr.setInt(2, produtos.getEstoque());
            pr.setDouble(3, produtos.getPreco());
            pr.setInt(4, produtos.getId());
            int total = pr.executeUpdate();
            conn.close();
            return total;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static int removeQtdProduto(int idProduto, int quantidadeRemover) {
        try {
            Connection conn = Conexao.conectaMySQL();
            String sql = "UPDATE produtos SET estoque = estoque - ? WHERE id = ? AND estoque >= ?";
            PreparedStatement pr = conn.prepareStatement(sql);
            pr.setInt(1, quantidadeRemover);
            pr.setInt(2, idProduto);
            pr.setInt(3, quantidadeRemover);
            int linhasAfetadas = pr.executeUpdate();
            conn.close();
            return linhasAfetadas;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    //==================================================================================================================
    public static int acharPorId(int id) {
        Produtos produtos = new Produtos();
        try {
            Connection conn = Conexao.conectaMySQL();
            String sql = "SELECT * FROM produtos WHERE id = ?";
            PreparedStatement pr = conn.prepareStatement(sql);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                produtos.setId(rs.getInt("id"));
                produtos.setNome(rs.getString("nome"));
                produtos.setEstoque(rs.getInt("estoque"));
                produtos.setPreco(rs.getDouble("preco"));
                return produtos.getId();
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return 0;
    }

    public static Produtos acharPorNome(String nome) {
        Produtos produtos = new Produtos();
        try {
            Connection conn = Conexao.conectaMySQL();
            String sql = "SELECT * FROM produtos WHERE nome = ?";
            PreparedStatement pr = conn.prepareStatement(sql);
            pr.setString(1, nome);
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                produtos.setId(rs.getInt("id"));
                produtos.setNome(rs.getString("nome"));
                produtos.setEstoque(rs.getInt("estoque"));
                produtos.setPreco(rs.getDouble("preco"));
                return produtos;
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Produtos> listarTodos(){
        ArrayList<Produtos> listaProds = new ArrayList<>();
        try {
            Connection conn = Conexao.conectaMySQL();
            String sql = "SELECT * FROM produtos";
            PreparedStatement pr = conn.prepareStatement(sql);
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                Produtos produtos = new Produtos();
                produtos.setId(rs.getInt("id"));
                produtos.setNome(rs.getString("nome"));
                produtos.setEstoque(rs.getInt("estoque"));
                produtos.setPreco(rs.getDouble("preco"));
                listaProds.add(produtos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaProds;
    }
    //==================================================================================================================
    public static int limpaTabelaProdutos() {
        try {
            Connection conn = Conexao.conectaMySQL();
            String sql = "DELETE FROM produtos WHERE id>0";
            PreparedStatement pr = conn.prepareStatement(sql);
            int total = pr.executeUpdate();

            sql = "ALTER TABLE produtos AUTO_INCREMENT = 0";
            pr = conn.prepareStatement(sql);
            pr.executeUpdate();
            conn.close();
            return total;
        } catch (Exception e) {
            e.printStackTrace();
            return  -1;
        }
    }
    //==================================================================================================================
    public static int carregarListaProdutos() {
        int resultadoTotal = 0;
        try(BufferedReader br = new BufferedReader(new FileReader("src/mercado/listas/listaProdutos.txt"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] vl = linha.split(",");
                String nome = vl[0];
                int quantidade = Integer.parseInt(vl[1].trim());
                double valor = Double.parseDouble(vl[2].substring(3).replace(",","."));
                Produtos produtos = new Produtos();
                    produtos.setNome(nome);
                    produtos.setEstoque(quantidade);
                    produtos.setPreco(valor);
                    resultadoTotal += insereProduto(produtos);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return resultadoTotal;
    }

    public static int FazerCompra() {
        int resultadoTotal;
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/mercado/listas/listaCompra.txt"));
            BufferedWriter bw = new BufferedWriter(new FileWriter("src/mercado/listas/notaFiscal.txt"));
            double valorTotal = 0;
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] vl = linha.split(",");
                String nomeProduto = vl[0];
                int quantidade = Integer.parseInt(vl[1].trim());
                Produtos produto = acharPorNome(nomeProduto);
                if (produto.getEstoque() < quantidade) {
                    throw new Exception("Produto não encontrado no estoque ou quantidade insuficiente: " + nomeProduto);
                }
                double valorCompra = produto.getPreco() * quantidade;
                valorTotal += valorCompra;
                bw.write(nomeProduto + " - " + quantidade + " - R$" + String.format("%.2f", valorCompra) + "\n");
                removeQtdProduto(produto.getId(), quantidade);
            }
            bw.write("TOTAL: R$" + String.format("%.2f", valorTotal));
            resultadoTotal = 1;
            bw.close();
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return resultadoTotal;
    }

}
