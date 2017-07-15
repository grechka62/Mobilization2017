package com.exwhythat.mobilization.ui.weather;

import com.exwhythat.mobilization.Constants;
import com.exwhythat.mobilization.model.WeatherItem;
import com.exwhythat.mobilization.network.RestApi;
import com.exwhythat.mobilization.ui.base.BasePresenterImpl;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by exwhythat on 11.07.17.
 */

public class WeatherPresenterImpl<V extends WeatherView> extends BasePresenterImpl<V>
        implements WeatherPresenter<V> {

    private RestApi restApi;

    private Disposable disposable = new CompositeDisposable();

    @Inject
    public WeatherPresenterImpl(RestApi restApi) {
        super();
        this.restApi = restApi;
    }

    @Override
    public WeatherItem refreshData() {
        getMvpView().showLoading();

        // TODO: extract work with network to separate class, consider repository pattern usage
        Single<WeatherItem> weatherItemSingle = restApi
                .getWeatherForCity(Constants.CityIds.MOSCOW, Constants.Units.METRIC)
                .map(WeatherItem::new);

        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
        disposable = weatherItemSingle
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccess, this::onError);
        return null;
    }

    private void onSuccess(WeatherItem item) {
        getMvpView().showResult(item);
    }

    private void onError(Throwable throwable) {
        getMvpView().showError();
    }
}
