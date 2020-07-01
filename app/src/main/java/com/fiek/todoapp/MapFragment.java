package com.fiek.todoapp;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterManager;
import java.util.ArrayList;
import java.util.List;
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private GoogleMap map;
    private ClusterManager<MyItem>clusterManager;
    private List<MyItem> items = new ArrayList<>();

    public MapFragment() {
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GoogleMapOptions options = new GoogleMapOptions();
        options.mapType(GoogleMap.MAP_TYPE_TERRAIN);
        options.zoomControlsEnabled(true);
        options.compassEnabled(true);
        SupportMapFragment mapFragment = SupportMapFragment.newInstance(options);
        FragmentTransaction ft = getFragmentManager().beginTransaction().replace(R.id.mapContainer, mapFragment);
        ft.commit();
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map=googleMap;
        clusterManager = new ClusterManager<MyItem>(getContext(),map);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(42.5623091,20.341632),6));
        map.setOnCameraIdleListener(clusterManager);
        map.setOnMarkerClickListener(clusterManager);
        addItems();
        clusterManager.cluster();
    }

    private void addItems() {
                clusterManager.addItem(new MyItem(42.675487, 21.210694, "Parku i Germise", "Prishtine"));
                clusterManager.addItem(new MyItem(42.663345, 21.164441, "Teatri", "Prishtine"));
                clusterManager.addItem(new MyItem(42.661936, 21.161777, "KinoABC", "Prishtine"));
                clusterManager.addItem(new MyItem(42.522513, 21.123540, "Parku i Qytetit", "Prishtine"));
                clusterManager.addItem(new MyItem(42.648687, 21.167176, "Universtiteti i Prishtines", "Prishtine"));
                clusterManager.addItem(new MyItem(42.654150, 21.153227 , " Bulevardi Bill Clinton", "Prishtine"));
                clusterManager.addItem(new MyItem(42.645686, 21.158185, "Stadiumi Futbollistik KF \"Ramiz Sadiku\"", "Prishtine"));
                clusterManager.addItem(new MyItem(42.209726,20.7433946, "Kalaja", "Prizren"));
                clusterManager.addItem(new MyItem(42.2113559,20.7415903, "Lidhja", "Prizren"));
                clusterManager.addItem(new MyItem(42.2176256,20.6034969, "Komuna", "Prizren"));
                clusterManager.addItem(new MyItem(42.2090193,20.7318047 , "Shadervani", "Prizren"));
                clusterManager.addItem(new MyItem(42.6938779,20.156527, "Gryka e Rugoves", "Peje"));
                clusterManager.addItem(new MyItem(42.5240403,20.5981099, "Ujevara e Mirushes", "Malisheve"));
                clusterManager.addItem(new MyItem(42.6234183,20.1857219, "National Park \"Bjeshkët e Nemuna\"", "Peje,Gjakove"));
                clusterManager.addItem(new MyItem(42.7428689,20.0338988, "Boge", "Boge"));
                clusterManager.addItem(new MyItem(42.2770685,21.5368789 , "Kitke", "Kamenice"));
                clusterManager.addItem(new MyItem(42.5293742,21.4635289, "Vali Ranch", "Perlepnice"));
                clusterManager.addItem(new MyItem(42.8895444,20.8605086  , "Trepça ", "Mitrovice"));
    }
}
