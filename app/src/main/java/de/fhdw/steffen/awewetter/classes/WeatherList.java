package de.fhdw.steffen.awewetter.classes;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class WeatherList {

    private static WeatherList weatherList;

    private ArrayList<Weather> weatherData = new ArrayList<Weather>();

    public static WeatherList getWeatherList()
    {
        //Prüfen ob der Server null ist
        if (weatherList == null)
        {
            //Neuen Server erstellen
            weatherList = new WeatherList();
        }
        return weatherList;
    }

    private WeatherList()
    {

    }

    public ArrayList<Weather> getWeatherData() {

        return weatherData;
    }

    public void setWeatherData(ArrayList<Weather> weatherData) {
        this.weatherData = weatherData;
    }

    //Quelle https://stackoverflow.com/questions/12910503/read-file-as-string
    public void writeToFile(File file) {
        try {

            final File weatherTxt = new File(file.getAbsolutePath()+"/WeatherData",  "weatherData.txt");
            FileOutputStream stream = new FileOutputStream(weatherTxt);
            try {
                stream.write(toJSONString(weatherData).getBytes());
            } finally {
                stream.close();
            }
        }
        catch (Exception e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public String readFromFile (File file) throws Exception {
        File fl = new File(file.getAbsolutePath()+"/WeatherData/weatherData.txt");
        FileInputStream fin = new FileInputStream(fl);
        String ret = convertStreamToString(fin);
        //Make sure you close all streams.
        fin.close();
        return ret;
    }

    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

    private String toJSONString(List<Weather> weatherData) {
        Gson gson = new Gson();
        StringBuilder sb = new StringBuilder();
        for(Weather d : weatherData) {
            sb.append(gson.toJson(d));
        }
        String tmp = sb.toString();
        return sb.toString();
    }

    public List<Weather> stringToList(String weather) {
        Gson gson = new Gson();
        TypeToken<List<Weather>> token = new TypeToken<List<Weather>>() {};
        return gson.fromJson(weather, token.getType());

    }

}
