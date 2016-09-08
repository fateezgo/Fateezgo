package tw.com.fateezgo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class RegistActivity extends BasicActivity {
    EditText firstname,lastname,email,phone,passwd,retypepw;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        firstname = (EditText) this.findViewById(R.id.firstname);
        email = (EditText) this.findViewById(R.id.email);
        phone = (EditText) this.findViewById(R.id.phone);
        passwd = (EditText)this.findViewById(R.id.passwd);
        retypepw = (EditText) this.findViewById(R.id.retypepw);

    }

    @Override
    void doViews() {
        Intent intent = new Intent(this, MemberActivity.class);
        startActivity(intent);
        finish();
    }

    void registAsMaster(View v) {
        DbTask lt = new DbTask();
        lt.execute("http://140.137.218.52:8080/fateezgo-ee/Register?type=master&name="+firstname.getText().toString() +
        "&passwd=" + passwd.getText().toString() + "&phone=" + phone.getText().toString() + "&email=" + email.getText().toString());
    }

    void registAsMember(View v) {
        DbTask lt = new DbTask();
        lt.execute("http://140.137.218.52:8080/fateezgo-ee/Register?type=member&name="+firstname.getText().toString() +
                "&passwd=" + passwd.getText().toString() + "&phone=" + phone.getText().toString() + "&email=" + email.getText().toString());
    }
}
