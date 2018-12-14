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
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.Rserve.RConnection;

import java.util.ArrayList;

import de.fhdw.steffen.awewetter.R;
import de.fhdw.steffen.awewetter.classes.Server;
import de.fhdw.steffen.awewetter.classes.Weather;
import de.fhdw.steffen.awewetter.classes.WeatherList;

public class WeatherListFragment extends Fragment{

    private View viewWeatherLlist;
    ArrayList<Weather> weatherArrayList;
    ListView listView;
    private static WeatherListFragmentAdapter adapter;

    private Server server;
    private MyTask mt;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mt =  new MyTask();
        mt.execute();

        viewWeatherLlist = inflater.inflate(R.layout.fragment_weather_list, container, false);

        listView = viewWeatherLlist.findViewById(R.id.list);

        SharedPreferences preferences = getContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);

        if (!preferences.getAll().isEmpty())
        {
            weatherArrayList = new ArrayList<>();
            weatherArrayList = WeatherList.getWeatherList().getWeatherData();

            //weatherArrayList.remove(0);

            adapter = new WeatherListFragmentAdapter(weatherArrayList,getActivity().getApplicationContext());

            listView.setAdapter(adapter);
        }

        return viewWeatherLlist;

    }

    class MyTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                server = new Server().getServer();
                server.connect("10.0.2.2");
                if(server.isConnected())
                    Log.d("Connection", "Verbunden");
                RConnection c = server.getConnection();
                REXP x = c.eval("R.version.string");
            } catch (Exception x) {
                x.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }
    }
}

