package trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import trees.money_calendar.com.moneycalander.R;
import trees.money_calendar.com.moneycalander.trees.money_calendar.com.moneycalander.Class.SingleRow;


/**set
 * Created by root on 06/06/15.
 */

public class SQLiteAdapter
{
    SQLiteHelper sqLiteHelper;
    private Context context;
    
   public SQLiteAdapter(Context context)
    {
        this.context=context;
        sqLiteHelper=new SQLiteHelper(context);
    }

    public long insertSingleRowToDatabase(String amount,String category,String description,String date,int in_or_out )
    {
        SQLiteDatabase db=sqLiteHelper.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put(SQLiteHelper.DATE_TIME,date);
        contentValues.put(SQLiteHelper.CATEGORY,category);
        contentValues.put(SQLiteHelper.AMOUNT,amount);
        contentValues.put(SQLiteHelper.DESCRIPTION,description);
        contentValues.put(SQLiteHelper.IN_OR_OUT,in_or_out);

        long id=db.insert(SQLiteHelper.TABLE_NAME,null,contentValues);
        db.close();

        return id;
    }

    public ArrayList<SingleRow> retrieveAllInsertedRowsFromDatabase()
    {
        SQLiteDatabase db=sqLiteHelper.getWritableDatabase();

//     select  Date, Category, Amount, Description from Table -> Incoming.
        String[] allColumnsFromDatabase_tableIncloming={SQLiteHelper.UID,SQLiteHelper.DATE_TIME,SQLiteHelper.CATEGORY,SQLiteHelper.AMOUNT,SQLiteHelper.DESCRIPTION,SQLiteHelper.IN_OR_OUT};
        Cursor cursor=db.query(SQLiteHelper.TABLE_NAME,allColumnsFromDatabase_tableIncloming,null,null,null,null,null);


        ///////////////////////////////
        String uid,date,category,amount,description,inOut="OUT";
        int columnIndex_uid, columnIndex_date, columnIndex_category, columnIndex_Amount, columnIndex_Description, columnIndex_INOUT;
        int in_or_out;
        ArrayList<SingleRow> list=new ArrayList<SingleRow>();
        String test="";

        while(cursor.moveToNext())
        {
//            fetching index of corresponding table table attributes.
            columnIndex_uid=cursor.getColumnIndex(sqLiteHelper.UID); //  gives index of UID i.e =0;
            columnIndex_date=cursor.getColumnIndex(sqLiteHelper.DATE_TIME);
            columnIndex_category=cursor.getColumnIndex(sqLiteHelper.CATEGORY);
            columnIndex_Amount=cursor.getColumnIndex(sqLiteHelper.AMOUNT);
            columnIndex_Description=cursor.getColumnIndex(sqLiteHelper.DESCRIPTION);
            columnIndex_INOUT=cursor.getColumnIndex(sqLiteHelper.IN_OR_OUT);

//            Retrieving values associated to the corresponding columnIndex of database table -one row at a time.
            uid=cursor.getString(columnIndex_uid);
            date=cursor.getString(columnIndex_date);
            category=cursor.getString(columnIndex_category);
            amount=cursor.getString(columnIndex_Amount);
            description=cursor.getString(columnIndex_Description);
            in_or_out=cursor.getInt(columnIndex_INOUT);

            //Message.message_longTime(context, description+" "+in_or_out+"\n");
            if(in_or_out==1)
                inOut="IN";
            else
                inOut="OUT";

            //LIST ADDING
            list.add(new SingleRow(R.drawable.trees, amount, date, description, inOut));


        }


        db.close();
        cursor.close();

        return list;
    }

    static class SQLiteHelper extends SQLiteOpenHelper
    {

        private Context context;

        //      Database attributes.
        private static final String DATABASE_NAME = "money_calendar";
        private static final int DATABASE_VERSION = 2;

        //    Table attributes.
        private static final String TABLE_NAME = "Incoming";
        private static final String UID = "_id";
        private static final String DESCRIPTION = "Description";
        private static final String CATEGORY = "Category";
        private static final String DATE_TIME = "Date";
        private static final String AMOUNT = "Amount";
        private static final String IN_OR_OUT="in_or_out";

        //    create table money_calendar (_id primary key autoincrement,date_time,amount,description ,category)
        private static final String CREATE_TABLE = "create table if not exists " + TABLE_NAME + " (" + UID + " integer primary key autoincrement," +
                "" + DATE_TIME + " date not null," + AMOUNT + " text not null," + DESCRIPTION + " varchar(60)," + CATEGORY + " text not null, "+IN_OR_OUT+" integer);";

        //    drop table if exists money_calendar
        private static final String DROP_TABLE = "drop table if exists " + TABLE_NAME + " ";

        public SQLiteHelper(Context context)
        {

            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {

// We need to check whether table that we are going to create already exists.
            //Because this method gets executed every time we create an object of this class.
            db.execSQL(CREATE_TABLE);
            Message.message(context, "onCreate() -> database has been called.");

            // Log.d("onCreate()", "database has been created!");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int old_version, int new_version)
        {
            db.execSQL(DROP_TABLE);
            onCreate(db);
            Message.message(context,"onUpgrade() -> database has been called.");
            //Log.d("onUpgrade()", "database has been deleted and is re-created!!");
        }
    }
}