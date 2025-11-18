package bruno.pequeno.event_manager.domain;

import bruno.pequeno.event_manager.domain.enums.StatusInscricao;
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

    @Test
    void deveInscreverUsuarioEmEvento() {
        String nome = "JavaConf 2025";
        String local = "São Paulo";
        LocalDateTime data = LocalDateTime.now().plusDays(30);

        TipoIngresso vip = new TipoIngresso("VIP", 20, BigDecimal.valueOf(220));
        List<TipoIngresso> tipos = List.of(vip);

        Evento evento = new Evento(nome, local, data, tipos);
        Long usuarioId = 1L;

        Inscricao inscricao = evento.inscreverUsuario(usuarioId, "VIP");

        assertNotNull(inscricao);
        assertEquals(usuarioId, inscricao.getIdUsuario());
        assertEquals(vip, inscricao.getTipoIngresso());
        assertEquals(StatusInscricao.PENDENTE, inscricao.getStatusInscricao());
    }

    @Test
    void naoDeveInscreverUsuarioSemVagasDisponiveis() {
        String nome = "JavaConf 2025";
        String local = "São Paulo";
        LocalDateTime data = LocalDateTime.now().plusDays(30);

        TipoIngresso vip = new TipoIngresso("VIP", 2, BigDecimal.valueOf(220));
        List<TipoIngresso> tipos = List.of(vip);

        Evento evento = new Evento(nome, local, data, tipos);

        evento.inscreverUsuario(1L, "VIP");
        evento.inscreverUsuario(2L, "VIP");

        assertThrows(IllegalArgumentException.class, () -> evento.inscreverUsuario(3L, "VIP"));
    }

    @Test
    void naoDeveInscreverMesmoUsuarioDuasVezes() {
        String nome = "JavaConf 2025";
        String local = "São Paulo";
        LocalDateTime data = LocalDateTime.now().plusDays(30);

        TipoIngresso vip = new TipoIngresso("VIP", 20, BigDecimal.valueOf(220));
        List<TipoIngresso> tipos = List.of(vip);

        Evento evento = new Evento(nome, local, data, tipos);

        evento.inscreverUsuario(1L, "VIP");

        assertThrows(IllegalArgumentException.class, () -> evento.inscreverUsuario(1L, "VIP"));
    }

    @Test
    void deveLiberarVagaAoCancelarInscricao() {
        String nome = "JavaConf 2025";
        String local = "São Paulo";
        LocalDateTime data = LocalDateTime.now().plusDays(30);

        TipoIngresso vip = new TipoIngresso("VIP", 2, BigDecimal.valueOf(220)); // Só 2 vagas
        List<TipoIngresso> tipos = List.of(vip);

        Evento evento = new Evento(nome, local, data, tipos);

        Inscricao inscricaoQueSeraCancelada = evento.inscreverUsuario(1L, "VIP");
        evento.inscreverUsuario(2L, "VIP");

        assertThrows(IllegalArgumentException.class, () -> evento.inscreverUsuario(3L, "VIP"));

        inscricaoQueSeraCancelada.cancelar();

        assertDoesNotThrow(() -> evento.inscreverUsuario(3L, "VIP"));
    }
}
