/**
 * WeatherList-Klasse
 * SingeltonListe mit allen Wetterobjekten
 *
 * @author Roman Speer, Steffen Höltje
 * @version 1.0
 */

package de.fhdw.steffen.awewetter.classes;

import android.content.Context;
import android.os.Environment;
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

    //Variablen
    private static WeatherList weatherList;
    private ArrayList<Weather> weatherData = new ArrayList<Weather>();

    /**
     * Erstellen/ zurückgeben der Wetterliste
     *
     * @return Singelton Wetterliste
     */
    public static WeatherList getWeatherList() {
        //Prüfen ob der Server null ist
        if (weatherList == null) {
            //Neuen Server erstellen
            weatherList = new WeatherList();
        }
        return weatherList;
    }

    private WeatherList() {

    }

    /**
     * @return zurückgeben der Wetterliste ArrayList
     */
    public ArrayList<Weather> getWeatherData() {

        return weatherData;
    }

    /**
     * Wetterdaten Liste setzten
     *
     * @param weatherData Wetterdaten Liste
     */
    public void setWeatherData(ArrayList<Weather> weatherData) {
        this.weatherData = weatherData;
    }

    /**
     * Wetterdaten in eine Datei schreiben
     *
     * @param file um den Dateipfad zu bekommen
     */
    public void writeToFile(File file) {
        try {

            final File weatherTxt = new File(file.getAbsolutePath() + "/WeatherData", "weatherData.txt");
            FileOutputStream stream = new FileOutputStream(weatherTxt);
            try {
                stream.write(toJSONString(weatherData).getBytes());
            } finally {
                stream.close();
            }
        } catch (Exception e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * String aus einer Datei holen
     *
     * @param file um den Dateipfad zu bekommen
     * @return
     * @throws Exception
     */
    public String readFromFile(File file) throws Exception {
        File fl = new File(file.getAbsolutePath() + "/WeatherData/weatherData.txt");
        FileInputStream fin = new FileInputStream(fl);
        String ret = convertStreamToString(fin);
        //Make sure you close all streams.
        fin.close();
        return ret;
    }

    /**
     * Input Stream zu einem String machen
     *
     * @param is der InputStream des String der Datei
     * @return
     * @throws Exception
     */
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

    /**
     * Wetter zu JsonString machen
     *
     * @param weatherData
     * @return
     */
    private String toJSONString(List<Weather> weatherData) {
        Gson gson = new Gson();
        StringBuilder sb = new StringBuilder();
        for (Weather d : weatherData) {
            sb.append(gson.toJson(d));
        }
        String tmp = sb.toString();
        return sb.toString();
    }

    //String zur Liste machen

    /**
     * String zur Liste machen
     *
     * @param weather Wetterdaten als String
     * @return Wetterdaten als Liste
     */
    public ArrayList<Weather> stringToList(String weather) {
        Gson gson = new Gson();
        TypeToken<List<Weather>> token = new TypeToken<List<Weather>>() {
        };
        return gson.fromJson(weather, token.getType());

    }

    /**
     * Alle Daten aus der Wetterliste löschen
     */
    public void deleteDataFromList() {
        this.weatherData = new ArrayList<Weather>();
    }

}
