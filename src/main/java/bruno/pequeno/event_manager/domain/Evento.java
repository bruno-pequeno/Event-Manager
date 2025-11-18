package bruno.pequeno.event_manager.domain;

import bruno.pequeno.event_manager.domain.enums.StatusInscricao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Evento {
    private Long id;
    private String nome;
    private String local;
    private LocalDateTime dataEvento;
    private Map<String, TipoIngresso> ingressos;
    private final List<Inscricao> inscricoes = new ArrayList<>();

    public Evento(String nome, String local, LocalDateTime dataEvento, List<TipoIngresso> ingressos) {
        validarNome(nome);
        validarLocal(local);
        validarData(dataEvento);
        validarTipos(ingressos);

        this.nome = nome;
        this.local = local;
        this.dataEvento = dataEvento;
        this.ingressos = converterParaMap(ingressos);
    }

    private Map<String, TipoIngresso> converterParaMap(List<TipoIngresso> ingressos) {
        return ingressos.stream()
                .collect(Collectors.toMap(
                        TipoIngresso::getNome,
                        tipo -> tipo,
                        (t1, t2) -> {
                            throw new IllegalArgumentException("Tipo duplicado");
                        }
                ));
    }

    public Integer quantidadeTiposIngresso() {
        return ingressos.size();
    }

    public TipoIngresso buscarTipoIngresso(String nomeTipo) {
        TipoIngresso tipo = ingressos.get(nomeTipo);

        if (tipo == null) {
            throw new IllegalArgumentException("Tipo de ingresso não encontrado");
        }

        return tipo;
    }

    public Boolean temTipoIngresso(String nomeTipo) {
        return ingressos.containsKey(nomeTipo);
    }

    private void validarTipos(List<TipoIngresso> ingressos) {
        if (ingressos == null || ingressos.isEmpty()) {
            throw new IllegalArgumentException("O evento precisa ter pelo menos um tipo de ingresso");
        }
    }

    private void validarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do evento não pode ser vazio");
        }
    }

    private void validarLocal(String local) {
        if (local == null || local.isBlank()) {
            throw new IllegalArgumentException("Local do evento não pode ser vazio");
        }
    }

    private void validarData(LocalDateTime dataEvento) {
        if (dataEvento == null || dataEvento.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Data do evento não pode ser null e nem no passado");
        }
    }

    public Inscricao inscreverUsuario(Long usuarioId, String nomeTipo) {
        TipoIngresso tipoIngresso = buscarTipoIngresso(nomeTipo);

        if (usuarioJaInscrito(usuarioId)) {
            throw new IllegalArgumentException("Usuário já está inscrito e confirmado nesse evento!");
        }

        if (!temVagaDisponivel(tipoIngresso)) {
            throw new IllegalArgumentException("Não há mais vagas para esse evento!");
        }

        Inscricao inscricao = new Inscricao(usuarioId, tipoIngresso);
        inscricoes.add(inscricao);

        return inscricao;
    }

    private boolean usuarioJaInscrito(Long usuarioId) {
        return inscricoes.stream()
                .anyMatch(inscricao -> inscricao.getIdUsuario().equals(usuarioId)
                        && inscricao.getStatusInscricao() != StatusInscricao.CANCELADA);
    }

    private boolean temVagaDisponivel(TipoIngresso tipo) {
        long inscricoesAtivas = inscricoes.stream()
                .filter(i -> i.getTipoIngresso().equals(tipo))
                .filter(i -> i.getStatusInscricao() != StatusInscricao.CANCELADA)
                .count();

        return inscricoesAtivas < tipo.getLimiteVagas();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getLocal() {
        return local;
    }

    public LocalDateTime getDataEvento() {
        return dataEvento;
    }

    void setId(Long id) {
        this.id = id;
    }
}
