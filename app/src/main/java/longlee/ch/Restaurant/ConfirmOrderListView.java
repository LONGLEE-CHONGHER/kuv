package longlee.ch.Restaurant;
/**
 * Created by LONGLEE_CHONGHER on 4/3/2558.
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;


public class ConfirmOrderListView extends ActionBarActivity {

    private TextView txtShowOfficer, txtShowDesk;
    private String strOfficer, strDesk;
    private ConfirmAdapter objConfirmAdapter;
    private OrderTABLE objOrderTABLE;
    private String[] strListFood, strListAmount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order_list_view);


        //Bind widget
        bindWidget();

        //Setup TextView
        setupTextView();

        //Create List View
        createListView();

    }
    public void clickAddOrder(View view) {

        backToOrder();

    }

    private void backToOrder() {
        Intent objIntent = new Intent(ConfirmOrderListView.this, OrderActivity.class);
        objIntent.putExtra("Officer", strOfficer);
        startActivity(objIntent);
        finish();
    }

    public void clickUploadOrder(View view) {

        //Setup New Policy
        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy myPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(myPolicy);
        }

        for (int i = 0; i < strListFood.length; i++) {

            //Up Value to mySQL
            upValueToMySQL(i);

        }   // for

        Toast.makeText(ConfirmOrderListView.this, "Finish update your order to us. Please wait", Toast.LENGTH_SHORT).show();

        clearAlldata();

        backToOrder();

    }   // clickUploadOrder to server

    private void clearAlldata() {
        SQLiteDatabase objSQLite = openOrCreateDatabase("restaurant.db", MODE_PRIVATE, null);
        objSQLite.delete("orderTABLE", null, null);
    }

    private void upValueToMySQL(int i) {

        try {

            ArrayList<NameValuePair> objNameValuePairs = new ArrayList<NameValuePair>();
            objNameValuePairs.add(new BasicNameValuePair("isAdd", "true"));
            objNameValuePairs.add(new BasicNameValuePair("Officer", strOfficer));
            objNameValuePairs.add(new BasicNameValuePair("Desk", strDesk));
            objNameValuePairs.add(new BasicNameValuePair("Food", strListFood[i]));
            objNameValuePairs.add(new BasicNameValuePair("Amount", strListAmount[i]));

            HttpClient objHttpClient = new DefaultHttpClient();
            HttpPost objHttpPost = new HttpPost("http://chongher.net84.net/get_data_officer.php");
            objHttpPost.setEntity(new UrlEncodedFormEntity(objNameValuePairs, "UTF-8"));
            objHttpClient.execute(objHttpPost);


        } catch (Exception e) {
            Log.d("Restaurant", "Cannot Up your order " + e.toString());
        }

    }   // upValue

    private void createListView() {

        objOrderTABLE = new OrderTABLE(this);
        strListFood = objOrderTABLE.listFood();
        strListAmount = objOrderTABLE.listAmount();

        objConfirmAdapter = new ConfirmAdapter(ConfirmOrderListView.this, strListFood, strListAmount);
        ListView confirmListView = (ListView) findViewById(R.id.listView2);
        confirmListView.setAdapter(objConfirmAdapter);

        //Set on Click
        confirmListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                alertConfirm(position);
            }
        });


    }   // createListView

    private void alertConfirm(final int intPositionDelete) {

        AlertDialog.Builder objBuilder = new AlertDialog.Builder(this);
        objBuilder.setIcon(R.drawable.edit);
        objBuilder.setTitle("What do you want to do?");
        objBuilder.setMessage("You can edit or delete your order");
        objBuilder.setCancelable(false);
        objBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteOrder(intPositionDelete);
                dialog.dismiss();
            }
        });
        objBuilder.setNeutralButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteOrder(intPositionDelete);
                backToOrder();
                dialog.dismiss();
            }
        });
        objBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        objBuilder.show();

    }   // alertConfirm

    private void deleteOrder(int intDelete) {

        SQLiteDatabase objSqLiteDatabase = openOrCreateDatabase("restaurant.db", MODE_PRIVATE, null);
        Cursor objCursor = objSqLiteDatabase.rawQuery("SELECT * FROM orderTABLE", null);
        objSqLiteDatabase.delete("orderTABLE", "_id" + "=" + intDelete + 1, null);

        Log.d("Restaurant", "id==>" + Integer.toString(intDelete));

        createListView();

    }   // deleteOrder

    private void setupTextView() {
        strOfficer = getIntent().getExtras().getString("Officer");
        strDesk = getIntent().getExtras().getString("Desk");

        Log.d("Restaurant", "Desk ======>" + strDesk);

        txtShowOfficer.setText(strOfficer);
        txtShowDesk.setText(strDesk);
    }   // setupTextView

    private void bindWidget() {
        txtShowOfficer = (TextView) findViewById(R.id.txtShowOfficerConfirm);
        txtShowDesk = (TextView) findViewById(R.id.txtShowDesk);
    }   // bindWidget





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_confirm_order_list_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
