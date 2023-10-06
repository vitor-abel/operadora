package com.darksun.service;

import com.darksun.model.Plano;
import com.darksun.repository.PlanoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanoService {
    @Autowired
    private PlanoRepository repository;

    public Plano criar(Plano plano) {
        return repository.save(plano);
    }

    public List<Plano> buscar() {
        return repository.findAll();
    }

    public Plano atualizar(Plano plano) {
        return repository.save(plano);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
