package tw.com.fateezgo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegistMasterActivity extends AppCompatActivity implements View.OnClickListener
{
    protected EditText name,phone,passwd,retypepw,email,address,hyper;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_master);

        Button OK = (Button) this.findViewById(R.id.OK),clear = (Button) this.findViewById(R.id.clear),cancle = (Button) this.findViewById(R.id.cancle);

        name = (EditText) this.findViewById(R.id.name);
        phone = (EditText) this.findViewById(R.id.phone);
        passwd = (EditText) this.findViewById(R.id.passwd);
        retypepw = (EditText) this.findViewById(R.id.retypepw);
        email = (EditText) this.findViewById(R.id.email);
        address = (EditText) this.findViewById(R.id.address);
        hyper = (EditText) this.findViewById(R.id.hyper);

        OK.setOnClickListener(this);
        clear.setOnClickListener(this);
        cancle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.OK:
                break;
            case R.id.clear:
                name.setText("");
                phone.setText("");
                passwd.setText("");
                retypepw.setText("");
                email.setText("");
                address.setText("");
                hyper.setText("");
                break;
            case R.id.cancle:
                finish();
                break;
        }
    }
}
