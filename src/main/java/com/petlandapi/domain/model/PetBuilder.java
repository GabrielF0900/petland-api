package com.petlandapi.domain.model;

public class PetBuilder {

    private String name;
    private PetSpecies species;
    private Owner owner;

    private PetBuilder() {}

    public static PetBuilder builder() {
        return new PetBuilder();
    }

    public PetBuilder withName(String name) {
        this.name = name;
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
        return new Pet(name, species, owner);
    }
}
