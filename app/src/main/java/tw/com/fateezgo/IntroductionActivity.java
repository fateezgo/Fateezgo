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
        finish();
    }


    void confirm(View v) {
        DbTask db = new DbTask();
        String intro = edIntro.getText().toString();
        intro = intro.replace(" ", "%20");
        db.execute("m_intro?id=" + member.uid() + "&data=" + intro);
    }
}
