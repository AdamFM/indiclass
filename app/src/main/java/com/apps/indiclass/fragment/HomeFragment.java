package com.apps.indiclass.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.indiclass.R;
import com.apps.indiclass.adapter.ProgramAdapter;
import com.apps.indiclass.model.ProgamModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }

    private RecyclerView recyclerView;
    private List<ProgamModel> progamsModelList;
    private ProgramAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        progamsModelList = new ArrayList<>();
        mAdapter = new ProgramAdapter(getActivity(), progamsModelList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(8), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);

        fetchStoreItems();
        return view;
    }
    private void fetchStoreItems() {
        String[] asd ={"SD", "SMP", "SMA - IPA", "SMA - IPS"};
        for (int i = 0; i < 4; i++) {
            ProgamModel p = new ProgamModel();
            p.setsPrice("Rp. 99.000");
            p.setsProgamName("Program Belajar " + asd[i]);
            progamsModelList.add(p);
        }

        // refreshing recycler view
        mAdapter.notifyDataSetChanged();
    }
}
