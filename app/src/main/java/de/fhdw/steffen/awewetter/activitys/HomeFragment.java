/**
 *
 * HomeFragment
 * Darstellung der Startseite der App.
 * Hier wird eine Uhr sowie bei hinterlegten Daten
 * auch die aktuellen Wetterinformationen angezeigt.
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.Rserve.RConnection;

import de.fhdw.steffen.awewetter.R;
import de.fhdw.steffen.awewetter.classes.Server;
import de.fhdw.steffen.awewetter.classes.Weather;
import de.fhdw.steffen.awewetter.classes.WeatherList;

public class HomeFragment extends Fragment {

    private View viewHome;
    public LinearLayout linearLayoutWeatherHome;
    public TextView textViewCityHome;
    public ImageView imageViewWeatherHome;
    public TextView textViewTemperatureHome;
    public TextView textViewPrecipitationHome;
    public TextView textViewSunriseHome;
    public TextView textViewSunsetHome;
    public TextView textViewInformationHome;
    public TextView textViewUpdateHome;

    private boolean citySet = true;

    //Strings weather

    //wird denke nicht benutzt
    private String temerature = "";
    private String precipitation = "";
    private String sunrise = "";
    private String sunset = "";
    private String information = "";
    private String update = "";

    private Server server;
    private MyTask mt;

    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mt = new MyTask();
        mt.execute();

        viewHome = inflater.inflate(R.layout.fragment_home, container, false);
        linearLayoutWeatherHome = viewHome.findViewById(R.id.linearLayoutWeatherHome);
        textViewCityHome = viewHome.findViewById(R.id.textViewCityHome);
        imageViewWeatherHome = viewHome.findViewById(R.id.imageViewWeatherHome);
        textViewTemperatureHome = viewHome.findViewById(R.id.textViewTemperatureHome);
        textViewPrecipitationHome = viewHome.findViewById(R.id.textViewPrecipitationHome);
        textViewSunriseHome = viewHome.findViewById(R.id.textViewSunriseHome);
        textViewSunsetHome = viewHome.findViewById(R.id.textViewSunsetHome);
        textViewInformationHome = viewHome.findViewById(R.id.textViewInformationHome);
        textViewUpdateHome = viewHome.findViewById(R.id.textViewUpdateHome);

        SharedPreferences preferences = getContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);

        if (!preferences.getAll().isEmpty())
        {
            //Hier currentWeather aus der liste holen
            Weather currentWeather = new Weather("1","1","01d",1d,1d,1d,"1");

            linearLayoutWeatherHome.setVisibility(View.VISIBLE);

            textViewCityHome.setText(getResources().getString(R.string.fragment_home_city) + preferences.getString("cityName", ""));
            textViewTemperatureHome.setText(getResources().getString(R.string.fragment_home_temperature) + temerature);
            textViewPrecipitationHome.setText(getResources().getString(R.string.fragment_home_precipitation) + precipitation);
            textViewSunriseHome.setText(getResources().getString(R.string.fragment_home_sunrise) + sunrise);
            textViewSunsetHome.setText(getResources().getString(R.string.fragment_home_sunset) + sunset);
            textViewUpdateHome.setText(getResources().getString(R.string.fragment_home_update) + update);

            switch (currentWeather.getIconWeather())
            {
                case "01d":
                    imageViewWeatherHome.setImageResource(R.drawable.sun01d);
                    break;
                case "02d":
                    imageViewWeatherHome.setImageResource(R.drawable.fewclouds02d);
                    break;
                case "03d":
                    imageViewWeatherHome.setImageResource(R.drawable.scatteredclouds03d);
                    break;
                case "04d":
                    imageViewWeatherHome.setImageResource(R.drawable.brokenclouds04d);
                    break;
                case "09d":
                    imageViewWeatherHome.setImageResource(R.drawable.showerrain09d);
                    break;
                case "10d":
                    imageViewWeatherHome.setImageResource(R.drawable.rain10d);
                    break;
                case "11d":
                    imageViewWeatherHome.setImageResource(R.drawable.thunderstorm11d);
                    break;
                case "13d":
                    imageViewWeatherHome.setImageResource(R.drawable.snow13d);
                    break;
                case "50d":
                    imageViewWeatherHome.setImageResource(R.drawable.mist50d);
                    break;
                default:
                    imageViewWeatherHome.setImageResource(R.drawable.image_not_found);
                    ;
            }

            //Logik wetter geeignet sport
            String sportType = preferences.getString("sportType", "");

            switch (sportType) {
                case "Kein Sport":
                    textViewInformationHome.setText(getResources().getString(R.string.fragment_home_information_not_given));
                    break;
                case "Fahrrad fahren":
                    if(currentWeather.getTempMaxWeather()< 30 || currentWeather.getTempMinWeather() > 0)
                    {
                        /*if(currentWeather.getDescritionWeather().equals"???")
                        {
                            textViewInformationHome.setText(getResources().getString(R.string.fragment_home_information_bad_good) + sportType);
                        }*/
                        textViewInformationHome.setText(getResources().getString(R.string.fragment_home_information_perfect) + sportType);
                    }
                    else
                    {
                        textViewInformationHome.setText(getResources().getString(R.string.fragment_home_information_bad) + sportType);
                    }
                    break;
                case "Laufen":
                    if(currentWeather.getTempMaxWeather()< 25 || currentWeather.getTempMinWeather() > 5)
                    {
                        /*if(currentWeather.getDescritionWeather().equals"???")
                        {
                            textViewInformationHome.setText(getResources().getString(R.string.fragment_home_information_bad_good) + sportType);
                        }*/
                        textViewInformationHome.setText(getResources().getString(R.string.fragment_home_information_perfect) + sportType);
                    }
                    else
                    {
                        textViewInformationHome.setText(getResources().getString(R.string.fragment_home_information_bad) + sportType);
                    }
                    break;
                case "Fallschirmspringen":
                        //????
                    break;
                case "Segeln":
                    //????
                    break;
                default:
                    textViewInformationHome.setVisibility(View.INVISIBLE);
            }

            textViewInformationHome.setText(getResources().getString(R.string.fragment_home_information_not_given) + information);
        }
        else
        {
            linearLayoutWeatherHome.setVisibility(View.INVISIBLE);
        }

        return viewHome;
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
