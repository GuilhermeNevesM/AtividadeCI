package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    private final PetRepository petRepository;

    public PetController(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @GetMapping
    public List<Pet> listarPets(){
        return petRepository.findAll();
    }

    @PostMapping
    public Pet criarPet(@RequestBody Pet novoPet){
        System.out.println(String.format("""
        Pet criado:
        nome = %s
        raca = %s
        porte = %s
        especie = %s
        """, novoPet.getNome(), novoPet.getRaca(), novoPet.getPorte(), novoPet.getEspecie()));
        return petRepository.save(novoPet);
    }

}
