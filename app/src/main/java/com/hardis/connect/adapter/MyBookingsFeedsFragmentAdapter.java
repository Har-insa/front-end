package com.hardis.connect.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hardis.connect.R;
import com.hardis.connect.model.CovoiturageOffreItem;

import java.util.Collections;
import java.util.List;

/**
 * Created by hp on 11/01/2016.
 */
public class MyBookingsFeedsFragmentAdapter extends RecyclerView.Adapter<MyBookingsFeedsFragmentAdapter.MyViewHolder> {

    List<CovoiturageOffreItem> contents = Collections.emptyList();
    private static final int TYPE_DISPONIBLE = 1;
    private static final int TYPE_EPUISEE = 1;

    public MyBookingsFeedsFragmentAdapter(List<CovoiturageOffreItem> contents) {
        this.contents = contents;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mybookings_item_card, parent, false);
        return new MyViewHolder(view,viewType );
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        CovoiturageOffreItem current = contents.get(position);
        holder.img.setImageResource(current.getImgResID());
        holder.date.setText(current.getDate());
        holder.time.setText(current.getTime());
        holder.trajet.setText(current.getTrajet());;
        holder.capcite.setText(current.getCapacite());;
        holder.userName.setText(current.getUserName());;
        holder.timeStamp.setText(current.getTimeStamp());;
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

        TextView date;
        TextView time;
        TextView trajet;
        TextView capcite;
        TextView userName;
        TextView timeStamp;
        ImageView img;



        public MyViewHolder(View itemView, int ViewType) {
            super(itemView);
            userName = (TextView) itemView.findViewById(R.id.userName);
            date =  (TextView) itemView.findViewById(R.id.date);
            time =  (TextView) itemView.findViewById(R.id.time);
            trajet =  (TextView) itemView.findViewById(R.id.trajet);
            capcite = (TextView) itemView.findViewById(R.id.capacite);
            timeStamp = (TextView) itemView.findViewById(R.id.timeStamp);
            img = (ImageView) itemView.findViewById(R.id.profilePic);
        }
    }

}
