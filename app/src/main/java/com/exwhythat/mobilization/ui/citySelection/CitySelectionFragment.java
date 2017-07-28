package com.exwhythat.mobilization.ui.citySelection;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.exwhythat.mobilization.R;
import com.exwhythat.mobilization.di.component.ActivityComponent;
import com.exwhythat.mobilization.model.CityInfo;
import com.exwhythat.mobilization.network.suggestResponse.part.Prediction;
import com.exwhythat.mobilization.ui.base.BaseFragment;
import com.exwhythat.mobilization.ui.main.MainActivity;
import com.exwhythat.mobilization.util.CityPrefs;
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

    private EditText editCity;
    private TextView placeId;
    private View view;

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
        setHasOptionsMenu(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_city_selection, container, false);

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
        suggestList = ButterKnife.findById(view, R.id.suggest_recycler);

        suggestAdapter = new CitySelectionAdapter();
        suggestList.setLayoutManager(layoutManager);
        suggestList.setAdapter(suggestAdapter);
        suggestAdapter.setListener(this);

        presenter.observeInput(RxTextView.textChanges(editCity)
                .debounce(400, TimeUnit.MILLISECONDS)
                .filter(text -> text.length() > 0));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.action_city_selection);
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
        if (suggestAdapter.getItemCount() == 5) {
            suggestList.setAdapter(suggestAdapter);
        }
    }

    @Override
    public void onClick(View view) {
        placeId = view.findViewById(R.id.place_id);
        presenter.getCityInfo(placeId.getText());
    }

    @Override
    public void saveNewCity(CityInfo cityInfo) {
        CityPrefs.putCity(getContext(), cityInfo);
        hideKeyboard();
        getActivity().onBackPressed();
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity()
                .getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
