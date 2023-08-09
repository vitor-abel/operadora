package com.darksun.model;

import com.darksun.model.type.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Linha {
    private Long id;
    private String ddd;
    private String numero;
    private Double saldo;
    private LocalDate dataFimAtivacao;
    private Cliente cliente;
    private Plano plano;
    private Status status;
}
