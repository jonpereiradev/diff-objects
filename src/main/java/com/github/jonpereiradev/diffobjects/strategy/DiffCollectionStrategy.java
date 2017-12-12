package com.github.jonpereiradev.diffobjects.strategy;

import com.github.jonpereiradev.diffobjects.DiffResult;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

final class DiffCollectionStrategy implements DiffStrategy {

    @Override
    public <T> DiffResult<T> diff(Object before, Object after, Method method) {
        Collection<T> beforeCollection = DiffReflections.invoke(method, before);
        Collection<T> afterCollection = DiffReflections.invoke(method, after);

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
            DiffStrategy diffStrategy = DiffStrategyType.DEEP.getStrategy();

            while (beforeIterator.hasNext() && afterIterator.hasNext()) {
                Object beforeObject = beforeIterator.next();
                Object afterObject = afterIterator.next();

                isEquals = diffStrategy.diff(beforeObject, afterObject, method) == null;

                if (!isEquals) {
                    break;
                }
            }
        }

        return isEquals ? null : new DiffResult<T>(null, null, false);
    }
}
