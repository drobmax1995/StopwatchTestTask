package by.drobmax.stopwatchtesttask.database;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import by.drobmax.stopwatchtesttask.models.TimeListModel;

/**
 * Created by Admin on 21.08.2015.
 */
public class TimeListDataProvider {
    private ContentResolver mContentResolver;

    public TimeListDataProvider(Context context) {
        mContentResolver = context.getContentResolver();
    }

    public long saveTimes(String content) {
        ContentValues cv = TableTimeList.createContentValues(content);
        return saveValues(cv, TableTimeList.CONTENT_URI);
    }

    public synchronized void getTimes(final TimesRequestCallbacks callback) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                final String request = "SELECT * FROM " + TableTimeList.TABLE_NAME;
                Cursor cursor = mContentResolver.query(TimeListContentProvider.BASE_CONTENT_URI,
                        null, request, null, null);
                List<TimeListModel> timeListModels = new ArrayList<>();
                if (cursor != null) {
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        do {
                            String content = getString(cursor, TableTimeList.CONTENT);
                            long id = getLong(cursor, TableTimeList.ID);
                            TimeListModel model = new TimeListModel(id, content);
                            timeListModels.add(model);
                        } while (cursor.moveToNext());
                    }
                    cursor.close();
                }
                callback.onLocksLoaded((ArrayList<TimeListModel>) timeListModels);
            }
        });
        thread.start();
    }


    public synchronized void deleteTimes(final TimeListModel model) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                mContentResolver.delete(TableTimeList.CONTENT_URI,
                        TableTimeList.ID + " = '" + model.getId() + "'", null);
            }
        });
        thread.start();
    }

    public interface TimesRequestCallbacks {
        void onLocksLoaded(ArrayList<TimeListModel> times);
    }

    private long saveValues(ContentValues cv, Uri uri) {
        Uri paymentUri = mContentResolver.insert(uri, cv);
        return ContentUris.parseId(paymentUri);
    }

    private long getLong(Cursor cursor, String columnName) {
        int columnIndex = cursor.getColumnIndex(columnName);
        return columnIndex == -1 ? -1 : cursor.getLong(columnIndex);
    }

    private String getString(Cursor cursor, String columnName) {
        int columnIndex = cursor.getColumnIndex(columnName);
        return columnIndex == -1 ? "" : cursor.getString(columnIndex);
    }

}
