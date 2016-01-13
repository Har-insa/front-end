package com.hardis.connect.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.hardis.connect.util.GlobalMethodes;
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
    private static TypedArray imgs;



    public static MyBookingFeedsFragment newInstance() {
        return new MyBookingFeedsFragment();
    }

    public void getData() {
        CovoiturageController.getMyBookings(getActivity().getApplicationContext(), new VolleyCallBack() {
            @Override
            public void onSuccess(String result) {
                int k=0;
                covoiturages = CovoiturageController.getMesReservations();
                for (int i = 0; i < covoiturages.size(); i++) {
                    Covoiturage covoiturage = CovoiturageController.getCovoiturageById(covoiturages.get(i).getId());
                    if (covoiturage != null) {
                        CovoiturageOffreItem offreItem = new CovoiturageOffreItem();
                        offreItem.setTrajet(covoiturage.getArrivalAgencyName() + " >> " + covoiturage.getDepartureAgencyName());
                        offreItem.setCapacite(covoiturage.getCapacite() + " place(s) disponible(s)");
                        offreItem.setUserName(covoiturage.getUserName());
                        offreItem.setEmail(covoiturage.getEmail());
                        String timeStamp=calculateTimeStamp(covoiturage.getDateCreation());
                        offreItem.setTimeStamp(timeStamp);
                        String depart = covoiturage.getDepartureTime().replace("T", " ");
                        String arrivee = covoiturage.getArrivalDate().replace("T", " ");
                        java.text.DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.FRENCH);
                        try {
                            Date dep =  df.parse(depart);
                            Date arr = df.parse(arrivee);
                            Format formatterdepart1 = new SimpleDateFormat("EEEE, dd MMMM yyyy");
                            Format formatterdepart2 = new SimpleDateFormat("HH:mm");
                            Format formatterarrivee = new SimpleDateFormat("HH:mm");
                            String departOutput=formatterdepart1.format(dep);
                            String departOutput1=formatterdepart2.format(dep);
                            String arriveeOutput=formatterarrivee.format(arr);
                            offreItem.setDate(departOutput);
                            offreItem.setTime(departOutput1 + "-" + arriveeOutput);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        offreItem.setImgResID(imgs.getResourceId(k, 1));
                        k++;
                        if(k>=imgs.length()){
                            k=0;
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

    private String calculateTimeStamp(String dateCreation) {
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date1=null,date2=null;
        String timeStamp="";

        try {
            dateCreation = dateCreation.replace("T"," ");
            date1 = simpleDateFormat.parse(dateCreation);
            date2 = new Date();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long result[] = GlobalMethodes.printDifference(date1, date2);
        if(result.length ==0) return "";
        else {
            if(result[0]!=0)
            {
                timeStamp = String.valueOf(result[0])+"d";
            }
            else if(result[1]!=0)
            {
                timeStamp = String.valueOf(result[1])+"h";
            }
            else if (result[2]!=0)
            {
                timeStamp = String.valueOf(result[2])+"m";
            }
            else if (result[3]!=0 && result[3] >0)
            {
                timeStamp = String.valueOf(result[3])+"s";
            }
            else if(result[3] <= 0)
            {
                timeStamp = "3s";
            }
            return timeStamp;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        imgs = getResources().obtainTypedArray(R.array.profile_icons);
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
                Intent serviceIntent = new Intent(getActivity().getApplicationContext(), MessageService.class);
                getActivity().startService(serviceIntent);
                Intent intent =  new Intent(getActivity(), MessagingActivity.class);
                Log.v("pos",String.valueOf(position));
                Log.v("datapos", data.get(position-1).getEmail());
                intent.putExtra("recipientId", data.get(position - 1).getEmail());
                startActivity(intent);

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
