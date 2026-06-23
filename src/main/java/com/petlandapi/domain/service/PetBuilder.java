package com.petlandapi.domain.service;

import com.petlandapi.domain.model.Owner;
import com.petlandapi.domain.model.Pet;
import com.petlandapi.domain.model.PetSpecies;

public class PetBuilder {

    private String nome;
    private PetSpecies species;
    private Owner owner;

    //Vamos omitir o ID porque será responsavel do gerador automatico do BD.

    private PetBuilder(){} //COnstrutor vazio porque força o uso do metodo static

    public static PetBuilder builder() {
        return new PetBuilder();
    }

    public PetBuilder withName(String nome) {
        this.nome = nome;
        return this;
    }

    public PetBuilder withSpecies(PetSpecies species) {
        this.species = species;
        return this;
    }

    public PetBuilder withOwner(Owner owner) {
        this.owner = owner;
        return this;
    }

    public Pet build() {
        validateState();
        return new Pet(nome, species, owner);
    }

    private void validateState() {
        if (nome == null || nome.isBlank()) {
            throw new IllegalStateException("Falha de Domínio: O nome do pet é obrigatório.");
        }
        if (species == null) {
            throw new IllegalStateException("Falha de Domínio: A espécie do pet é obrigatória.");
        }
        if (owner == null) {
            throw new IllegalStateException("Falha de Domínio: O tutor do pet é obrigatório.");
        }
    }
    
}
