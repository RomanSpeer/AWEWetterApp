/**
 * WeatherListFragment
 *
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
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import de.fhdw.steffen.awewetter.R;
import de.fhdw.steffen.awewetter.classes.Weather;

public class WeatherListFragment extends Fragment{

    private View viewWeatherLlist;
    ArrayList<Weather> weatherArrayList;
    ListView listView;
    private static WeatherListFragmentAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        viewWeatherLlist = inflater.inflate(R.layout.fragment_weather_list, container, false);

        listView = viewWeatherLlist.findViewById(R.id.list);

        SharedPreferences preferences = getContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);

        if (!preferences.getAll().isEmpty())
        {
            weatherArrayList = new ArrayList<>();

            weatherArrayList.add(new Weather("1","1",1,1,1,"1"));
            weatherArrayList.add(new Weather("1","1",1,1,1,"1"));
            weatherArrayList.add(new Weather("1","1",1,1,1,"1"));
            weatherArrayList.add(new Weather("1","1",1,1,1,"1"));
            weatherArrayList.add(new Weather("1","1",1,1,1,"1"));
            weatherArrayList.add(new Weather("1","1",1,1,1,"1"));
            weatherArrayList.add(new Weather("1","1",1,1,1,"1"));
            weatherArrayList.add(new Weather("1","1",1,1,1,"1"));
            weatherArrayList.add(new Weather("1","1",1,1,1,"1"));
            weatherArrayList.add(new Weather("1","1",1,1,1,"1"));

            adapter = new WeatherListFragmentAdapter(weatherArrayList,getActivity().getApplicationContext());

            listView.setAdapter(adapter);
        }

        return viewWeatherLlist;

    }
}

