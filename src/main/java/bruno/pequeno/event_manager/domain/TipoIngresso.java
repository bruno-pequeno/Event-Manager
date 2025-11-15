package bruno.pequeno.event_manager.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class TipoIngresso {
    private final String nome;
    private final int limiteVagas;
    private final BigDecimal preco;

    public TipoIngresso(String nome, int limiteVagas, BigDecimal preco) {
        validarNome(nome);
        validarLimiteVagas(limiteVagas);
        validarPreco(preco);

        this.nome = nome;
        this.limiteVagas = limiteVagas;
        this.preco = preco;
    }

    private void validarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do ingresso não pode ser vazio");
        }
    }

    private void validarLimiteVagas(int limiteVagas) {
        if (limiteVagas <= 0) {
            throw new IllegalArgumentException("Limite de vagas deve ser positivo");
        }
    }

    private void validarPreco(BigDecimal preco) {
        if (preco == null || preco.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Preço deve ser positivo");
        }
    }

    public String getNome() { return nome; }
    public int getLimiteVagas() { return limiteVagas; }
    public BigDecimal getPreco() { return preco; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoIngresso that = (TipoIngresso) o;
        return limiteVagas == that.limiteVagas &&
                Objects.equals(nome, that.nome) &&
                Objects.equals(preco, that.preco);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, limiteVagas, preco);
    }

    @Override
    public String toString() {
        return String.format("TipoIngresso{nome='%s', limiteVagas=%d, preco=%s}",
                nome, limiteVagas, preco);
    }
}