package mercado.testes;

import mercado.Conexao;
import mercado.ProdutosService;
import mercado.model.Produtos;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BancoDeDadosTest {

    @BeforeAll
    static void inicializador() {
        ProdutosService.limpaTabelaProdutos();
    }

//    @Test
//    @Order(1)
//    void insereProduto() {
//        Produtos produto = new Produtos(0,"Feijão", 10, 4.33);
//        Assertions.assertEquals(1, ProdutosService.insereProduto(produto));
//    }
//    @Test
//    @Order(1)
//    void insereProduto2() {
//        Produtos produto = new Produtos(0,"Pão", 10, 4.33);
//        Assertions.assertEquals(1, ProdutosService.insereProduto(produto));
//    }
//
//    @Test
//    @Order(2)
//    void alteraProduto() {
//        Produtos produto = new Produtos(1, "Arroz", 5, 3.00);
//        Assertions.assertEquals(1, ProdutosService.alteraProduto(produto));
//    }

//    @Test
//    @Order(3)
//    void removeQtdProduto() {
//        Assertions.assertEquals(1, ProdutosService.removeQtdProduto(1, 5));
//    }

//    @Test
//    @Order(4)
//    void excluiProduto() {
//        Assertions.assertEquals(1, ProdutosService.removeProduto(1));
//    }

    @Test
    @Order(1)
    void carregaListaProdutos() {
        Assertions.assertNotEquals(-1, ProdutosService.carregarListaProdutos());
    }

    @Test
    @Order(2)
    void fazCompra() {
        Assertions.assertNotEquals(-1, ProdutosService.FazerCompra());
    }
}
