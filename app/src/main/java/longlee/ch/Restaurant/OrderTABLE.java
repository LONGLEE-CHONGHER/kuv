package longlee.ch.Restaurant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by LONGLEE_CHONGHER on 25/3/2558. AD
 */
public class OrderTABLE {

    // My declare

    private MyOpenHelper objMyOpenHelper;
    private SQLiteDatabase writeSQLite, readSQLite;

    public static final String ORDER_TABLE = "orderTABLE";
    public static final String COLUMN_ID_ORDER = "_id";
    public static final String COLUMN_OFFICER_ORDER = "OfficerOrder";
    public static final String COLUMN_DESK = "Desk";
    public static final String COLUMN_NAME_FOOD = "NameFood";
    public static final String COLUMN_AMOUNT = "Amount";

    public OrderTABLE(Context context) {

        objMyOpenHelper = new MyOpenHelper(context);
        writeSQLite = objMyOpenHelper.getWritableDatabase();
        readSQLite = objMyOpenHelper.getReadableDatabase();

    }   // Constructor

    public String[] listAmount() {

        String strListAmount[] = null;
        Cursor objCursor = readSQLite.query(ORDER_TABLE, new String[]{COLUMN_ID_ORDER, COLUMN_AMOUNT}, null, null, null, null, null);
        objCursor.moveToFirst();
        strListAmount = new String[objCursor.getCount()];
        for (int i = 0; i < objCursor.getCount(); i++) {
            strListAmount[i] = objCursor.getString(objCursor.getColumnIndex(COLUMN_AMOUNT));
            objCursor.moveToNext();
        }
        objCursor.close();
        return strListAmount;
    }

    public String[] listFood() {

        String strListFood[] = null;
        Cursor objCursor = readSQLite.query(ORDER_TABLE, new String[]{COLUMN_ID_ORDER, COLUMN_NAME_FOOD}, null, null, null, null, null);
        objCursor.moveToFirst();
        strListFood = new String[objCursor.getCount()];
        for (int i = 0; i < objCursor.getCount(); i++) {
            strListFood[i] = objCursor.getString(objCursor.getColumnIndex(COLUMN_NAME_FOOD));
            objCursor.moveToNext();
        }
        objCursor.close();
        return strListFood;
    }

    public long addValueOrder(String strOfficer, String strDesk, String strFood, String strAmount) {

        ContentValues objContentValues = new ContentValues();
        objContentValues.put(COLUMN_OFFICER_ORDER, strOfficer);
        objContentValues.put(COLUMN_DESK, strDesk);
        objContentValues.put(COLUMN_NAME_FOOD, strFood);
        objContentValues.put(COLUMN_AMOUNT, strAmount);

        return writeSQLite.insert(ORDER_TABLE, null, objContentValues);
    }   // addValue

}
