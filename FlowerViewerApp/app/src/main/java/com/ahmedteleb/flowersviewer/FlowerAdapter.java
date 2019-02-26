package com.ahmedteleb.flowersviewer;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.j256.ormlite.dao.Dao;
import com.squareup.picasso.Picasso;

import java.sql.SQLException;
import java.util.ArrayList;


public class FlowerAdapter extends BaseAdapter {

    private ArrayList<Flower> flowers;

    public FlowerAdapter(ArrayList<Flower> flowers) {
        this.flowers = flowers;
    }

    @Override
    public int getCount() {
        return flowers.size();
    }

    @Override
    public Flower getItem(int position) {
        return flowers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return flowers.get(position).getProductId();
    }

    @Override
    public View getView(int position, View oldView, ViewGroup adapterView) {

        oldView = LayoutInflater.from(adapterView.getContext()).inflate(R.layout.item_flower, null);

        ImageView ivFlower = oldView.findViewById(R.id.iv_flower);
        final ImageView ivFlowerSave = oldView.findViewById(R.id.iv_flower_save);
        TextView tvPrice = oldView.findViewById(R.id.tv_flower_price);
        TextView tvName = oldView.findViewById(R.id.tv_flower_name);

        final Flower currentFlower = getItem(position);

        tvName.setText(currentFlower.getName());
        tvPrice.setText(String.valueOf(currentFlower.getPrice()));
        Picasso.with(adapterView.getContext())
                .load("http://services.hanselandpetal.com/photos/" + currentFlower.getPhoto())
                .into(ivFlower);


        DatabaseHelper helper = new DatabaseHelper(adapterView.getContext());
        final Dao<Flower,Integer> dao = helper.getFlowerDao();

        try {
            if(dao.idExists(currentFlower.getProductId())){
                ivFlowerSave.setImageResource(R.drawable.ic_action_save);
            }else{
                ivFlowerSave.setImageResource(R.drawable.ic_action_unsave);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ivFlowerSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(dao.idExists(currentFlower.getProductId())){
                        ivFlowerSave.setImageResource(R.drawable.ic_action_unsave);
                        dao.delete(currentFlower);
                    }else{
                        ivFlowerSave.setImageResource(R.drawable.ic_action_save);
                        dao.createIfNotExists(currentFlower);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });



        return oldView;
    }
}

