/**
 * StartBildschirm der den Nutzer angezeigt wird, wenn er die App startet
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
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.fhdw.steffen.awewetter.R;
import de.fhdw.steffen.awewetter.classes.Server;
import de.fhdw.steffen.awewetter.classes.Weather;
import de.fhdw.steffen.awewetter.classes.WeatherList;

public class SplahScreen extends AppCompatActivity {

    //Variablen
    private static int SPLASH_TIME_OUT = 4000;
    private Server server;
    private MyTask mt;
    private WeatherList weatherList;

    //Variablen für das Wetter
    private String city = "";
    private String day = "";
    private String icon = "";
    private double tempMax = 0d;
    private double tempMin = 0d;
    private double windSpeed = 0d;
    private String windDirection = "";

    SharedPreferences preferences = null;

    private Weather currentWeather = null;

    /**
     * onCreate-Methode, zum erstellen der Activity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        //holen der gespeicherten SharedPreferences
        preferences = this.getApplicationContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);

        mt = new MyTask();
        mt.execute();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splahscreen);

        //Ornder erstellen wenn nicht vorhanden
        final File weatherDir = new File(getFilesDir().getAbsolutePath() + "/WeatherData");
        if (!weatherDir.exists()) {
            weatherDir.mkdir();
        }

        //Handler der die nächste Activity öffnet
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(SplahScreen.this, MainActivity.class);
                startActivity(homeIntent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    /**
     * Asynchroner Prozess, welcher die Daten holt sowie verarbeitet.
     */
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

                if (server != null) {
                    RConnection c = server.getConnection();

                    if (c.isConnected()) {
                        WeatherList weatherList = WeatherList.getWeatherList();

                        String prefCity = preferences.getString("cityName", "");

                        if (prefCity.equals("")) {
                            prefCity = "Paderborn,de";
                        }

                        //aktuelles Wetter
                        c.eval("library(rjson)");
                        c.eval("json_file <- \"http://api.openweathermap.org/data/2.5/weather?q=" + prefCity + "&appid=f12d8e86e92a47da5effe7ac1cda7c72\"");

                        REXP jsonData = c.eval("json_data <- fromJSON(file=json_file)");
                        DecimalFormat f = new DecimalFormat("#0.00");

                        city = c.eval("json_data$name").asString();
                        Format yearFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                        day = yearFormatter.format(Calendar.getInstance().getTime());
                        icon = c.eval("json_data$weather[[1]]$icon").asString();
                        tempMax = c.eval("json_data$main$temp_max").asDouble();
                        tempMax = tempMax - 273.15;
                        tempMax = Double.parseDouble(f.format(tempMax));
                        tempMin = c.eval("json_data$main$temp_min").asDouble();
                        tempMin = tempMin - 273.15;
                        tempMin = Double.parseDouble(f.format(tempMin));
                        windSpeed = c.eval("json_data$wind$speed").asDouble();
                        windDirection = c.eval("json_data$wind$deg").asString();
                        double currentTmp = c.eval("json_data$main$temp").asDouble();
                        currentTmp = currentTmp - 273.15;
                        currentTmp = Double.parseDouble(f.format(currentTmp));
                        int sunrise = c.eval("json_data$sys$sunrise").asInteger();
                        int sunset = c.eval("json_data$sys$sunset").asInteger();
                        Double humidity = c.eval("json_data$main$humidity").asDouble();

                        currentWeather = new Weather(city, day, icon, new Double(tempMax),
                                new Double(tempMin), new Double(windSpeed), windDirection);

                        currentWeather.setCurrentTmp(currentTmp);
                        currentWeather.setSunrise(String.valueOf(sunrise));
                        currentWeather.setSunset(String.valueOf(sunset));
                        currentWeather.setHumidity(humidity);

                        ArrayList<Weather> weatherData = weatherList.getWeatherData();
                        weatherData.add(currentWeather);

                        //Wettertrend
                        c.eval("json_file <- \"http://api.openweathermap.org/data/2.5/forecast?q=" + prefCity + "&appid=f12d8e86e92a47da5effe7ac1cda7c72\"");


                        jsonData = c.eval("json_data <- fromJSON(file=json_file)");
                        int count = c.eval("json_data$cnt").asInteger();
                        city = c.eval("json_data$city$name").asString();
                        for (int i = 1; i < count; i++) {
                            Weather tmpWeather = null;
                            day = c.eval("json_data$list[[" + i + "]]$dt_txt").asString();
                            icon = c.eval("json_data$list[[" + i + "]]$weather[[1]]$icon").asString();
                            tempMax = c.eval("json_data$list[[" + i + "]]$main$temp_max").asDouble();
                            tempMax = tempMax - 273.15;
                            tempMax = Double.parseDouble(f.format(tempMax));
                            tempMin = c.eval("json_data$list[[" + i + "]]$main$temp_min").asDouble();
                            tempMin = tempMin - 273.15;
                            tempMin = Double.parseDouble(f.format(tempMin));
                            windSpeed = c.eval("json_data$list[[" + i + "]]$wind$speed").asDouble();
                            windDirection = c.eval("json_data$list[[" + i + "]]$wind$deg").asString();

                            tmpWeather = new Weather(city, day, icon, new Double(tempMax),
                                    new Double(tempMin), new Double(windSpeed), windDirection);

                            weatherData.add(tmpWeather);
                        }

                        //Wetterdaten der Liste hinzufügen
                        weatherList.setWeatherData(weatherData);
                        weatherList.writeToFile(getFilesDir());

                        //Erstellung der Grafiken
                        SharedPreferences preferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
                        if (server.isConnected()) {
                            String location = preferences.getString("cityName", "");
                            if (location != "") {
                                // Request Data
                                c.eval("library(rjson)");
                                c.eval("json_file <- \"http://api.openweathermap.org/data/2.5/forecast?q=" + location + "&appid=f12d8e86e92a47da5effe7ac1cda7c72\"");

                                c.eval("result <- fromJSON(file=json_file)");

                                //Create lists
                                c.eval("maxForecast <- c()");
                                c.eval("avgForecast <- c()");
                                c.eval("lowForecast <- c()");
                                c.eval("pressure <- c()");
                                c.eval("humidity <- c()");
                                c.eval("windSpeed <- c()");
                                c.eval("dates <- c()");

                                //Get Some Data
                                c.eval("items <- result[[3]]");

                                //Get WeatherData
                                c.eval("for (i in 1:items) {" +
                                        "dates <- c(dates, result[[4]][[i]][[8]]);" +
                                        "avgForecast <- c(avgForecast, result[[4]][[i]][[2]][[1]] - 273.15);" +
                                        "maxForecast <- c(maxForecast, result[[4]][[i]][[2]][[3]] - 273.15);" +
                                        "lowForecast <- c(lowForecast, result[[4]][[i]][[2]][[2]] - 273.15);" +
                                        "pressure <- c(pressure, result[[4]][[i]][[2]][[4]]);" +
                                        "humidity <- c(humidity, result[[4]][[i]][[2]][[5]]);" +
                                        "windSpeed <- c(windSpeed, result[[4]][[i]][[5]][[1]]);" +
                                        "}");

                                //Calculate Next
                                for (int i = 8; i > 0; i--) {
                                    //Calculate Weather
                                    c.eval("diff <- avgForecast[items-8+" + i + "] - avgForecast[items-16+" + i + "]");
                                    c.eval("avgForecast <- c(avgForecast, avgForecast[items-8+" + i + "] + diff)");

                                    c.eval("diff <- maxForecast[items-8+" + i + "] - maxForecast[items-16+" + i + "]");
                                    c.eval("maxForecast <- c(maxForecast, maxForecast[items-8+" + i + "] + diff)");

                                    c.eval("diff <- lowForecast[items-8+" + i + "] - lowForecast[items-16+" + i + "]");
                                    c.eval("lowForecast <- c(lowForecast, lowForecast[items-8+" + i + "] + diff)");

                                    //Calculate Weather attributes
                                    c.eval("diff <- pressure[items-8+" + i + "] - pressure[items-16+" + i + "]");
                                    c.eval("pressure <- c(pressure, pressure[items-8+" + i + "] + diff)");

                                    c.eval("diff <- humidity[items-8+" + i + "] - humidity[items-16+" + i + "]");
                                    c.eval("humidity <- c(humidity, humidity[items-8+" + i + "] + diff)");

                                    c.eval("diff <- windSpeed[items-8+" + i + "] - windSpeed[items-16+" + i + "]");
                                    c.eval("windSpeed <- c(windSpeed, windSpeed[items-8+" + i + "] + diff)");
                                }

                                //Define Opjects
                                REXP image;
                                Bitmap bmp;
                                File file;
                                FileOutputStream fOut;

                                //Create Image
                                c.eval("jpeg('weather.jpg',quality=90)");

                                //Plot Weather
                                c.parseAndEval("plot(avgForecast, type=\"o\", ylab=\"Temperature\", col=\"blue\"); " +
                                        "lines(maxForecast, type=\"o\", col=\"red\");" +
                                        "lines(lowForecast, type=\"o\", col=\"red\");" +
                                        "dev.off()");


                                //Create Image from plot
                                image = c.parseAndEval("r=readBin('weather.jpg','raw',1024*1024); unlink('weather.jpg'); r");

                                //Convert Bytes To Image
                                bmp = BitmapFactory.decodeByteArray(image.asBytes(), 0, image.asBytes().length);

                                //Save Image To Drive
                                file = new File(getFilesDir().getAbsolutePath() + "/WeatherData/weatherImage1.png");
                                fOut = new FileOutputStream(file);
                                bmp.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                                fOut.flush();
                                fOut.close();

                                //Create Image
                                c.eval("jpeg('pressure.jpg',quality=90)");

                                //plot Pressure
                                c.parseAndEval("plot(pressure, type=\"o\", ylab=\"Presure\", col=\"blue\"); " +
                                        "dev.off()");

                                //Create Image from plot
                                image = c.parseAndEval("r=readBin('pressure.jpg','raw',1024*1024); unlink('pressure.jpg'); r");

                                //Convert Bytes To Image
                                bmp = BitmapFactory.decodeByteArray(image.asBytes(), 0, image.asBytes().length);

                                //Save Image To Drive
                                file = new File(getFilesDir().getAbsolutePath() + "/WeatherData/weatherImage2.png");
                                fOut = new FileOutputStream(file);
                                bmp.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                                fOut.flush();
                                fOut.close();

                                //Create Image
                                c.eval("jpeg('humidity.jpg',quality=90)");

                                //plot humidity
                                c.parseAndEval("plot(humidity, type=\"o\", ylab=\"Humidity\", col=\"blue\"); " +
                                        "dev.off()");

                                //Create Image from plot
                                image = c.parseAndEval("r=readBin('humidity.jpg','raw',1024*1024); unlink('humidity.jpg'); r");

                                //Convert Bytes To Image
                                bmp = BitmapFactory.decodeByteArray(image.asBytes(), 0, image.asBytes().length);

                                //Save Image To Drive
                                file = new File(getFilesDir().getAbsolutePath() + "/WeatherData/weatherImage3.png");
                                fOut = new FileOutputStream(file);
                                bmp.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                                fOut.flush();
                                fOut.close();

                                //Create Image
                                c.eval("jpeg('speed.jpg',quality=90)");

                                //plot Windspeed
                                c.parseAndEval("plot(windSpeed, type=\"o\", ylab=\"windspeed\", col=\"blue\"); " +
                                        "dev.off()");

                                //Create Image from plot
                                image = c.parseAndEval("r=readBin('speed.jpg','raw',1024*1024); unlink('speed.jpg'); r");

                                //Convert Bytes To Image
                                bmp = BitmapFactory.decodeByteArray(image.asBytes(), 0, image.asBytes().length);

                                //Save Image To Drive
                                file = new File(getFilesDir().getAbsolutePath() + "/WeatherData/weatherImage4.png");
                                fOut = new FileOutputStream(file);
                                bmp.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                                fOut.flush();
                                fOut.close();
                            }
                        }
                    }
                } else {
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
