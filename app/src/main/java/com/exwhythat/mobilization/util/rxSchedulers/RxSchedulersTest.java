package com.exwhythat.mobilization.util.rxSchedulers;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * @author e.matsyuk
 */
public class RxSchedulersTest extends RxSchedulersAbs {

    @Override
    public Scheduler getMainThreadScheduler() {
        return Schedulers.trampoline();
    }

    @Override
    public Scheduler getIOScheduler() {
        return Schedulers.trampoline();
    }

    @Override
    public Scheduler getComputationScheduler() {
        return Schedulers.trampoline();
    }

}