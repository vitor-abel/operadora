package com.darksun.service;

import com.darksun.model.Pagamento;
import com.darksun.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PagamentoService {
    @Autowired
    private PagamentoRepository repository;

    public Pagamento criar(Pagamento pagamento) {
        return repository.save(pagamento);
    }

    public List<Pagamento> buscar() {
        return repository.findAll();
    }

    public Pagamento atualizar(Pagamento pagamento) {
        return repository.save(pagamento);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
