package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

import model.MyWish;

/**
 * Created by Hytham on 10/28/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    private final ArrayList<MyWish> wishList = new ArrayList<>();
    public DatabaseHandler(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        String CREATE_TABLE_WISHES = "CREATE TABLE" + Constants.TABLE_NAME
//                + "(" + Constants.KEY_ID + "INTEGER PRIMARY KEY," + Constants.TITLE_NAME + "TEXT," +
//                Constants.CONTENT_NAME + "TEXT," + Constants.DATE_NAME + "LONG);";
//        db.execSQL(CREATE_TABLE_WISHES);

        String CREATE_WISHES_TABLE = "CREATE TABLE " + Constants.TABLE_NAME + "(" + Constants.KEY_ID +
                " INTEGER PRIMARY KEY, " + Constants.TITLE_NAME + " TEXT, " + Constants.CONTENT_NAME + " TEXT, " +
                Constants.DATE_NAME + " LONG); ";
        db.execSQL(CREATE_WISHES_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + Constants.TABLE_NAME);

        onCreate(db);
    }

    //add wishes
    public void addWishes(MyWish wish){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.TITLE_NAME , wish.getTitle());
        contentValues.put(Constants.CONTENT_NAME , wish.getContent());
        contentValues.put(Constants.DATE_NAME , java.lang.System.currentTimeMillis());

        db.insert(Constants.TABLE_NAME , null , contentValues);

       // Log.v("Wish successfully !" , "Yeah...!");
        db.close();

    }
    //get wishes now
    public ArrayList<MyWish> getWishes(){


        String selectQuery = " Select * from " + Constants.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Constants.TABLE_NAME , new String[] { Constants.KEY_ID, Constants.TITLE_NAME ,
                Constants.CONTENT_NAME , Constants.DATE_NAME } ,null ,null,null,null,Constants.DATE_NAME + " DESC" );
        if (cursor.moveToFirst()){
            do {
                MyWish wish = new MyWish();
                wish.setTitle(cursor.getString(cursor.getColumnIndex(Constants.TITLE_NAME)));
                wish.setContent(cursor.getString(cursor.getColumnIndex(Constants.CONTENT_NAME)));
                wish.setItemId(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ID)));

                java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
                String dataData = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.DATE_NAME))).getTime());

                wish.setRecordDate(dataData);
                wishList.add(wish);

            } while (cursor.moveToNext());
        }

        return wishList;
    }

    // delete row from database

    public void delete(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.TABLE_NAME, Constants.KEY_ID + " = ? ", new String[]{
                String.valueOf(id)

        });

    }
}
