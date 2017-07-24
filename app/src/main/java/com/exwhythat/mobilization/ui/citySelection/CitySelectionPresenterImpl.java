package com.exwhythat.mobilization.ui.citySelection;

import com.exwhythat.mobilization.model.WeatherItem;
import com.exwhythat.mobilization.repository.LocalWeatherRepository;
import com.exwhythat.mobilization.repository.RemoteWeatherRepository;
import com.exwhythat.mobilization.repository.WeatherRepository;
import com.exwhythat.mobilization.ui.base.BasePresenterImpl;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Grechka on 23.07.2017.
 */

public class CitySelectionPresenterImpl<V extends CitySelectionView> extends BasePresenterImpl<V>
        implements CitySelectionPresenter<V> {


    @Inject
    public CitySelectionPresenterImpl() {
        super();
    }

    @Override
    public void onInputChanged() {

    }

    @Override
    public void onCitySelected() {

    }
}
