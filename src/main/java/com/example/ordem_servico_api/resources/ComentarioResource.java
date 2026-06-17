package com.example.ordem_servico_api.resources;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.ordem_servico_api.entities.Comentario;
import com.example.ordem_servico_api.services.ComentarioService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;




@RestController
@RequestMapping("/comentarios")
@CrossOrigin(origins = "http://127.0.0.1:5501")

public class ComentarioResource {

    @Autowired
    ComentarioService comentarioService;

    //1
    @GetMapping
    public ResponseEntity<List<Comentario>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(comentarioService.findAll());
    }

    //2
    @GetMapping("/{id}")
    public ResponseEntity<Comentario> getById(@PathVariable Long id) {
        
        return ResponseEntity.ok(comentarioService.getById(id));
        
    }

    //3
    @PostMapping()
    public ResponseEntity<Comentario> save(@RequestBody Comentario comentario) { 
          
        comentario = comentarioService.save(comentario);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(comentario.getId())
        .toUri();

        return ResponseEntity.created(uri).body(comentario);
    }

    //4
    @PutMapping("/{id}")
    public Comentario update(@PathVariable Long id, @RequestBody Comentario comentario){
        
        return comentarioService.update(id, comentario);

    }

    //5
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        
        comentarioService.delete(id);

        return ResponseEntity.noContent().build();

    }

    @GetMapping("/exemplo")
    public ResponseEntity<String> exemplo() {
        
        String mensagem = "Uma mensagem de exemplo";

        return ResponseEntity.ok()
        .header("X-Exemplo", "Um exemplo qualquer")
        .header("Cache-control", "no-cache")
        .body(mensagem);

    }
    

}
