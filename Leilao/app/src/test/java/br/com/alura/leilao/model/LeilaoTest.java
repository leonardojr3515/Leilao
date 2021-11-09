package br.com.alura.leilao.model;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import br.com.alura.leilao.builder.LeilaoBuilder;
import br.com.alura.leilao.exception.LanceMenorQueUltimoLanceException;
import br.com.alura.leilao.exception.LanceSeguidoDoMesmoUsuarioException;
import br.com.alura.leilao.exception.UsuarioJaDeuCincoLancesException;

import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

public class LeilaoTest {

    public static final double DELTA = 0.0001;
    // Criar cenários de teste;
    private final Leilao CONSOLE = new Leilao("Console");
    private final Usuario LEONARDO = new Usuario("Leonardo");

    @Test
    public void deve_DevolverDescricao_QuandoRecebeDescricao() {
        // Executar ação esperada;
        String descricaoDevolvida = CONSOLE.getDescricao();

        // Testar resultado esperado;
//        assertEquals("Console", descricaoDevolvida);
        assertThat(descricaoDevolvida, is(equalTo("Console")));
    }

    // [Nome do método][Estado de teste][Resultado esperado]
    // [Deve][Resultado esperado][Estado de teste]
    @Test
    public void deve_DevolverMaiorLance_QuandoRecebeApenasUmLance() {
        // Verifica se devolve o maior ou apenas um lance;
        CONSOLE.propoe(new Lance(LEONARDO, 200.0));
        //System.out.println(5.1 + 4.3);
        //System.out.println(4.0 + 5.4);
        // O "delta", pega a diferença entre valores com pontos flutuantes e se ele for maior,
        // significa que os valores são equivalentes;
        //System.out.println((5.1 + 4.3) == (4.0 + 5.4));
        double maiorLanceDevolvido = CONSOLE.getMaiorLance();
        //assertEquals(9.4, 9.399999999999999,  0.00000000000001);
//        assertEquals(200.0, maiorLanceDevolvido, DELTA);
        assertThat(maiorLanceDevolvido, closeTo(200.0, DELTA));
//        assertThat(4.1 + 5.3, closeTo(4.4 + 5.0, DELTA));

    }

    @Test
    public void deve_DevolverMaiorLance_QuandoRecebeMaisDeUmLanceEmOrdemCrescente() {
        // Verifica se devolve maior lance com mais de um lance em ordem crescente;
        CONSOLE.propoe(new Lance(LEONARDO, 100.0));
        CONSOLE.propoe(new Lance(new Usuario("Anna"), 200.0));

        double maiorLanceDevolvido = CONSOLE.getMaiorLance();

        assertEquals(200., maiorLanceDevolvido, DELTA);
    }

//    @Test
//    public void deve_DevolverMaiorLance_QuandoRecebeMaisDeUmLanceEmOrdemDecrescente(){
//        // Verifica se devolve maior lance com mais de um lance em ordem decrescente;
//        CONSOLE.propoe(new Lance(LEONARDO, 10000.0));
//        CONSOLE.propoe(new Lance(new Usuario("Anna"), 9000.0));
//
//        double maiorLanceDevolvido = CONSOLE.getMaiorLance();
//
//        assertEquals(10000.0, maiorLanceDevolvido, DELTA);
//    }

    @Test
    public void deve_DevolverMenorLance_QuandoRecebeApenasUmLance() {
        CONSOLE.propoe(new Lance(LEONARDO, 200.0));

        double menorLanceDevolvido = CONSOLE.getMenorLance();

        assertEquals(200.0, menorLanceDevolvido, DELTA);
    }

