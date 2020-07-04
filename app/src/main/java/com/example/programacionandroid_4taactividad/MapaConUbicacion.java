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
public class MapaConUbicacion extends Fragment {
    // DE AQUI
    View vista;
    Button btnBoton;
    EditText txtLatitud;
    EditText txtLongitud;
    // HASTA ACA ES NUEVO

    public MapaConUbicacion() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //DE AQUI
        vista = inflater.inflate(R.layout.fragment_mapa_con_ubicacion, container, false);

        btnBoton = vista.findViewById(R.id.btnBuscar);
        txtLatitud = vista.findViewById(R.id.txtLatitud);
        txtLongitud = vista.findViewById(R.id.txtLongitud);

        txtLatitud.setText("-27.125959");
        txtLongitud.setText("-109.349576");

        btnBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Buscando las coordenadas.",Toast.LENGTH_LONG).show();
                buscarUbicacion();
            }
        });

        return vista;
        //HASTA ACÁ
    }

    //DE AQUI
    public void buscarUbicacion(){
      double latitud = Double.parseDouble(txtLatitud.getText().toString());
      double longitud = Double.parseDouble(txtLongitud.getText().toString());

      MapadeGoogle.latitud = latitud;
      MapadeGoogle.longitud = longitud;

      Intent i = new Intent(getActivity(), MapadeGoogle.class);
      getActivity().startActivity(i);
    }
    //HASTA ACA ES NUEVO
}
