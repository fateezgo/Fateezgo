package tw.com.fateezgo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegistMemberActivity extends AppCompatActivity implements View.OnClickListener
{
    protected EditText name,phone,passwd,retypepw,email;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_master);

        Button ok = (Button) this.findViewById(R.id.ok),clear = (Button) this.findViewById(R.id.clear),cancle = (Button) this.findViewById(R.id.cancle);

        name = (EditText) this.findViewById(R.id.name);
        phone = (EditText) this.findViewById(R.id.phone);
        passwd = (EditText) this.findViewById(R.id.passwd);
        retypepw = (EditText) this.findViewById(R.id.retypepw);
        email = (EditText) this.findViewById(R.id.email);

        ok.setOnClickListener(this);
        clear.setOnClickListener(this);
        cancle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.ok:
                break;
            case R.id.clear:
                name.setText("");
                phone.setText("");
                passwd.setText("");
                retypepw.setText("");
                email.setText("");
                break;
            case R.id.cancle:
                finish();
                break;
        }
    }
}