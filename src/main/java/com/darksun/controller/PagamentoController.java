package com.darksun.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/clientes")
public class PagamentoController {

   /* @Autowired
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
    }*/


}