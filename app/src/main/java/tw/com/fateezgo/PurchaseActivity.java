package tw.com.fateezgo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class PurchaseActivity extends BasicActivity {
    String[] strArray;
    private int masterUid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);
        ListView listView = (ListView) this.findViewById(R.id.listview_class);
        Intent intent=getIntent();
        strArray=intent.getStringArrayExtra("checkbox_info");
        for (int i = 0; i < strArray.length; i++) {
            Log.d("Purchase", strArray[i]);
        }
        masterUid = intent.getIntExtra("master_uid", 15);
        Log.d("Purchase", "master uid:" + masterUid);

        PurchaseAdapter adapter = new PurchaseAdapter(this);
        ListView lv = (ListView) findViewById(R.id.list_purchase);
        lv.setAdapter(adapter);
    }

    @Override
    void doViews() {

    }

    void purchase(View v) {
        String prof = "";
        for (int i = 0; i < strArray.length; i++) {
            String[] strings = strArray[i].split(",");
            prof += strings[0];
            prof += ",";
        }
        Log.d("Purchase", "prof = " + prof);
        DbTask dbTask = new DbTask();
        dbTask.execute("http://140.137.218.52:8080/fateezgo-ee/SetOrder?type=insert" +
                "&memuid=" + member.uid() +
                "&masteruid=" + masterUid +
                "&professionalid=" + prof);
    }

    class PurchaseAdapter extends BaseAdapter
    {
        LayoutInflater inflater;

        public PurchaseAdapter(BasicActivity m)
        {
            inflater = LayoutInflater.from(m);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = inflater.inflate(R.layout.activity_purchase_listview_info, null);
            TextView tv = (TextView) view.findViewById(R.id.buyinfo_id);
            tv.setText(strArray[position]);
            String[] strArr = strArray[position].split(",");
            return view;
        }

        @Override
        public int getCount() {
            return strArray.length;
        }

        @Override
        public Object getItem(int i) {
            return strArray[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

    }

}
