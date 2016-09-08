package tw.com.fateezgo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;

public class DetailedOrderActivity extends BasicActivity {
    private static final int FUNC_EVALUATE = 100;
    private TextView tvMaster;
    private TextView tvDate;
    private TextView tvPlace;
    private Button bFunc;
    private int orderId;
    private TextView tvSerialNoTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_order);

        findViews();

        Intent intent = getIntent();
        orderId = intent.getIntExtra("order_id", 3);

        //Date date = new Date(intent.getLongExtra("r-date", 0));
        //DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(this);
        //tv.setText(dateFormat.format(date).toString());

        DbTask dbTask = new DbTask();
        dbTask.execute("http://140.137.218.52:8080/fateezgo-ee/order?type=one&id="+ orderId);
    }

    void doViews() {
        String string = strList.get(0);
        if (string != null) {
            String[] strArray = string.split(",");
            //id, memberuid, name, masteruid, name, professionalid, pdate, rdate, rplace, estate, sn
            tvMaster.setText(strArray[0]);
            tvDate.setText(strArray[1]);
            tvPlace.setText(strArray[2]);
            String serialNo = strArray[4];
            String eState = strArray[3];
            if (eState.equals("N")) {
                bFunc.setVisibility(View.VISIBLE);
                bFunc.setText("傳送憑證");
                bFunc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), DetailedOrderActivity.class);
                        intent.putExtra("order_id", orderId);
                        startActivity(intent);
                        finish();
                    }
                });
            }
            else if (eState.equals("E")) {
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
            }
            else {  // "F"
                bFunc.setVisibility(View.VISIBLE);
                bFunc.setText("確定");
                bFunc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), OrderListActivity.class);
                        startActivity(intent);
                    }
                });

            }
        }
    }

    private void findViews() {
        tvMaster = (TextView) findViewById(R.id.tv_master_name);
        tvDate = (TextView) findViewById(R.id.tv_r_date);
        tvPlace = (TextView) findViewById(R.id.tv_r_place);
        bFunc = (Button) findViewById(R.id.b_evaluate);
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
}
