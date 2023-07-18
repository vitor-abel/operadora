package com.darksun.controller;

import com.darksun.model.Linha;
import com.darksun.service.LinhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/linhas")
public class LinhaController {

    @Autowired
    private LinhaService service;

    @PostMapping("")
    public ResponseEntity<Linha> criar (@RequestBody Linha linha){
        return new ResponseEntity<>(service.criar(linha), HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<Linha>> buscar(){
        return new ResponseEntity<>(service.buscar(), HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<Linha> atualizar (@RequestBody Linha linha){
        return new ResponseEntity<>(service.atualizar(linha), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Linha> deletar (@RequestParam Long id){
        service.deletar(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }



}