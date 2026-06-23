package com.petlandapi.domain.model;

public sealed interface PetSpecies permits PetSpecies.Dog, PetSpecies.Cat, PetSpecies.Bird {

    record Dog() implements PetSpecies {}
    record Cat() implements PetSpecies {}
    record Bird() implements PetSpecies {}

}
