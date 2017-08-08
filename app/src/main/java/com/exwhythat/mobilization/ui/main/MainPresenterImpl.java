package com.exwhythat.mobilization.ui.main;

import com.exwhythat.mobilization.model.CheckedCity;
import com.exwhythat.mobilization.model.City;
import com.exwhythat.mobilization.repository.cityRepository.LocalCityRepository;
import com.exwhythat.mobilization.ui.base.BasePresenterImpl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import nl.nl2312.rxcupboard2.DatabaseChange;

/**
 * Created by exwhythat on 07.07.17.
 */

public class MainPresenterImpl extends BasePresenterImpl<MainView>
        implements MainPresenter {

    private LocalCityRepository repository;
    private List<City> cities = new ArrayList<>();
    private Disposable disposable = new CompositeDisposable();

    @Inject
    public MainPresenterImpl(LocalCityRepository repository) {
        this.repository = repository;
    }

    @Override
    public void initCities() {
        cities.clear();
        final CheckedCity[] checked = new CheckedCity[1];
        disposable = repository.getCheckedId()
                .flatMapObservable(checkedCity -> {
                    checked[0] = checkedCity;
                    return repository.getAllCities();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cities::add, this::onError, () -> {
                            if (getMvpView() != null)
                                getMvpView().setCitiesOnDrawer(cities, checked[0].getCityId());
                        });
    }

    @Override
    public void initCheckedCity() {
        repository.initCheckedCity();
        onDrawerCitySelectionClick();
    }

    @Override
    public void observeCheckedCity() {
        repository.observeCheckedCity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(change -> {
                    MainView v = getMvpView();
                    if ((v != null) && (change.entity().getCityId() != 0)) {
                        v.setCheckedCity((int) change.entity().getCityId());
                        v.showWeather();
                    }
                });
    }

    @Override
    public void observeCity() {
        repository.observeCity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(change -> {
                    if (getMvpView() != null) {
                        if (change.getClass() == DatabaseChange.DatabaseInsert.class) {
                            getMvpView().addCity(change.entity());
                        } else {
                            getMvpView().deleteCity((int) change.entity().getId());
                        }
                    }
                });
    }

    private void onError(Throwable t){}

    @Override
    public void onDrawerWeatherClick(int id) {
        repository.changeCheckedCity(id)
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    @Override
    public void onDrawerAboutClick() {
        MainView view = getMvpView();
        if (view != null) {
            view.showAbout();
        }
    }

    @Override
    public void onDrawerSettingsClick() {
        MainView view = getMvpView();
        if (view != null) {
            view.showSettings();
        }
    }

    @Override
    public void onDrawerCitySelectionClick() {
        MainView view = getMvpView();
        if (view != null) {
            view.showCitySelection();
        }
    }

    @Override
    public void onDrawerCityDeletingClick(int id, long checkedCityId) {
        repository.deleteCity(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(item -> {
                    if (id == checkedCityId) {
                        onDrawerWeatherClick((int) item.getId());
                    }
                    if (getMvpView() != null) getMvpView().deleteCity(id);
                });
    }



    @Override
    public void onDetach() {
        disposable.dispose();
        repository = null;
        super.onDetach();
    }
}
