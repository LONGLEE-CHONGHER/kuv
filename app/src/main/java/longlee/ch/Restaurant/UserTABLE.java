package longlee.ch.Restaurant;

/**
 * Created by LONGLEE_CHONGHER on 25/3/2558.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class UserTABLE {

    private MyOpenHelper objMyOpenHelper;
    private SQLiteDatabase writeSQLite, readSQLite;

    public static final String USER_TABLE = "userTABLE";
    public static final String COLUMN_ID_USER = "_id";
    public static final String COLUMN_USER = "User";
    public static final String COLUMN_PASSWORD = "Password";
    public static final String COLUMN_OFFICER = "Officer";
    public static final String COLUMN_ADDRESS = "Address";
    public static final String COLUMN_TEL = "Tel";

    public UserTABLE(Context context) {

        objMyOpenHelper = new MyOpenHelper(context);
        writeSQLite = objMyOpenHelper.getWritableDatabase();
        readSQLite = objMyOpenHelper.getReadableDatabase();

    }   // Constructor

    //Search User in my Database
    public String[] searchUser(String strUser) {

        try {

            String strResult[] = null;
            Cursor objCursor = readSQLite.query(USER_TABLE,
                    new String[] {COLUMN_ID_USER, COLUMN_USER, COLUMN_PASSWORD, COLUMN_OFFICER}, COLUMN_USER+"=?",
                    new String[] {String.valueOf(strUser)},
                    null, null, null, null );

            if (objCursor != null) {
                if (objCursor.moveToFirst()) {
                    strResult = new String[objCursor.getColumnCount()];
                    strResult[0] = objCursor.getString(0);
                    strResult[1] = objCursor.getString(1);
                    strResult[2] = objCursor.getString(2);
                    strResult[3] = objCursor.getString(3);
                }   // if2
            }   // if1
            objCursor.close();
            return strResult;

        } catch (Exception e) {
            return null;
        }

        //return new String[0];
    }   // searchUser

    //Add Value to userTABLE
    public long addValueToUser(String strUser, String strPassword, String strOfficer, String strAddress, String strTel) {
        ContentValues objContentValues = new ContentValues();
        objContentValues.put(COLUMN_USER, strUser);
        objContentValues.put(COLUMN_PASSWORD, strPassword);
        objContentValues.put(COLUMN_OFFICER, strOfficer);
        objContentValues.put(COLUMN_ADDRESS, strAddress);
        objContentValues.put(COLUMN_TEL, strTel);
        return writeSQLite.insert(USER_TABLE, null, objContentValues);
    }   // End Of addValueToUser
} // End of the Main classs

// My code Error in line 45 By: LONGLEE fix!! 26/3/2015
