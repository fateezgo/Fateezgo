package tw.com.fateezgo;

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

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class NewListActivity extends AppCompatActivity {

    String userid = "Jacky";
    private ListView lstvMastNewlist;
    //        ArrayList
    ArrayList<String> strlsit = new ArrayList<>();

    public void login(View view){
        String s= "getmaster?userid="+ userid ;
        new LoginTask().execute(s);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_list);
//        Findview
        lstvMastNewlist = (ListView) findViewById(R.id.listViewMasterNewList);
        MyAdapter myAdapter =new MyAdapter(this);
        lstvMastNewlist.setAdapter(myAdapter);

    }

    private class LoginTask extends AsyncTask <String, Void, Integer>{


        @Override
        protected Integer doInBackground(String... strings) {
            int data = 0;
            try {
                URL url = new URL(strings[0]);
                InputStream is =url.openStream();
                data=is.read();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;
        }
    }

    private class MyAdapter extends BaseAdapter{
        LayoutInflater inflater;

        public MyAdapter(NewListActivity n) {
            inflater =LayoutInflater.from(n);
        }

        @Override
        public int getCount() {
            return strlsit.size();
        }

        @Override
        public Object getItem(int i) {
            return strlsit.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = inflater.inflate(R.layout.layout_newlist_listview,null);
                    TextView textPrefess= (TextView) view.findViewById(R.id.item1TextviewNewlist);
                    TextView textTime= (TextView) view.findViewById(R.id.item2TextviewNewlist);

            return view;
        }
    }
}
