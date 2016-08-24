package tw.com.fateezgo;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class DetailedOrderActivity extends Activity {

    ListView LV01;
    String[] dtOrder_id = new String[]{"1","2","3"};//dtOrder_date,dtOrder_msg,dtOrder_ord,dtOrder_crd;
    String[] dtOrder_date = new String[]{"A","B","C"};
    String[] dtOrder_msg = new String[]{"預約1","預約2","預約3"};
    String[] dtOrder_ord = new String[]{"已預約","已預約","未預約"};
    String[] dtOrder_crd = new String[]{"已完成","未完成","未完成"};
    DtOrderAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_order);

        LV01 = (ListView) findViewById(R.id.lv01);
        adapter = new DtOrderAdapter(this);
        LV01.setAdapter(adapter);

    }
    class DtOrderAdapter extends BaseAdapter{

        LayoutInflater myInflater;

        public DtOrderAdapter(DetailedOrderActivity c){
            myInflater = LayoutInflater.from(c);
        }

        @Override
        public int getCount() {
            return dtOrder_id.length;
        }

        @Override
        public Object getItem(int position) {
            return dtOrder_id[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            convertView = myInflater.inflate(R.layout.layoutdetailedorder, null);
            TextView txt_id = ((TextView) convertView.findViewById(R.id.txt_id));
            TextView txt_date = ((TextView) convertView.findViewById(R.id.txt_date));
            TextView txt_msg = ((TextView) convertView.findViewById(R.id.txt_msg));
            TextView txt_ord = ((TextView) convertView.findViewById(R.id.txt_ord));
            TextView txt_crd = ((TextView) convertView.findViewById(R.id.txt_crd));

            txt_id.setText(dtOrder_id[position]);
            txt_date.setText(dtOrder_date[position]);
            txt_msg.setText(dtOrder_msg[position]);
            txt_ord.setText(dtOrder_ord[position]);
            txt_crd.setText(dtOrder_crd[position]);

            return convertView;
        }
    }
}
