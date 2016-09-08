package tw.com.fateezgo;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
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
    private RatingBar ratingBar;
    private TextView txtRatingValue;
    private ArrayList<Integer> stardata = new ArrayList<Integer>();
    private int masuid;
    int nullcount =0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_evaluation);

//        stardata = new ArrayList<String>();
        Intent intent = getIntent();
        masuid = getIntent().getIntExtra("uid", 15);
        System.out.println(masuid);

//        String webstar = "http://140.137.218.70:8080/fateezgo-ee/weav?type="+type+"&starvalue="+starvalue+"&id="+id ;

        String weburl = "http://140.137.218.70:8080/fateezgo-ee/masev?masuid="+masuid ;
        System.out.println(weburl) ;
        DbTask lt = new DbTask();
        lt.execute(weburl);
//        lt.execute("http://140.137.218.70:8080/fateezgo-ee/masev?masuid="+masuid);


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


    }//doview end

    private class MyAdapter extends BaseAdapter {
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

            String[] fields = strList.get(i).split("%#");
            System.out.println(fields[2]);
//            if ( fields[1].equals("")) {
//                        if ( fields[2].equals("null") ) {
//                return null;
//            }else {
                txtName.setText(fields[0]);
                txtText.setText(fields[1]);
                return view;
//            }


        }
    }


   private class DbTask extends AsyncTask<String, Void, ArrayList<String>> {

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
                String[] fields_clone = strList.get(i).split("%#");
                if (fields_clone[2].equals("null")) {
                    nullcount++;
                }
                else {
                    stardata.add(Integer.parseInt(fields_clone[2]));
                }
                System.out.println(strings.get(i));
                System.out.println(stardata);
            }


            doViews();
            addListenerOnRatingBar();
        }
    } //DBTask end


    public void addListenerOnRatingBar() {

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        txtRatingValue = (TextView) findViewById(R.id.txtRatingValue);


//            float average = (total * 2) / (stardata.size() );
//        System.out.println(total+"    "+ average);

        int total =0;
        for (int j=0; j<stardata.size(); j++){
           int onevalue = stardata.get(j);
           total = total+ onevalue;
           System.out.println("total value="+total);
        }

           float rating = (float) (total ) / (stardata.size() );
           System.out.println(total+"    "+ rating);



//        float rating = 2.3f;
        ratingBar.setRating(rating);

//        txtRatingValue.setText(String.valueOf(rating));

    } //addListenerRatingBar

}
