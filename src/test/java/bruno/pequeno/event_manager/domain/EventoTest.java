package bruno.pequeno.event_manager.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class EventoTest {
    @Test
    void deveCriarEventoValido() {
        String nome = "Java Party";
        String local = "São Paulo Center";
        LocalDateTime dataEvento = LocalDateTime.now().plusDays(30);

        TipoIngresso ingressoTeste = new TipoIngresso("VIP", 20, BigDecimal.valueOf(220.00));
        List<TipoIngresso> ingressos = new ArrayList<>();
        ingressos.add(ingressoTeste);

        Evento evento = new Evento(nome, local, dataEvento, ingressos);

        assertNotNull(evento, "O evento não pode ser nulo");
        assertEquals(nome, evento.getNome(), "O nome deve ser igual ao fornecido");
        assertEquals(local, evento.getLocal(), "O local deve ser igual ao fornecido");
        assertEquals(dataEvento, evento.getDataEvento(), "A data deve ser igual a fornecida");
    }

    @Test
    void naoDeveCriarEventoComNomeVazio() {
        String nome = "";
        String local = "São Paulo Center";
        LocalDateTime dataEvento = LocalDateTime.now().plusDays(30);
        List<TipoIngresso> ingressos = new ArrayList<>();

        assertThrows(IllegalArgumentException.class, () -> new Evento(nome, local, dataEvento, ingressos));
    }

    @Test
    void naoDeveCriarEventoComLocalVazio() {
        String nome = "Arraia Folia";
        String local = "";
        LocalDateTime dataEvento = LocalDateTime.now().plusDays(30);
        List<TipoIngresso> ingressos = new ArrayList<>();

        assertThrows(IllegalArgumentException.class, () -> new Evento(nome, local, dataEvento, ingressos));
    }

    @Test
    void naoDeveCriarEventoComDataNoPassado() {
        String nome = "SP Tech";
        String local = "São Paulo Center";
        LocalDateTime dataEvento = LocalDateTime.now().minusDays(3);
        List<TipoIngresso> ingressos = new ArrayList<>();

        assertThrows(IllegalArgumentException.class, () -> new Evento(nome, local, dataEvento, ingressos));
    }
}
