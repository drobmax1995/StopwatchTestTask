package by.drobmax.stopwatchtesttask.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import by.drobmax.stopwatchtesttask.models.TimeListModel;

/**
 * Created by Admin on 21.08.2015.
 */
public class TableTimeList {

    public static final String TABLE_NAME = "TableTimes";
    public static final String ID = "id";
    public static final String CONTENT = "content";
    public static final Uri CONTENT_URI = TimeListContentProvider.BASE_CONTENT_URI.buildUpon().
            appendPath(TABLE_NAME).build();

    public static final String CREATE_TABLE_REQUEST =
            "create table " + TABLE_NAME + " (" +
                    ID + " integer primary key autoincrement, " +
                    CONTENT + " text);";

    public static void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_REQUEST);
    }

    public static void onUpgrade(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_NAME);
    }

    public static ContentValues createContentValues(String content) {
        ContentValues cv = new ContentValues();
        cv.put(CONTENT, content);
        return cv;
    }
}
