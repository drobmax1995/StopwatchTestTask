package by.drobmax.stopwatchtesttask.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Admin on 21.08.2015.
 */
public class DataBaseSqlHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "times_data_base";
    private static int VERSION_ID = 1;

    public DataBaseSqlHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, null, version);
    }

    public DataBaseSqlHelper(Context context){
        super(context,DB_NAME,null,VERSION_ID);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        TableTimeList.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion>oldVersion){
            TableTimeList.onUpgrade(db);
        }
        onCreate(db);
    }
}