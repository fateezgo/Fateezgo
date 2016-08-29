package tw.com.fateezgo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class MemberActivity extends Activity {
    // 1.宣告物件
    //
    private Button btnmember;
    private Button btnclass;
    private Button btnschedule;
    private Button btnestimation;
    private TextView T01;
    private Spinner Sp01;
    private TextView T02;
    String[] Balls = {"本頁", "首頁","找老師","AITEM"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 2.連結元件
        btnmember=(Button)findViewById(R.id.btnmember);
        btnclass =(Button)findViewById(R.id.btnclass);
        btnschedule =(Button)findViewById(R.id.btnschedule);
        btnestimation =(Button)findViewById(R.id.btnestimation);
        Sp01 = (Spinner) findViewById(R.id.sp01);
        T01 = (TextView) findViewById(R.id.t01);
        T02 = (TextView) findViewById(R.id.t02);
        ArrayAdapter<String> adapterBalls=new ArrayAdapter<String>
                (this,android.R.layout.simple_spinner_item,Balls);

        adapterBalls.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Sp01.setAdapter(adapterBalls);

        // 3.建立事件: 跳頁
        btnmember.setOnClickListener(new Button.OnClickListener(){

            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent=new Intent();
                intent.setClass(MemberActivity.this,ModifyMemberActivity.class);
                startActivity(intent);
            }});
        Toast.makeText(getApplicationContext(), "onCreate(1)", Toast.LENGTH_SHORT).show();

        // 3.建立事件: 跳頁
        btnclass.setOnClickListener(new Button.OnClickListener(){

            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent=new Intent();
                intent.setClass(MemberActivity.this,OrderListActivity.class);
                startActivity(intent);
            }});
        Toast.makeText(getApplicationContext(), "onCreate(1)", Toast.LENGTH_SHORT).show();



        // 3.建立事件: 跳頁
        btnschedule.setOnClickListener(new Button.OnClickListener(){

            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent=new Intent();
                intent.setClass(MemberActivity.this,ContactActivity.class);
                startActivity(intent);
            }});

        Toast.makeText(getApplicationContext(), "onCreate(1)", Toast.LENGTH_SHORT).show();

        // 3.建立事件: 跳頁
        btnestimation.setOnClickListener(new Button.OnClickListener(){

            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent=new Intent();
                intent.setClass(MemberActivity.this,ContactActivity.class);
                startActivity(intent);
            }});

        Toast.makeText(getApplicationContext(), "onCreate(1)", Toast.LENGTH_SHORT).show();





        // 3.建立事件
        Sp01.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
//                T02.setText(" 最喜歡的球類運動是："+Sp01.getSelectedItem().toString());
                String choice = Sp01.getSelectedItem().toString();
                T02.setText(" 選擇的頁面是：" + choice);


                if (choice.equals("AITEM")) {
                    Intent intent=new Intent();
                    intent.setClass(MemberActivity.this,ContactActivity.class);
                    startActivity(intent);
                } else if (choice.equals("首頁")) {
                    Intent intent=new Intent();
                    intent.setClass(MemberActivity.this,ContactActivity.class);
                    startActivity(intent);

                } else if (choice.equals("找老師")) {
                    Intent intent=new Intent();
                    intent.setClass(MemberActivity.this,ContactActivity.class);
                    startActivity(intent);
                }

            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });












    } //on create end

    @Override
    protected void onDestroy() {
        // TODO 自動產生的方法 Stub
        super.onDestroy();
        Toast.makeText(getApplicationContext(), "onDestroy(1)", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        // TODO 自動產生的方法 Stub
        super.onPause();
        Toast.makeText(getApplicationContext(), "onPause(1)", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        // TODO 自動產生的方法 Stub
        super.onRestart();
        Toast.makeText(getApplicationContext(), "onRestart(1)", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        // TODO 自動產生的方法 Stub
        super.onResume();
        Toast.makeText(getApplicationContext(), "onResume(1)", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        // TODO 自動產生的方法 Stub
        super.onStart();
        Toast.makeText(getApplicationContext(), "onStart(1)", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        // TODO 自動產生的方法 Stub
        super.onStop();
        Toast.makeText(getApplicationContext(), "onStop(1)", Toast.LENGTH_SHORT).show();
    }
}



