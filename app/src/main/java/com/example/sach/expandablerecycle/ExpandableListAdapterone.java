package com.example.sach.expandablerecycle;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.androidquery.AQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anandbose on 09/06/15.
 */
public class ExpandableListAdapterone extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int HEADER = 0;
    public static final int CHILD = 1;
    Context con;

int lk=0;

    private List<Item> data;

    public ExpandableListAdapterone(Context con, List<Item> data) {
        this.con = con;  this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View view = null;
        Context context = parent.getContext();
        float dp = context.getResources().getDisplayMetrics().density;
        int subItemPaddingLeft = (int) (18 * dp);
        int subItemPaddingTopAndBottom = (int) (5 * dp);
        switch (type) {
            case HEADER:
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.list_group, parent, false);
                ListHeaderViewHolder header = new ListHeaderViewHolder(view);
                return header;
            case CHILD:
                LayoutInflater inflater1 = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater1.inflate(R.layout.list_group, parent, false);
                ListHeaderViewHolder header1 = new ListHeaderViewHolder(view);
                return header1;

        }
        return null;
    }

    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final Item item = data.get(position);

        switch (item.type) {
            case HEADER:
                final ListHeaderViewHolder itemController = (ListHeaderViewHolder) holder;

                itemController.refferalItem = item;
                itemController.header_title.setText(item.text);
                itemController.header_title1.setText(item.text1);
                lk++;
                if(lk<=8){
try{
                item.invisibleChildren = new ArrayList<Item>();
                int count = 0;
                int pos = data.indexOf(itemController.refferalItem);
                while (data.size() > pos + 1 && data.get(pos + 1).type == CHILD) {
                    item.invisibleChildren.add(data.remove(pos + 1));
                    count++;
                }
                notifyItemRangeRemoved(pos + 1, count);
                itemController.btn_expand_toggle.setImageResource(R.drawable.circle_plus);}
catch (IndexOutOfBoundsException e){}
                catch (Exception y){}}
                if (item.invisibleChildren == null) {
                    itemController.btn_expand_toggle.setImageResource(R.drawable.circle_minus);
                } else {
                    itemController.btn_expand_toggle.setImageResource(R.drawable.circle_plus);
                }
                itemController.liner.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // starting new activity and expecting some response back
                       if (item.invisibleChildren == null) {
                            item.invisibleChildren = new ArrayList<Item>();
                            int count = 0;
                            int pos = data.indexOf(itemController.refferalItem);
                            while (data.size() > pos + 1 && data.get(pos + 1).type == CHILD) {
                                item.invisibleChildren.add(data.remove(pos + 1));
                                count++;
                            }
                            notifyItemRangeRemoved(pos + 1, count);
                            itemController.btn_expand_toggle.setImageResource(R.drawable.circle_plus);

                          /*  Intent intent = new Intent(context, MainActivity.class);

                            context.startActivity(intent);*/
                        } else {
                            int pos = data.indexOf(itemController.refferalItem);
                            int index = pos + 1;
                            for (Item i : item.invisibleChildren) {
                                data.add(index, i);
                                index++;
                            }
                            notifyItemRangeInserted(pos + 1, index - pos - 1);
                            itemController.btn_expand_toggle.setImageResource(R.drawable.circle_minus);
                            item.invisibleChildren = null;
                        }
                    }
                });
                break;
            case CHILD:
                final ListHeaderViewHolder itemController1 = (ListHeaderViewHolder) holder;
                itemController1.refferalItem = item;
                itemController1.header_title.setText(item.text);
                itemController1.header_title1.setText(item.text1);

                AQuery aq = new AQuery(itemController1.itemView);
                aq.id(R.id.imag).image("http://ukonnect.azurewebsites.net/aadmin/images/" + item.text2, true, true, 0, 0, null, AQuery.FADE_IN_NETWORK, 1.0f);






                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).type;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private static class ListHeaderViewHolder extends RecyclerView.ViewHolder {
        public TextView header_title;
        public TextView header_title1;
        public ImageView btn_expand_toggle;
        public Item refferalItem;
        public LinearLayout liner;


        public ListHeaderViewHolder(View itemView) {
            super(itemView);
            header_title = (TextView) itemView.findViewById(R.id.txtprovince);
            header_title1 = (TextView) itemView.findViewById(R.id.txtpnum);
            liner = (LinearLayout) itemView.findViewById(R.id.liner);
            btn_expand_toggle = (ImageView) itemView.findViewById(R.id.imag);
        }
    }


    public static class Item {
        public int type;
        public String text;
        public String text1;
        public String text2;
        public List<Item> invisibleChildren;

        public Item() {
        }

        public Item(int type, String text,String text1,String text2) {
            this.type = type;
            this.text = text;
            this.text1 = text1;
            this.text2 = text2;
        }
    }
}
