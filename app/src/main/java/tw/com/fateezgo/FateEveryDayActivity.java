package tw.com.fateezgo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == FUNC_CARD) && (resultCode == RESULT_OK)) {
            LayoutInflater layoutInflater = getLayoutInflater();
            LinearLayout ll = new LinearLayout(getApplicationContext());
            SpreadLayout spreadLayout = new SpreadLayout(SpreadLayout.SPREAD_EVERY_DAY);
            View v = layoutInflater.inflate(spreadLayout.getSpreadLayoutResId(), ll, true);
            int[] imgIds = spreadLayout.getSpreadImgResIds();
            ImageView img = (ImageView) v.findViewById(imgIds[0]);
            img.setImageResource(CardActivity.imgIds[0]);
            Bitmap b = spreadLayout.createBitmap(v);
            imgCard.setImageBitmap(b);
        }
    }
}
