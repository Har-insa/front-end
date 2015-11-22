package com.hardis.connect.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.hardis.connect.R;
import com.hardis.connect.adapter.CovoiturageFeedsFragmentAdapter;
import com.hardis.connect.adapter.FeedsFragmentAdapter;
import com.hardis.connect.model.CovoiturageOffreItem;
import com.hardis.connect.model.NavDrawerItem;

import java.util.ArrayList;
import java.util.List;


public class CovoiturageFeedsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    public static CovoiturageFeedsFragment newInstance() {
        return new CovoiturageFeedsFragment();
    }

    public static List<CovoiturageOffreItem> getData() {
        List<CovoiturageOffreItem> data = new ArrayList<>();


        // preparing navigation drawer items
        //1
        CovoiturageOffreItem offreItem = new CovoiturageOffreItem();
            offreItem.setUserName("Hassan El Moutaraji");
            offreItem.setTimeStamp("1h");
            offreItem.setTrajet("Lyon >> Paris");
            offreItem.setDate("Demain à 10h");
            offreItem.setCapacite("2 place(s) disponible(s)");
            offreItem.setImgResID(R.drawable.col4);
            data.add(offreItem);

        CovoiturageOffreItem offreItem2 = new CovoiturageOffreItem();
        offreItem2.setUserName("Maha Benannou");
        offreItem2.setTimeStamp("1h");
        offreItem2.setTrajet("Paris >> Grenoble");
        offreItem2.setDate("Mardi à 14h");
        offreItem2.setCapacite("2 place(s) disponible(s)");
        offreItem2.setImgResID(R.drawable.ibm);
        data.add(offreItem2);

        CovoiturageOffreItem offreItem3 = new CovoiturageOffreItem();
        offreItem3.setUserName("Minh Trinh Hoang");
        offreItem3.setTimeStamp("1h");
        offreItem3.setTrajet("Lyon >> Grenoble");
        offreItem3.setDate("Mercredi à 10h");
        offreItem3.setCapacite("2 place(s) disponible(s)");
        offreItem3.setImgResID(R.drawable.col2);
        data.add(offreItem3);

        CovoiturageOffreItem offreItem4 = new CovoiturageOffreItem();
        offreItem4.setUserName("Israe El Moutaraji");
        offreItem4.setTimeStamp("1h");
        offreItem4.setTrajet("Grenoble >> Lyon");
        offreItem4.setDate("Mercredi à 11h");
        offreItem4.setCapacite("2 place(s) disponible(s)");
        offreItem4.setImgResID(R.drawable.col3);
        data.add(offreItem4);

        CovoiturageOffreItem offreItem5 = new CovoiturageOffreItem();
        offreItem5.setUserName("Alexandra Dupond");
        offreItem5.setTimeStamp("1h");
        offreItem5.setTrajet("Lyon >> Paris");
        offreItem5.setDate("Jeudi à 9h");
        offreItem5.setCapacite("2 place(s) disponible(s)");
        offreItem5.setImgResID(R.drawable.ibm);
        data.add(offreItem5);

        return data;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recyclerview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        //permet un affichage sous forme liste verticale
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);


        //penser à passer notre Adapter (ici : FeedsFragmentAdapter) à un RecyclerViewMaterialAdapter
        mAdapter = new RecyclerViewMaterialAdapter(new CovoiturageFeedsFragmentAdapter(getData()));
        mRecyclerView.setAdapter(mAdapter);

        //notifier le MaterialViewPager qu'on va utiliser une RecyclerView
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
    }
}
