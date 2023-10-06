package com.darksun.controller;

import com.darksun.model.Cliente;
import com.darksun.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @PostMapping("")
    public ResponseEntity<Cliente> criar (@RequestBody Cliente cliente){
        return new ResponseEntity<>(service.criar(cliente), HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<Cliente>> buscar(){
        return new ResponseEntity<>(service.buscar(), HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<Cliente> atualizar (@RequestBody Cliente cliente){
        return new ResponseEntity<>(service.atualizar(cliente), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
        public ResponseEntity<Cliente> deletar (@RequestParam Long id){
        service.deletar(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }



}
