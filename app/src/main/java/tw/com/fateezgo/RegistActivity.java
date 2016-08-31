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

public class RegistActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener, View.OnClickListener {
    EditText firstname,lastname,email,phone,passwd,retypepw;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        Button teacherreg = (Button) this.findViewById(R.id.teacherreg),memberreg = (Button) this.findViewById(R.id.memberrage);

        Toolbar toolbar = (Toolbar) RegistActivity.this.findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(this);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        Spinner countrycode = (Spinner) this.findViewById(R.id.countrycode);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(RegistActivity.this,R.array.list,android.R.layout.simple_spinner_dropdown_item);
        countrycode.setAdapter(adapter);

        firstname = (EditText) this.findViewById(R.id.firstname);
        lastname = (EditText) this.findViewById(R.id.lastname);
        email = (EditText) this.findViewById(R.id.email);
        phone = (EditText) this.findViewById(R.id.phone);
        passwd = (EditText)this.findViewById(R.id.passwd);
        retypepw = (EditText) this.findViewById(R.id.retypepw);

        teacherreg.setOnClickListener(this);
        memberreg.setOnClickListener(this);
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
        Intent intent;
        switch (v.getId())
        {
            case R.id.teacherreg:
                intent = new Intent(RegistActivity.this,RegistMasterActivity.class);
                startActivity(intent);
                break;
            case R.id.memberrage:
                intent = new Intent(RegistActivity.this,RegistMemberActivity.class);
                startActivity(intent);
                break;
        }
    }
}
