package tw.com.fateezgo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

public class SetMasterScheduleActivity extends BasicActivity {
    private static final int GET_DATA = 1;
    private static final int SET_AVA_TIME = 2;

    private int state = GET_DATA;
    private int masteruid;
    private CustomCalendarView cv;
    private String yearMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_master_schedule);

        masteruid = member.uid();
        Calendar now = Calendar.getInstance();
        Calendar calendar = (Calendar) now.clone();
        calendar.add(Calendar.MONTH, 2);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        yearMonth = year + "-" + month + "-";
        String strMonth = yearMonth + "01";
        DbTask db = new DbTask();
        db.execute("http://140.137.218.52:8080/fateezgo-ee/AvaTime?type=get&id=" + masteruid + "&mon=" + strMonth);
    }

    @Override
    void doViews() {
        switch (state) {
            case GET_DATA:
                int avaTime = 0;
                if (strList.size() > 0) {
                    String[] strArray = strList.get(0).split(",");
                    avaTime = Integer.valueOf(strArray[0]);
                    Log.d("MasterSchedule", "ava time: " + avaTime);
                }
                HashSet<Date> events = new HashSet<>();
                for (int i = 0; i < 32; i++) {
                    int val = 1 << i;
                    int res = avaTime & val;
                    if (res != 0) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String day = yearMonth + (i + 1);
                        Log.d("MasterSchedule", "date STR: " + day);
                        Date d;
                        try {
                            d = simpleDateFormat.parse(day);
                            Log.d("MasterSchedule", "date: " + d.getDate() + "/" + (d.getMonth() + 1) + "/" + d.getYear());
                            events.add(d);
                        } catch (ParseException e) {

                        }
                    }
                }
                cv = (CustomCalendarView) findViewById(R.id.calendar);
                cv.updateCalendar(events, CustomCalendarView.CAL_SEL_MUL);
                break;
            case SET_AVA_TIME:
                Intent intent = new Intent(this, ModifyMasterActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    void confirm(View v) {
        state = SET_AVA_TIME;
        Log.d("MasterSchedule", "ava time: " + cv.getMulSelected());
        DbTask db = new DbTask();
        String month = yearMonth + "01";
        db.execute("http://140.137.218.52:8080/fateezgo-ee/AvaTime?type=set&id=" + masteruid + "&mon=" + month + "&time=" + cv.getMulSelected());
    }

}
