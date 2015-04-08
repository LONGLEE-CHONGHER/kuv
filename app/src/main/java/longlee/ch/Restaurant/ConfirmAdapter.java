package longlee.ch.Restaurant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by LONGLEE_CHONGHER on 4/3/2558.
 */

public class ConfirmAdapter extends BaseAdapter {


    private Context objContext;
    private String[] strMyFood, strMyAmount;

    public ConfirmAdapter(Context context, String[] strFood, String[] strAmount) {

        this.objContext = context;
        this.strMyFood = strFood;
        this.strMyAmount = strAmount;

    }   // Constrictor

    @Override
    public int getCount() {
        return strMyFood.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater objLayoutInflater = (LayoutInflater) objContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = objLayoutInflater.inflate(R.layout.list_confirm_order, parent, false);
        //For Show Food
        TextView txtShowFood = (TextView) view.findViewById(R.id.txtListFoodConfirm);
        txtShowFood.setText(strMyFood[position]);

        //For Amount
        TextView txtShowAmout = (TextView) view.findViewById(R.id.txtListAmountConfirm);
        txtShowAmout.setText(strMyAmount[position]);

        return view;
    }

}
