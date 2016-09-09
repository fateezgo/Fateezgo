package tw.com.fateezgo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class MemberActivity extends BasicActivity {
    private static final int FUNC_NOT_LOGIN = 100;
    // 1.宣告物件
    //
    private Button btnmember;
    private Button btnclass;
    private Button btncontact;
    private Button btnlogout;
    private TextView T01;
    private Spinner Sp01;
    private TextView T02;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);

        if (member.isLogin() == false) {
            Intent intent = new Intent(this, NotLoginActivity.class);
            startActivityForResult(intent, FUNC_NOT_LOGIN);
        }

        // 2.連結元件
        btnmember=(Button)findViewById(R.id.btnmember);
        btnclass =(Button)findViewById(R.id.btnclass);
        btncontact =(Button)findViewById(R.id.btncontact);
        btnlogout =(Button)findViewById(R.id.btnlogout);
        //T01 = (TextView) findViewById(R.id.t01);


        // 3.建立事件:
        btnmember.setOnClickListener(new Button.OnClickListener(){

            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent=new Intent();
                intent.setClass(MemberActivity.this,LoginActivity.class);
                startActivity(intent);
            }});



        btnclass.setOnClickListener(new Button.OnClickListener(){

            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent=new Intent();
                intent.setClass(MemberActivity.this,OrderListActivity.class);
                startActivity(intent);
            }});




        btncontact.setOnClickListener(new Button.OnClickListener(){

            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent=new Intent();
                intent.setClass(MemberActivity.this,ContactActivity.class);
                startActivity(intent);
            }});




        btnlogout.setOnClickListener(new Button.OnClickListener(){

            public void onClick(View v) {
                // TODO Auto-generated method stub
                member.logout(getApplicationContext());
                Intent intent=new Intent();
                intent.setClass(MemberActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }});




    } //on create end

    @Override
    void doViews() {
        // do nothing
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == FUNC_NOT_LOGIN) && (resultCode != RESULT_OK)) {
            finish();
        }
    }
}


