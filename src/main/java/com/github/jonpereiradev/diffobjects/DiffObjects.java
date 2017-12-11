package com.github.jonpereiradev.diffobjects;

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
    public static <T> DiffResults diff(T before, T after) {
        DiffResults result = new DiffResults();
//        List<DiffMetadata> metadatas = DiffReflections.discover(before.getClass());
//
//        for (DiffMetadata metadata : metadatas) {
//            Object beforeObject = DiffReflections.invoke(metadata.getMethod(), before);
//            Object afterObject = DiffReflections.invoke(metadata.getMethod(), after);
//
//            result.diff(metadata, beforeObject, afterObject);
//        }

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
        DiffResults result = new DiffResults();
//        List<DiffMetadata> metadatas = DiffReflections.discover(before.getClass());
//
//        for (DiffMetadata metadata : metadatas) {
//            Object beforeObject = DiffReflections.invoke(metadata.getMethod(), before);
//            Object afterObject = DiffReflections.invoke(metadata.getMethod(), after);
//
//            result.diff(metadata, beforeObject, afterObject);
//
//            if (!result.isEquals()) {
//                return false;
//            }
//        }

        return true;
    }

}
