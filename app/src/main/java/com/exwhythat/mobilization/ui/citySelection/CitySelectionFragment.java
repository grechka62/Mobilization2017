package com.exwhythat.mobilization.ui.citySelection;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.exwhythat.mobilization.R;
import com.exwhythat.mobilization.di.component.ActivityComponent;
import com.exwhythat.mobilization.ui.base.BaseFragment;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by Grechka on 23.07.2017.
 */

public class CitySelectionFragment extends BaseFragment implements CitySelectionView {
    public static final String TAG = CitySelectionFragment.class.getCanonicalName();

    @Inject
    CitySelectionPresenterImpl<CitySelectionView> presenter;

    AutoCompleteTextView editCity;
    ArrayAdapter<String> suggestAdapter;
    String[] suggests = {"Moscow", "Saint-Petersburg", "Kemerovo", "Tomsk", "Krasnoyarsk"};

    @NonNull
    public static CitySelectionFragment newInstance() {
        return new CitySelectionFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city_selection, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editCity = ButterKnife.findById(view, R.id.edit_city);
        suggestAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, suggests);
        editCity.setAdapter(suggestAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.action_city_selection);
    }

    @Override
    public void onDestroyView() {
        presenter.onDetach();
        super.onDestroyView();
    }
}
