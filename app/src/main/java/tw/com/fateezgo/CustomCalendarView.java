package tw.com.fateezgo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private LinearLayout header;
    private ImageView bPrev;
    private ImageView bNext;
    private TextView txtDate;
    private GridView grid;
    private LayoutInflater inflater;
    private Calendar currentDate = Calendar.getInstance();;
    private int[] colors = { R.color.cal_summer, R.color.cal_fall, R.color.cal_winter, R.color.cal_spring };
    private int[] monthSeason = {2, 2, 3, 3, 3, 0, 0, 0, 1, 1, 1, 2};

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
        ArrayList<Date> cells = new ArrayList<>();
        Calendar calendar = (Calendar)currentDate.clone();

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
        grid.setAdapter(new CalendarAdapter(getContext(), cells, events));

        // update title
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        txtDate.setText(sdf.format(currentDate.getTime()));

        // set header color according to current season
        int season = monthSeason[currentDate.get(Calendar.MONTH)];
        header.setBackgroundColor(getResources().getColor(colors[season]));
    }


    private class CalendarAdapter extends ArrayAdapter<Date> {
        private LayoutInflater inflater;
        private HashSet<Date> eventDays;

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

            // today
            Date today = new Date();

            // inflate item if it does not exist yet
            if (view == null)
                view = inflater.inflate(R.layout.calendar_day, viewGroup, false);

            CheckBox cb1 = (CheckBox) view.findViewById(R.id.cb_reserve1);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);    //2 for tuesday
            if ((dayOfWeek == 1) || (dayOfWeek == 7)) {
                cb1.setVisibility(INVISIBLE);
            }

            if (eventDays != null)
            {
                for (Date eventDate : eventDays)
                {
                    if (eventDate.getDate() == day &&
                            eventDate.getMonth() == month &&
                            eventDate.getYear() == year)
                    {
                        // mark this day for event
                        cb1.setVisibility(INVISIBLE);
                        break;
                    }
                }
            }

            TextView tvDay = (TextView) view.findViewById(R.id.tv_day);
            tvDay.setTypeface(null, Typeface.NORMAL);
            tvDay.setTextColor(Color.BLACK);

            if (date.getMonth() != today.getMonth() ||
                    date.getYear() != today.getYear())
            {
                // if this day is outside current month, grey it out
                tvDay.setTextColor(getResources().getColor(R.color.cal_grey));
            }
            else if (date.getDate() == today.getDate())
            {
                // if it is today, set it to blue/bold
                tvDay.setTypeface(null, Typeface.BOLD);
                tvDay.setTextColor(getResources().getColor(R.color.cal_today));
            }

            // set text
            tvDay.setText(String.valueOf(date.getDate()));

            return view;
        }
    }
}
