package tw.com.fateezgo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

public class ReservationActivity extends BasicActivity {
    private static final int GET_ORDER_MASTER = 1;
    private static final int GET_AVA_TIME = 2;
    private static final int SET_ORDER = 3;
    private static final int FUNC_PLACE = 100;
    private int masteruid;
    private int orderuid;
    private CustomCalendarView cv;
    private int state = GET_ORDER_MASTER;
    private HashSet<Date> events;
    private String yearMonth;
    private Calendar now;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        masteruid = getIntent().getIntExtra("masteruid", 14);
        orderuid = getIntent().getIntExtra("order_id", 2);
        state = GET_ORDER_MASTER;
        DbTask db = new DbTask();
        db.execute("http://140.137.218.52:8080/fateezgo-ee/order?type=rdate&id=" + masteruid);
    }

    @Override
    void doViews() {
        switch (state) {
            case GET_ORDER_MASTER:
                events = new HashSet<>();
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
                state = GET_AVA_TIME;
                now = Calendar.getInstance();
                int year = now.get(Calendar.YEAR);
                int month = now.get(Calendar.MONTH) + 1;
                yearMonth = year + "-" + month + "-";
                String strMonth = yearMonth + "01";
                strList.clear();
                DbTask db = new DbTask();
                db.execute("http://140.137.218.52:8080/fateezgo-ee/AvaTime?type=get2&id=" + masteruid + "&mon=" + strMonth);
                break;
            case GET_AVA_TIME:
                int avaTime = 0;
                for (int count = 0; count < strList.size(); count++) {
                    String[] strArray = strList.get(count).split(",");
                    avaTime = Integer.valueOf(strArray[0]);
                    Log.d("MasterSchedule", "ava time: " + avaTime);
                    avaTime = ~avaTime;
                    HashSet<Date> events = new HashSet<>();
                    for (int i = 0; i < now.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
                        int val = 1 << i;
                        int res = avaTime & val;
                        if (res != 0) {
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            String day = yearMonth + (i + 1);
                            //Log.d("MasterSchedule", "date STR: " + day);
                            Date d;
                            try {
                                d = simpleDateFormat.parse(day);
                                Log.d("MasterSchedule", "date: " + d.getDate() + "/" + (d.getMonth() + 1) + "/" + d.getYear());
                                events.add(d);
                            } catch (ParseException e) {

                            }
                        }
                    }

                    int cyear = now.get(Calendar.YEAR);
                    int cmonth = now.get(Calendar.MONTH) + 1;
                    yearMonth = cyear + "-" + (cmonth+1) + "-";
                }
                cv = (CustomCalendarView) findViewById(R.id.calendar);
                Log.d("MasterSchedule", "cv = " + cv);
                cv.updateCalendar(events, CustomCalendarView.CAL_SEL_ONE);
                break;
            case SET_ORDER:
                Intent intent = new Intent(this, ReservePlaceActivity.class);
                intent.putExtra("masteruid", masteruid);
                startActivityForResult(intent, FUNC_PLACE);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FUNC_PLACE) {
            if (resultCode == RESULT_OK) {
                finish();
            }
        }
    }
}
