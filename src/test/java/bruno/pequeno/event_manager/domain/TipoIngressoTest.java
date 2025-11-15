package bruno.pequeno.event_manager.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TipoIngressoTest {
    @Test
    void criarTipoIngressoValido() {
        String nome = "VIP";
        Integer limiteVagas = 20;
        BigDecimal preco = BigDecimal.valueOf(220.00);

        TipoIngresso tipoIngresso = new TipoIngresso(nome, limiteVagas, preco);

        assertNotNull(tipoIngresso);
        assertEquals(nome, tipoIngresso.getNome());
        assertEquals(limiteVagas, tipoIngresso.getLimiteVagas());
        assertEquals(preco, tipoIngresso.getPreco());
    }

    @Test
    void tiposIngressoComMesmosValoresSaoIguais() {
        TipoIngresso tipo1 = new TipoIngresso("VIP", 20, BigDecimal.valueOf(220.00));
        TipoIngresso tipo2 = new TipoIngresso("VIP", 20, BigDecimal.valueOf(220.00));

        assertEquals(tipo1, tipo2, "Tipos com mesmos valores devem ser iguais");
        assertEquals(tipo1.hashCode(), tipo2.hashCode(), "HashCodes devem ser iguais");
    }

    @Test
    void tiposIngressoComValoresDiferentesSaoDiferentes() {
        TipoIngresso tipo1 = new TipoIngresso("VIP", 20, BigDecimal.valueOf(220.00));
        TipoIngresso tipo2 = new TipoIngresso("Regular", 100, BigDecimal.valueOf(80.00));

        assertNotEquals(tipo1, tipo2, "Tipos com valores diferentes não devem ser iguais");
    }

    @Test
    void deveCriarEventoComTiposDeIngresso() {
        String nome = "JavaConf 2025";
        String local = "São Paulo Center";
        LocalDateTime dataEvento = LocalDateTime.now().plusDays(30);

        TipoIngresso vip = new TipoIngresso("VIP", 20, BigDecimal.valueOf(220));
        TipoIngresso regular = new TipoIngresso("Regular", 100, BigDecimal.valueOf(80));
        TipoIngresso estudante = new TipoIngresso("Estudante", 50, BigDecimal.valueOf(40));

        List<TipoIngresso> tipos = List.of(vip, regular, estudante);

        Evento evento = new Evento(nome, local, dataEvento, tipos);

        assertNotNull(evento);
        assertEquals(3, evento.quantidadeTiposIngresso());

        assertEquals(vip, evento.buscarTipoIngresso("VIP"));
        assertEquals(regular, evento.buscarTipoIngresso("Regular"));
        assertEquals(estudante, evento.buscarTipoIngresso("Estudante"));

        assertTrue(evento.temTipoIngresso("VIP"));
        assertTrue(evento.temTipoIngresso("Regular"));
        assertFalse(evento.temTipoIngresso("Premium"));
    }

    @Test
    void deveLancarExcecaoAoBuscarTipoInexistente() {
        String nome = "JavaConf 2025";
        String local = "São Paulo Center";
        LocalDateTime dataEvento = LocalDateTime.now().plusDays(30);

        TipoIngresso vip = new TipoIngresso("VIP", 20, BigDecimal.valueOf(220));
        TipoIngresso regular = new TipoIngresso("Regular", 100, BigDecimal.valueOf(80));
        TipoIngresso estudante = new TipoIngresso("Estudante", 50, BigDecimal.valueOf(40));

        List<TipoIngresso> tipos = List.of(vip, regular, estudante);

        Evento evento = new Evento(nome, local, dataEvento, tipos);

        assertThrows(IllegalArgumentException.class,
                () -> evento.buscarTipoIngresso("Premium"));
    }

    @Test
    void demonstracaoEqualsHashCode() {
        TipoIngresso vip1 = new TipoIngresso("VIP", 20, BigDecimal.valueOf(220));
        TipoIngresso vip2 = new TipoIngresso("VIP", 20, BigDecimal.valueOf(220));
        TipoIngresso regular = new TipoIngresso("Regular", 100, BigDecimal.valueOf(80));

        System.out.println("vip1.equals(vip2): " + vip1.equals(vip2));        // true
        System.out.println("vip1.equals(regular): " + vip1.equals(regular));  // false
        System.out.println("vip1 == vip2: " + (vip1 == vip2));                // false

        System.out.println("vip1.hashCode(): " + vip1.hashCode());
        System.out.println("vip2.hashCode(): " + vip2.hashCode());            // Mesmo de vip1
        System.out.println("regular.hashCode(): " + regular.hashCode());      // Diferente

        Set<TipoIngresso> tipos = new HashSet<>();
        tipos.add(vip1);
        tipos.add(vip2);
        tipos.add(regular);

        System.out.println("Tamanho do Set: " + tipos.size());
    }
}
