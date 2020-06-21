package patel.krupesh.sciencefactscollection;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Lalu on 2/5/2018.
 */

public class MyAdapter_dataList extends ArrayAdapter<String>{

    Context context;
    int layoutResourceId;
    ArrayList<String> list_all = null;


    static class CommonHolder {
        TextView txtTitle;
        TextView txtposition;
        TextView txtview;

        CommonHolder() {
        }
    }

    public MyAdapter_dataList(Context context, int layoutResourceId, ArrayList<String> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.list_all = data;
    }



    @SuppressLint("PrivateResource")
    public View getView(int position, View convertView, ViewGroup parent) {
        CommonHolder holder;
        View row = convertView;
        if (row == null) {
            row = LayoutInflater.from(this.context).inflate(this.layoutResourceId, parent, false);
            holder = new CommonHolder();
            holder.txtTitle = (TextView) row.findViewById(R.id.title_lv_item);
            holder.txtposition = (TextView) row.findViewById(R.id.position);
            holder.txtview = (TextView) row.findViewById(R.id.view);
            row.setTag(holder);
        } else {
            holder = (CommonHolder) row.getTag();
        }
        Typeface face = Typeface.createFromAsset(this.context.getAssets(), "fonts/ClearSans-Regular.ttf");
        holder.txtTitle.setTypeface(face);
        holder.txtposition.setTypeface(face);
        holder.txtTitle.setText(Html.fromHtml((String) this.list_all.get(position)));
        holder.txtposition.setText((position + 1) + BuildConfig.FLAVOR);
        switch (position % 17) {
            case R.styleable.View_android_theme /*0*/:
                holder.txtview.setBackgroundColor(this.context.getResources().getColor(R.color.color0));
                break;


            //Large Changes
          /* case R.styleable.View_android_focusable /*1*/ //:
            //   holder.txtview.setBackgroundColor(this.context.getResources().getColor(R.color.color1));
            /*    break; */


            case 2 /*2*/:
                holder.txtview.setBackgroundColor(this.context.getResources().getColor(R.color.color2));
                break;
            case 3 /*3*/:
                holder.txtview.setBackgroundColor(this.context.getResources().getColor(R.color.color3));
                break;
            case R.styleable.View_theme /*4*/:
                holder.txtview.setBackgroundColor(this.context.getResources().getColor(R.color.color4));
                break;
            case R.styleable.Toolbar_contentInsetStart /*5*/:
                holder.txtview.setBackgroundColor(this.context.getResources().getColor(R.color.color5));
                break;
            case R.styleable.Toolbar_contentInsetEnd /*6*/:
                holder.txtview.setBackgroundColor(this.context.getResources().getColor(R.color.color6));
                break;
            case R.styleable.Toolbar_contentInsetLeft /*7*/:
                holder.txtview.setBackgroundColor(this.context.getResources().getColor(R.color.color7));
                break;
            case R.styleable.Toolbar_contentInsetRight /*8*/:
                holder.txtview.setBackgroundColor(this.context.getResources().getColor(R.color.color8));
                break;
            case R.styleable.Toolbar_contentInsetStartWithNavigation /*9*/:
                holder.txtview.setBackgroundColor(this.context.getResources().getColor(R.color.color9));
                break;
            case R.styleable.Toolbar_contentInsetEndWithActions /*10*/:
                holder.txtview.setBackgroundColor(this.context.getResources().getColor(R.color.color10));
                break;
            case R.styleable.Toolbar_popupTheme /*11*/:
                holder.txtview.setBackgroundColor(this.context.getResources().getColor(R.color.color11));
                break;
            case R.styleable.Toolbar_titleTextAppearance /*12*/:
                holder.txtview.setBackgroundColor(this.context.getResources().getColor(R.color.color12));
                break;
            case R.styleable.Toolbar_subtitleTextAppearance /*13*/:
                holder.txtview.setBackgroundColor(this.context.getResources().getColor(R.color.color13));
                break;
            case R.styleable.Toolbar_titleMargin /*14*/:
                holder.txtview.setBackgroundColor(this.context.getResources().getColor(R.color.color14));
                break;
            case R.styleable.Toolbar_titleMarginStart /*15*/:
                holder.txtview.setBackgroundColor(this.context.getResources().getColor(R.color.color15));
                break;
            case R.styleable.Toolbar_titleMarginEnd /*16*/:
                holder.txtview.setBackgroundColor(this.context.getResources().getColor(R.color.color16));
                break;
        }
        return row;
    }

}
