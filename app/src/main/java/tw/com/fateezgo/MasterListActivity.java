package tw.com.fateezgo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

public class MasterListActivity extends AppCompatActivity {

    private Button masterevbtn;
    private Button mastDatatime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_list);
        masterevbtn = (Button) findViewById(R.id.mastEvabtn);
        mastDatatime = (Button) findViewById(R.id.mastDatatime);
    }

}
