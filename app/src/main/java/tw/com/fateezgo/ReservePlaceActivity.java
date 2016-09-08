package tw.com.fateezgo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

public class ReservePlaceActivity extends BasicActivity {
    private static final int GET_DATA = 1;
    private static final int SET_DATA = 2;
    private int state = GET_DATA;
    private int masteruid;
    private int orderuid;
    private CheckBox cbSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_place);
        Intent intent = getIntent();
        masteruid = getIntent().getIntExtra("masteruid", 14);
        orderuid = getIntent().getIntExtra("order_id", 2);
        state = GET_DATA;
        DbTask db = new DbTask();
        db.execute("GetPlace?uid=" + masteruid);
    }

    @Override
    void doViews() {
        switch (state) {
            case GET_DATA:
                MyAdapter adapter = new MyAdapter(this);
                ListView lv = (ListView) findViewById(R.id.list_place);
                lv.setAdapter(adapter);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        // do when item i is clicked
                    }
                });
                break;
            case SET_DATA:
                Intent intent = new Intent(this, OrderListActivity.class);
                startActivity(intent);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }

    void confirm(View v) {
        state = SET_DATA;
        DbTask db = new DbTask();
        int select = (int) cbSelected.getTag();
        String[] strings = strList.get(select).split(",");
        Log.d("ReservePlace", "place: " + strings[0]);
        db.execute("SetOrder?type=place&id=" + orderuid + "&place=" + strings[0]);
    }

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
            view = inflater.inflate(R.layout.place_item, null);
            CheckBox cb = (CheckBox) view.findViewById(R.id.checkBox);
            String[] strings = strList.get(i).split(",");
            cb.setText(strings[1]);
            cb.setTag(i);
            cb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CheckBox cb = (CheckBox) view;
                    if (cb != cbSelected) {
                        if (cbSelected != null) {
                            cbSelected.setChecked(false);
                        }
                        cbSelected = cb;
                        cbSelected.setChecked(true);
                    }
                }
            });

            return view;
        }
    }

}
