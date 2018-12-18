/**
 * WeatherListFragment
 * <p>
 * Fragment, zur Darstellung der Wetterinformationen für
 * die nächsten Tage. Es wird die Temperatur, der Niderschlag,
 * ... angezeigt.
 *
 * @author Steffen Höltje
 * @version 1.0
 */

package de.fhdw.steffen.awewetter.activitys;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import de.fhdw.steffen.awewetter.R;
import de.fhdw.steffen.awewetter.classes.Weather;
import de.fhdw.steffen.awewetter.classes.WeatherList;

public class WeatherListFragment extends Fragment {

    private View viewWeatherLlist;
    ArrayList<Weather> weatherArrayList;
    ListView listView;
    private static WeatherListFragmentAdapter adapter;

    /**
     *
     * Erstellen der WeatherListFragment-View
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return WeatherListFragment mit allen Darstellungen und Informationen
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        viewWeatherLlist = inflater.inflate(R.layout.fragment_weather_list, container, false);

        listView = viewWeatherLlist.findViewById(R.id.list);

        //Holen der SharedPreferences
        SharedPreferences preferences = getContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);

        //Prüfen ob die SharedPreferences leer sind
        if (!preferences.getAll().isEmpty()) {
            //Wetterdaten aus der Wetterliste holen
            weatherArrayList = new ArrayList<>();
            weatherArrayList = WeatherList.getWeatherList().getWeatherData();

            //weatherArrayList.remove(0);
            //Erstellen des Adapter für die Liste und speichern der Daten im Adapter
            adapter = new WeatherListFragmentAdapter(weatherArrayList, getActivity().getApplicationContext());

            listView.setAdapter(adapter);
        }

        return viewWeatherLlist;

    }
}

