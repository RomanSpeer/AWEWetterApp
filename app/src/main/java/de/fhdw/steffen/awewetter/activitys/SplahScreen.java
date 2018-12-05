/**
 * MainActivity
 *
 * @author Steffen Höltje, Roman Speer
 * @version 1.0
 */

package de.fhdw.steffen.awewetter.activitys;

import android.content.Intent;
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
import android.view.MenuItem;

import java.io.File;

import de.fhdw.steffen.awewetter.R;
import de.fhdw.steffen.awewetter.classes.Server;

public class SplahScreen extends AppCompatActivity{
    private static int SPLASH_TIME_OUT = 4000;

    //Hier muss der Download der Daten stattfinden wenn Daten hinterlegt sind...

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
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
}
