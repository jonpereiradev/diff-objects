package com.github.jonpereiradev.diffobjects.builder;

import com.github.jonpereiradev.diffobjects.strategy.DiffMetadata;

import java.util.List;

public interface DiffConfiguration {

    List<DiffMetadata> getConfigurations();

}
