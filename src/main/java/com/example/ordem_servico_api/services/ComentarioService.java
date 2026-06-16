package com.example.ordem_servico_api.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ordem_servico_api.entities.Comentario;
import com.example.ordem_servico_api.exceptions.ResourceNotFoundException;
import com.example.ordem_servico_api.repositories.ComentarioRespository;

@Service
public class ComentarioService {

    @Autowired
    ComentarioRespository comentarioRespository;

    public List<Comentario> findAll() {
        return comentarioRespository.findAll();
    }

    public Comentario getById(Long id) {
        
        return comentarioRespository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comentario", id));

    }

    public Comentario save(Comentario comentario) {
        
        //exemplos de regras de negócio:Posso comentar em uma OS encerrada? Posso comentar em uma OS mesmo não sendo eu quem a abriu? 

        if (comentario.getDescricao() == null || comentario.getDescricao().trim().isEmpty()) {
            throw new IllegalArgumentException("O texto do comentário não pode ser vazio.");
        }

        if (comentario.getDescricao().trim().length() < 5) {
            throw new IllegalArgumentException("O texto do comentário deve ter mais que 5 caracteres.");
        }

        return comentarioRespository.save(comentario);

    }

    public Comentario update(Long id, Comentario comentario) {

        Comentario comentarioExistente = comentarioRespository.findById(id).orElseThrow(
            ()-> new ResourceNotFoundException("Cometario", id)
        );

        //Regras de negócio para comentário

        comentarioExistente.setDescricao(comentario.getDescricao());

        return comentarioRespository.save(comentarioExistente);

    }

    public void delete(Long id) {
        
        comentarioRespository.findById(id).orElseThrow(
            ()-> new ResourceNotFoundException("Cometario", id)
        );

        comentarioRespository.deleteById(id);    
    }

}
