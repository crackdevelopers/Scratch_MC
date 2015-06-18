package trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.database;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by root on 07/06/15.
 */
public class GetDateAndTime
{
    public String getDateTime(String getDate,String getTime)
    {
        SimpleDateFormat simpleDateFormat=null;

        if(getDate=="date" && getTime=="")
        {
            simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        }

        else if(getDate=="" && getTime=="time")
        {
            simpleDateFormat=new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        }

        else if (getDate=="date" && getTime=="time")
        {
            simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        }

        else
        {
            System.out.print("date and time fields cannot be empty simultaneously -> ");
            return ("error!!");
        }


        Date date=new Date();
        return simpleDateFormat.format(date);
    }
}

