package com.example.programacionandroid_4taactividad;

import androidx.fragment.app.FragmentActivity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapadeGoogleRuta extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    //DE AQUI
    public static double latitudInicio = 0;
    public static double longitudInicio = 0;

    public static double latitudDestino = 0;
    public static double longitudDestino = 0;
    //HASTA ACA ES NUEVO

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapade_google_ruta);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Marcar dos puntos y trazar una ruta
        LatLng inicio = new LatLng(latitudInicio, longitudInicio);
        mMap.addMarker(new MarkerOptions().position(inicio).title("Aqui comienza todo"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(inicio, 12));

        LatLng fin = new LatLng(latitudDestino, longitudDestino);
        mMap.addMarker(new MarkerOptions().position(fin).title("...y aqui termina"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(fin, 12));

        //String url = getRequestUrl(inicio, fin);

        //TaskResquestDirection taskResquestDirection = new TaskResquestDirection();
        //taskResquestDirection.execute(url);
        //COMENTADAS ESTAS LÍNEAS FUNCIONA SIN MOSTRAR TRAZADO, SOLO DOS PUNTOS. QUITO COMENTARIO, SE VA A LA B

    }

    private String getRequestUrl(LatLng origen, LatLng destino) {
        String resultado = "";

        String string_origen = "origin="+origen.latitude+","+origen.longitude;
        String string_destino = "destination="+destino.latitude+","+destino.longitude;

        String sensor = "sensor=false";
        String modo = "mode=driving";

        String param = string_origen+"&"+string_destino+"&"+sensor+"&"+modo;
        String salida = "json";

        resultado = "https://maps.googleapis.com/maps/api/directions/"+salida+"?"+param;

        //https://maps.googleapis.com/maps/api/directions/json?origin=123,123&destination=456,654&sensor=false&code=driving



        return resultado;
    }

    private String requestDirection(String reqUrl) throws IOException {
        String responseString = "";
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;

        try {
            URL url = new URL(reqUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();

            inputStream = httpURLConnection.getInputStream(); //abrir conexión, entrada (lector de flujo)
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream); // objeto que lee entrada (flujo de datos)
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader); //buffer de lectura que almacenará lo que leerá

            StringBuffer stringBuffer = new StringBuffer();
            String linea = "";

            while((linea = bufferedReader.readLine()) != null){ //por cada línea que lees, hasta que sea nulo, en ese momento sale de while
                stringBuffer.append(linea); // mientras lee linea a linea guardando en buffer

            }

            responseString = stringBuffer.toString();
            bufferedReader.close();
            inputStreamReader.close();;


        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(inputStream != null)
                inputStream.close();

            httpURLConnection.disconnect();
        }

        return  responseString;
    }

    public class TaskResquestDirection extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String responseString = "";

            try{
                responseString = requestDirection(strings[0]);

            }catch(IOException e){
                e.printStackTrace();
            }


            return responseString;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            TaskParser taskParser = new TaskParser();
            taskParser.execute();
        }
    }

    public class TaskParser extends AsyncTask<String, Void, List<List<HashMap<String, String>>>>{

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... strings) {
            JSONObject jsonObject = null;
            List<List<HashMap<String, String>>> routes = null;

            try{
                jsonObject = new JSONObject(strings [0]);
                DirectionsParser directionsParser = new DirectionsParser();
                routes = directionsParser.parse(jsonObject);

            }catch (JSONException e){
                e.printStackTrace();
            }

            return  routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> lists) {
            super.onPostExecute(lists);

            ArrayList points = null;
            PolylineOptions polylineOptions = null;

            for(List<HashMap<String, String>> path : lists){
                points = new ArrayList();
                polylineOptions = new PolylineOptions();

                for(HashMap<String, String> point: path){
                    double lat = Double.parseDouble(point.get("lat"));
                    double lon = Double.parseDouble(point.get("lon"));

                    points.add(new LatLng(lat, lon));

                }

                polylineOptions.addAll(points);
                polylineOptions.width(15);
                polylineOptions.color(Color.BLUE);
                polylineOptions.geodesic(true);

            }

            if(polylineOptions != null){
                mMap.addPolyline(polylineOptions);
            }else{
                Toast.makeText(getApplicationContext(), "Dirección no encontrada", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
