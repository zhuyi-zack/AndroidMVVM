package com.sephiroth.network.exception;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class ExceptionHandlerFunction<T> implements Function<Throwable, Observable<T>> {

    @Override
    public Observable<T> apply(Throwable throwable) throws Exception {
        return Observable.error(ExceptionHandler.handleException(throwable));
    }
}
