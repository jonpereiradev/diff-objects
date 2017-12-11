package com.github.jonpereiradev.diff.objects;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/**
 * @author jonpereiradev@gmail.com
 */
public class DiffCollectionDeepStrategy implements DiffExecutable {

    @Override
    public DiffObject diff(DiffProperty annotation, Object before, Object after) {
        Collection<?> beforeCollection = (Collection<?>) before;
        Collection<?> afterCollection = (Collection<?>) after;

        if (beforeCollection == null) {
            beforeCollection = Collections.emptyList();
        }

        if (afterCollection == null) {
            afterCollection = Collections.emptyList();
        }

        boolean isEquals = beforeCollection.size() == afterCollection.size();

        if (beforeCollection.size() == afterCollection.size()) {
            Iterator<?> beforeIterator = beforeCollection.iterator();
            Iterator<?> afterIterator = afterCollection.iterator();
            DiffExecutable diffExecutable = DiffStrategyType.DEEP_SEARCH.getDiffExecutable();

            while (beforeIterator.hasNext() && afterIterator.hasNext()) {
                Object beforeObject = beforeIterator.next();
                Object afterObject = afterIterator.next();

                isEquals = diffExecutable.diff(annotation, beforeObject, afterObject) == null;

                if (!isEquals) {
                    break;
                }
            }
        }

        return isEquals ? null : new DiffObject(annotation, before, after);
    }

}
