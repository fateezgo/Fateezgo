package tw.com.fateezgo;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2016/8/30.
 */
public class SpreadLayout {
    public static final int SPREAD_EVERY_DAY = 0;
    public static final int SPREAD_TIME = 1;
    public static final int SPREAD_LOVE = 2;
    public static final int SPREAD_SPIRIT = 3;
    public static final int SPREAD_SELECT = 4;
    public static final int SPREAD_TRIANGLE = 5;

    private final int[] cardPatternFateEvery = {R.id.img_fate_ed_1}; // fate every
    private final int[] cardPatternTime = {R.id.img_time_1, R.id.img_time_2, R.id.img_time_3};  // time
    private final int[] cardPatternLove = {R.id.img_love_1, R.id.img_love_2, R.id.img_love_3, R.id.img_love_4}; //love
    private final int[] cardPatternSpirit = {R.id.img_spirit_1, R.id.img_spirit_2, R.id.img_spirit_3};  //spirit
    private final int[] cardPatternSelect = {R.id.img_select_1, R.id.img_select_2, R.id.img_select_3, R.id.img_select_4, R.id.img_select_5}; // 1/2
    private final int[] cardPatternTriangle = {R.id.img_triangle_1, R.id.img_triangle_2, R.id.img_triangle_3};  // triangle
    private final int[][] cardPatterns = {cardPatternFateEvery, cardPatternTime, cardPatternLove, cardPatternSpirit, cardPatternSelect, cardPatternTriangle};
    public final static int[] spreadLayoutResId = {R.layout.spread_fate_every_day, R.layout.spread_time, R.layout.spread_love, R.layout.spread_spirit, R.layout.spread_select, R.layout.spread_triangle};

    private int spread = 0;

    SpreadLayout(int spread) {
        this.spread = spread;
    }

    public int[] getSpreadImgResIds() {
        return cardPatterns[spread];
    }

    public int getSpreadLayoutResId() {
        return spreadLayoutResId[spread];
    }

    public int getSpread() {
        return spread;
    }

    public static Bitmap createBitmap(View v) {
        //Provide it with a layout params. It should necessarily be wrapping the
        //content as we not really going to have a parent for it.
        v.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        //Pre-measure the view so that height and width don't remain null.
        v.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        Log.d("SPREAD", "bitmap w: " + v.getMeasuredWidth() + " h: " + v.getMeasuredHeight());

        //Assign a size and position to the view and all of its descendants
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());

        Bitmap b = Bitmap.createBitmap(v.getMeasuredWidth(), v.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);
        //saveFile(b);
        return b;
    }


}
