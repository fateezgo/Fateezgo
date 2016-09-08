package tw.com.fateezgo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MasterDetailsActivity extends BasicActivity{

    private ImageView imageMast;
    private TextView tvMastname;
    private TextView tvMastprof;
    private TextView tvMastdet;
    private final int eva=26;

    DbTask db = new DbTask();
    private Button masteva;
    private Button fateresv;
    private TextView tvmastuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_details);

        imageMast = (ImageView) findViewById(R.id.picMaster);
        tvmastuid = (TextView) findViewById(R.id.textViewMasteruid);
        tvMastname = (TextView) findViewById(R.id.textViewMastname);
        tvMastprof = (TextView) findViewById(R.id.textViewMastprof);
        tvMastdet = (TextView) findViewById(R.id.textViewMastDet);
        masteva = (Button) findViewById(R.id.masteva);
        fateresv = (Button) findViewById(R.id.fateresv);

        Intent intent = getIntent();
        String mastuid = intent.getStringExtra("Mast_NAME");
        System.out.println("mastuid:"+mastuid);

        db.execute("http://140.137.218.77:8080/fateezgo-ee/getmastdet?mastname="+mastuid);

        fateresv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(),FateSelectionActivity.class);
                String[] sa = strList.get(0).split(",");
                System.out.print(sa[0]);
                intent1.putExtra("uid",Integer.valueOf(sa[0]));
                startActivity(intent1);
            }
        });
        masteva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getApplicationContext(),MasterEvaluationActivity.class);
                startActivity(intent2);
            }
        });
    }



    @Override
    void doViews() {
        String[] fields = strList.get(0).split(",");
        tvmastuid.setText(fields[0]+"號老師");
        tvMastname.setText("暱稱:"+fields[1]);
        tvMastprof.setText("專長:"+fields[2]);
        tvMastdet.setText(fields[3]);


    }




}
