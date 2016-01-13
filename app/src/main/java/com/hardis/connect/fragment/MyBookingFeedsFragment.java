package com.hardis.connect.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.hardis.connect.activity.MessagingActivity;
import com.hardis.connect.activity.PendingRequests;
import com.hardis.connect.adapter.MyBookingsFeedsFragmentAdapter;
import com.hardis.connect.adapter.MyOffersFeedsFragmentAdapter;
import com.hardis.connect.controller.CovoiturageController;
import com.hardis.connect.controller.VolleyCallBack;
import com.hardis.connect.model.Covoiturage;
import com.hardis.connect.model.CovoiturageOffreItem;
import com.hardis.connect.util.MessageService;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MyBookingFeedsFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<CovoiturageOffreItem> data = new ArrayList<>();
    private List<Covoiturage> covoiturages;


    public static MyBookingFeedsFragment newInstance() {
        return new MyBookingFeedsFragment();
    }

    public void getData() {
        CovoiturageController.getMyBookings(getActivity().getApplicationContext(), new VolleyCallBack() {
            @Override
            public void onSuccess(String result) {
                covoiturages = CovoiturageController.getMesReservations();
                for (int i = 0; i < covoiturages.size(); i++) {
                    Covoiturage covoiturage = CovoiturageController.getCovoiturageById(covoiturages.get(i).getId());
                    if (covoiturage != null) {
                        CovoiturageOffreItem offreItem = new CovoiturageOffreItem();
                        offreItem.setTrajet(covoiturage.getArrivalAgencyName() + " >> " + covoiturages.get(i).getDepartureAgencyName());
                        offreItem.setCapacite(covoiturage.getCapacite() + " place(s) disponible(s)");

                        String depart = covoiturage.getDepartureTime().replace("T", " ");
                        String arrivee = covoiturage.getArrivalDate().replace("T", " ");
                        java.text.DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.FRENCH);
                        try {
                            Date dep = df.parse(depart);
                            Date arr = df.parse(arrivee);
                            Format formatterdepart = new SimpleDateFormat("EEEE, dd MMMM yyyy, HH:mm");
                            Format formatterarrivee = new SimpleDateFormat("HH:mm");
                            String departOutput = formatterdepart.format(dep);
                            String arriveeOutput = formatterarrivee.format(arr);
                            offreItem.setDate(departOutput + "-" + arriveeOutput);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        data.add(offreItem);
                    }
                    mAdapter.notifyDataSetChanged();
                    mRecyclerView.setAdapter(mAdapter);
                }
            }

            @Override
            public void onFailed(String result) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(covoiturages!=null)
        {
            covoiturages.clear();
        }
        if(data!=null)
        {
            data.clear();
        }
        return inflater.inflate(R.layout.mybookings_recyclerview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewMyoffers);

        //permet un affichage sous forme liste verticale
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new RecyclerViewMaterialAdapter(new MyBookingsFeedsFragmentAdapter(data));

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // Save the position and pass it as an argument to the bundle so you can retrive the details later
                //   Bundle bundle = fragment.getArguments();
                //   bundle.putInteger("position",position);
                //Intent serviceIntent = new Intent(getActivity().getApplicationContext(), MessageService.class);
                //getActivity().startService(serviceIntent);
                Intent intent =  new Intent(getActivity(), MessagingActivity.class);
                //startActivity(intent);

            }

            @Override
            public void onItemLongClick(View view, int position) {
                // ...
            }
        }));

        getData();
        //penser à passer notre Adapter (ici : FeedsFragmentAdapter) à un RecyclerViewMaterialAdapter
        //notifier le MaterialViewPager qu'on va utiliser une RecyclerView
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
    }
}
