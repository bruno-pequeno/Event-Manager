package bruno.pequeno.event_manager.domain;

import bruno.pequeno.event_manager.domain.enums.StatusInscricao;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class InscricaoTest {

    @Test
    void deveCriarInscricaoPendente() {
        Long usuarioId = 1L;
        TipoIngresso tipoVip = new TipoIngresso("VIP", 20, BigDecimal.valueOf(220));

        Inscricao inscricao = new Inscricao(usuarioId, tipoVip);

        assertNotNull(inscricao);
        assertEquals(usuarioId, inscricao.getIdUsuario());
        assertEquals(tipoVip, inscricao.getTipoIngresso());
        assertEquals(StatusInscricao.PENDENTE, inscricao.getStatusInscricao());
        assertNotNull(inscricao.getDataInscricao());
    }

    @Test
    void naoDeveCriarInscricaoComUsuarioNulo() {
        TipoIngresso tipoIngresso = new TipoIngresso("VIP", 20, BigDecimal.valueOf(220));

        assertThrows(IllegalArgumentException.class, () -> new Inscricao(null, tipoIngresso));
    }

    @Test
    void naoDeveCriarInscricaoComTipoIngressoNulo() {
        Long idUsuario = 1L;

        assertThrows(IllegalArgumentException.class, () -> new Inscricao(idUsuario, null));
    }

    @Test
    void deveConfirmarInscricaoPendente() {
        Long usuarioId = 1L;
        TipoIngresso ingresso = new TipoIngresso("VIP", 20, BigDecimal.valueOf(220));
        Inscricao inscricao = new Inscricao(usuarioId, ingresso);

        inscricao.confirmar();

        assertEquals(StatusInscricao.CONFIRMADA, inscricao.getStatusInscricao());
    }

    @Test
    void deveCancelarInscricaoPendente() {
        Long usuarioId = 1L;
        TipoIngresso ingresso = new TipoIngresso("VIP", 20, BigDecimal.valueOf(220));
        Inscricao inscricao = new Inscricao(usuarioId, ingresso);

        inscricao.cancelar();

        assertEquals(StatusInscricao.CANCELADA, inscricao.getStatusInscricao());
    }

    @Test
    void deveCancelarInscricaoConfirmada() {
        Long usuarioId = 1L;
        TipoIngresso ingresso = new TipoIngresso("VIP", 20, BigDecimal.valueOf(220));
        Inscricao inscricao = new Inscricao(usuarioId, ingresso);

        inscricao.confirmar();

        inscricao.cancelar();

        assertEquals(StatusInscricao.CANCELADA, inscricao.getStatusInscricao());
    }

    @Test
    void naoDeveConfirmarInscricaoJaConfirmada() {
        Long usuarioId = 1L;
        TipoIngresso ingresso = new TipoIngresso("VIP", 20, BigDecimal.valueOf(220));
        Inscricao inscricao = new Inscricao(usuarioId, ingresso);

        inscricao.confirmar();

        assertThrows(IllegalArgumentException.class, inscricao::confirmar);
    }

    @Test
    void naoDeveConfirmarInscricaoJaCancelada() {
        Long usuarioId = 1L;
        TipoIngresso ingresso = new TipoIngresso("VIP", 20, BigDecimal.valueOf(220));
        Inscricao inscricao = new Inscricao(usuarioId, ingresso);

        inscricao.cancelar();

        assertThrows(IllegalArgumentException.class, inscricao::confirmar);
    }

    @Test
    void naoDeveCancelarInscricaoJaCancelada() {
        Long usuarioId = 1L;
        TipoIngresso ingresso = new TipoIngresso("VIP", 20, BigDecimal.valueOf(220));
        Inscricao inscricao = new Inscricao(usuarioId, ingresso);

        inscricao.cancelar();

        assertThrows(IllegalArgumentException.class, inscricao::cancelar);
    }
}
