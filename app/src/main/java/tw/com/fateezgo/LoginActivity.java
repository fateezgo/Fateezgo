package tw.com.fateezgo;

import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener, View.OnClickListener
{
    EditText userid,passwd;

    private String uid,pw;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(this);
        setSupportActionBar(toolbar);

        Button login = (Button) this.findViewById(R.id.login);


       if(getSupportActionBar() != null)
       {
           getSupportActionBar().setDisplayHomeAsUpEnabled(true);
           getSupportActionBar().setHomeButtonEnabled(true);
       }

        userid = (EditText) this.findViewById(R.id.userid);
        passwd = (EditText) this.findViewById(R.id.passwd);

        login.setOnClickListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
        }
        return true;
    }

    @Override
    public void onClick(View v)
    {


        String urlname = "http://localhost:8080/fateezgo-ee/mem?name="+uid+"&passwd="+pw;
        if (v.getId() == R.id.login)
        {
            new loginTask().execute(urlname);
        }
    }

    class loginTask extends AsyncTask<String,Void,Integer>
    {

        @Override
        protected Integer doInBackground(String... strings)
        {
            int data =0;

            try
            {
                URL url = new URL(strings[0]);
            /*由URL產生連線, 並轉型為HttpURLConnection*/
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            /*由連線取得輸入資料流物件*/
                InputStream is = conn.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                 /*轉換為可正確讀取中文的Reader物件*/

                data = br.read();

                Log.d("NET",String.valueOf(data));
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            return data;
        }

        @Override
        protected void onPostExecute(Integer data)
        {
            uid = userid.getText().toString();
            pw = String.valueOf(passwd.getText());
            if (data == 49)
            {
                Toast.makeText(LoginActivity.this,"登入成功",Toast.LENGTH_LONG).show();
                getIntent().putExtra("LOGIN_USER",uid);
                getIntent().putExtra("LOGIN_PASSWD",pw);
                setResult(RESULT_OK,getIntent());

                finish();
            }
            else
            {
                new AlertDialog.Builder(LoginActivity.this)
                        .setTitle("登入失敗")
                        .setMessage("帳號或密碼錯誤!!")
                        .setPositiveButton("OK", null)
                        .show();
            }
        }
    }
}
