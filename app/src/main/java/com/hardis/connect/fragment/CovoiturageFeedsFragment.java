package com.hardis.connect.fragment;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.hardis.connect.R;
import com.hardis.connect.activity.CovoiturageOfferDetailsActivity;
import com.hardis.connect.adapter.CovoiturageFeedsFragmentAdapter;
import com.hardis.connect.adapter.FeedsFragmentAdapter;
import com.hardis.connect.controller.CovoiturageController;
import com.hardis.connect.controller.VolleyCallBack;
import com.hardis.connect.model.Covoiturage;
import com.hardis.connect.model.CovoiturageOffreItem;
import com.hardis.connect.model.NavDrawerItem;
import com.hardis.connect.util.GlobalMethodes;

import java.net.ConnectException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class CovoiturageFeedsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<CovoiturageOffreItem> data = new ArrayList<>();
    private List<Covoiturage> covoiturages;
    private static TypedArray imgs;


    public static CovoiturageFeedsFragment newInstance() {
        return new CovoiturageFeedsFragment();
    }

    public void getData() {

        CovoiturageController.getOffresCovoiturage(getActivity().getApplicationContext(), new VolleyCallBack() {
            @Override
            public void onSuccess(String result) {
                covoiturages = CovoiturageController.getCovoiturages();
                int k=0;
                for (int i =0;i<covoiturages.size();i++) {
                    CovoiturageOffreItem offreItem = new CovoiturageOffreItem();
                    offreItem.setUserName(covoiturages.get(i).getUserName());
                    String timeStamp=calculateTimeStamp(covoiturages.get(i).getDateCreation());
                    covoiturages.get(i).setTimeStamp(timeStamp);
                    offreItem.setTimeStamp(timeStamp);
                    offreItem.setTrajet(covoiturages.get(i).getDepartureAgencyName() + " >> " + covoiturages.get(i).getArrivalAgencyName());
                    offreItem.setDate(covoiturages.get(i).getDepartureTime().replace("T", " "));
                    offreItem.setCapacite(covoiturages.get(i).getCapacite() + " place(s) disponible(s)");

                    offreItem.setImgResID(imgs.getResourceId(k, 1));
                    k++;
                    if(k>=imgs.length()){
                        k=0;
                    }
                    data.add(offreItem);
                }
                Log.v("size",String.valueOf(data.size()));
                mAdapter.notifyDataSetChanged();
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailed(String result) {

            }
        });
    }

    private String calculateTimeStamp(String dateCreation) {
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Log.v("datecreation",dateCreation);
        Date date1=null,date2=null;
        String timeStamp="";

        try {
            dateCreation = dateCreation.replace("T"," ");
            Log.v("datecreationnew",dateCreation);
            date1 = simpleDateFormat.parse(dateCreation);
            Log.v("date1", date1.toString());
            date2 = new Date();
            Log.v("date2",date2.toString());
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
            Log.v("size","remove data");
            data.removeAll(data);
        }
        return inflater.inflate(R.layout.covoiturage_search_recyclerview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        //permet un affichage sous forme liste verticale
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new RecyclerViewMaterialAdapter(new CovoiturageFeedsFragmentAdapter(data));

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // Save the position and pass it as an argument to the bundle so you can retrive the details later
                //   Bundle bundle = fragment.getArguments();
                //   bundle.putInteger("position",position);
                Intent intent =  new Intent(getActivity(), CovoiturageOfferDetailsActivity.class);
                intent.putExtra("covoiturage",covoiturages.get(position-1));
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

class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener
{
    public static interface OnItemClickListener
    {
        public void onItemClick(View view, int position);
        public void onItemLongClick(View view, int position);
    }

    private OnItemClickListener mListener;
    private GestureDetector mGestureDetector;

    public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnItemClickListener listener)
    {
        mListener = listener;

        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener()
        {
            @Override
            public boolean onSingleTapUp(MotionEvent e)
            {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e)
            {
                View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());

                if(childView != null && mListener != null)
                {
                    mListener.onItemLongClick(childView, recyclerView.getChildPosition(childView));
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e)
    {
        View childView = view.findChildViewUnder(e.getX(), e.getY());

        if(childView != null && mListener != null && mGestureDetector.onTouchEvent(e))
        {
            mListener.onItemClick(childView, view.getChildPosition(childView));
        }

        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView view, MotionEvent motionEvent){}

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}