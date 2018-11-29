/**
 * SettingsFragment
 * Eingabe des Ortes wofür das Wetter angezeigt werden soll
 * und die Auswahl der Sportart.
 * Ist ein Ort mehrmals verfügbar kann der genaue Ort ausgewählt werden.
 * Bei Fehlerhaften eingaben werden Fehlermeldungen ausgegeben.
 *
 *
 * @author Steffen Höltje
 * @version 1.0
 */

package de.fhdw.steffen.awewetter.activitys;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import de.fhdw.steffen.awewetter.R;

public class SettingsFragment extends Fragment {

    private View viewSettings;
    private Spinner spinnerSport;
    private EditText editTextCity;
    private ImageButton btnSave;
    private boolean isClickSave = false;

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

        viewSettings = inflater.inflate(R.layout.fragment_settings, container, false);
        spinnerSport = viewSettings.findViewById(R.id.spinnerSport);
        editTextCity = viewSettings.findViewById(R.id.editTextCity);
        btnSave = viewSettings.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                btnSaveClick();
            }
        });

        editTextCity.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s)  {
                if(isClickSave == true)
                {
                    isClickSave = false;
                    editTextCity.setError(null);
                }
            }
        });

        String[] items = new String[]{"Fahrrad", "Laufen", " "};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(viewSettings.getContext(), android.R.layout.simple_dropdown_item_1line, items);
        spinnerSport.setAdapter(adapter);

        return viewSettings;
    }

    private void btnSaveClick() {

        //city String
        String cityName = editTextCity.getText().toString();
        String cityWithoutSpace = cityName.replaceAll(" ","");

        //sport string
        String sportName = spinnerSport.getSelectedItem().toString();

        isClickSave = true;

        //check is city empty
        if(cityName.isEmpty() || cityName.equals("") || cityWithoutSpace.equals(""))
        {
            //set error
            Toast.makeText(getActivity(), "Bitte Stadt eingeben", Toast.LENGTH_LONG).show();
            editTextCity.setError("This field can not be blank");
        }
        else
        {
            //check if city exists
            //nur einmal vorhanden dann speichern sonst auswahl anzeigen
        }
    }

}
