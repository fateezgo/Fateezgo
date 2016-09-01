package tw.com.fateezgo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class PurchaseActivity extends AppCompatActivity {
    ArrayList<String> strlist=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);
        ListView listView = (ListView) this.findViewById(R.id.listview_class);
        Intent intent=getIntent();
        String[] strArray=intent.getStringArrayExtra("checkbox_info");

        for (int i = 0; i < strArray.length; i++) {
            System.out.println(strArray[i]);
        }

    }
}
