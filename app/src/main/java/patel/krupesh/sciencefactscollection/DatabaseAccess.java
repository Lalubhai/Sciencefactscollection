package patel.krupesh.sciencefactscollection;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.readystatesoftware.android.sqliteassethelper.BuildConfig;

import java.util.ArrayList;

public class DatabaseAccess {
    private static DatabaseAccess instance;
    private SQLiteDatabase database;
    private SQLiteOpenHelper openHelper;

    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public void open() {
        this.database = this.openHelper.getWritableDatabase();
    }

    public void close() {
        if (this.database != null) {
            this.database.close();
        }
    }

    public ArrayList<Message> getAllMessage() {
        ArrayList<Message> messages = new ArrayList();
        Cursor cursor = this.database.query(DatabaseOpenHelper.tbl_name, null, null, null, null, null, null);
        if (cursor == null) {
            Log.d(Setting.TAG, "cursor is null");
        } else {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(0);
                String data = cursor.getString(1);
                if (data != null) {
                    messages.add(new Message(id, data, cursor.getInt(2), cursor.getInt(3)));
                }
                cursor.moveToNext();
            }
        }
        return messages;
    }

    public ArrayList<Message> getMessageFavorite() {
        ArrayList<Message> messages = new ArrayList();
        Cursor cursor = this.database.query(DatabaseOpenHelper.tbl_name, null, "smsFavorite=?", new String[]{"1"}, null, null, null);
        if (cursor == null) {
            Log.d(Setting.TAG, "cursor is null");
        } else {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(0);
                String data = cursor.getString(1);
                if (data != null) {
                    messages.add(new Message(id, data, cursor.getInt(2), cursor.getInt(3)));
                }
                cursor.moveToNext();
            }
        }
        return messages;
    }

    public int changeFavorite(int smsId, int new_favorite) {
        ContentValues value = new ContentValues();
        value.put("smsFavorite", Integer.valueOf(new_favorite));
        int result = this.database.update(DatabaseOpenHelper.tbl_name, value, "msmId=?", new String[]{smsId + BuildConfig.FLAVOR});
        if (result == 0) {
            Log.e(Setting.TAG, "update favorite fail");
        } else {
            Log.d(Setting.TAG, "update favorite success");
        }
        return result;
    }

    public ArrayList<Message> getMessagesForEachType(int topic_position) {
        int type = topic_position + 1;
        ArrayList<Message> messages = new ArrayList();
        Cursor cursor = this.database.query(DatabaseOpenHelper.tbl_name, null, "type=?", new String[]{type + BuildConfig.FLAVOR}, null, null, null);
        if (cursor == null) {
            Log.d(Setting.TAG, "cursor is null");
        } else {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(0);
                String data = cursor.getString(1);
                if (data != null) {
                    messages.add(new Message(id, data, cursor.getInt(2), cursor.getInt(3)));
                }
                cursor.moveToNext();
            }
        }
        return messages;
    }



}
