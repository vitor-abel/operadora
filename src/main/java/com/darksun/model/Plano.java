package com.darksun.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Plano {
    public Long id;
    public String nome;
    public Boolean isPrePago;
    public Double preco;
    public Integer duracaoDias;
    public Boolean isLigacaoIlimitada;
    public Integer qtdGigasInternet;
    public Integer mesesFidelidade;
}
