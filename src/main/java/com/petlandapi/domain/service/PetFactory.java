package com.petlandapi.domain.service;

import org.springframework.stereotype.Component;
import com.petlandapi.domain.dto.PetRequestDTO;
import com.petlandapi.domain.model.Owner;
import com.petlandapi.domain.model.Pet;
import com.petlandapi.domain.model.PetSpecies;
import com.petlandapi.domain.model.PetBuilder; // IMPORTANTE: Importando o Builder do pacote model

@Component
public class PetFactory {

    public Pet criar(PetRequestDTO dto, Owner owner) {
        // Traduz a string bruta para o VO selado
        PetSpecies especieTraduzida = traduzirEspecie(dto.species());

        // Constrói a entidade rica de forma fluida
        return PetBuilder.builder()
                .withName(dto.name()) // Ajustado de nome() para name() conforme o DTO
                .withSpecies(especieTraduzida)
                .withOwner(owner)
                .build();
    }

    private PetSpecies traduzirEspecie(String speciesBruta) { // Ajustado de Object para String
        // Validação perimetral contra dados nulos
        if (speciesBruta == null) {
            throw new IllegalArgumentException("Falha na fábrica: A espécie enviada não pode ser nula.");
        }

        // O switch expression agora vive perfeitamente dentro do método
        return switch (speciesBruta.toUpperCase().trim()) { // Corrigido para toUpperCase() com C maiúsculo
            case "DOG" -> new PetSpecies.Dog();
            case "CAT" -> new PetSpecies.Cat();
            case "BIRD" -> new PetSpecies.Bird();
            default -> throw new IllegalArgumentException("Falha de Fábrica: Espécie não homologada no sistema: " + speciesBruta);
        }; // Semicolo fechando a expressão do return
    }
}