/**
 * WeatherGraphicFragment
 *
 * Fragment, zur Darstellung der Wetter-Grafiken.
 *
 * @author Steffen Höltje
 * @version 1.0
 */

package de.fhdw.steffen.awewetter.activitys;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.Rserve.RConnection;

import java.io.File;

import de.fhdw.steffen.awewetter.R;
import de.fhdw.steffen.awewetter.classes.Server;

public class WeatherGraphicFragment extends Fragment {

    private View viewWeatherGrapic;
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;

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

        mt =  new MyTask();
        mt.execute();

        viewWeatherGrapic = inflater.inflate(R.layout.fragment_weather_graphic, container, false);
        imageView1 = viewWeatherGrapic.findViewById(R.id.imageView1);
        imageView2 = viewWeatherGrapic.findViewById(R.id.imageView2);
        imageView3 = viewWeatherGrapic.findViewById(R.id.imageView3);
        imageView4 = viewWeatherGrapic.findViewById(R.id.imageView4);

        SharedPreferences preferences = getContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);

        if (!preferences.getAll().isEmpty())
        {
            final File weatherDir = new File(getContext().getFilesDir().getAbsolutePath()+"/WeatherData");

            if(weatherDir.exists())
            {
                final File weatherImage1 = new File(getContext().getFilesDir().getAbsolutePath() + "/WeatherData/weatherImage1.png");
                final File weatherImage2 = new File(getContext().getFilesDir().getAbsolutePath() + "/WeatherData/weatherImage2.png");
                final File weatherImage3 = new File(getContext().getFilesDir().getAbsolutePath() + "/WeatherData/weatherImage3.png");
                final File weatherImage4 = new File(getContext().getFilesDir().getAbsolutePath() + "/WeatherData/weatherImage4.png");

                if (weatherImage1.exists()) {
                    Bitmap weatherBitmap1 = BitmapFactory.decodeFile(weatherImage1.getAbsolutePath());
                    imageView1.setImageBitmap(weatherBitmap1);
                } else {
                    imageView1.setImageResource(R.drawable.image_not_found);
                }

                if (weatherImage2.exists()) {
                    Bitmap weatherBitmap2 = BitmapFactory.decodeFile(weatherImage2.getAbsolutePath());
                    imageView2.setImageBitmap(weatherBitmap2);
                } else {
                    imageView2.setImageResource(R.drawable.image_not_found);
                }

                if (weatherImage3.exists()) {
                    Bitmap weatherBitmap3 = BitmapFactory.decodeFile(weatherImage3.getAbsolutePath());
                    imageView3.setImageBitmap(weatherBitmap3);
                } else {
                    imageView3.setImageResource(R.drawable.image_not_found);
                }

                if (weatherImage4.exists()) {
                    Bitmap weatherBitmap4 = BitmapFactory.decodeFile(weatherImage4.getAbsolutePath());
                    imageView4.setImageBitmap(weatherBitmap4);
                } else {
                    imageView4.setImageResource(R.drawable.image_not_found);
                }
            }
        }


        return viewWeatherGrapic;

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
                RConnection connection = server.getConnection();
                REXP version = connection.eval("R.version.string");

                //TODO:
                String location = "Paderborn,de";
                if(location.equals("")) {
                    location = "Paderborn,de";
                }
                // Request Data
                connection.eval("json_file <- \"http://api.openweathermap.org/data/2.5/forecast?q="+location+"&appid=f12d8e86e92a47da5effe7ac1cda7c72\"");
                connection.eval("library(rjson)");

                REXP jsonData = connection.eval("result <- fromJSON(file=json_file)");

                //Create lists
                connection.eval("maxForecast <- c()");
                connection.eval("avgForecast <- c()");
                connection.eval("lowForecast <- c()");
                connection.eval("pressure <- c()");
                connection.eval("humidity <- c()");
                connection.eval("windSpeed <- c()");

                //Get Some Data
                connection.eval("items <- result[[3]]");

                //Get WeatherData
                connection.eval("for (i in 1:items) { " +
                        "avgForecast <- c(avgForecast, result[[4]][[i]][[2]][[1]] - 273.15); " +
                        "maxForecast <- c(maxForecast, result[[4]][[i]][[2]][[3]] - 273.15); " +
                        "lowForecast <- c(lowForecast, result[[4]][[i]][[2]][[2]] - 273.15); " +
                        "pressure <- c(pressure, result[[4]][[i]][[2]][[4]]); " +
                        "humidity <- c(pressure, result[[4]][[i]][[2]][[5]]); " +
                        " }");
                //connection.eval("avgForecast <- c(avgForecast, result[[4]][[i]][[2]][[1]] - 273.15)");
                //connection.eval("maxForecast <- c(maxForecast, result[[4]][[i]][[2]][[3]] - 273.15)");
                //connection.eval("lowForecast <- c(lowForecast, result[[4]][[i]][[2]][[2]] - 273.15)");
                //connection.eval("pressure <- c(pressure, result[[4]][[i]][[2]][[4]])");
                //connection.eval("humidity <- c(pressure, result[[4]][[i]][[2]][[5]])");
                //connection.eval("windSpeed <- c(pressure, result[[4]][[i]][[5]][[1]])");
                //connection.eval("}");

                //Calculate Next
                for(int i = 0; i < 8; i++) {
                    //Calculate Weather
                    connection.eval("diff <- avgForecast[items-8+"+i+"] - avgForecast[items-16+"+i+"]");
                    connection.eval("avgForecast <- c(avgForecast, avgForecast[items-8+"+i+"] + diff)");

                    connection.eval("diff <- maxForecast[items-8+"+i+"] - maxForecast[items-16+"+i+"]");
                    connection.eval("maxForecast <- c(maxForecast, maxForecast[items-8+"+i+"] + diff)");

                    connection.eval("diff <- lowForecast[items-8+"+i+"] - lowForecast[items-16+"+i+"]");
                    connection.eval("lowForecast <- c(lowForecast, lowForecast[items-8+"+i+"] + diff)");

                    //Calculate Weather attributes
                    connection.eval("diff <- pressure[items-8+"+i+"] - pressure[items-16+"+i+"]");
                    connection.eval("pressure <- c(pressure, pressure[items-8+"+i+"] + diff)");

                    connection.eval("diff <- humidity[items-8+"+i+"] - humidity[items-16+"+i+"]");
                    connection.eval("humidity <- c(humidity, humidity[items-8+"+i+"] + diff)");

                    connection.eval("diff <- windSpeed[items-8+"+i+"] - windSpeed[items-16+"+i+"]");
                    connection.eval("windSpeed <- c(windSpeed, windSpeed[items-8+"+i+"] + diff)");
                }
                //Plot Data
                /*
                connection.eval("plot(avgForecast, type=\"o\", col=\"blue\", ylab=\"Temperatur\")");
                connection.eval("title(ylab=\"Temperatur\")");
                //connection.eval("axis(side=1, at = 1:4, labels=LETTERS[1:4])");
                connection.eval("lines(maxForecast, type=\"o\", col=\"red\")");
                REXP plotWeather = connection.eval("lines(lowForecast, type=\"o\", col=\"red\")");
                //Log.d("**WGF**","doInBackground: " + plotWeather.asString());
                plotWeather.asBytes();

                REXP plotPressure = connection.eval("plot(pressure, type=\"o\", col=\"blue\")");
                connection.eval("title(ylab=\"Temperatur\")");

                plotPressure.asBytes();


                REXP plotHumidity = connection.eval("plot(humidity, type=\"o\", col=\"blue\")");
                plotHumidity.asBytes();


                REXP plotWindSpeed = connection.eval("plot(windSpeed, type=\"o\", col=\"blue\")");
                plotWindSpeed.asBytes();
                */

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
