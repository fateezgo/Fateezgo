package tw.com.fateezgo;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class PlaceActivity extends BasicActivity {

    ArrayList<String> strList = new ArrayList<String>();
    String puid,place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        PlaceTask pt = new PlaceTask();
        pt.execute("place");
//        final Intent placeintent = new Intent(this, ModifyMasterActivity.class);
//        startActivity(placeintent);
    }

    @Override
    void doViews() {
        PlaceAdapter adapter = new PlaceAdapter(this);
        ListView plv = (ListView) findViewById(R.id.plv);
        plv.setAdapter(adapter);
        Button btn_add = (Button) findViewById(R.id.btn_add);
        btn_add.setOnClickListener(btnAddListener);

    }
    class PlaceAdapter extends BaseAdapter{
        LayoutInflater myInflater;
        public PlaceAdapter(PlaceActivity c){
            myInflater = LayoutInflater.from(c);
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
            view = myInflater.inflate(R.layout.layout_place, null);
            TextView txt_place = (TextView) view.findViewById(R.id.txt_place);
            String[] fields = strList.get(i).split(",");
            puid = fields[0];
            txt_place.setText(fields[1]);
            return view;
        }
    }
    class PlaceTask extends AsyncTask<String, Void, ArrayList<String>>{
        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                InputStream is = url.openStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(is));
                String strLine;
                while ((strLine = in.readLine()) != null){
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
            for (int i = 0; i < strings.size(); i++){
                System.out.println(strings.get(i));
            }
            doViews();
        }
    }
    private Button.OnClickListener btnAddListener = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            EditText edtxt = (EditText) findViewById(R.id.edTxt);
            String input = (edtxt.getText().toString());//+",";
            System.out.println(input);
            place = puid+","+input+",";
            strList.add(place);//puid+","+input+",");

            doViews();
            edtxt.setText("");

//            DbTask spt = new DbTask();
//            spt.execute("http://140.137.218.66:8080/fateezgo-ee/setplace?uid="+puid+"&place="+input);
        }
    };
}
