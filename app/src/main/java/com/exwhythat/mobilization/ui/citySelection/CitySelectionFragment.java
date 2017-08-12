package com.exwhythat.mobilization.ui.citySelection;

import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.exwhythat.mobilization.App;
import com.exwhythat.mobilization.R;
import com.exwhythat.mobilization.network.suggestResponse.Prediction;
import com.exwhythat.mobilization.ui.base.BaseFragment;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by Grechka on 23.07.2017.
 */

public class CitySelectionFragment extends BaseFragment implements CitySelectionView,
        View.OnClickListener {
    public static final String TAG = CitySelectionFragment.class.getCanonicalName();

    @Inject
    CitySelectionPresenter presenter;

    private ProgressBar loading;

    private EditText editCity;
    private RecyclerView suggestList;
    private CitySelectionAdapter suggestAdapter;
    private LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

    @NonNull
    public static CitySelectionFragment newInstance() {
        return new CitySelectionFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getComponent().inject(this);
        setHasOptionsMenu(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_city_selection, container, false);
        editCity = ButterKnife.findById(v, R.id.edit_city);

        /*ShapeDrawable shape = new ShapeDrawable(new RectShape());
        shape.getPaint().setColor(getActivity().getResources().getColor(R.color.colorPrimaryDark));
        shape.getPaint().setStyle(Paint.Style.STROKE);
        shape.getPaint().setStrokeWidth(8);*/

        // Assign the created border to EditText widget
        //editCity.setBackgroundResource(R.drawable.edittext_border);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        suggestList = ButterKnife.findById(view, R.id.suggest_recycler);
        loading = ButterKnife.findById(view, R.id.loading_suggest);

        suggestAdapter = new CitySelectionAdapter();
        suggestList.setLayoutManager(layoutManager);
        suggestList.setAdapter(suggestAdapter);
        suggestAdapter.setListener(this);

        presenter.observeCityInput(RxTextView.textChanges(editCity)
                .debounce(400, TimeUnit.MILLISECONDS)
                .filter(text -> text.length() > 0));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.action_add_city);
        presenter.onAttach(this);
    }

    @Override
    public void onDestroyView() {
        presenter.onDetach();
        super.onDestroyView();
    }

    @Override
    public void clearSuggestions() {
        suggestAdapter.clear();
    }

    @Override
    public void showCitySuggest(Prediction suggest) {
        suggestAdapter.add(suggest);
        loading.setVisibility(View.GONE);
        suggestAdapter.notifyDataSetChanged();
        suggestList.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        TextView placeId = view.findViewById(R.id.place_id);
        presenter.chooseCity(placeId.getText());
    }

    @Override
    public void showLoading() {
        suggestList.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void showWeather() {
        getActivity().onBackPressed();
    }

    @Override
    public void showError(Throwable throwable) {
        loading.setVisibility(View.GONE);
        String errorText = String.format(getString(R.string.error_with_msg), throwable.getLocalizedMessage());
        Toast.makeText(getContext(), errorText, Toast.LENGTH_LONG).show();
    }
}
