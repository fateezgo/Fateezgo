package tw.com.fateezgo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends BasicActivity
{
    EditText userid;
    EditText passwd;
    private TextView tvMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userid = (EditText) findViewById(R.id.userid);
        passwd = (EditText) findViewById(R.id.passwd);
        tvMsg = (TextView) findViewById(R.id.err_msg);
    }

    @Override
    void doViews()
    {
        if (strList.size()> 0) {
            //success!
            Log.d("LOGIN", "string: " + strList.get(0));
            String[] strArray = strList.get(0).split(",");
            String str = strList.get(1);
            boolean isMaster;
            if (str.equals("1")) {
                isMaster = true;
            }
            else {
                isMaster = false;
            }
            member.setMemberData(
                    this,
                    Integer.valueOf(strArray[0]),
                    userid.getText().toString(),
                    userid.getText().toString(),
                    strArray[1],
                    strArray[2],
                    isMaster);
            setResult(RESULT_OK, getIntent());
            finish();
        }
        else {
            tvMsg.setText("登入失敗! 請再試一次!");
        }
    }

    void login(View v)
    {
        DbTask db = new DbTask();
        String uid = userid.getText().toString();
        String pass = passwd.getText().toString();
        Log.d("LOGIN", "login!!");
        db.execute("login?uid=" + uid + "&passwd=" + pass);
    }
}
