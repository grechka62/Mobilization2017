package com.exwhythat.mobilization.util.rxSchedulers;

import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.SingleTransformer;

/**
 * Utils class that provide Rx Schedulers and appropriate Rx Transformers
 *
 * @author e.matsyuk
 */
public abstract class RxSchedulersAbs {

    abstract public Scheduler getMainThreadScheduler();
    abstract public Scheduler getIOScheduler();
    abstract public Scheduler getComputationScheduler();

    public <T> ObservableTransformer<T, T> getIOToMainTransformer()  {
        return objectObservable -> objectObservable
                .subscribeOn(getIOScheduler())
                .observeOn(getMainThreadScheduler());
    }

    public <T> SingleTransformer<T, T> getIOToMainTransformerSingle()  {
        return objectObservable -> objectObservable
                .subscribeOn(getIOScheduler())
                .observeOn(getMainThreadScheduler());
    }

    public <T> ObservableTransformer<T, T> getComputationToMainTransformer()  {
        return objectObservable -> objectObservable
                .subscribeOn(getComputationScheduler())
                .observeOn(getMainThreadScheduler());
    }

    public <T> SingleTransformer<T, T> getComputationToMainTransformerSingle()  {
        return objectObservable -> objectObservable
                .subscribeOn(getComputationScheduler())
                .observeOn(getMainThreadScheduler());
    }

}