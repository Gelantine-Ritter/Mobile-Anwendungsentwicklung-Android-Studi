package com.example.myrouteplam.ui.alle_routen;

import com.example.myrouteplam.R;
import com.example.myrouteplam.entities.Route;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class AdapterListe extends RecyclerView.Adapter<AdapterListe.ExampleViewHolder> {
    private ArrayList<Route> mExampleList;
    

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        //Variablen für die Items aus TestItem xml
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;

        //Konstruktor für Items im ViewHolder
        public ExampleViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
            mTextView1 = itemView.findViewById(R.id.textView);
            mTextView2 = itemView.findViewById(R.id.textView2);
            //um values einzufügen --> onBindViewHolder
            //System.out.println(mTextView1);

        }
    }

    //
    public AdapterListe(ArrayList<Route> exampleList) {
        mExampleList = exampleList;

    }

    @NonNull
    @Override
    //Layout in den Adapter packen
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Layout für ein Item einfügen
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ltest_item_liste, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }


    //Infos für Konstruktor Items kommt aus ArrayList aus AlleRoutenFragment
    //position ist item bei 0 angefangen
    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        Route currentItem = mExampleList.get(position);
        holder.mTextView1.setText(currentItem.getBezeichnung());
        holder.mTextView2.setText("" + currentItem.getDauer());


    }

    @Override
    public int getItemCount() {

        return mExampleList.size();
    }


}
