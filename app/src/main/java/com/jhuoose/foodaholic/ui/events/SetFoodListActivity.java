package com.jhuoose.foodaholic.ui.events;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jhuoose.foodaholic.R;
import com.jhuoose.foodaholic.viewmodel.ActivityProfile;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class SetFoodListActivity extends AppCompatActivity {
    Button addFoodBtn;
    EditText foodNameEt;
    String foodName;
    LinearLayout foodListLayout;
    ArrayList<ActivityProfile> activityList = AddEventActivity.activityList;
    private AlertDialog deleteFoodDialog;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        addFoodBtn = findViewById(R.id.btn_add_food);
        foodNameEt = findViewById(R.id.et_food_name);
        foodListLayout = findViewById(R.id.foodList_Layout);

        for (int i = 0; i< activityList.size(); i++) {
            Button existedFood = new Button(this);
            existedFood.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            existedFood.setText(activityList.get(i).getActivityName());
            existedFood.setId(i+1);
            foodListLayout.addView(existedFood);
        }

        addFoodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                foodName = foodNameEt.getText().toString();
                if (foodName==null||foodName.equals("")){
                    Toast.makeText(SetFoodListActivity.this,"Invalid foodName",Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    for (int num = 0; num< activityList.size(); num++){
                        if (activityList.get(num).getActivityName().equals(foodName)){
                            Toast.makeText(SetFoodListActivity.this,"Do not add the same food",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }

                final Button newFood = new Button(SetFoodListActivity.this);
                newFood.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                newFood.setText(foodName);
                newFood.setId(activityList.size()+1);

                newFood.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(SetFoodListActivity.this);
                        builder.setTitle("Delete "+newFood.getText().toString()+" ?");
                        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteFoodDialog.dismiss();
                            }
                        });

                        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                for (int j = 0; j< activityList.size(); j++){
                                    if (activityList.get(j).getActivityName().equals(foodName)){
                                        activityList.remove(j);
                                        break;
                                    }
                                }
                                foodListLayout.removeView(newFood);
                                deleteFoodDialog.dismiss();
                                Toast.makeText(SetFoodListActivity.this, foodName+" is Deleted",Toast.LENGTH_SHORT).show();
                            }
                        });

                        deleteFoodDialog = builder.create();
                        deleteFoodDialog.show();
                    }
                });

                activityList.add(new ActivityProfile(foodName));
                foodListLayout.addView(newFood);
                foodNameEt.setText("");
            }
        });
    }
}
