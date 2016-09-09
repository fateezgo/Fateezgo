package tw.com.fateezgo;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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

abstract class BasicActivity extends AppCompatActivity {
    public ArrayList<String> strList = new ArrayList<String>();
    public Member member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_basic);

        //DbTask lt = new DbTask();
        //lt.execute("mem");

        member = Member.getInstance();
        member.getMemberData(this);
        Log.d("BasicActivity", "isLogin: " + member.isLogin());
    }

    abstract void doViews();/* {
        MyAdapter adapter = new MyAdapter(this);
        ListView lv = (ListView) findViewById(R.id.base_list_view);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // do when item i is clicked
            }
        });
    }*/

    class MyAdapter extends BaseAdapter {
        LayoutInflater inflater;

        public MyAdapter(BasicActivity m) {
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
            view = inflater.inflate(R.layout.item_message, null);
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
                URL url = new URL("http://140.137.218.77:8080/fateezgo-ee/" + params[0]);
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
            Log.d("BasicActivity", "count:" + strings.size());
            for (int i = 0; i < strings.size(); i++) {
                Log.d("BasicActivity", strings.get(i));
            }

            doViews();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_basic, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_find_master:
                Log.d("BasicActivity", "find master");
                intent = new Intent(this, FindMasterActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.action_free_area:
                Log.d("BasicActivity", "free area");
                intent = new Intent(this, FreeMasterActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.action_fate_every_day:
                Log.d("BasicActivity", "fate everyday");
                intent = new Intent(this, FateEveryDayActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.action_member:
                Log.d("BasicActivity", "member");
                intent = new Intent(this, MemberActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        return false;
    }
}
