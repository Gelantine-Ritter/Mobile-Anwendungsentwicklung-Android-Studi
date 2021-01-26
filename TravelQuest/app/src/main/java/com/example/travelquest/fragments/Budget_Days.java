package com.example.travelquest.fragments;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.travelquest.QuestionActivity;
import com.example.travelquest.R;
import com.example.travelquest.database.dao.TQDao;
import com.example.travelquest.database.db.TQDatabase;
import com.example.travelquest.database.entities.UserEntry;
import com.example.travelquest.database.logic.DestinationCalculator;
import com.example.travelquest.database.util.ObjectHandler;

public class Budget_Days extends Fragment {

    private static final String TAG = "";

    int budgetResult;

    Button btnGo;
    SeekBar seekBarBudget;
    SeekBar seekBarDays;
    TextView statusBudget;
    TextView statusDays;




    private Budget_Dayd_View_Model mViewModel;

    public static Budget_Days newInstance() {
        return new Budget_Days();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.budget_days_fragment, container, false);

        /**
         * Onclick zum nächsten Fragment wechseln und Daten an Datenbank übermitteln
         */
        btnGo = v.findViewById(R.id.btn_go);

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transferData();

                //Nächstes Fragment
                F1_Reisefuehrer fragmentReisefuehrer = new F1_Reisefuehrer();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                //altest Fragment in mainLayout wird ersetzt mit dem neuem Fragment
                transaction.replace(R.id.mainLayoutQuestionActivity, fragmentReisefuehrer);
                transaction.commit();

            }
        });
        return v;
    }

    /**
     * Datentransfer an die Datenbank
     */
    private void transferData() {
        ObjectHandler.INSTANCE.getUserEntry().setBudget(budgetResult);
    }

    /**
     * Methode um die Daten aus dem Fragment an die Datenbank übertragen
     */


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(Budget_Dayd_View_Model.class);
        // TODO: Use the ViewModel

        seekBarBudget = (SeekBar) getView().findViewById(R.id.seekBar_budget);
        seekBarDays = (SeekBar) getView().findViewById(R.id.seekBar_days);
        statusBudget = (TextView) getView().findViewById(R.id.txt_Sbar_status_budget);
        statusDays = (TextView) getView().findViewById(R.id.txt_Sbar_status_days);

        /**
         * Methode für die SeekBar um Budget einzustellen
         */
        seekBarBudget.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = progress / 100 * 100;

                if (progress == 3000) {
                    statusBudget.setText(new StringBuilder().append(progress).append("+").toString());


                } else {
                    statusBudget.setText(String.valueOf(progress));

                }

                 budgetResult = progress;

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        /**
         * Methode für die SeekBar um die Anzahl der Tage einzustellen
         */
        seekBarDays.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress == 14) {
                    statusDays.setText(new StringBuilder().append(progress).append("+").toString());
                    Log.d(TAG, "onProgressChanged: " + progress);

                } else {
                    statusDays.setText(String.valueOf(progress));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

}