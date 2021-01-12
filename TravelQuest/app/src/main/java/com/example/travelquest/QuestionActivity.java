package com.example.travelquest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import android.os.Bundle;

import com.example.travelquest.database.dao.TQDao;
import com.example.travelquest.database.db.TQDatabase;
import com.example.travelquest.database.entities.UserEntry;
import com.example.travelquest.database.logic.DestinationBuilder;
import com.example.travelquest.fragments.Budget_Days;

public class QuestionActivity extends AppCompatActivity {

    Fragment fragment;
    TQDatabase db;
    TQDao tqDao;
    static UserEntry userEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        //erstes Fragment hinzufügen
        //"mainLayoutQuestionActivity" ist die ID des activity_question.xml
        Budget_Days fragmentBudgetDays = new Budget_Days();
        FragmentManager fm = getSupportFragmentManager();
        //Fragment zur mainactivity hinzufügen
        fm.beginTransaction().add(R.id.mainLayoutQuestionActivity, fragmentBudgetDays).commit();

        // DB INITIALIZATION
        db = Room.databaseBuilder(getApplicationContext(),
                TQDatabase.class, "tq_database").allowMainThreadQueries().build();
        tqDao = db.tqDao();
        DestinationBuilder.INSTANCE.createDestinationEntries(db, tqDao);

         userEntry = new UserEntry();


    }

    public UserEntry getUserEntry(){
        return userEntry;
    }

    public TQDatabase getDatabase() {
        return db;
    }

    public TQDao getDao() {
        return tqDao;

    }
}