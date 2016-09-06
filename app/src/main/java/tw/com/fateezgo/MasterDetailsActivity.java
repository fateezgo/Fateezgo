package tw.com.fateezgo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MasterDetailsActivity extends BasicActivity{

    private ImageView imageMast;
    private TextView tvMastname;
    private TextView tvMastprof;
    private TextView tvMastdet;


    DbTask db = new DbTask();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_details);

        imageMast = (ImageView) findViewById(R.id.picMaster);
        tvMastname = (TextView) findViewById(R.id.textViewMastname);
        tvMastprof = (TextView) findViewById(R.id.textViewMastprof);
        tvMastdet = (TextView) findViewById(R.id.textViewMastDet);

        Intent intent = getIntent();
        String mastname = intent.getStringExtra("Mast_NAME");
        db.execute("http://140.137.218.77:8080/fateezgo-ee/getmastdet?mastname="+mastname);


    }



    @Override
    void doViews() {
        String[] fields = strList.get(0).split(",");
        tvMastname.setText("老師:"+fields[0]);
        tvMastprof.setText("專長:"+fields[1]);
        tvMastdet.setText(fields[2]);


    }




}
