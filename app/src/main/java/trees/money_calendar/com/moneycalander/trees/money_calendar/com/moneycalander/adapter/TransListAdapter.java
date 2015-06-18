package trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

import trees.money_calendar.com.moneycalander.R;
import trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.Class.SingleRow;
import trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.database.SQLiteAdapter;

/**
 * Created by trees on 6/7/15.
 */
public class TransListAdapter extends BaseAdapter{

    /*
    *This Class is a BaseAdapter that takes the data as ArrayList and pumps it into the ListView
     *
      * It contains Basically
      *
      * 1)Constructor
      *
      * 2)getCount() method
      * This method returns total count of the list item
      *
      * 3)getItem()
      * This method returns the current item on the list
      *
      * 4)In this class Id of each item of the list is just their position so we return just the position
      *   but the id are different and unique while implementing databases
      *
      * 5)getView() method
      *  This is the most vital method of this adapter class it inflates the xml view and pumps all the data
      *  to the individual row of a list and returns it to the list
      *
      *
     */


    private Context context;
    private ArrayList<SingleRow> list;
    SQLiteAdapter sqLiteAdapter;

    public TransListAdapter(Context c)
    {

        this.context=c;
        sqLiteAdapter=new SQLiteAdapter(c);
        list=sqLiteAdapter.retrieveAllInsertedRowsFromDatabase();




    }

    @Override
    public int getCount()
    {
        return list.size();
    }

    @Override
    public Object getItem(int position)
    {
        return list.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        View row=convertView;
        ViewHolder holder=null;

        if(row==null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.transaction_row, parent, false);

            holder=new ViewHolder(row);
            row.setTag(holder);

        }
        else
        {
            holder=(ViewHolder)row.getTag();
        }
        SingleRow temp=list.get(position);


        holder.amount.setText(temp.getAmount());
        holder.description.setText(temp.getDescription());
        holder.flow.setText(temp.getFlow());
        holder.date.setText(temp.getDate());
        holder.image.setImageResource(R.drawable.trees);

        if(temp.getFlow().equals("IN"))
        {
            holder.flow.setTextColor(context.getResources().getColor(R.color.flowIn));
            holder.amount.setTextColor(context.getResources().getColor(R.color.flowIn));
        }
        else
        {
            holder.flow.setTextColor(context.getResources().getColor(R.color.flowOut));
            holder.amount.setTextColor(context.getResources().getColor(R.color.flowOut));
        }



        return row;
    }



}




class ViewHolder
{
    ImageView image;
    TextView amount, description, date, flow;

    ViewHolder(View row)
    {
        amount=(TextView)row.findViewById(R.id.textAmount);
        date=(TextView)row.findViewById(R.id.textDate);
        description=(TextView)row.findViewById(R.id.textDescription);
        flow=(TextView)row.findViewById(R.id.textFlow);
        image=(ImageView)row.findViewById(R.id.cat_icon);

    }
}
