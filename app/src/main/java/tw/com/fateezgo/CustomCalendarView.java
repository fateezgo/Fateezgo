package tw.com.fateezgo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Administrator on 2016/9/7.
 */
public class CustomCalendarView extends LinearLayout {
    private static final int DAYS_COUNT = 42;
    private static final String DATE_FORMAT = "MMM yyyy";
    public static final int CAL_SEL_ONE = 1;
    public static final int CAL_SEL_MUL = 2;
    private LinearLayout header;
    private ImageView bPrev;
    private ImageView bNext;
    private TextView txtDate;
    private GridView grid;
    private LayoutInflater inflater;
    private Calendar currentDate = Calendar.getInstance();
    private int[] colors = { R.color.cal_summer, R.color.cal_fall, R.color.cal_winter, R.color.cal_spring };
    private int[] monthSeason = {2, 2, 3, 3, 3, 0, 0, 0, 1, 1, 1, 2};
    private CalendarAdapter calAdapter;
    private int type = CAL_SEL_ONE;
    private int avaTime = 0;

    public CustomCalendarView(Context context)
    {
        super(context);
    }

    public CustomCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomCalendarView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs)
    {
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_calendar, this);

        header = (LinearLayout)findViewById(R.id.calendar_header);
        bPrev = (ImageView)findViewById(R.id.calendar_prev_button);
        bNext = (ImageView)findViewById(R.id.calendar_next_button);
        txtDate = (TextView)findViewById(R.id.calendar_date_display);
        grid = (GridView)findViewById(R.id.calendar_grid);

        bNext.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                currentDate.add(Calendar.MONTH, 1);
                updateCalendar(null);
            }
        });

        bPrev.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                currentDate.add(Calendar.MONTH, -1);
                updateCalendar(null);
            }
        });

        updateCalendar(null);
    }

    public void updateCalendar(HashSet<Date> events)
    {
        updateCalendar(events, type);
    }

    public void updateCalendar(HashSet<Date> events, int type)
    {
        this.type = type;
        ArrayList<Date> cells = new ArrayList<>();
        Calendar calendar = (Calendar)currentDate.clone();

        if (type == CAL_SEL_MUL) {
            calendar.add(Calendar.MONTH, 2);
        }

        // update title
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        if (type == CAL_SEL_ONE) {
            txtDate.setText(sdf.format(currentDate.getTime()));
        }
        else {
            txtDate.setText(sdf.format(calendar.getTime()));
        }

        // determine the cell for current month's beginning
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        // move calendar backwards to the beginning of the week
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);

        // fill cells
        while (cells.size() < DAYS_COUNT)
        {
            cells.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        // update grid
        calAdapter = new CalendarAdapter(getContext(), cells, events);
        grid.setAdapter(calAdapter);

        // set header color according to current season
        int season = monthSeason[currentDate.get(Calendar.MONTH)];
        header.setBackgroundColor(getResources().getColor(colors[season]));
    }

    public Date getSelected() {
        return (Date)calAdapter.cbSelected.getTag();
    }
    public int getMulSelected() { return avaTime; }

    private class CalendarAdapter extends ArrayAdapter<Date> {
        private LayoutInflater inflater;
        private HashSet<Date> eventDays;
        public CheckBox cbSelected = null;

        public CalendarAdapter(Context context, ArrayList<Date> days, HashSet<Date> eventDays)
        {
            super(context, R.layout.calendar_day, days);
            this.eventDays = eventDays;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            // day in question
            Date date = (Date)getItem(i);
            int day = date.getDate();
            int month = date.getMonth();
            int year = date.getYear();
            Date today;
            if (type == CAL_SEL_MUL) {
                Calendar calendar = (Calendar)currentDate.clone();
                calendar.add(Calendar.MONTH, 2);
                today = calendar.getTime();
            }
            else {// today
                today = new Date();
            }

            // inflate item if it does not exist yet
            if (view == null)
                view = inflater.inflate(R.layout.calendar_day, viewGroup, false);

            CheckBox cb = (CheckBox) view.findViewById(R.id.cbReserve);

            if (eventDays != null)
            {
                for (Date eventDate : eventDays)
                {
                    if (eventDate.getDate() == day &&
                            eventDate.getMonth() == month &&
                            eventDate.getYear() == year)
                    {
                        // mark this day for event
                        if (type == CAL_SEL_ONE) {
                            cb.setVisibility(INVISIBLE);
                            LinearLayout ll = (LinearLayout) view.findViewById(R.id.cal_item);
                            ll.setBackgroundResource(R.drawable.reminder);
                        }
                        else {
                            cb.setChecked(true);
                            avaTime |= (1 << (eventDate.getDate()-1));
                        }
                        break;
                    }
                }
            }

            cb.setTag(date);
            cb.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (type == CAL_SEL_ONE) {
                        if ((cbSelected != null) && (view != cbSelected)) {
                            cbSelected.setChecked(false);
                        }
                        cbSelected = (CheckBox) view;
                        cbSelected.setChecked(true);
                    }
                    else {
                        CheckBox checkBox = (CheckBox)view;
                        Date d = (Date)checkBox.getTag();
                        Log.d("Calendar", "day: " + d.getDate());
                        if (checkBox.isChecked() == true)
                            avaTime |= (1 << (d.getDate()-1));
                        else
                            avaTime &= ~(1 << (d.getDate()-1));
                        Log.d("Calendar", "avaTime: " + avaTime);
                    }
                }
            });

            TextView tvDay = (TextView) view.findViewById(R.id.tv_day);
            tvDay.setTypeface(null, Typeface.NORMAL);
            tvDay.setTextColor(Color.BLACK);

            if (date.getMonth() != today.getMonth() ||
                    date.getYear() != today.getYear())
            {
                // if this day is outside current month, grey it out
                tvDay.setTextColor(getResources().getColor(R.color.cal_grey));
                cb.setVisibility(INVISIBLE);
            }
            else if (date.getDate() == today.getDate())
            {
                if (type == CAL_SEL_ONE) {
                    // if it is today, set it to blue/bold
                    tvDay.setTypeface(null, Typeface.BOLD);
                    tvDay.setTextColor(getResources().getColor(R.color.cal_today));
                    cb.setVisibility(INVISIBLE);
                }
            }

            // set text
            tvDay.setText(String.valueOf(date.getDate()));

            return view;
        }
    }
}
