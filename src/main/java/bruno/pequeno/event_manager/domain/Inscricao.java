package bruno.pequeno.event_manager.domain;

import bruno.pequeno.event_manager.domain.enums.StatusInscricao;

import java.time.LocalDateTime;

public class Inscricao {
    private Long id;
    private final Long idUsuario;
    private final TipoIngresso tipoIngresso;
    private StatusInscricao statusInscricao;
    private final LocalDateTime dataInscricao;

    public Inscricao (Long idUsuario, TipoIngresso tipoIngresso) {
        if (idUsuario == null) {
            throw new IllegalArgumentException("O ID do usuário é obrigatório!");
        }

        if (tipoIngresso == null) {
            throw new IllegalArgumentException("Tipo do ingresso não especificado!");
        }

        this.idUsuario = idUsuario;
        this.tipoIngresso = tipoIngresso;
        this.statusInscricao = StatusInscricao.PENDENTE;
        this.dataInscricao = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public TipoIngresso getTipoIngresso() {
        return tipoIngresso;
    }

    public StatusInscricao getStatusInscricao() {
        return statusInscricao;
    }

    public LocalDateTime getDataInscricao() {
        return dataInscricao;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void confirmar() {
        if (this.statusInscricao == StatusInscricao.CONFIRMADA) {
            throw new IllegalArgumentException("Inscrição já confirmada!");
        }

        if (this.statusInscricao == StatusInscricao.PENDENTE) {
            this.statusInscricao = StatusInscricao.CONFIRMADA;
        } else {
            throw new IllegalArgumentException("Não pode confirmar inscrição já cancelada!");
        }
    }

    public void cancelar() {
        if (this.statusInscricao == StatusInscricao.CANCELADA) {
            throw new IllegalStateException("Inscrição já cancelada!");
        }

        this.statusInscricao = StatusInscricao.CANCELADA;
    }
}
