package com.darksun.service;

import com.darksun.model.Cliente;
import com.darksun.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    public Cliente criar(Cliente cliente) {
        return repository.save(cliente);
    }

    public List<Cliente> buscar() {
        return repository.findAll();
    }

    public Cliente atualizar(Cliente cliente) {
        return repository.save(cliente);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

}
