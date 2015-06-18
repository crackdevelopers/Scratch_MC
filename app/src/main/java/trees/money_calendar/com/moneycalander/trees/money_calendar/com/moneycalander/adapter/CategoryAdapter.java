package trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import trees.money_calendar.com.moneycalander.R;
import trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.fragments.SpinnerDataClass;
//import trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.SpinnerDataClass;

/**
 * Created by trees on 6/8/15.
 */
public class CategoryAdapter extends ArrayAdapter
{

    Context context;
    ArrayList<SpinnerDataClass> list;

    public CategoryAdapter(Context context, int textViewResourceId, String[] objects)
    {
        super(context, textViewResourceId, objects);
        this.context=context;
        list=new ArrayList<SpinnerDataClass>();


    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent)
    {
        View row=convertView;
        ViewHolderSpinner holder=null;

        if(row==null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.category_row, parent, false);

            holder=new ViewHolderSpinner(row);
            row.setTag(holder);

        }
        else
        {
            holder=(ViewHolderSpinner)row.getTag();
        }
        holder.image.setImageResource(R.drawable.trees);
        holder.category.setText("Fooding");


        return row;
    }

}

class ViewHolderSpinner
{
    ImageView image;
    TextView category;

    ViewHolderSpinner(View row)
    {
        image=(ImageView)row.findViewById(R.id.imageCategory);
        category=(TextView)row.findViewById(R.id.textCategoryName);
    }


}




