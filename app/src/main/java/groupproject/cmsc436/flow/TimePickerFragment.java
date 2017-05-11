package groupproject.cmsc436.flow;

/**
 * Created by magneta94 on 5/8/17.
 */


import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;


public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

    private static String currTime = "";


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);


        return new TimePickerDialog(getActivity(),this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    //onTimeSet() callback method
    public void onTimeSet(TimePicker view, int hourOfDay, int minute){
        String aMpM = "AM";
        int  currentHour;
        String currMinute;
        if(hourOfDay >11) {
            aMpM = "PM";
        }

        if(hourOfDay > 12) {
            currentHour = hourOfDay - 12;
        } else if (hourOfDay == 0) {
            currentHour = 12;
        } else {
                currentHour = hourOfDay;
        }

        if (minute < 10) {
           currMinute = Integer.toString(minute) + "0";
        } else {
            currMinute = Integer.toString(minute);
        }

        currTime = Integer.toString(currentHour) + ":" + currMinute + "" + aMpM;
        CreateEventFragment.setEndTime(currTime);
    }


    public static String getTime(){
        return currTime;
    }



}