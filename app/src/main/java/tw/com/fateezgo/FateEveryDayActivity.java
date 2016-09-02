package tw.com.fateezgo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class FateEveryDayActivity extends BasicActivity {

    private static final int FUNC_CARD = 100;
    private ImageView imgCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fate_every_day);

        imgCard = (ImageView) findViewById(R.id.img_card);
        Intent intent = new Intent(this, CardActivity.class);
        intent.putExtra("spread", SpreadLayout.SPREAD_EVERY_DAY);
        startActivityForResult(intent,  FUNC_CARD);

        // test
        //member.getMemberData(this);
        //Log.d("FATE_ED", "mem name: " + member.name());
        //member.setMemberData(this, true, 2, "belle", "123321", "0937000000", "ed@g.com", false);
        //Log.d("FATE_ED", "mem name: " + member.name());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FUNC_CARD) {
            if (resultCode == RESULT_OK) {
                LayoutInflater layoutInflater = getLayoutInflater();
                LinearLayout ll = new LinearLayout(getApplicationContext());
                SpreadLayout spreadLayout = new SpreadLayout(SpreadLayout.SPREAD_EVERY_DAY);
                View v = layoutInflater.inflate(spreadLayout.getSpreadLayoutResId(), ll, true);
                int[] imgIds = spreadLayout.getSpreadImgResIds();
                ImageView img = (ImageView) v.findViewById(imgIds[0]);
                Log.d("FATE_ED", "cards: " + data.getStringExtra("cards"));
                img.setImageResource(CardActivity.imgIds[Integer.valueOf(data.getStringExtra("cards"))]);
                Bitmap b = spreadLayout.createBitmap(v);
                imgCard.setImageBitmap(b);
            }
            else {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
}
