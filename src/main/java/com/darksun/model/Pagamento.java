package com.darksun.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Pagamento {
    public Long id;
    public LocalDate data;
    public LocalDate dataVencimento;
    public Linha linha;
}
