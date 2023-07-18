package com.darksun.model;

import com.darksun.model.type.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Linha {
    public Long id;
    public String ddd;
    public String numero;
    public Double saldo;
    public List<Pagamento> pagamentos;
    public Cliente cliente;
    public Plano plano;
    public Status status;
    
}
