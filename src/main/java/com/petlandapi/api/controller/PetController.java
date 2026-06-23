package com.petlandapi.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.GetMapping;

@RestController

@RequestMapping("/pets")

public class PetController {

    @GetMapping

    public String listarPets() {

        return "Lista de pets";

    }

}