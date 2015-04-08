package longlee.ch.Restaurant;

/**
 * Created by LONGLEE_CHONGHER on 4/3/2558.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter{

    //Explicit
    private Context objContext;
    private String[] strShowMenu, strShowPrice;
    private int[] intImageMenu;

    public MyAdapter(Context context, String[] strMenu, String[] strPrice, int[] intMenu) {

        this.objContext = context;
        this.strShowMenu = strMenu;
        this.strShowPrice = strPrice;
        this.intImageMenu = intMenu;

    }   // Constructor

    @Override
    public int getCount() {
        return strShowMenu.length;
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
        View view = objLayoutInflater.inflate(R.layout.list_view_rom, parent, false);

        //List Menu
        TextView ListMenu = (TextView) view.findViewById(R.id.txtListMenu);
        ListMenu.setText(strShowMenu[position]);

        //List Price
        TextView ListPrice = (TextView) view.findViewById(R.id.txtListPrice);
        ListPrice.setText(strShowPrice[position]);

        //List Image
        ImageView ListImageMenu = (ImageView) view.findViewById(R.id.imvMenu);
        ListImageMenu.setBackgroundResource(intImageMenu[position]);

        return view;
    }
}   // Main Class
