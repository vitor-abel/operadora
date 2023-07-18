package com.darksun.controller;

import com.darksun.model.Pagamento;
import com.darksun.service.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/clientes")
public class PagamentoController {

    @Autowired
    private PagamentoService service;

    @PostMapping("")
    public ResponseEntity<Pagamento> criar (@RequestBody Pagamento pagamento){
        return new ResponseEntity<>(service.criar(pagamento), HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<Pagamento>> buscar(){
        return new ResponseEntity<>(service.buscar(), HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<Pagamento> atualizar (@RequestBody Pagamento pagamento){
        return new ResponseEntity<>(service.atualizar(pagamento), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Pagamento> deletar (@RequestParam Long id){
        service.deletar(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }



}