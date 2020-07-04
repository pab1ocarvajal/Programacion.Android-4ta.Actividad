package com.example.programacionandroid_4taactividad;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class mapaConGps extends Fragment {

    //DE AQUI
    View vistaGps;
    Button btnBotonGps;
    EditText txtLatGps;
    EditText txtLongGps;
    // HASTA ES NUEVO

    public mapaConGps() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vistaGps = inflater.inflate(R.layout.fragment_mapa_con_gps, container, false);

        btnBotonGps = vistaGps.findViewById(R.id.btnGPS);
        txtLatGps = vistaGps.findViewById(R.id.txtLatitudGps);
        txtLongGps = vistaGps.findViewById(R.id.txtLongitudGps);

        txtLatGps.setText("-18.488693");
        txtLongGps.setText("-70.286996");

        btnBotonGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Buscando ubicación...",Toast.LENGTH_LONG).show();
                buscarUbicacionGPS();
            }
        });

        return vistaGps;
    }

    //DE AQUI
    public void buscarUbicacionGPS(){
        double latitudGPS = Double.parseDouble(txtLatGps.getText().toString());
        double longitudGPS = Double.parseDouble(txtLongGps.getText().toString());

        MapadeGoogleGPS.latGPS = latitudGPS;
        MapadeGoogleGPS.longGPS = longitudGPS;

        Intent i = new Intent(getActivity(), MapadeGoogleGPS.class);
        getActivity().startActivity(i);
    }
    //HASTA ACA ES NUEVO

}
