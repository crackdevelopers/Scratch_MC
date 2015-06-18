package trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.zip.Inflater;

import trees.money_calendar.com.moneycalander.R;
import trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.adapter.CategoryAdapter;
import trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.database.GetDateAndTime;
import trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.database.Message;
import trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.database.SQLiteAdapter;
//import trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.SpinnerDataClass;

/**
 * Created by trees on 6/5/15.
 */
public class Add extends Fragment implements View.OnClickListener{


    private Spinner spinner;
    private Button addButton;
    private Switch inOutSwitch;
    private EditText days, month, year, amount, description;
    private Context context;
    private String categoryName;

    //DATE ATTRIBUTES
    private Calendar c;
    private int curntYr,curntMnth, curntDays;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_layout, container, false);
        initialize(v);
        return v;
    }


    private void initialize(View v)
    {

        //CURRENT DATE ATTRIBUTES
        c=Calendar.getInstance();
        curntYr=c.get(Calendar.YEAR);
        curntMnth=c.get(Calendar.MONTH); curntMnth++;
        curntDays=c.get(Calendar.DAY_OF_MONTH);


        context=(Context)getActivity();
        inOutSwitch=(Switch)v.findViewById(R.id.addSwitch);
        spinner = (Spinner) v.findViewById(R.id.spinnerCategory);
        days = (EditText) v.findViewById(R.id.addDay);            days.setText(""+curntDays);
        month = (EditText) v.findViewById(R.id.addMonth);         month.setText(""+curntMnth);
        year = (EditText) v.findViewById(R.id.addYear);           year.setText(curntYr+"");
        amount = (EditText) v.findViewById(R.id.addAmount);
        description = (EditText) v.findViewById(R.id.addDescription);
        addButton=(Button)v.findViewById(R.id.addButton);




        String list[] = {"others", "Shopping", "Groceries", "Maintainence", "Rent", "Fun"};

        spinner.setAdapter(new CategoryAdapter(getActivity(), R.layout.category_row, list));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoryName = resolveCategories(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                Toast.makeText(context, "not selected", Toast.LENGTH_SHORT).show();

            }
        });

        addButton.setOnClickListener(this);


    }


    //This method validates the form
    private boolean validation()
    {

        boolean flag=true;


        if(days!=null && !days.getText().toString().equals(""))
        {
            int dys = Integer.parseInt(days.getText().toString());
            if(dys>0 &&  dys<=curntDays)
            {
                days.setTextColor(context.getResources().getColor(R.color.primaryText));
            }
            else
            {
                int mnts=0;
                if(month!=null && !month.getText().toString().equals(""))
                {
                    mnts = Integer.parseInt(month.getText().toString());
                }

                if(mnts!=curntMnth && dys<32 && dys>0)
                {
                    days.setTextColor(context.getResources().getColor(R.color.primaryText));
                }
                else
                {
                    days.setTextColor(context.getResources().getColor(R.color.flowOut));
                    flag = false;
                }
            }
        }
        else
        {
            days.setHint("dd");
            flag=false;
        }

        //For Month
        if(month!=null && !month.getText().toString().equals(""))
        {


            int mnts = Integer.parseInt(month.getText().toString());
            if(mnts>0 &&  mnts<=curntMnth)
            {
                month.setTextColor(context.getResources().getColor(R.color.primaryText));
            }
            else
            {
                int yr=0;

                if(year!=null && !year.getText().toString().equals(""))
                {
                    yr = Integer.parseInt(year.getText().toString());
                }
                if(curntMnth<=2 && yr==curntYr-1 && mnts>0 && mnts<=12)
                {
                    Log.i("YEAR","2014");
                    month.setTextColor(context.getResources().getColor(R.color.primaryText));
                }
                else {
                    month.setTextColor(context.getResources().getColor(R.color.flowOut));
                    flag = false;
                }
            }
        }
        else
        {
            month.setHint("mm");
            flag=false;
        }

        //For YEAR
        if(year!=null && !year.getText().toString().equals(""))
        {



            int yr = Integer.parseInt(year.getText().toString());
            if(yr!=curntYr)
            {
                if (yr == curntYr - 1 && curntMnth <= 2 )
                {
                    year.setTextColor(context.getResources().getColor(R.color.primaryText));
                }
                else
                {
                    year.setTextColor(context.getResources().getColor(R.color.flowOut));
                    flag = false;
                }
            }
            else
                year.setTextColor(context.getResources().getColor(R.color.primaryText));
        }
        else
        {
            year.setHint("yyyy");
            flag=false;
        }


        if(amount==null || amount.getText().toString().equals(""))
        {
            amount.setHint("empty");
            flag=false;
        }

        if(description==null || description.getText().toString().equals(""))
        {
            description.setHint("empty");
            flag=false;
        }


            return flag;

    }




    @Override
    public void onClick(View v)
    {
        if(v.getId()==R.id.addButton)
        {
            if(validation())
            {
                String date=year.getText().toString()+"-"+month.getText().toString()+"-"+days.getText().toString();
                String amnt=amount.getText().toString();
                String dscrptn=description.getText().toString();
                int in_or_out=0;
                if(inOutSwitch.isChecked())
                    in_or_out=1;
                //Toast.makeText(context, categoryName, Toast.LENGTH_SHORT).show();

                SQLiteAdapter dbAdapter=new SQLiteAdapter(context);
                GetDateAndTime dateAndTime=new GetDateAndTime();

//            Perform Insert operation to database table -one row at a time.
                long rows=dbAdapter.insertSingleRowToDatabase(amnt, categoryName, dscrptn, date, in_or_out);
                if(rows>0) Message.message_longTime(context, "successful"+rows);
                else
                    Message.message_longTime(context, "insertFail");


            }
            else
                Toast.makeText(context, "invalid", Toast.LENGTH_SHORT).show();


        }






    }


    private String resolveCategories(int position)
    {

        ///"others", "Shopping", "Groceries", "Maintainence", "Rent", "Fun"

        if(position==0) return "Others";
        if(position==1) return "Shopping";
        if(position==2) return "Groceries";
        if(position==3) return "Maintainence";
        if(position==4) return "Rent";
        if(position==5) return "Fun";

        return "others";
    }
}
