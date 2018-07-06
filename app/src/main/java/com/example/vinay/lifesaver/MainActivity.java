package com.example.vinay.lifesaver;

import android.Manifest;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.example.vinay.lifesaver.Adapter.CustomAdapter;
import com.example.vinay.lifesaver.Adapter.MyListAdapter;
import com.example.vinay.lifesaver.Model.DataModel;
import com.example.vinay.lifesaver.Model.MyData1;

public class MainActivity extends Activity implements
        SearchView.OnQueryTextListener, SearchView.OnCloseListener{

    private SearchView search;
    private MyListAdapter listAdapter;
    private ExpandableListView myList;
    private ArrayList<Group> groupList = new ArrayList<Group>();
   private ArrayList<Item> itemList = new ArrayList<Item>();
/////////////
private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;
    private static final int MY_PERMISSIONS_REQUEST_call = 1;
    ///////////////////
    //location
    private LocationManager locationManager;
    Location location;

    private LocationListener locationListener;
    String mloc=new String("nill_bro");

    //
     NavigationView navigation;
     Button button;


    //////////////
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        search = (SearchView) findViewById(R.id.search);
        search.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        search.setIconifiedByDefault(true);
        search.setOnQueryTextListener(this);
        search.setOnCloseListener(this);

        displayList();//display the list
       myList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView listview, View view,
                                        int groupPos, int childPos, long id) {
                /*Toast.makeText(getApplicationContext(),
                        "You clicked : " + listAdapter.getChild(groupPos,childPos),
                        Toast.LENGTH_SHORT).show();*/
                Toast.makeText(MainActivity.this, ""+ ((TextView) view.findViewById(R.id.name)).getText(), Toast.LENGTH_SHORT).show();
               String select= (String) ((TextView) view.findViewById(R.id.name)).getText();
               // startActivity(new Intent(MainActivity.this,Anemia.class));
               if(select.equals("Anemia")){
                   startActivity(new Intent(MainActivity.this,Anemia.class));
               }
                if(select.equals("Flood")){
                    startActivity(new Intent(MainActivity.this,Flood.class));
                }
               if(select.replaceAll("\\s","").equalsIgnoreCase("ElectricShock")){
                    startActivity(new Intent(MainActivity.this,Electric.class));
                }
                if(select.replaceAll("\\s","").equalsIgnoreCase("CardiacArrest")){
                    startActivity(new Intent(MainActivity.this,Cardiac.class));
                }
                if(select.replaceAll("\\s","").equalsIgnoreCase("HeartBlock")){
                    startActivity(new Intent(MainActivity.this,Heart.class));
                }
                if(select.equals("Rabies")){
                    startActivity(new Intent(MainActivity.this,Rabies.class));
                }
                if(select.equals("Malaria")){
                    startActivity(new Intent(MainActivity.this,Malaria.class));
                }
                if(select.replaceAll("\\s","").equalsIgnoreCase("ChickenPox")){
                    startActivity(new Intent(MainActivity.this,Pox.class));
                }
                if(select.replaceAll("\\s","").equalsIgnoreCase("EarInfection")){
                    startActivity(new Intent(MainActivity.this,Ear.class));
                }
                return true;
            }
        });


       ///////////
        checkcalPermission();
        checkForSmsPermission();
        //////////

        button=findViewById(R.id.ebt);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///////////////////////////////////////////
                sendSms();

                /////////////////////////////////////////
                Intent phoneIntent = new Intent(Intent.ACTION_CALL);
                phoneIntent.setData(Uri.parse("tel:9731748979"));
                startActivity(phoneIntent);

            }
        });



    }

    private void expandAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++){
            myList.expandGroup(i);
        }
    }

    //method to expand all groups
    private void displayList() {
        loadSomeData();
        myList = (ExpandableListView) findViewById(R.id.expandableList);
        listAdapter = new MyListAdapter(MainActivity.this, groupList);
        myList.setAdapter(listAdapter);
    }

    private void loadSomeData() {

        ArrayList<Item> itemList = new ArrayList<Item>();
        Item item = new Item("Anemia");
        itemList.add(item);
        Group group = new Group("Blood", itemList);
        groupList.add(group);

        itemList = new ArrayList<Item>();
        item = new Item("Flood");
        itemList.add(item);
        item = new Item("Electric Shock");
        itemList.add(item);
        group = new Group("Environmental", itemList);
        groupList.add(group);

        itemList = new ArrayList<Item>();
        item = new Item("Cardiac arrest");
        itemList.add(item);
        item = new Item("Heart block");
        itemList.add(item);
        group = new Group("Heart", itemList);
        groupList.add(group);

        itemList = new ArrayList<Item>();
        item = new Item("Rabies");
        itemList.add(item);
        item = new Item("Malaria");
        itemList.add(item);
        item = new Item("Chicken Pox");
        itemList.add(item);
        item = new Item("Ear Infection");
        itemList.add(item);
        group = new Group("Infectious Disease", itemList);
        groupList.add(group);

        itemList = new ArrayList<Item>();
        item = new Item("Nose bleed");
        itemList.add(item);
        item = new Item("Bite");
        itemList.add(item);
        item = new Item("Bone fracture");
        itemList.add(item);
        item = new Item("Burns");
        itemList.add(item);
        item = new Item("Head injury");
        itemList.add(item);
        item = new Item("Wound");
        itemList.add(item);
        group = new Group("Injury", itemList);
        groupList.add(group);

        itemList = new ArrayList<Item>();
        item = new Item("Drowning");
        itemList.add(item);
        item = new Item("Asthma");
        itemList.add(item);
        item = new Item("Pneumonia");
        itemList.add(item);
        item = new Item("Respiratory failure");
        itemList.add(item);
        group = new Group("Lungs", itemList);
        groupList.add(group);

        itemList = new ArrayList<Item>();
        item = new Item("Poisoning");
        itemList.add(item);
        item = new Item("Food Poisoning");
        itemList.add(item);
        group = new Group("Toxicological", itemList);
        groupList.add(group);

        itemList = new ArrayList<Item>();
        item = new Item("Skin disease");
        itemList.add(item);
        item = new Item("Spinal cord Injury");
        itemList.add(item);
        group = new Group("Other", itemList);
        groupList.add(group);

    }

    @Override
    public boolean onClose() {
        listAdapter.filterData("");
        expandAll();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        listAdapter.filterData(query);
        expandAll();
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        listAdapter.filterData(query);
        expandAll();
        return false;
    }




    public void checkcalPermission() {

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE) !=
                PackageManager.PERMISSION_GRANTED) {
            // Log.d(TAG, getString(R.string.permission_not_granted));
            // Permission not yet granted. Use requestPermissions().
            // MY_PERMISSIONS_REQUEST_SEND_SMS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    MY_PERMISSIONS_REQUEST_call);
        } else {
            // Permission already granted. Enable the SMS button.
            return;
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void sendSms() {
        ////////////////////location sending
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {


                mloc="vs "+location.getLatitude()+"   "+location.getLongitude();
                Toast.makeText(MainActivity.this, "loc updated", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                Toast.makeText(MainActivity.this, "e1", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onProviderEnabled(String provider) {
                Toast.makeText(MainActivity.this, "e2", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onProviderDisabled(String provider) {
                Toast.makeText(MainActivity.this, "e3", Toast.LENGTH_SHORT).show();

            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.INTERNET
            },10);
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        else{


            //locationManager.requestLocationUpdates("gps", 2000, 0, locationListener);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locationListener);
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            mloc="vs "+location.getLatitude()+"   "+location.getLongitude();
            //Toast.makeText(this, "loc updated", Toast.LENGTH_SHORT).show();

        }

        ///////////////////
        SmsManager smsManager = SmsManager.getDefault();

        Toast.makeText(this, "Your Cordintaes\n"+mloc+"\nreach me", Toast.LENGTH_SHORT).show();

        smsManager.sendTextMessage("9731748979", null, "i'm in emergency\n"+mloc+"\nreach me", null, null);
        Toast.makeText(this, "msg sent", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onRequestPermissionsResult(int requestcode,String[] permissions,int[]grantresults) {
        switch (requestcode){
            case 10:
                if(grantresults.length>0 && grantresults[0]==PackageManager.PERMISSION_GRANTED)
                    return;
        }
    }

    public void checkForSmsPermission() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS) !=
                PackageManager.PERMISSION_GRANTED) {
            // Log.d(TAG, getString(R.string.permission_not_granted));
            // Permission not yet granted. Use requestPermissions().
            // MY_PERMISSIONS_REQUEST_SEND_SMS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    MY_PERMISSIONS_REQUEST_SEND_SMS);
        } else {
            // Permission already granted. Enable the SMS button.
            return;
        }
    }
    ////////////
}
