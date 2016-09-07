package tw.com.fateezgo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

public class SetMasterScheduleActivity extends BasicActivity {

    private int masteruid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_master_schedule);

        masteruid = getIntent().getIntExtra("masteruid", 14);
        DbTask db = new DbTask();
        db.execute("http://140.137.218.52:8080/fateezgo-ee/order?type=rdate&id=" + masteruid);
    }

    @Override
    void doViews() {
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
        CustomCalendarView cv = (CustomCalendarView)findViewById(R.id.calendar);
        Log.d("MasterSchedule", "cv = " + cv);
        cv.updateCalendar(events);
    }

}
