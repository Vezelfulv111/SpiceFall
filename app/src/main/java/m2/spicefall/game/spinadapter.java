package m2.spicefall.game;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

public class spinadapter extends ArrayAdapter<String> {

Context context;
String [] Languages;
int[] images;

    public spinadapter(Context context, String[] Languages,int[] images) {
     super(context, R.layout.spinner_language,Languages);
     this.context=context;
     this.Languages=Languages;
     this.images=images;

 }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {


            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row =inflater.inflate(R.layout.spinner_language,null);
            TextView t1 = (TextView)row.findViewById(R.id.Language_text);
            ImageView v1 = (ImageView) row.findViewById(R.id.flag);

            t1.setText(Languages[position]);
            v1.setImageResource(images[position]);


        return row;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View  row =inflater.inflate(R.layout.spinner_language,null);
        TextView t1 = (TextView)row.findViewById(R.id.Language_text);
        ImageView v1 = (ImageView) row.findViewById(R.id.flag);

        t1.setText(Languages[position]);
        v1.setImageResource(images[position]);


        return row;
    }
}
