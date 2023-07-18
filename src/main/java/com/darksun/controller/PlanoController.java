package com.darksun.controller;

import com.darksun.model.Plano;
import com.darksun.service.PlanoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/clientes")
public class PlanoController {

    @Autowired
    private PlanoService service;

    @PostMapping("")
    public ResponseEntity<Plano> criar (@RequestBody Plano plano){
        return new ResponseEntity<>(service.criar(plano), HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<Plano>> buscar(){
        return new ResponseEntity<>(service.buscar(), HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<Plano> atualizar (@RequestBody Plano plano){
        return new ResponseEntity<>(service.atualizar(plano), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Plano> deletar (@RequestParam Long id){
        service.deletar(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }



}