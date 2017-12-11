package com.github.jonpereiradev.diff.objects;

import java.util.List;

/**
 * @author jonpereiradev@gmail.com
 */
public final class DiffObjects {

    /**
     * Executa o diff entre o antes e o depois.
     *
     * @param <T>    tipo do objeto comparado.
     * @param before objeto com as informações antes da alteração.
     * @param after  objeto com as informaçnoes depois da alteração.
     * @return resultado do diff.
     */
    public static <T> DiffResult diff(T before, T after) {
        return diff(before, after, null);
    }

    /**
     * Executa o diff entre o antes e o depois.
     *
     * @param <T>    tipo do objeto comparado.
     * @param before objeto com as informações antes da alteração.
     * @param after  objeto com as informaçnoes depois da alteração.
     * @param group  group que será aplicado o diff ou null para nenhum.
     * @return resultado do diff.
     */
    public static <T> DiffResult diff(T before, T after, String group) {
        DiffResult result = new DiffResult();
        List<DiffMetadata> metadatas = DiffReflections.discover(before.getClass(), group);

        for (DiffMetadata metadata : metadatas) {
            Object beforeObject = DiffReflections.invoke(metadata.getMethod(), before);
            Object afterObject = DiffReflections.invoke(metadata.getMethod(), after);

            result.diff(metadata, beforeObject, afterObject);
        }

        return result;
    }

    /**
     * Verifica se os objetos são iguais no diff.
     *
     * @param <T>    tipo do objeto comparado.
     * @param before objeto com as informações antes da alteração.
     * @param after  objeto com as informaçnoes depois da alteração.
     * @return resultado do diff.
     */
    public static <T> boolean isEquals(T before, T after) {
        return isEquals(before, after, null);
    }

    /**
     * Verifica se os objetos são iguais no diff.
     *
     * @param <T>    tipo do objeto comparado.
     * @param before objeto com as informações antes da alteração.
     * @param after  objeto com as informaçnoes depois da alteração.
     * @param group  group que será aplicado o diff ou null para nenhum.
     * @return resultado do diff.
     */
    public static <T> boolean isEquals(T before, T after, String group) {
        DiffResult result = new DiffResult();
        List<DiffMetadata> metadatas = DiffReflections.discover(before.getClass(), group);

        for (DiffMetadata metadata : metadatas) {
            Object beforeObject = DiffReflections.invoke(metadata.getMethod(), before);
            Object afterObject = DiffReflections.invoke(metadata.getMethod(), after);

            result.diff(metadata, beforeObject, afterObject);

            if (!result.isEquals()) {
                return false;
            }
        }

        return true;
    }

}
