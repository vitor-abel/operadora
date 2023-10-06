package com.darksun.controller;

import com.darksun.model.Linha;
import com.darksun.model.Plano;
import com.darksun.model.type.Status;
import com.darksun.service.LinhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/linhas")
public class LinhaController {

    @Autowired
    private LinhaService service;

    @PostMapping
    public ResponseEntity<Linha> criar(@RequestBody Linha linha) {
        return new ResponseEntity<>(service.criar(linha), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Linha>> buscar() {
        return new ResponseEntity<>(service.buscar(), HttpStatus.OK);
    }

    @GetMapping("/telefone")
    public ResponseEntity<Linha> buscarPorTelefone(@RequestParam String ddd, @RequestParam String numero) {
        return new ResponseEntity<>(service.buscarPorTelefone(ddd, numero), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Linha> atualizar(@RequestBody Linha linha) {
        return new ResponseEntity<>(service.atualizar(linha), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Linha> deletar(@RequestParam Long id) {
        service.deletar(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/saldo")
    public ResponseEntity<Linha> inserirSaldo(@RequestParam String ddd, @RequestParam String numero, @RequestParam Double saldo) {
        service.inserirSaldo(ddd, numero, saldo);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/ligacao")
    public ResponseEntity<Boolean> realizarLigacao(@RequestParam String ddd, @RequestParam String numero) {
        return new ResponseEntity<Boolean>(service.realizarLigacao(ddd, numero), HttpStatus.OK);
    }

    @PutMapping("/plano")
    public ResponseEntity<Linha> alterarPlano(@RequestParam String ddd, @RequestParam String numero, @RequestBody Plano plano) {
        service.alterarPlano(ddd, numero, plano);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/bloquear")
    public ResponseEntity<Linha> bloquearPorPerda(@RequestParam String ddd, @RequestParam String numero) {
        service.bloquearPorPerda(ddd, numero);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/desbloquear")
    public ResponseEntity<Linha> desbloquearPorPerda(@RequestParam String ddd, @RequestParam String numero) {
        service.desbloquearPorPerda(ddd, numero);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/barrar")
    public ResponseEntity<Linha> barrarLinha(@RequestParam String ddd, @RequestParam String numero) {
        service.barrarLinha(ddd, numero);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/cancelar")
    public ResponseEntity<Linha> cancelarLinha(@RequestParam String ddd, @RequestParam String numero) {
        service.cancelarLinha(ddd, numero);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/status")
    public ResponseEntity<Status> verStatus(@RequestParam String ddd, @RequestParam String numero) {
        return new ResponseEntity<>(service.verStatus(ddd, numero), HttpStatus.OK);
    }
}