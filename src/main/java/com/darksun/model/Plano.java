package com.darksun.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Plano implements Serializable {
    @Serial
    private static final long serialVersionUID = -4573755564044682575L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Double preco;
    private Integer duracaoDias;
    private Boolean isLigacaoIlimitada;
    private Integer qtdGigasInternet;
    @OneToMany(mappedBy = "plano")
    @JsonIgnoreProperties("plano")
    private List<Linha> linhas;

}
