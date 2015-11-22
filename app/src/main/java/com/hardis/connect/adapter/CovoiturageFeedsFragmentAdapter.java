package com.hardis.connect.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hardis.connect.R;
import com.hardis.connect.model.CovoiturageOffreItem;
import com.hardis.connect.model.NavDrawerItem;

import java.util.Collections;
import java.util.List;

public class CovoiturageFeedsFragmentAdapter extends RecyclerView.Adapter<CovoiturageFeedsFragmentAdapter.MyViewHolder> {

    List<CovoiturageOffreItem>  contents = Collections.emptyList();
    private static final int TYPE_DISPONIBLE = 1;
    private static final int TYPE_EPUISEE = 1;

    public CovoiturageFeedsFragmentAdapter(List<CovoiturageOffreItem> contents) {
        this.contents = contents;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.covoiturage_item_card, parent, false);
        return new MyViewHolder(view,viewType );
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        CovoiturageOffreItem current = contents.get(position);

        holder.img.setImageResource(current.getImgResID());
        holder.userName.setText(current.getUserName());;
        holder.timeStamp.setText(current.getTimeStamp());;
        holder.date.setText(current.getDate());;
        holder.trajet.setText(current.getTrajet());;
        holder.capcite.setText(current.getCapacite());;
    }
    @Override
    public int getItemCount() {
        return contents.size();
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_DISPONIBLE;
    }
    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView userName;
        TextView timeStamp;
        ImageView img;
        TextView date;
        TextView trajet;
        TextView capcite;

        public MyViewHolder(View itemView, int ViewType) {
            super(itemView);

                userName = (TextView) itemView.findViewById(R.id.userName);
                img = (ImageView) itemView.findViewById(R.id.profilePic);
                timeStamp = (TextView) itemView.findViewById(R.id.timeStamp);
                date =  (TextView) itemView.findViewById(R.id.date);
                trajet =  (TextView) itemView.findViewById(R.id.trajet);
                capcite = (TextView) itemView.findViewById(R.id.capacite);
        }
    }
}