/**
 * MainActivity
 *
 * @author Steffen Höltje, Roman Speer
 * @version 1.0
 */

package de.fhdw.steffen.awewetter.activitys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.REngineException;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.fhdw.steffen.awewetter.R;
import de.fhdw.steffen.awewetter.classes.Server;
import de.fhdw.steffen.awewetter.classes.Weather;
import de.fhdw.steffen.awewetter.classes.WeatherList;

public class SplahScreen extends AppCompatActivity{
    private static int SPLASH_TIME_OUT = 4000;
    private Server server;
    private MyTask mt;
    private WeatherList weatherList;

    private String city = "";
    private String day = "";
    private String icon = "";
    private double tempMax = 0d;
    private double tempMin = 0d;
    private double windSpeed = 0d;
    private String windDirection = "";

    SharedPreferences preferences = null;

    private Weather currentWeather = null;
    //Hier muss der Download der Daten stattfinden wenn Daten hinterlegt sind...

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        preferences = this.getApplicationContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);

        weatherList =  new WeatherList().getWeatherList();
        List<Weather> weatherData = new ArrayList<Weather>();

        mt =  new MyTask();
        mt.execute();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splahscreen);

        //Ornder erstellen wenn nicht vorhanden
        final File weatherDir = new File(getFilesDir().getAbsolutePath()+"/WeatherData");
        if(!weatherDir.exists())
        {
            weatherDir.mkdir();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(SplahScreen.this, MainActivity.class);
                startActivity(homeIntent);
                finish();
            }
        },SPLASH_TIME_OUT);
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

                RConnection c = server.getConnection();

                if(c.isConnected()) {
                    WeatherList weatherList = WeatherList.getWeatherList();

                    String prefCity = preferences.getString("cityName", "");

                    if(prefCity.equals("")) {
                        prefCity ="Paderborn,de";
                    }

                    //aktuelles Wetter
                    c.eval("library(rjson)");
                    c.eval("json_file <- \"http://api.openweathermap.org/data/2.5/weather?q="+prefCity+"&appid=f12d8e86e92a47da5effe7ac1cda7c72\"");

                    REXP jsonData = c.eval("json_data <- fromJSON(file=json_file)");

                    city = c.eval("json_data$name").asString();
                    day = Calendar.getInstance().getTime().toString();
                    icon =  c.eval("json_data$weather[[1]]$icon").asString();
                    tempMax =  c.eval("json_data$main$temp_max").asDouble();
                    tempMin =  c.eval("json_data$main$temp_min").asDouble();
                    windSpeed =  c.eval("json_data$wind$speed").asDouble();
                    windDirection =  c.eval("json_data$wind$deg").asString();

                    currentWeather = new Weather(city,day,icon, new Double(tempMax),
                            new Double(tempMin), new Double(windSpeed), windDirection);

                     List<Weather> weatherData = weatherList.getWeatherData();
                     weatherData.add(currentWeather);

                     //Wettertrend
                     c.eval("json_file <- \"http://api.openweathermap.org/data/2.5/forecast?q="+prefCity+"&appid=f12d8e86e92a47da5effe7ac1cda7c72\"");

                     jsonData = c.eval("json_data <- fromJSON(file=json_file)");
                     city = c.eval("json_data$city$name").asString();
                     for (int i = 1 ; i <= 40 ; i++) {
                         Weather tmpWeather = null;
                         day = c.eval("json_data$list[["+ i + "]]$dt_txt").asString();
                         icon =  c.eval("json_data$list[["+ i + "]]$weather[[1]]$icon").asString();
                         tempMax =  c.eval("json_data$list[["+ i + "]]$main$temp_max").asDouble();
                         tempMin =  c.eval("json_data$list[[" + i + "]]$main$temp_min").asDouble();
                         windSpeed =  c.eval("json_data$list[[" + i + "]]$wind$speed").asDouble();
                         windDirection =  c.eval("json_data$list[[" + i + "]]$wind$deg").asString();

                         tmpWeather = new Weather(city,day,icon, new Double(tempMax),
                                 new Double(tempMin), new Double(windSpeed), windDirection);

                         weatherData.add(tmpWeather);

                     }

                    weatherList.setWeatherData(weatherData);

                } else {
                    //read Data from File

                    weatherList.setWeatherData(weatherList.stringToList(weatherList.readFromFile(getFilesDir())));
                }


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
