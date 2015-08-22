package by.drobmax.stopwatchtesttask.database;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import java.util.ArrayList;

/**
 * Created by Admin on 21.08.2015.
 */
public class TimeListContentProvider extends ContentProvider{

    public static final String AUTHORITY = "by.drobmax.stopwatchtesttask.database";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    private DataBaseSqlHelper mOpenHelper;

    @Override
    public boolean onCreate() {
        mOpenHelper = new DataBaseSqlHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        return db.rawQuery(selection, null);
    }

    @Override
    public String getType(Uri uri) {
        return TableTimeList.TABLE_NAME;
    }

    @Override
    public synchronized ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> operations)
            throws OperationApplicationException {
        ContentProviderResult[] contentProviderResults = super.applyBatch(operations);
        return contentProviderResults;
    }

    @Override
    public synchronized Uri insert(Uri uri, ContentValues contentValues) {
        final String tableName = TableTimeList.TABLE_NAME;
        if (!tableName.isEmpty() && contentValues != null) {
            final SQLiteDatabase sqlDB = mOpenHelper.getWritableDatabase();
            final long id = sqlDB.insert(tableName, null, contentValues);

            final Uri contentUri = ContentUris.withAppendedId(uri, id);
            getContext().getContentResolver().notifyChange(contentUri, null);
            return contentUri;
        } else {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int retVal = db.delete(TableTimeList.TABLE_NAME, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return retVal;
    }
    @Override
    public int update(Uri uri, ContentValues contentValues, String whereClause, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final String tableName = TableTimeList.TABLE_NAME;
        return db.update(tableName, contentValues, whereClause, selectionArgs);
    }

}