    @Test
    public void deve_DevolverMenorLance_QuandoRecebeMaisDeUmLanceEmOrdemCrescente() {
        // Verifica se devolve maior lance com mais de um lance em ordem crescente;
        CONSOLE.propoe(new Lance(LEONARDO, 100.0));
        CONSOLE.propoe(new Lance(new Usuario("Anna"), 200.0));

        double menorLanceDevolvido = CONSOLE.getMenorLance();

        assertEquals(100., menorLanceDevolvido, DELTA);
    }

/*    @Test
    public void deve_DevolverMenorLance_QuandoRecebeMaisDeUmLanceEmOrdemDecrescente()
    {
        // Verifica se devolve maior lance com mais de um lance em ordem decrescente;
        CONSOLE.propoe(new Lance(LEONARDO, 10000.0));
        CONSOLE.propoe(new Lance(new Usuario("Anna"), 9000.0));

        double menorLanceDevolvido = CONSOLE.getMenorLance();

        assertEquals(9000.0, menorLanceDevolvido, DELTA);
    }*/

    @Test
    public void deve_DevolverTresMaioresLances_QuandoRecebeExatosTresLances() {
        CONSOLE.propoe(new Lance(LEONARDO, 200.0));
        CONSOLE.propoe(new Lance(new Usuario("Anna"), 300.0));
        CONSOLE.propoe(new Lance(LEONARDO, 400.0));

        // Test Driven Development
        List<Lance> tresMaioresLancesDevolvidos = CONSOLE.tresMaioresLances();

//        assertEquals(3, tresMaioresLancesDevolvidos.size());
//        assertThat(tresMaioresLancesDevolvidos, hasSize(equalTo(3)));

//        assertEquals(400.0, tresMaioresLancesDevolvidos.get(0).getValor(), DELTA);
//        assertThat(tresMaioresLancesDevolvidos, hasItem(new Lance(LEONARDO, 400.0)));

//        assertEquals(300.0, tresMaioresLancesDevolvidos.get(1).getValor(), DELTA);
//        assertEquals(200.0, tresMaioresLancesDevolvidos.get(2).getValor(), DELTA);

//        assertThat(tresMaioresLancesDevolvidos, contains(
//                new Lance(LEONARDO, 400.0),
//                new Lance(new Usuario("Anna"), 300.0),
//                new Lance(LEONARDO, 200.0)));

        assertThat(tresMaioresLancesDevolvidos,
                both(Matchers.<Lance>hasSize(3))
                        .and(contains(new Lance(LEONARDO, 400),
                                new Lance(new Usuario("Anna"), 300.0),
                                new Lance(LEONARDO, 200.0))));
    }

    @Test
    public void deve_DevolverTresMaioresLances_QuandoNaoRecebeLances() {
        List<Lance> tresMaioresLancesDevolvidos = CONSOLE.tresMaioresLances();

        assertEquals(0, tresMaioresLancesDevolvidos.size());
    }

    @Test
    public void deve_DevolverTresMaioresLances_QuandoRecebeApenasUmLance() {
        CONSOLE.propoe(new Lance(LEONARDO, 200.0));

        List<Lance> tresMaioresLancesDevolvidos = CONSOLE.tresMaioresLances();

        assertEquals(1, tresMaioresLancesDevolvidos.size());
        assertEquals(200.0, tresMaioresLancesDevolvidos.get(0).getValor(), DELTA);
    }

    @Test
    public void deve_DevolverTresMaioresLances_QuandoRecebeApenasDoisLances() {
        CONSOLE.propoe(new Lance(LEONARDO, 300.0));
        CONSOLE.propoe(new Lance(new Usuario("Anna"), 400.0));

        List<Lance> tresMaioresLancesDevolvidos = CONSOLE.tresMaioresLances();

        assertEquals(2, tresMaioresLancesDevolvidos.size());
        assertEquals(400.0, tresMaioresLancesDevolvidos.get(0).getValor(), DELTA);
        assertEquals(300.0, tresMaioresLancesDevolvidos.get(1).getValor(), DELTA);
    }

