package com.exwhythat.mobilization.presenters;

import com.exwhythat.mobilization.ui.main.MainPresenter;
import com.exwhythat.mobilization.ui.main.MainPresenterImpl;
import com.exwhythat.mobilization.ui.main.MainView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

/**
 * Created by Grechka on 30.07.2017.
 */

public class MainPresenterUnitTest {
    private MainPresenter<MainView> presenter = new MainPresenterImpl<>();

    @Mock
    MainView view;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        presenter.onAttach(view);
    }

    @Test
    public void shouldGoToWeather() {
        presenter.onDrawerWeatherClick();
        verify(view, only()).showWeather();
    }

    @Test
    public void shouldGoToSettings() {
        presenter.onDrawerSettingsClick();
        verify(view, only()).showSettings();
    }

    @Test
    public void shouldGoToAbout() {
        presenter.onDrawerAboutClick();
        verify(view, only()).showAbout();
    }

    @Test
    public void shouldGoToCitySelection() {
        presenter.onDrawerCitySelectionClick();
        verify(view, only()).showCitySelection();
    }

    @After
    public void finish() {
        presenter.onDetach();
    }
}
