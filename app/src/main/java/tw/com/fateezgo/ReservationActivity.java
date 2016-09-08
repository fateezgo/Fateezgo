package tw.com.fateezgo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

public class ReservationActivity extends BasicActivity {
    private static final int GET_DATA = 1;
    private static final int SET_ORDER = 2;
    private int masteruid;
    private int orderuid;
    private CustomCalendarView cv;
    private int state = GET_DATA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        masteruid = getIntent().getIntExtra("masteruid", 14);
        orderuid = getIntent().getIntExtra("order_id", 2);
        state = GET_DATA;
        DbTask db = new DbTask();
        db.execute("http://140.137.218.52:8080/fateezgo-ee/order?type=rdate&id=" + masteruid);
    }

    @Override
    void doViews() {
        switch (state) {
            case GET_DATA:
                HashSet<Date> events = new HashSet<>();
                for (int i = 0; i < strList.size(); i++) {
                    String[] strArray = strList.get(i).split(",");
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date d;
                    try {
                        d = simpleDateFormat.parse(strArray[0]);
                        Log.d("MasterSchedule", "date: " + d.getDate() + "/" + d.getMonth() + "/" + d.getYear());
                        events.add(d);
                    } catch (ParseException e) {

                    }
                }
                cv = (CustomCalendarView) findViewById(R.id.calendar);
                Log.d("MasterSchedule", "cv = " + cv);
                cv.updateCalendar(events, CustomCalendarView.CAL_SEL_ONE);
                break;
            case SET_ORDER:
                // go to next activity
                break;
            default:
                break;
        }
    }

    void reserve(View v) {
        Date date = cv.getSelected();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = simpleDateFormat.format(date);
        //Log.d("MasterSchedule", "date: " + strDate);
        strDate += "%2014:30:00";
        state = SET_ORDER;
        DbTask db = new DbTask();
        db.execute("http://140.137.218.52:8080/fateezgo-ee/SetOrder?type=rdate&id=" + orderuid + "&date=" + strDate);
    }
}