    @Test
    public void deve_DevolverTresMaioresLances_QuandoRecebeMaisDeTresLances() {
        CONSOLE.propoe(new Lance(LEONARDO, 300.0));
        Usuario ANNA = new Usuario("Anna");
        CONSOLE.propoe(new Lance(ANNA, 400.0));
        CONSOLE.propoe(new Lance(LEONARDO, 500.0));
        CONSOLE.propoe(new Lance(ANNA, 600.0));

        final List<Lance> tresMaioresLancesDevolvidosParaQuatroLances = CONSOLE.tresMaioresLances();

        assertEquals(3, tresMaioresLancesDevolvidosParaQuatroLances.size());
        assertEquals(600.0, tresMaioresLancesDevolvidosParaQuatroLances.get(0).getValor(), DELTA);
        assertEquals(500.0, tresMaioresLancesDevolvidosParaQuatroLances.get(1).getValor(), DELTA);
        assertEquals(400.0, tresMaioresLancesDevolvidosParaQuatroLances.get(2).getValor(), DELTA);

        CONSOLE.propoe(new Lance(LEONARDO, 700));
        final List<Lance> tresMaioresLancesDevolvidosParaCincoLances = CONSOLE.tresMaioresLances();

        assertEquals(3, tresMaioresLancesDevolvidosParaCincoLances.size());
        assertEquals(700.0, tresMaioresLancesDevolvidosParaCincoLances.get(0).getValor(), DELTA);
        assertEquals(600.0, tresMaioresLancesDevolvidosParaCincoLances.get(1).getValor(), DELTA);
        assertEquals(500.0, tresMaioresLancesDevolvidosParaCincoLances.get(2).getValor(), DELTA);
    }

    @Test
    public void deve_DevolverValorZeroParaMaiorLance_QuandoNaoTiverLances() {
        double maiorLanceDevolvido = CONSOLE.getMaiorLance();

        assertEquals(0.0, maiorLanceDevolvido, DELTA);
    }

    @Test
    public void deve_DevolverValorZeroParaMenorLance_QuandoNaoTiverLances() {
        double menorLanceDevolvido = CONSOLE.getMenorLance();

        assertEquals(0.0, menorLanceDevolvido, DELTA);
    }

    @Test(expected = LanceMenorQueUltimoLanceException.class)
    public void deve_LancarException_QuandoReceberLanceMenorQueMaiorLance() {
        // Implementação de teste que espera exception;

        CONSOLE.propoe(new Lance(LEONARDO, 500.0));
        CONSOLE.propoe(new Lance(new Usuario("Anna"), 400.0));
    }

    @Test(expected = LanceSeguidoDoMesmoUsuarioException.class)
    public void deve_LancarException_QuandoReceberOMesmoUsuarioDoUltimoLance() {
        // Implementação de teste que espera exception;
        CONSOLE.propoe(new Lance(LEONARDO, 500.0));
        CONSOLE.propoe(new Lance(LEONARDO, 600.0));
    }

    @Test(expected = UsuarioJaDeuCincoLancesException.class)
    public void deve_LancarException_QuandoReceberCincoLancesDoMesmoUsuario() {
        // Implementação de teste que espera exception;
        CONSOLE.propoe(new Lance(LEONARDO, 100.0));
        final Usuario ANNA = new Usuario("Anna");
        CONSOLE.propoe(new Lance(ANNA, 200.0));
        CONSOLE.propoe(new Lance(LEONARDO, 300.0));
        CONSOLE.propoe(new Lance(ANNA, 400.0));
        CONSOLE.propoe(new Lance(LEONARDO, 500.0));
        CONSOLE.propoe(new Lance(ANNA, 600.0));
        CONSOLE.propoe(new Lance(LEONARDO, 700.0));
        CONSOLE.propoe(new Lance(ANNA, 800.0));
        CONSOLE.propoe(new Lance(LEONARDO, 900.0));
        CONSOLE.propoe(new Lance(ANNA, 1000.0));
        CONSOLE.propoe(new Lance(LEONARDO, 1100.0));
    }
}
