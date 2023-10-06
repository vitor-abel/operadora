package com.darksun.model;

import com.darksun.model.type.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Linha implements Serializable {
    @Serial
    private static final long serialVersionUID = 7296487774945353279L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ddd;
    private String numero;
    private Double saldo;
    private Double saldoBloqueado;
    private LocalDate dataParaBarrar;
    private LocalDate dataFimAtivacao;
    @ManyToOne
    @JsonIgnoreProperties("linhas")
    private Cliente cliente;
    @ManyToOne
    @JsonIgnoreProperties("linhas")
    private Plano plano;
    private Status status;
}
