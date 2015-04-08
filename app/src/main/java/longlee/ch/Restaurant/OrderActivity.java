package longlee.ch.Restaurant;
/**
 * Created by LONGLEE_CHONGHER on 4/3/2558.
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


public class OrderActivity extends ActionBarActivity {

    private FoodTABLE objFoodTABLE;
    private TextView txtShowOffecer;
    private EditText edtTable;
    private ListView myListview;
    private String strMyOfficer, strNumberTable, strFood, strAmount;
    private String[] strMenu, strPrice;
    private OrderTABLE objOrderTABLE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        objOrderTABLE = new OrderTABLE(this);



        bindWidtet();

        objFoodTABLE = new FoodTABLE(this);

        synJSONtoFoodTABLE();

        createListView();

        showOfficer();

        activeClickOnListView();

    }   // onCreate

    private void checkZero() {
        strNumberTable = edtTable.getText().toString().trim();
        if (strNumberTable.equals("")) {
            MyAlertDialog objMyAlertDialog = new MyAlertDialog();
            objMyAlertDialog.alertDialog(OrderActivity.this, "TABLE NUMBER", "Please insert your table number");
        }
    }

    private void activeClickOnListView() {

        myListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                strNumberTable = edtTable.getText().toString().trim();
                if (strNumberTable.equals("")) {
                    MyAlertDialog objMyAlertDialog = new MyAlertDialog();
                    objMyAlertDialog.alertDialog(OrderActivity.this, "TABLE NUMBER", "Please insert your table number");
                } else {
                    alertAmountOrder();
                    strFood = strMenu[position];
                }   // if



            }   // event
        });

    }   // activeClickOnListView

    private void alertAmountOrder() {

        CharSequence[] charAmount = {"1", "2", "3", "4", "5"};

        AlertDialog.Builder objBuilder = new AlertDialog.Builder(this);
        objBuilder.setIcon(R.drawable.amount);
        objBuilder.setTitle("Amount of your Order");
        objBuilder.setSingleChoiceItems(charAmount, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case 0:
                        strAmount = "1";
                        break;
                    case 1:
                        strAmount = "2";
                        break;
                    case 2:
                        strAmount = "3";
                        break;
                    case 3:
                        strAmount = "4";
                        break;
                    case 4:
                        strAmount = "5";
                        break;
                }   // switch

                showLog();

                //Update Value to SQLite
                updateOrderToOrderTABLE();

                dialog.dismiss();
            }   //event
        });
        objBuilder.show();

    }   // End of my alertAmountOrder

    private void updateOrderToOrderTABLE() {

        long addValue = objOrderTABLE.addValueOrder(strMyOfficer, strNumberTable, strFood, strAmount);

    }   // update

    private void showLog() {

        Log.d("Restaurant", "Officer = " + strMyOfficer);
        Log.d("Restaurant", "Table = " + strNumberTable);
        Log.d("Restaurant", "Food = " + strFood);
        Log.d("Restaurant", "Amount = " + strAmount);

    }   // shwoLog


    private void showOfficer() {

        strMyOfficer = getIntent().getExtras().getString("Officer");
        txtShowOffecer.setText(strMyOfficer);

    }   // showOfficer

    private void bindWidtet() {
        txtShowOffecer = (TextView) findViewById(R.id.txtShowOffecer);
        edtTable = (EditText) findViewById(R.id.edtTable);
        myListview = (ListView) findViewById(R.id.listView);
    }   // bindWidget

    private void createListView() {

        strMenu = objFoodTABLE.listMenu();
        strPrice = objFoodTABLE.listPrice();

        int[] intImageMenu = {R.drawable.food1, R.drawable.food2, R.drawable.food3, R.drawable.food4, R.drawable.food5,
                R.drawable.food6, R.drawable.food7, R.drawable.food8, R.drawable.food9, R.drawable.food10,
                R.drawable.food11, R.drawable.food12, R.drawable.food13, R.drawable.food14, R.drawable.food15,
                R.drawable.food16, R.drawable.food17, R.drawable.food18, R.drawable.food19, R.drawable.food20,
                R.drawable.food21, R.drawable.food22, R.drawable.food23, R.drawable.food24, R.drawable.food25,
                R.drawable.food26, R.drawable.food27, R.drawable.food28, R.drawable.food29, R.drawable.food30,
                R.drawable.food31, R.drawable.food32, R.drawable.food33, R.drawable.food34, R.drawable.food35,
                R.drawable.food36, R.drawable.food37, R.drawable.food38, R.drawable.food39, R.drawable.food40,
                R.drawable.food41, R.drawable.food42, R.drawable.food43, R.drawable.food44, R.drawable.food45,
                R.drawable.food46, R.drawable.food47, R.drawable.food48, R.drawable.food49, R.drawable.food50 };

        MyAdapter objMyAdapter = new MyAdapter(OrderActivity.this, strMenu, strPrice, intImageMenu);
        myListview.setAdapter(objMyAdapter);

    }   // createListView

    private void synJSONtoFoodTABLE() {

        //Change Policy
        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy myPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(myPolicy);
        }   // if


        InputStream objInputStream = null;
        String strJSON = "";

        //Create InputStream
        try {

            HttpClient objHttplient = new DefaultHttpClient();
            HttpPost objHttpPost = new HttpPost("http://chongher.net84.net/get_data_food.php");
            HttpResponse objHttpResponse = objHttplient.execute(objHttpPost);
            HttpEntity objHttpEntity = objHttpResponse.getEntity();
            objInputStream = objHttpEntity.getContent();

        } catch (Exception e) {
            Log.d("Restaurant", "InputStream ==> " + e.toString());
        }


        //Create strJSON
        try {

            BufferedReader objBufferedReader = new BufferedReader(new InputStreamReader(objInputStream, "UTF-8"));
            StringBuilder objStringBuilder = new StringBuilder();
            String strLine = null;

            while ((strLine = objBufferedReader.readLine()) != null) {
                objStringBuilder.append(strLine);
            }   // while

            objInputStream.close();
            strJSON = objStringBuilder.toString();

        } catch (Exception e) {
            Log.d("Restaurant", "strJSON ==> " + e.toString());
        }


        //Update userTABLE
        try {

            final JSONArray objJSONArray = new JSONArray(strJSON);
            for (int i = 0; i < objJSONArray.length(); i++) {

                JSONObject objJSONObject = objJSONArray.getJSONObject(i);
                String strFood = objJSONObject.getString("Food");
                String strPrice = objJSONObject.getString("Price");
                long addValue = objFoodTABLE.addValueToFood(strFood, strPrice);


            }   // for

        } catch (Exception e) {
            Log.d("Restaurant", "Update SQLite ==> " + e.toString());
        }

    }   // synJSONtoFoodTABLE



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_order, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.itemConfirm:
                Intent objIntent = new Intent(OrderActivity.this, ConfirmOrderListView.class);
                objIntent.putExtra("Officer", strMyOfficer);
                objIntent.putExtra("Desk", strNumberTable);
                startActivity(objIntent);
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
