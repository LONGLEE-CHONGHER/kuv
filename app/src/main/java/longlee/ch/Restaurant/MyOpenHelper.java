package longlee.ch.Restaurant;

/**
 * Created by LONGLEE_CHONGHER on 4/3/2558.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenHelper extends SQLiteOpenHelper{


    // My Database name is "restaurant.db"
    // Three TABLE

    private static final String DATABASE_NAME = "restaurant.db";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_USER_TABLE = "create table userTABLE (_id integer primary key, " + " User text, Password text, Officer text, Address text, Tel text);";
    private static final String CREATE_FOOD_TABLE = "create table foodTABLE (_id integer primary key, "+" Food text, Price text);";
    private static final String CREATE_ORDER_TABLE = "create table orderTABLE (_id integer primary key, "+" OfficerOrder text, Desk text, NameFood text, Amount text);";

    public MyOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }   // Constructor

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_FOOD_TABLE);
        db.execSQL(CREATE_ORDER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}   // End of Main Class
