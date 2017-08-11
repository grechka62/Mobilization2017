package com.exwhythat.mobilization.ui.main;

import com.exwhythat.mobilization.model.City;
import com.exwhythat.mobilization.repository.cityRepository.LocalCityRepository;
import com.exwhythat.mobilization.repository.cityRepository.LocalCityRepositoryImpl;
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

    private Disposable disposable = new CompositeDisposable();
    private Disposable cityDisposable = new CompositeDisposable();
    private Disposable checkedCityDisposable = new CompositeDisposable();

    private List<City> cities = new ArrayList<>();

    @Inject
    public MainPresenterImpl(LocalCityRepositoryImpl repository) {
        this.repository = repository;
    }

    @Override
    public void initCities() {
        cities.clear();
        disposable = repository.getAllCities()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cities::add, this::onError, this::showCitiesList);
    }

    private void showCitiesList() {
        if (getMvpView() != null) {
            getMvpView().setCitiesOnDrawer(cities);
        }
    }

    private void onError(Throwable t) {
        t.printStackTrace();
    }

    @Override
    public void initCheckedCity() {
        disposable = repository.initCheckedCity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> goToCitySelection(), this::onError);
    }

    @Override
    public void getCheckedCity() {
        disposable = repository.getCheckedId()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(checkedCity -> setCheckedCity(checkedCity.getCityId()));
    }

    private void setCheckedCity(long id) {
        if (getMvpView() != null) {
            getMvpView().setCheckedCity((int) id);
        }
    }

    @Override
    public void observeCheckedCity() {
        checkedCityDisposable = repository.observeCheckedCity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(change -> {
                    if (change.entity().getCityId() != 0) {
                        showCheckedCityChange(change.entity().getCityId());
                    }
                });
    }

    private void showCheckedCityChange(long id) {
        MainView v = getMvpView();
        if (v != null) {
            v.setCheckedCity((int) id);
            v.showWeather();
        }
    }

    @Override
    public void observeCity() {
        cityDisposable = repository.observeCity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(change -> {
                    if (change.getClass() == DatabaseChange.DatabaseDelete.class) {
                        showCityDeleting(change.entity().getId());
                    } else {
                        showCityAddition(change.entity());
                    }
                });
    }

    @Override
    public void deleteCity(int id, long checkedCityId) {
        disposable = repository.deleteCity(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(item -> {
                    showCityDeleting(id);
                    if (id == checkedCityId) {
                        goToAnotherCityWeather((int) item.getId());
                    }
                });
    }

    private void showCityDeleting(long id) {
        if (getMvpView() != null) {
            getMvpView().deleteCity((int) id);
        }
    }

    private void showCityAddition(City city) {
        if (getMvpView() != null) {
            getMvpView().addCity(city);
        }
    }

    @Override
    public void goToAnotherCityWeather(int id) {
        disposable = repository.changeCheckedCity(id)
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    @Override
    public void goToCitySelection() {
        MainView view = getMvpView();
        if (view != null) {
            view.showCitySelection();
        }
    }

    @Override
    public void goToSettings() {
        MainView view = getMvpView();
        if (view != null) {
            view.showSettings();
        }
    }

    @Override
    public void goToAbout() {
        MainView view = getMvpView();
        if (view != null) {
            view.showAbout();
        }
    }

    @Override
    public void onDetach() {
        disposable.dispose();
        cityDisposable.dispose();
        checkedCityDisposable.dispose();
        super.onDetach();
    }
}
