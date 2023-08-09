package com.darksun.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Cliente {
    private Long id;
    private String nome;
    private LocalDate dataCadastro;
    private LocalDate dataNascimento;
    private String cpf;
    private List<Linha> linhas;
}
