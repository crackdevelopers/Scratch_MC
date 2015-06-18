package trees.money_calendar.com.moneycalander;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Calendar;

import trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.adapter.CategoryAdapter;
import trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.database.GetDateAndTime;
import trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.database.Message;
import trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.database.SQLiteAdapter;


public class AddActivity extends ActionBarActivity implements View.OnClickListener{


    private Spinner spinner;
    private Button addButton;
    private Switch inOutSwitch;
    private EditText days, month, year, amount, description;
    private String categoryName;

    //DATE ATTRIBUTES
    private Calendar c;
    private int curntYr,curntMnth, curntDays;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        initialize();
    }


    private void initialize()
    {

        //CURRENT DATE ATTRIBUTES
        c=Calendar.getInstance();
        curntYr=c.get(Calendar.YEAR);
        curntMnth=c.get(Calendar.MONTH); curntMnth++;
        curntDays=c.get(Calendar.DAY_OF_MONTH);



        inOutSwitch=(Switch)findViewById(R.id.addSwitch);
        spinner = (Spinner) findViewById(R.id.spinnerCategory);
        days = (EditText) findViewById(R.id.addDay);            days.setText(""+curntDays);
        month = (EditText)findViewById(R.id.addMonth);          month.setText(""+curntMnth);
        year = (EditText) findViewById(R.id.addYear);           year.setText(curntYr+"");
        amount = (EditText)findViewById(R.id.addAmount);
        description = (EditText) findViewById(R.id.addDescription);
        addButton=(Button)findViewById(R.id.addButton);




        String list[] = {"others", "Shopping", "Groceries", "Maintainence", "Rent", "Fun"};

        spinner.setAdapter(new CategoryAdapter(this, R.layout.category_row, list));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoryName = resolveCategories(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {



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
                days.setTextColor(this.getResources().getColor(R.color.primaryText));
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
                    days.setTextColor(this.getResources().getColor(R.color.primaryText));
                }
                else
                {
                    days.setTextColor(this.getResources().getColor(R.color.flowOut));
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
                month.setTextColor(this.getResources().getColor(R.color.primaryText));
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
                    Log.i("YEAR", "2014");
                    month.setTextColor(this.getResources().getColor(R.color.primaryText));
                }
                else {
                    month.setTextColor(this.getResources().getColor(R.color.flowOut));
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
                    year.setTextColor(this.getResources().getColor(R.color.primaryText));
                }
                else
                {
                    year.setTextColor(this.getResources().getColor(R.color.flowOut));
                    flag = false;
                }
            }
            else
                year.setTextColor(this.getResources().getColor(R.color.primaryText));
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


               // Toast.makeText(this, categoryName, Toast.LENGTH_SHORT).show();

                SQLiteAdapter dbAdapter=new SQLiteAdapter(this);
                GetDateAndTime dateAndTime=new GetDateAndTime();

//            Perform Insert operation to database table -one row at a time.
                dbAdapter.insertSingleRowToDatabase(amnt, categoryName, dscrptn, date, in_or_out);



            }
            else
                Toast.makeText(this, "Fill the valid data", Toast.LENGTH_SHORT).show();




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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
