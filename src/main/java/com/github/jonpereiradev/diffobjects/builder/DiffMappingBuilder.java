package com.github.jonpereiradev.diffobjects.builder;

interface DiffMappingBuilder {

    DiffInstanceBuilder builder();

    DiffMappingBuilder mapping(String field);

    DiffMappingBuilder mapping(String field, String value);

}

