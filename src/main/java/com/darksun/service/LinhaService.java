package com.darksun.service;

import com.darksun.model.Linha;
import com.darksun.repository.LinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class LinhaService {

    @Autowired
    private LinhaRepository repository;

    public Linha criar(Linha linha) {
        return repository.save(linha);
    }

    public List<Linha> buscar() {
        return repository.findAll();
    }

    public Linha atualizar(Linha linha) {
        return repository.save(linha);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
