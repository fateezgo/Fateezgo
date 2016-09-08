package tw.com.fateezgo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FindMasterActivity extends AppCompatActivity {

    Button b01,b02,b03,b04,b05,b06,b07,b08,b09;
    String astromast="astromast";
    String tarot="tarot";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_master);

        b01=(Button) findViewById(R.id.btnnamingmast);
        b02=(Button) findViewById(R.id.btnchoidmast);
        b03=(Button) findViewById(R.id.btncharmast);
        b04=(Button) findViewById(R.id.btnastromast);
        b05=(Button) findViewById(R.id.btntaromast);
        b06=(Button) findViewById(R.id.btnfesumast);
        b07=(Button) findViewById(R.id.btnziweimast);
        b08=(Button) findViewById(R.id.btntellersmast);
        b09=(Button) findViewById(R.id.btndivinmast);

        //3.建立事件

        b01.setOnClickListener(listener);
        b02.setOnClickListener(listener);
        b03.setOnClickListener(listener);
        b04.setOnClickListener(listener);
        b05.setOnClickListener(listener);
        b06.setOnClickListener(listener);
        b07.setOnClickListener(listener);
        b08.setOnClickListener(listener);
        b09.setOnClickListener(listener);

    }
    Button.OnClickListener listener =new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(FindMasterActivity.this, MasterListActivity.class);
            switch(view.getId()){
                case R.id.btnastromast:
                    intent.putExtra("QTYPE", MasterListActivity.PROF);
                    intent.putExtra("PROF",astromast);
                    startActivity(intent);
                break;
                case R.id.btntaromast:
                    intent.putExtra("QTYPE", MasterListActivity.PROF);
                    intent.putExtra("PROF",tarot);
                    startActivity(intent);
                break;
            }


        }
    };
}

