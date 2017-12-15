package com.github.jonpereiradev.diffobjects;

import com.github.jonpereiradev.diffobjects.builder.DiffConfiguration;
import com.github.jonpereiradev.diffobjects.strategy.DiffMetadata;
import com.github.jonpereiradev.diffobjects.strategy.DiffReflections;

import java.util.List;

/**
 * @author jonpereiradev@gmail.com
 */
public final class DiffObjects {

    /**
     * Executa o builder entre o antes e o depois.
     *
     * @param <T>    tipo do objeto comparado.
     * @param before objeto com as informações antes da alteração.
     * @param after  objeto com as informaçnoes depois da alteração.
     * @return resultado do builder.
     */
    public static <T> DiffResults diff(T before, T after) {
        DiffResults result = new DiffResults();
        List<DiffMetadata> metadatas = DiffReflections.discover(before.getClass());

        for (DiffMetadata metadata : metadatas) {
            result.getResults().add(metadata.getStrategy().diff(before, after, metadata));
        }

        return result;
    }

    /**
     * Verifica se os objetos são iguais no builder.
     *
     * @param <T>    tipo do objeto comparado.
     * @param before objeto com as informações antes da alteração.
     * @param after  objeto com as informações depois da alteração.
     * @return resultado do builder.
     */
    public static <T> boolean isEquals(T before, T after) {
        List<DiffMetadata> metadatas = DiffReflections.discover(before.getClass());

        for (DiffMetadata metadata : metadatas) {
            DiffResult<T> result = metadata.getStrategy().diff(before, after, metadata);

            if (!result.isEquals()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Executa o builder entre o antes e o depois.
     *
     * @param <T>    tipo do objeto comparado.
     * @param before objeto com as informações antes da alteração.
     * @param after  objeto com as informaçnoes depois da alteração.
     * @return resultado do builder.
     */
    public static <T> DiffResults diff(T before, T after, DiffConfiguration configuration) {
        DiffResults result = new DiffResults();

        for (DiffMetadata metadata : configuration.getConfigurations()) {
            result.getResults().add(metadata.getStrategy().diff(before, after, metadata));
        }

        return result;
    }

    /**
     * Verifica se os objetos são iguais no builder.
     *
     * @param <T>    tipo do objeto comparado.
     * @param before objeto com as informações antes da alteração.
     * @param after  objeto com as informações depois da alteração.
     * @return resultado do builder.
     */
    public static <T> boolean isEquals(T before, T after, DiffConfiguration configuration) {
        for (DiffMetadata metadata : configuration.getConfigurations()) {
            DiffResult<T> result = metadata.getStrategy().diff(before, after, metadata);

            if (!result.isEquals()) {
                return false;
            }
        }

        return true;
    }

}
