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
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;

import de.fhdw.steffen.awewetter.R;

public class WeatherGraphicFragment extends Fragment {

    private View viewWeatherGrapic;
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;
    private ImageView imageView5;
    private ImageView imageView6;


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

        viewWeatherGrapic = inflater.inflate(R.layout.fragment_weather_graphic, container, false);
        imageView1 = viewWeatherGrapic.findViewById(R.id.imageView1);
        imageView2 = viewWeatherGrapic.findViewById(R.id.imageView2);
        imageView3 = viewWeatherGrapic.findViewById(R.id.imageView3);
        imageView4 = viewWeatherGrapic.findViewById(R.id.imageView4);
        imageView5 = viewWeatherGrapic.findViewById(R.id.imageView5);
        imageView6 = viewWeatherGrapic.findViewById(R.id.imageView6);

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
                final File weatherImage5 = new File(getContext().getFilesDir().getAbsolutePath() + "/WeatherData/weatherImage5.png");
                final File weatherImage6 = new File(getContext().getFilesDir().getAbsolutePath() + "/WeatherData/weatherImage6.png");

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
                    imageView1.setImageBitmap(weatherBitmap4);
                } else {
                    imageView4.setImageResource(R.drawable.image_not_found);
                }

                if (weatherImage5.exists()) {
                    Bitmap weatherBitmap5 = BitmapFactory.decodeFile(weatherImage5.getAbsolutePath());
                    imageView1.setImageBitmap(weatherBitmap5);
                } else {
                    imageView5.setImageResource(R.drawable.image_not_found);
                }

                if (weatherImage6.exists()) {
                    Bitmap weatherBitmap6 = BitmapFactory.decodeFile(weatherImage6.getAbsolutePath());
                    imageView6.setImageBitmap(weatherBitmap6);
                } else {
                    imageView6.setImageResource(R.drawable.image_not_found);
                }
            }
        }


        return viewWeatherGrapic;

    }
}
