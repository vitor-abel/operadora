package com.darksun.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Plano {
    private Long id;
    private String nome;
    private Double preco;
    private Integer duracaoDias;
    private Boolean isLigacaoIlimitada;
    private Integer qtdGigasInternet;
}
