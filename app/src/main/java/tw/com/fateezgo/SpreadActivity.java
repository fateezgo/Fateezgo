package tw.com.fateezgo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class SpreadActivity extends AppCompatActivity {

    private int[] imgIds = {R.id.img_fate_ed, R.id.img_time, R.id.img_love, R.id.img_spirit, R.id.img_select, R.id.img_triangle};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spread);

        LayoutInflater layoutInflater = getLayoutInflater();
        for (int i = 0; i < imgIds.length; i++) {
            LinearLayout ll = new LinearLayout(getApplicationContext());
            Bitmap b = SpreadLayout.createBitmap(layoutInflater.inflate(SpreadLayout.spreadLayoutResId[i], ll, true));
            ImageView img = (ImageView) findViewById(imgIds[i]);
            img.setImageBitmap(b);
            img.setTag(i);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = getIntent();
                    intent.putExtra("spread", (Integer) view.getTag());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
        }
    }
}
