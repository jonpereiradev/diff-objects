package com.github.jonpereiradev.diffobjects;

import com.github.jonpereiradev.diffobjects.builder.DiffConfiguration;
import com.github.jonpereiradev.diffobjects.strategy.DiffMetadata;
import com.github.jonpereiradev.diffobjects.strategy.DiffReflections;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * @author jonpereiradev@gmail.com
 */
public final class DiffObjects {

    /**
     * Executa o instance entre o antes e o depois.
     *
     * @param <T>         tipo do objeto comparado.
     * @param beforeState objeto com as informações antes da alteração.
     * @param afterState  objeto com as informaçnoes depois da alteração.
     * @return resultado do instance.
     */
    public static <T> List<DiffResult<?>> diff(T beforeState, T afterState) {
        Objects.requireNonNull(beforeState, "Before state is required.");
        Objects.requireNonNull(afterState, "After state is required.");

        return diff(beforeState, afterState, DiffReflections.mapAnnotations(beforeState.getClass()));
    }

    /**
     * Verifica se os objetos são iguais no instance.
     *
     * @param <T>         tipo do objeto comparado.
     * @param beforeState objeto com as informações antes da alteração.
     * @param afterState  objeto com as informações depois da alteração.
     * @return resultado do instance.
     */
    public static <T> boolean isEquals(T beforeState, T afterState) {
        Objects.requireNonNull(beforeState, "Before state is required.");
        Objects.requireNonNull(afterState, "After state is required.");

        return isEquals(beforeState, afterState, DiffReflections.mapAnnotations(beforeState.getClass()));
    }

    /**
     * Executa o instance entre o antes e o depois.
     *
     * @param <T>         tipo do objeto comparado.
     * @param beforeState objeto com as informações antes da alteração.
     * @param afterState  objeto com as informaçnoes depois da alteração.
     * @return resultado do instance.
     */
    public static <T> List<DiffResult<?>> diff(T beforeState, T afterState, DiffConfiguration configuration) {
        Objects.requireNonNull(beforeState, "Before state is required.");
        Objects.requireNonNull(afterState, "After state is required.");
        Objects.requireNonNull(configuration, "Configuration is required.");

        List<DiffResult<?>> results = new LinkedList<>();

        for (DiffMetadata metadata : configuration.build()) {
            DiffResult<Object> diff = metadata.getStrategy().diff(beforeState, afterState, metadata);
            diff.setProperties(Collections.unmodifiableMap(metadata.getProperties()));
            results.add(diff);
        }

        return results;
    }

    /**
     * Verifica se os objetos são iguais no instance.
     *
     * @param <T>         tipo do objeto comparado.
     * @param beforeState objeto com as informações antes da alteração.
     * @param afterState  objeto com as informações depois da alteração.
     * @return resultado do instance.
     */
    public static <T> boolean isEquals(T beforeState, T afterState, DiffConfiguration configuration) {
        Objects.requireNonNull(beforeState, "Before state is required.");
        Objects.requireNonNull(afterState, "After state is required.");
        Objects.requireNonNull(configuration, "Configuration is required.");

        for (DiffMetadata metadata : configuration.build()) {
            DiffResult<T> result = metadata.getStrategy().diff(beforeState, afterState, metadata);

            if (!result.isEquals()) {
                return false;
            }
        }

        return true;
    }

}
