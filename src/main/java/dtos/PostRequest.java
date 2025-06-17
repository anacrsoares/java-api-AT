package dtos;

public record PostRequest (
        String titulo,
        String descricao,
        boolean feito,
        String prioridade) {}
