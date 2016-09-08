package tw.com.fateezgo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NotLoginActivity extends BasicActivity {
    private static final int FUNC_LOGIN = 1;
    private static final int FUNC_REGISTER = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Button login = (Button) this.findViewById(R.id.loginbtn),regist = (Button) this.findViewById(R.id.registbtn);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_login);
    }

    @Override
    void doViews() {

    }

    void login(View v) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent, FUNC_LOGIN);
    }

    void register(View v) {
        Intent intent = new Intent(this, RegistActivity.class);
        startActivityForResult(intent, FUNC_REGISTER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == FUNC_LOGIN) && (resultCode == RESULT_OK)) {
            setResult(RESULT_OK, getIntent());
            finish();
        }
        else if ((requestCode == FUNC_REGISTER) && (resultCode == RESULT_OK)) {
            setResult(RESULT_OK, getIntent());
            finish();
        }
    }
}
