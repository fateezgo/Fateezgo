package tw.com.fateezgo;

import android.content.Intent;
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

public class OrderListActivity extends BasicActivity {

    ArrayList<String> strList = new ArrayList<String>();
    boolean orderlist_bl = false;
    String[] orderlist_item;
    int nameuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        nameuid = member.uid();
        OrderListTask olt = new OrderListTask();
        olt.execute("http://140.137.218.66:8080/fateezgo-ee/order?type=list&id="+nameuid);
    }

    @Override
    void doViews() {
        OrderListAdapter adapter = new OrderListAdapter(this);
        ListView lv_ord = (ListView) findViewById(R.id.lv_ord);
        lv_ord.setAdapter(adapter);
        lv_ord.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                orderlist_item = strList.get(i).split(",");
                if(orderlist_item[7].equals("null") || orderlist_item[7].equals("") || orderlist_item[8].equals("null") || orderlist_item[8].equals("")){
                    orderlist_bl = false;
                } else {
                    orderlist_bl = true;
                }
                System.out.println(orderlist_bl);
                if(orderlist_bl){
                    Intent intent = new Intent(getApplicationContext(), DetailedOrderMapsActivity.class);
                    intent.putExtra("ORDERID_EXTRA", orderlist_item);
                    startActivity(intent);
                }
                else {
                    //id, memberuid, mem-name, masteruid, master-name, professionalid, pdate, rdate, rplace, estate, sn
                    Intent intent = new Intent(getApplicationContext(), ReservationActivity.class);
                    intent.putExtra("masteruid", orderlist_item[3]);
                    intent.putExtra("order_id", orderlist_item[0]);
                    startActivity(intent);
                }
            }
        });
    }
    class OrderListAdapter extends BaseAdapter{

        LayoutInflater myInflater;
        public OrderListAdapter(OrderListActivity c){
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
            view = myInflater.inflate(R.layout.layout_order_list, null);
            TextView txt_id = (TextView) view.findViewById(R.id.txt_ord_id);
            TextView txt_date = (TextView) view.findViewById(R.id.txt_ord_date);
            TextView txt_msg = (TextView) view.findViewById(R.id.txt_ord_msg);
            TextView txt_ord = (TextView) view.findViewById(R.id.txt_ord_ord);
            TextView txt_crd = (TextView) view.findViewById(R.id.txt_ord_crd);
            orderlist_item = strList.get(i).split(",");
//            String nameuid = String.valueOf(member.uid());
//            System.out.println(nameuid);
//            if(orderlist_item[1].equals(nameuid)||orderlist_item[8].equals(nameuid)){
                txt_id.setText(orderlist_item[0]);
                txt_date.setText(orderlist_item[6]);
                txt_msg.setText(orderlist_item[4]+"/"+orderlist_item[5]);
                if(orderlist_item[7].equals("null") || orderlist_item[7].equals("") || orderlist_item[8].equals("null") || orderlist_item[8].equals("")){
                    txt_ord.setText("否");
//                    orderlist_bl = false;
                } else{
                    txt_ord.setText("是");
//                    orderlist_bl = true;
                }
                txt_crd.setText(orderlist_item[9]);
//            }
            return view;
        }
    }
    class OrderListTask extends AsyncTask<String, Void, ArrayList<String>>{
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
}
