package longlee.ch.Restaurant;

/**
 * Created by LONGLEE_CHONGHER on 4/4/2558.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class FoodTABLE {

    private MyOpenHelper objMyOpenHelper;
    private SQLiteDatabase writeSQLite, readSQLite;

    public static final String FOOD_TABLE = "foodTABLE";
    public static final String COLUMN_ID_FOOD = "_id";
    public static final String COLUMN_FOOD = "Food";
    public static final String COLUMN_PRICE = "Price";

    public FoodTABLE(Context context) {

        objMyOpenHelper = new MyOpenHelper(context);
        writeSQLite = objMyOpenHelper.getWritableDatabase();
        readSQLite = objMyOpenHelper.getReadableDatabase();

    }   // Constructor

    public String[] listPrice() {

        String strListPrice[] = null;
        Cursor objCursor = readSQLite.query(FOOD_TABLE, new String[]{COLUMN_ID_FOOD, COLUMN_PRICE}, null, null, null, null, null);
        objCursor.moveToFirst();
        strListPrice = new String[objCursor.getCount()];
        for (int i = 0; i < objCursor.getCount(); i++) {
            strListPrice[i] = objCursor.getString(objCursor.getColumnIndex(COLUMN_PRICE));
            objCursor.moveToNext();
        }
        objCursor.close();
        return strListPrice;
    }   // listPrice

    public String[] listMenu() {

        String strListMenu[] = null;
        Cursor objCursor = readSQLite.query(FOOD_TABLE, new String[]{COLUMN_ID_FOOD, COLUMN_FOOD}, null, null, null, null, null);
        objCursor.moveToFirst();
        strListMenu = new String[objCursor.getCount()];
        for (int i = 0; i < objCursor.getCount(); i++) {
            strListMenu[i] = objCursor.getString(objCursor.getColumnIndex(COLUMN_FOOD));
            objCursor.moveToNext();
        }   // for
        objCursor.close();
        return strListMenu;
    }   // listMenu



    public long addValueToFood(String strFood, String strPrice) {
        ContentValues objContentValue = new ContentValues();
        objContentValue.put(COLUMN_FOOD, strFood);
        objContentValue.put(COLUMN_PRICE, strPrice);
        return writeSQLite.insert(FOOD_TABLE, null, objContentValue);
    }   // addValueToFood

}   // End of Main Class

// Longleee !!!! Update new food list Don't forgot