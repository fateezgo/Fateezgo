package tw.com.fateezgo;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MasterListActivity extends AppCompatActivity {

    public static final int FREE =100;
    public static final int SEARCH = 200;
    public static final int PROF = 300;
    private Button masterevbtn;
    private Button mastDatatime;
    private ListView lvfm;
    private String qtype = "QTYPE";
    private String requestType;
    int[] resIds = {R.drawable.femalemast1, R.drawable.malemast1,
            R.drawable.malemast2, R.drawable.femalemast2};
    ArrayList<String> strList = new ArrayList<String>();
    LoginTask lt = new LoginTask();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_list);
        masterevbtn = (Button) findViewById(R.id.btnEvamast);
        mastDatatime = (Button) findViewById(R.id.mastDatatime);

        lvfm = (ListView) findViewById(R.id.listViewMastList);

        Intent intent = getIntent();
        int type = intent.getIntExtra(qtype,FREE);
        String profType = intent.getStringExtra("PROF");
//        System.out.println("專長="+profType);
        switch (type){
            case 100:
                requestType="free";
                lt.execute("getmaster?qtype="+requestType);
            break;

            case 200:
            break;

            case 300:
                requestType="prof";
                lt.execute("getmaster?qtype="+requestType+"&profType="+profType);
            break;
        }

    }

    void doViews() {
        MyAdapter adapter = new MyAdapter(this);
        ListView lv = (ListView) findViewById(R.id.listViewMastList);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),MasterDetailsActivity.class);
                String s = lvfm.getItemAtPosition(i).toString();
                String[] sa = s.split(",");
//                System.out.println("name:"+sa[0]);
                intent.putExtra("Mast_NAME",sa[0]);
                startActivity(intent);
            }
        });
    }

    class MyAdapter extends BaseAdapter {
        LayoutInflater inflater;

        public MyAdapter(MasterListActivity m) {
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
            view = inflater.inflate(R.layout.layout_master_list, null);
            ImageView imgLogo = (ImageView) view.findViewById(R.id.imgMast);
            TextView txtuid = (TextView) view.findViewById(R.id.txtuid);
            TextView txtName = (TextView) view.findViewById(R.id.txtName);
            TextView txtText = (TextView) view.findViewById(R.id.txtMastProf);

            String[] fields = strList.get(i).split(",");
            System.out.println(fields[0]);
            imgLogo.setImageResource(resIds[i]);
            txtuid.setText(fields[0]+"號老師");
            txtName.setText(fields[1]);
            txtText.setText("專長："+fields[2]);

            return view;
        }
    }


    class LoginTask extends AsyncTask<String, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
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
            for (int i = 0; i < strings.size(); i++) {


            }
            doViews();
        }
    }


}



