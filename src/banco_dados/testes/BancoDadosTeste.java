package banco_dados.testes;

import banco_dados.CidadeService;
import banco_dados.ClienteService;
import banco_dados.Conexao;
import banco_dados.model.Cidade;
import banco_dados.model.Cliente;
import org.junit.Assert;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class BancoDadosTeste {


//    @BeforeEach
//    void insereCidade() {
//        //ClienteService.limpaTabelaCliente();
//        CidadeService.limpaTabelaCidade();
//    }
//
    @Test
    @Order(1)
    void testaConeccao() {
        Connection conn = Conexao.conectaMySql();
        Assertions.assertNotNull(conn);
    }
//
    @Test
    @Order(2)
    void insereCidadeTest() {
        System.out.println("City 1");
        Cidade c = new Cidade(1,"Araranguá","SC");
        Assertions.assertEquals(1, CidadeService.insereCidade(c));
    }
//
//    @Test
//    @Order(3)
//    void insereClienteTest() {
//        System.out.println("Client 1");
//        Cliente c = new Cliente( 0,"Jona", 20, "M", new Cidade(2, "orleans", "SC"));
//        Assertions.assertEquals(1, ClienteService.insereCliente(c));
//    }

    @Test
    @Order(3)
    void alteraCidadeExistente() {
        Cidade c = new Cidade(2, "Brasília", "DF");
        Assertions.assertEquals(1, CidadeService.alteraCidade(c));
	}


    @Test
    @Order(4)
    void deletaCidade() {
        Cidade c = new Cidade(20, "", "");
        Assertions.assertEquals(1, CidadeService.deletaCidade(c.getId()));
    }

    @Test
    @Order(5)
    void listaCidadeTest() {
        System.out.println("********* 2");
        ArrayList<Cidade> lista = CidadeService.listAll();

    }

    @Test
    @Order(6)
    void escreveArquivo() {
        ArrayList<Cidade> lista = CidadeService.listAll();
        CidadeService.escreverCidades(lista, "cidades.txt");
    }

    @Test
    @Order(7)
    void carregaClientes() {
        ClienteService.lerArquivoListaClientes();
    }

    @Test
    @Order(8)
    void escreveArquivoClientes() {
    ArrayList<Cliente> listaClientes = ClienteService.listAll();
    ClienteService.escreverClientes(listaClientes, "crientekk.txt");
    }



}
