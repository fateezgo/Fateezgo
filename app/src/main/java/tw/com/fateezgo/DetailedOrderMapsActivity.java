package tw.com.fateezgo;

import android.*;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.identity.intents.Address;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class DetailedOrderMapsActivity extends BasicActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener  {
    private static final int FUNC_EVALUATE = 100;
    private static final int STATE_GET_DATA = 1;
    private static final int STATE_SET_ORDER_ESTATE = 2;
    private static final int B_FUNC_VERIFY_SN = 1;
    private static final int B_FUNC_EVALUATE = 2;
    private static final int B_FUNC_OK = 3;
    private TextView tvMaster;
    private TextView tvDate;
    private TextView tvPlace;
    private Button bFunc;
    private int orderId;
    private TextView tvSerialNoTitle;

    private static final int REQUEST_LOC = 100;
    private GoogleMap mMap;
    boolean isMapReady = false;
    boolean isDataReady = false;
    private String serialNo;
    private TextView tvSN;
    private EditText edSN;
    private Button bOnline;
    private boolean isOnline = true;
    private boolean isMaster = false;

    private int state = STATE_GET_DATA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_order_maps);

        findViews();

        Intent intent = getIntent();
        orderId = intent.getIntExtra("order_id", 11);

        //Date date = new Date(intent.getLongExtra("r-date", 0));
        //DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(this);
        //tv.setText(dateFormat.format(date).toString());

        DbTask dbTask = new DbTask();
        dbTask.execute("http://140.137.218.52:8080/fateezgo-ee/order?id="+ orderId);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    void doViews() {
        switch (state) {
            case STATE_GET_DATA:
                String string = strList.get(0);
                if (string != null) {
                    String[] strArray = string.split(",");
                    tvMaster.setText(strArray[0]);
                    tvDate.setText(strArray[1]);
                    tvPlace.setText(strArray[2]);
                    if (isOnline) {
                        tvPlace.setText("Online");
                        bOnline.setVisibility(View.VISIBLE);
                        findViewById(R.id.map).setVisibility(View.INVISIBLE);
                    }
                    serialNo = strArray[4];
                    tvSN.setText(serialNo);
                    edSN.setVisibility(View.INVISIBLE);
                    String eState = strArray[3];
                    Log.d("DETAILED_ORDER", "estate: " + eState);
                    if (eState.equals("N")) {
                        if (isMaster == true) {
                            setFunc(B_FUNC_VERIFY_SN);
                        }
                    } else if (eState.equals("E")) {
                        if (isMaster == false) {
                            setFunc(B_FUNC_EVALUATE);
                        }
                        else {
                            setFunc(B_FUNC_OK);
                        }
                    } else {  // "F"
                        setFunc(B_FUNC_OK);
                    }
                }
                isDataReady = true;
                try {
                    setPlace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case STATE_SET_ORDER_ESTATE:
                Intent intent = new Intent(getApplicationContext(), OrderListActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void setFunc(int func) {
        switch (func) {
            case B_FUNC_VERIFY_SN:
                tvSN.setText("");
                edSN.setVisibility(View.VISIBLE);
                bFunc.setVisibility(View.VISIBLE);
                bFunc.setText("驗證憑證");
                bFunc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (edSN.getText().toString().equals(serialNo)) {
                            tvSN.setText("驗證成功");
                            state = STATE_SET_ORDER_ESTATE;
                            DbTask db = new DbTask();
                            db.execute("http://140.137.218.52:8080/fateezgo-ee/SetOrder?id=" + orderId + "&type=estate&value=E");
                        } else {
                            tvSN.setText("驗證失敗");
                        }
                    }
                });
                break;
            case B_FUNC_EVALUATE:
                bFunc.setVisibility(View.VISIBLE);
                bFunc.setText("給評價");
                bFunc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), EvaluationActivity.class);
                        intent.putExtra("order_id", orderId);
                        startActivityForResult(intent, FUNC_EVALUATE);
                    }
                });
                break;
            case B_FUNC_OK:
                bFunc.setVisibility(View.VISIBLE);
                bFunc.setText("確定");
                bFunc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), OrderListActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                break;
        }
    }

    private void findViews() {
        tvMaster = (TextView) findViewById(R.id.tv_master_name);
        tvDate = (TextView) findViewById(R.id.tv_r_date);
        tvPlace = (TextView) findViewById(R.id.tv_r_place);
        bFunc = (Button) findViewById(R.id.b_evaluate);
        tvSN = (TextView) findViewById(R.id.tv_sn);
        edSN = (EditText) findViewById(R.id.ed_sn);
        bOnline = (Button) findViewById(R.id.b_online);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FUNC_EVALUATE) {
            if (requestCode == RESULT_OK) {
                finish();
            }
        }
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
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(
                    this,
                    new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_LOC);
        }
        else {
            setupMyLocation();
        }

        isMapReady = true;
        try {
            setPlace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setPlace() throws IOException {
        if (isMapReady && isDataReady) {
            Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
            List<android.location.Address> addressLocation = geoCoder.getFromLocationName(tvPlace.getText().toString(), 1);
            //List<android.location.Address> addressLocation = geoCoder.getFromLocationName("大安森林公園", 1);
            double latitude = addressLocation.get(0).getLatitude();
            double longitude = addressLocation.get(0).getLongitude();

            // Add a marker in Sydney and move the camera
            LatLng ll = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(ll).title("Fateezgo Address"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ll, 16));
        }
    }

    private void setupMyLocation() {
        //noinspection MissingPermission
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
    }

    @Override
    public boolean onMyLocationButtonClick() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        @SuppressWarnings("MissingPermission")
        Location loc = lm.getLastKnownLocation("gps");
        if (loc != null) {
            LatLng ll = new LatLng(loc.getAltitude(), loc.getLongitude());
            mMap.addMarker(new MarkerOptions().position(ll).title("Fateezgo Address"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ll, 16));
        }
        return false;
    }

    void consultOnline(View v) {
        Intent intent = new Intent(this, ConsultOnlineActivity.class);
        intent.putExtra("order_id", orderId);
        intent.putExtra("order_sn", serialNo);
        startActivity(intent);
    }
}
