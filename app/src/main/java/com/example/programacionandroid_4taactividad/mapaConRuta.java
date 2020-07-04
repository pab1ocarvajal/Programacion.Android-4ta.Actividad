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
public class mapaConRuta extends Fragment {
    //DE AQUI
    View vistaRuta;
    Button btnBotonRuta;
    EditText txtLatitudInicio;
    EditText txtLongitudInicio;
    EditText txtLatitudDestino;
    EditText txtLongitudDestino;
    // HASTA ES NUEVO

    public mapaConRuta() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vistaRuta = inflater.inflate(R.layout.fragment_mapa_con_ruta, container, false);

        btnBotonRuta = vistaRuta.findViewById(R.id.btnBuscarRuta);
        txtLatitudInicio = vistaRuta.findViewById(R.id.txtLatitudInicio);
        txtLongitudInicio = vistaRuta.findViewById(R.id.txtLongitudInicio);

        txtLatitudDestino = vistaRuta.findViewById(R.id.txtLatitudDestino);
        txtLongitudDestino = vistaRuta.findViewById(R.id.txtLongitudDestino);

        txtLatitudInicio.setText("-18.465452");
        txtLongitudInicio.setText("-70.305503");

        txtLatitudDestino.setText("-18.460465");
        txtLongitudDestino.setText("-70.301147");

        btnBotonRuta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Trazando la ruta...",Toast.LENGTH_LONG).show();
                buscarUbicacionRuta();
            }
        });

        return vistaRuta;
    }

    //DE AQUI
    public void buscarUbicacionRuta(){
        double latitudInicio = Double.parseDouble(txtLatitudInicio.getText().toString());
        double longitudInicio = Double.parseDouble(txtLongitudInicio.getText().toString());

        double latitudDestino = Double.parseDouble(txtLatitudDestino.getText().toString());
        double longitudDestino = Double.parseDouble(txtLongitudDestino.getText().toString());

        MapadeGoogleRuta.latitudInicio = latitudInicio;
        MapadeGoogleRuta.longitudInicio = longitudInicio;

        MapadeGoogleRuta.latitudDestino = latitudDestino;
        MapadeGoogleRuta.longitudDestino = longitudDestino;

        Intent i = new Intent(getActivity(), MapadeGoogleRuta.class);
        getActivity().startActivity(i);
    }
    //HASTA ACA ES NUEVO
}
