package model;
import java.time.LocalDateTime;

public class Task {
    public int id;
    public String titulo;
    public String descricao;
    public boolean feito;
    public String prioridade; // ex: baixa, média, alta
    public LocalDateTime dataCriacao;

    // Construtor padrao
    public Task() { }

    // Construtor
    public Task(int id, String titulo, String descricao, boolean feito, String prioridade, LocalDateTime dataCriacao) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.feito = feito;
        this.prioridade = prioridade;
        this.dataCriacao = dataCriacao;
    }

    // Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isFeito() {
        return feito;
    }

    public void setFeito(boolean feito) {
        this.feito = feito;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }


}
