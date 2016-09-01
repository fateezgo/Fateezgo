package tw.com.fateezgo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MasterEvaluationActivity extends AppCompatActivity {
    ArrayList<String> strList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_evaluation);

        DbTask lt = new DbTask();
        lt.execute("http://140.137.218.70:8080/fateezgo-ee/maseva");
    }

    void doViews() {
        MyAdapter adapter = new MyAdapter(this);
        ListView lv = (ListView) findViewById(R.id.base_list_view);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // do when item i is clicked
            }
        });
    }

    class MyAdapter extends BaseAdapter {
        LayoutInflater inflater;

        public MyAdapter(MasterEvaluationActivity m) {
            // TODO Auto-generated constructor stub
            inflater = LayoutInflater.from(m);
        }

        @Override
        public int getCount() {
            return strList.size();
        }

        @Override
        public Object getItem(int i) {
            return strList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = inflater.inflate(R.layout.layout_master_evaluation, null);
            TextView txtName = (TextView) view.findViewById(R.id.tv_name);
            TextView txtText = (TextView) view.findViewById(R.id.tv_text);

            String[] fields = strList.get(i).split(",");
            txtName.setText(fields[0]);
            txtText.setText(fields[1]);

            return view;
        }
    }


    class DbTask extends AsyncTask<String, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                InputStream is = url.openStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(is));
                String strLine;
                while ((strLine = in.readLine()) != null) {
                    strList.add(strLine);
                }
                in.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return strList;
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            super.onPostExecute(strings);
            System.out.println("count:" + strings.size());
            for (int i = 0; i < strings.size(); i++) {
                System.out.println(strings.get(i));
            }

            doViews();
        }
    }
}
