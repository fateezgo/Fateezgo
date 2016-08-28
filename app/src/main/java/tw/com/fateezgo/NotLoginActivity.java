package tw.com.fateezgo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NotLoginActivity extends AppCompatActivity implements View.OnClickListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Button login = (Button) this.findViewById(R.id.loginbtn),regist = (Button) this.findViewById(R.id.registbtn);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_login);

        login.setOnClickListener(this);
        regist.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        Intent intent;

        switch (v.getId())
        {
            case R.id.loginbtn:
                intent = new Intent(NotLoginActivity.this,LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.registbtn:
                intent = new Intent(NotLoginActivity.this,RegistActivity.class);
                startActivity(intent);
                break;
        }
    }
}
