package com.example.vinay.lifesaver;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class home extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;
    private static final int MY_PERMISSIONS_REQUEST_call = 1;
    ///////////////////
    //location
    private LocationManager locationManager;
    private LocationListener locationListener;

    //////////////

    public home() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ////checking for permissions
        checkcalPermission();
        checkForSmsPermission();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.emergency: {
                Toast.makeText(getApplicationContext(), "you clicked" + item.getTitle(), Toast.LENGTH_LONG).show();
                callemergency();
            }
            break;

        }
        return super.onOptionsItemSelected(item);

    }

    private void callemergency() {
        ///////////////////////////////////////////
        sendSms();

        /////////////////////////////////////////
        Intent phoneIntent = new Intent(Intent.ACTION_CALL);
        phoneIntent.setData(Uri.parse("tel:9731748979"));
        startActivity(phoneIntent);
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

    private void sendSms() {

        ////////////////////location sending
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            requestPermissions(new String[]);
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        else{


            locationManager.requestLocationUpdates("gps", 500, 50, locationListener);

        }
        //////////

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage("9731748979", null, "i'm in emergency", null, null);
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
}

