package tw.com.fateezgo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class ModifyMemberActivity extends BasicActivity {

    private EditText edtname;
    private EditText edtphone;
    private EditText edtpw;
    private EditText edtmail;
    DbTask lt = new DbTask();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_member);

        edtname = (EditText) findViewById(R.id.edtmemname);
        edtphone = (EditText) findViewById(R.id.edtmemphone);
        edtpw = (EditText) findViewById(R.id.edtmempw);
        edtmail = (EditText) findViewById(R.id.edtmememail);

        lt.execute("http://140.137.218.77:8080/fateezgo-ee/getmaster?qtype=\"+requestType");
    }

    @Override
    void doViews() {

    }
}
