package com.example.abgabe_4.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abgabe_4.R;
import com.example.abgabe_4.database.entities.Route;
import com.example.abgabe_4.database.util.Generator;
import com.example.abgabe_4.database.util.Koordinate;
import com.example.abgabe_4.database.util.ObjectHandler;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Collections;
import java.util.List;

public class RouteView extends AppCompatActivity {

    private static final String TAG = "RouteView: ";
    TextView routeName, routeDauer;
    GraphView graph;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_view);

        routeName = findViewById(R.id.txt_route_name);
        routeDauer = findViewById(R.id.txt_route_dauer);
        imageView = findViewById(R.id.imageView);
        graph = (GraphView) findViewById(R.id.mapView);

        // get id of clicked Route to retrieve data from db
        Bundle b = getIntent().getExtras();
        String routeBezeichnung = b.getString("route_bezeichnung");
        Route route = ObjectHandler.INSTANCE.getRouteDao().getRoute(routeBezeichnung);

        routeName.setText(route.getBezeichnung());
        routeDauer.setText("Dauer: " + String.valueOf(route.getDauer()));

        generateGraph(route);
    }

    void generateGraph (Route route) {

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>();

        series.setDrawDataPoints(true);
        series.setDrawAsPath(true);

        // calculate highest lat + highest long to fit the viewport
        double highestLat = 0, highestLong = 0;

        // sort the list so graphview can work with it
        List<Koordinate> koordinateList =Generator.INSTANCE.getSortedKoordinateList(route.getKoordinateList());

        for (Koordinate koordinate : koordinateList) {
            // calculate highest lat + highest long to fit the viewport
            if (koordinate.getLatitude() > highestLat) highestLat = koordinate.getLatitude();
            if (koordinate.getLongitude() > highestLong) highestLong = koordinate.getLongitude();

            series.appendData(new DataPoint(koordinate.getLatitude(),koordinate.getLongitude()), false, koordinateList.size());
        }

        // set min / max values for the axis and add 100 to have a larger view of the graph (to don't cut it on the sides)
        graph.getViewport().setMinX(highestLat + 100);
        graph.getViewport().setMinY(highestLong + 100);

        // remove grid and lat/long values from x and y axes
        graph.getGridLabelRenderer().setGridStyle( GridLabelRenderer.GridStyle.NONE );
        graph.getGridLabelRenderer().setVerticalLabelsVisible(false);
        graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);

        // delete all possible content
        graph.removeAllSeries();
        // add / print series object (with all the data)
        graph.addSeries(series);

    }
}