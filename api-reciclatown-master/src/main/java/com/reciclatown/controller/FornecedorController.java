package com.reciclatown.controller;

import com.reciclatown.model.Fornecedor;
import com.reciclatown.repository.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class FornecedorController {

    @Autowired
    FornecedorRepository repository;

    @GetMapping(value = "/fornecedores", produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Fornecedor> buscarTodosFornecedores() {
        return repository.findAll();
    }

    @PostMapping(value = "/fornecedores", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Fornecedor cadastrarNovoFornecedor(@RequestBody Fornecedor fornecedor) {
        return repository.save(fornecedor);
    }

    @GetMapping(value = "/fornecedores/{id}")
    public Optional<Fornecedor> buscarFornecedorPeloId(@PathVariable String id) {
        return repository.findById(id);
    }

    @PutMapping(value = "/fornecedores/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void atualizarDadosDoFornecedor(@RequestBody Fornecedor fornecedor) {
        repository.save(fornecedor);
    }

    @DeleteMapping(value = "/fornecedores/{id}")
    public void excluirFornecedor(@PathVariable String id) {
        Fornecedor st = new Fornecedor();
        st.setId(id);
        repository.delete(st);
    }

}
