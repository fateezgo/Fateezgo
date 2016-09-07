package tw.com.fateezgo;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class MasterEvaluationActivity extends BasicActivity {

    DbTask lt = new DbTask();

    private ListView blv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_evaluation);

        blv = (ListView) findViewById(R.id.base_list_view);

        lt.execute("http://140.137.218.77:8080/fateezgo-ee/weva");




    }

    @Override
    void doViews() {

        Dbadapter db = new Dbadapter(this);
        blv.setAdapter(db);

    }


    private class Dbadapter extends BaseAdapter{
        LayoutInflater inflater;

        public Dbadapter(MasterEvaluationActivity m) {
            inflater=LayoutInflater.from(m);
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
            System.out.println("onMaster:"+strList);
            view=inflater.inflate(R.layout.layoutmastereva,null);
            TextView txtd = (TextView) view.findViewById(R.id.textmasteva);
            String[] fields = strList.get(i).split(",");
            txtd.setText(fields[0]);
            return view;
        }
    }
}
