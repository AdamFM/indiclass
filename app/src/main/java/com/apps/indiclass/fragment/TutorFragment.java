package com.apps.indiclass.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.apps.indiclass.R;
import com.apps.indiclass.TutorActivity;
import com.apps.indiclass.adapter.TutorAdapter;
import com.apps.indiclass.api.ApiCall;
import com.apps.indiclass.api.ApiUtils;
import com.apps.indiclass.model.ProgamModel;
import com.apps.indiclass.model.TutorData;
import com.apps.indiclass.model.TutorModel;
import com.apps.indiclass.model.TutorRest;
import com.apps.indiclass.util.Constant;
import com.apps.indiclass.util.SessionManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TutorFragment extends Fragment implements SearchView.OnQueryTextListener {


    private static final String TAG = "TutorFragment";

    SearchView searchView;
    private int[] myImageList = new int[]{R.drawable.a1, R.drawable.a2,
            R.drawable.a3, R.drawable.a4, R.drawable.a5, R.drawable.a6,
            R.drawable.a7, R.drawable.a8, R.drawable.a9, R.drawable.a10,
            R.drawable.a11, R.drawable.a12};

    private RecyclerView recyclerView;
    private List<TutorModel> tutorModelList;
    private TutorAdapter mAdapter;
    String id_user, sToken;

    ApiCall mAPIService;
    SessionManager session;

    public TutorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tutor, container, false);

        session = new SessionManager(getContext());
        id_user = session.getIds();
        sToken = session.getToken();
        mAPIService = ApiUtils.getRestAPI(Constant.BASE_URL);

        searchView = v.findViewById(R.id.searchViewTool);
        setupSearchView();

        recyclerView = v.findViewById(R.id.recycler_view);
        tutorModelList = new ArrayList<>();
        mAdapter = new TutorAdapter(getActivity(), tutorModelList, new TutorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(TutorModel item) {

                Intent intent =new Intent(getActivity(), TutorActivity.class);
                intent.putExtra("data", item);
                startActivity(intent);
            }
        });

        if (getActivity().findViewById(R.id.bottom_navigation) != null) {

//            GridLayoutManager manager = new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
            RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(manager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
            recyclerView.setNestedScrollingEnabled(false);

            fetchStoreItems();
        } else {
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
            recyclerView.setNestedScrollingEnabled(false);

            fetchStoreItems();
        }
        return v;
    }

    private void fetchStoreItems() {

        String[] array = {"Robith Ritz", "Mahestara J", "Budi Susilo", "Pratama Putra", "Diana Amelia",
                "Seara Chan", "Thomas Alfa", "Hilder Wake", "Tane Tsi", "Rakan Garo", "Veelaju El", "Tesharion"};
        final String[] arrays = {"13 Tahun Pengalaman", "11 Tahun Pengalaman", "5 Tahun Pengalaman", "9 Tahun Pengalaman",
                "7 Tahun Pengalaman", "3 Tahun Pengalaman"};
        String[] arraya = {"Subject: Math", "Subject: English", "Subject: Chemistry", "Subject: Physics",
                "Subject: Bahasa", "Subject: Computer"};
        mAPIService.gettutor(id_user, sToken).enqueue(new Callback<TutorRest>() {
            @Override
            public void onResponse(Call<TutorRest> call, Response<TutorRest> response) {

                if (response.body().getTutorData() != null) {
                    if (response.body().getTutorData().size() > 0) {
                        for (int i = 0; i < response.body().getTutorData().size(); i++) {
                            String randomStrs = arrays[new Random().nextInt(arrays.length)];
                            TutorData td = response.body().getTutorData().get(i);
                            TutorModel p = new TutorModel();
                            p.setsImageTutor(td.getTutor_image());
                            p.setsNameTutor(td.getTutor_name());
                            p.setsExpTutor(randomStrs);
                            p.setsSubjectTutor(td.getTutor_country());
                            tutorModelList.add(p);
                        }

                        // refreshing recycler view
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<TutorRest> call, Throwable t) {

            }
        });

//        for (int i = 0; i < myImageList.length; i++) {
//            String randomStrs = arrays[new Random().nextInt(arrays.length)];
//            String randomStra = arraya[new Random().nextInt(arraya.length)];
//            TutorModel p = new TutorModel();
//            p.setsImageTutor(myImageList[i]);
//            p.setsNameTutor(array[i]);
//            p.setsExpTutor(randomStrs);
//            p.setsSubjectTutor(randomStra);
//            int z = i;
//            z++;
//            p.setsPriceTutor("Rp. "+z+"0.000");
//            tutorModelList.add(p);
//        }
//
//        // refreshing recycler view
//        mAdapter.notifyDataSetChanged();
    }

    public void setupSearchView() {
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint(getString(R.string.search_tutor_here));
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.i(TAG, "onQueryTextChange: " + newText);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }
}
