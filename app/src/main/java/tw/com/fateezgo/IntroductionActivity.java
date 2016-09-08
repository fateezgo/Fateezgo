package tw.com.fateezgo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class IntroductionActivity extends BasicActivity {

    private EditText edIntro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);
        edIntro = (EditText) findViewById(R.id.et_intro);
    }

    @Override
    void doViews() {
        Intent intent = new Intent(this, ModifyMasterActivity.class);
        startActivity(intent);
        finish();
    }


    void confirm(View v) {
        DbTask db = new DbTask();
        db.execute("http://140.137.218.52:8080/fateezgo-ee/m_intro?id=" + member.uid() + "&data=" + edIntro.getText().toString());
    }
}
