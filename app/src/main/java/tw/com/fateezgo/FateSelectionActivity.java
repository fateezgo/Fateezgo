package tw.com.fateezgo;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class FateSelectionActivity extends BasicActivity{
    private Button buy01;
    private boolean[] checkedInfo;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fate_selection);

        id = getIntent().getIntExtra("uid", 26);
        System.out.print("Fateselection"+id);
        DbTask lt = new DbTask();
        lt.execute("GetMasterProf?uid="+ id);


        buy01= (Button) findViewById(R.id.buy01);
        buy01.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count=0;
                for (int i = 0; i < strList.size(); i++) {
                    if (checkedInfo[i] == true){
                        count++;
                    }
                }
                System.out.println("count: " + count);
                String[] strArray = new String[count];
                int cur = 0;
                for (int i = 0; i < strList.size(); i++) {
                    if (checkedInfo[i] == true){
                        strArray[cur++]=strList.get(i).toString();
                    }
                }

                Intent intent=new Intent(getApplicationContext(),PurchaseActivity.class);
                intent.putExtra("master_uid", id);
                intent.putExtra("checkbox_info", strArray);
                startActivity(intent);
            }
        });
    }

    class ProfAdapter extends ArrayAdapter
    {
        private static final int mResourceId = R.layout.activity_fate_selection_listview_info;
        private LayoutInflater mInflater;

        public ProfAdapter(Context context)
        {
            super(context, mResourceId);
            mInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = mInflater.inflate(mResourceId, parent, false);

            String[] strArray = strList.get(position).split(",");
            TextView textInfo;
            textInfo = (TextView)view.findViewById(R.id.info_Id);
            textInfo.setText(strArray[1]);
            TextView tvCost = (TextView) view.findViewById(R.id.tv_cost);
            tvCost.setText("金額:"+strArray[2]);
            TextView tvLeadTime = (TextView) view.findViewById(R.id.tv_leadtime);
            tvLeadTime.setText("時程:"+strArray[3]);
            CheckBox checkBox= (CheckBox) view.findViewById(R.id.info_checkbox);
            checkBox.setTag(position);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    int position = (int)compoundButton.getTag();
                    checkedInfo[position] = b;
                }
            });
            return view;
        }

        @Override
        public int getCount()
        {
            return strList.size();
        }
    }

    @Override
    void doViews() {
        ListView listView = (ListView) this.findViewById(R.id.listview_class);
        ProfAdapter adapter = new ProfAdapter(this);
        listView.setAdapter(adapter);
        checkedInfo = new boolean[strList.size()];
    }
}
