package com.example.ordem_servico_api.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ordem_servico_api.entities.Cliente;
import com.example.ordem_servico_api.services.ClienteService;


@RestController
@RequestMapping("/clientes")
public class ClienteResources {

    @Autowired
    ClienteService clienteService;

    @GetMapping
    public List<Cliente> findAll(){
        
        List<Cliente> clientes = clienteService.findAll();
        return clientes;

    }

    @PostMapping
    public ResponseEntity<Cliente> save(@Validated @RequestBody Cliente cliente){

        cliente = clienteService.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(cliente);

    }

    @PutMapping("/{id}")
    public Cliente update(@Validated @RequestBody Cliente cliente, @PathVariable Long id){

        cliente = clienteService.update(cliente, id);
        return cliente;

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){

        clienteService.deleteById(id);

        return ResponseEntity.noContent().build();

    }
    
}
