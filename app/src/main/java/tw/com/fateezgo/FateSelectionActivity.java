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

public class FateSelectionActivity extends AppCompatActivity {
    ArrayList<String>strlist=new ArrayList<String>();
    static ArrayList<CheckBox>checkBoxArrayList=new ArrayList<CheckBox>();

    private Button buy01;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fate_selection);

        checkBoxArrayList.clear();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        int id = 100;
        MyTask mytask=new MyTask();
        mytask.execute("http://140.137.218.94:8080/fateezgo/Purchase?id="+id);

        ListView listView = (ListView) this.findViewById(R.id.listview_class);
        MyAdapter adapter = new MyAdapter(this,strlist);
        listView.setAdapter(adapter);

        buy01= (Button) findViewById(R.id.buy01);
        buy01.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Log.d("here here","msg");

                Intent intent=new Intent(getApplicationContext(),PurchaseActivity.class);
                int count=0;
                for (int i = 0; i < strlist.size(); i++) {
                    CheckBox checkBox=checkBoxArrayList.get(i);
                    if (checkBox.isChecked()){
                        count++;
                    }
                }
                String[] strArray = new String[count];
                int cur = 0;
                for (int i = 0; i < strlist.size(); i++) {
                    CheckBox checkBox=checkBoxArrayList.get(i);
                    if (checkBox.isChecked()){
                        strArray[cur++]=checkBox.getText().toString();
                    }
                }

                for (int i = 0; i < strArray.length; i++) {
                    System.out.println(strArray[i]);
                }

                intent.putExtra("checkbox_info", strArray);
                startActivity(intent);
            }
        });

    }

    class MyTask extends AsyncTask<String,Void,ArrayList<String>>{


        @Override
        protected ArrayList<String> doInBackground(String... strings) {

            try {
                URL url=new URL(strings[0]);
                InputStream is=url.openStream();
                BufferedReader in=new BufferedReader(new InputStreamReader(is));
                String strline;
                while ((strline=in.readLine())!=null){
                    strlist.add(strline);
                    System.out.println(strline);
                }
                in.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return strlist;

        }
    }


    private static class MyAdapter extends ArrayAdapter
    {
        private static final int mResourceId = R.layout.activity_fate_selection_listview_info;
        private LayoutInflater mInflater;
        ArrayList<String>strlist;
        // String [] classInfo = {"命名", "擇日", "八字", "占星", "塔羅", "風水", "紫微"};

        public MyAdapter(Context context,ArrayList<String>strlist)
        {
            super(context, mResourceId);
            mInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            this.strlist = strlist;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = mInflater.inflate(mResourceId, parent, false);
            } else {
                view = convertView;
            }

            TextView textInfo;
            textInfo = (TextView)view.findViewById(R.id.info_Id);
            textInfo.setText(strlist.get(position));
            CheckBox checkBox= (CheckBox) view.findViewById(R.id.info_checkbox);
            checkBoxArrayList.add(checkBox);
            return view;
        }

        @Override
        public int getCount()
        {
            return strlist.size();
        }
    }

}
