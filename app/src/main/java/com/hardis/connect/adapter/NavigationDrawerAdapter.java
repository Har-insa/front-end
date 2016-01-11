package com.hardis.connect.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hardis.connect.R;
import com.hardis.connect.model.NavDrawerItem;

import java.util.Collections;
import java.util.List;


/**
 * Created by El Moutaraji Hassan on 27-05-2015.
 */
public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.MyViewHolder> {
    List<NavDrawerItem> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_SEPARATOR = 2;
    private static final int TYPE_SIMPLE_SEPARATOR = 3;


    public NavigationDrawerAdapter(Context context, List<NavDrawerItem> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    public void delete(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {
            View v = inflater.inflate(R.layout.nav_drawer_row, parent, false); //Inflating the layout
            MyViewHolder vhItem = new MyViewHolder(v, viewType); //Creating ViewHolder and passing the object of type view
            return vhItem; // Returning the created object

        } else if (viewType == TYPE_SEPARATOR) {
            View v = inflater.inflate(R.layout.separator, parent, false);
            MyViewHolder vhSeparator = new MyViewHolder(v, viewType);
            return vhSeparator;

        }else if (viewType == TYPE_SIMPLE_SEPARATOR) {
            View v = inflater.inflate(R.layout.category_separator, parent, false);
            MyViewHolder vhSimpleseparator = new MyViewHolder(v, viewType);
            return vhSimpleseparator;

        }
        return null;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


        if(holder.Holderid == 1) {
            // as the list view is 1going to be called after the header view so we decrement the
            NavDrawerItem current = data.get(position);

                holder.title.setText(current.getTitle());
                holder.img.setImageResource(current.getImgResID());

        }if (holder.Holderid==2){
            NavDrawerItem current = data.get(position);
            holder.separator.setText(current.getTitle());//Catégorie
        }
    }

    //Handling the Views :
    @Override
    public int getItemViewType(int position) {
        if(isSeparator(position)){
            return TYPE_SEPARATOR;
        }
        if (isSimpleSeparator(position)) {
            return TYPE_SIMPLE_SEPARATOR;
        }
        return TYPE_ITEM;
    }
    private boolean isSeparator(int position){
        return position==1 || position== 4;
    }
    private boolean isSimpleSeparator(int position) {
        return position == 9 || position == 12;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        int Holderid;
        RelativeLayout itemLayout;
        RelativeLayout separatorLayout;

        TextView title;
        ImageView img;
        TextView separator;

        public MyViewHolder(View itemView, int ViewType) {
            super(itemView);


            if(ViewType == TYPE_ITEM) {
                title = (TextView) itemView.findViewById(R.id.title);
                img = (ImageView) itemView.findViewById(R.id.drawer_icon);
                Holderid = 1;                                                 // setting holder id as 1 as the object being populated are of type item row
            }
            if(ViewType == TYPE_SEPARATOR){
                separator = (TextView) itemView.findViewById(R.id.separator);
                Holderid = 2;
            }
            if(ViewType == TYPE_SIMPLE_SEPARATOR){
                Holderid = 3;
            }

        }
    }
}
