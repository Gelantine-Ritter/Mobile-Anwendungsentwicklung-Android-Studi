package com.example.abgabe_4.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.abgabe_4.R;
import com.example.abgabe_4.database.entities.Route;

import java.util.ArrayList;

public class RouteListAdapter extends RecyclerView.Adapter<RouteListAdapter.ViewHolder> {

    private ArrayList<Route> mRouteList;
    private OnRouteListener mOnRouteListener;

    public RouteListAdapter(ArrayList<Route> routes, OnRouteListener onRouteListener){
        this.mRouteList =routes;
        this.mOnRouteListener=onRouteListener;
    }

    public RouteListAdapter(ArrayList<Route> routeList) {
        mRouteList = routeList;

    }



    //Interface für einzelne Items
    public interface OnRouteListener{
        void onRouteClick(int position);
    }

    /**
     * ViewHolder beinhaltet die Items im Recyclerview
     */
    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{

        //  ImageView hikingIcon;
        RelativeLayout parentLayout;
        TextView routeName;
        TextView routeInfo;
        OnRouteListener onRouteListener;


        public ViewHolder(@NonNull View itemView, OnRouteListener onRouteListener ) {
            super(itemView);
            this.onRouteListener=onRouteListener;

            parentLayout = itemView.findViewById(R.id.parent_layout);
            routeName = itemView.findViewById(R.id.route_name);
            routeInfo = itemView.findViewById(R.id.route_info);
            //um values einzufügen --> onBindViewHolder


            itemView.setOnClickListener(this);

        }



        public void onClick(View v) {
            onRouteListener.onRouteClick(getAdapterPosition());

        }


    }


    @NonNull
    @Override
    //Layout in den Adapter mappen
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Layout für ein Item einfügen
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.route_item, parent, false);
        ViewHolder evh = new ViewHolder(v, mOnRouteListener);
        return evh;
    }

    /**
     * Infos für Konstruktor Items kommt aus ArrayList aus Route_List Fragment
     * - position ist item bei 0 angefangen
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Route currentItem = mRouteList.get(position);
        holder.routeName.setText(currentItem.getBezeichnung());
        holder.routeInfo.setText("" + currentItem.getDauer());

    }

    @Override
    public int getItemCount() {
        return mRouteList.size();
    }



}
