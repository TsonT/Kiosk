package com.example.Kiosk;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Space;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.room.Room;

import com.example.Kiosk.DatabaseClasses.OrderRoomDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Function_Create_Item {


    public static void Create(Activity activity, String itemType, LinearLayout MainLinearLayout, ListView lstOrder, TextView txtinfo, Button btnCheckOut)
    {

        LinearLayout mainLinearLayout;

        OrderRoomDatabase MenuDatabase= Room.databaseBuilder(activity, OrderRoomDatabase.class, "MenuDatabase").allowMainThreadQueries().build();

        mainLinearLayout = MainLinearLayout;

        ArrayList<Menu_Item> arrayListMenu;
        Type listType = new TypeToken<ArrayList<Menu_Item>>() {}.getType();
        arrayListMenu = new Gson().fromJson(MenuDatabase.getMenuArrayDAO().getArray(), listType);

        LinearLayout.LayoutParams spaceParams = null;
        LinearLayout.LayoutParams linearLayoutParams = null;
        LinearLayout.LayoutParams imageParams = null;
        LinearLayout.LayoutParams textViewParams = null;
        LinearLayout.LayoutParams FABParams = null;
        
        for (int i = 0; i < arrayListMenu.size(); i++) {

            String name = arrayListMenu.get(i).getItemName();
            String type = arrayListMenu.get(i).getItemType();
            Integer price = arrayListMenu.get(i).getPrice();
            String info = arrayListMenu.get(i).getInfo();

            if (itemType.equals("Sandwich"))
            {
                spaceParams = new LinearLayout.LayoutParams((int) activity.getResources().getDimension(R.dimen.Space_Width), (int)activity.getResources().getDimension(R.dimen.Space_Height));

                linearLayoutParams = new LinearLayout.LayoutParams((int) activity.getResources().getDimension(R.dimen.LinearLayout_Width), (int)activity.getResources().getDimension(R.dimen.LinearLayout_Height));
                linearLayoutParams.weight = 1;

                imageParams = new LinearLayout.LayoutParams((int) activity.getResources().getDimension(R.dimen.itemImageSandwich_Width), (int)activity.getResources().getDimension(R.dimen.itemImageSandwich_Height));
                imageParams.weight = 1;

                textViewParams = new LinearLayout.LayoutParams((int) activity.getResources().getDimension(R.dimen.textView_Width), (int)activity.getResources().getDimension(R.dimen.textView_Height));
                textViewParams.weight = 1;
                textViewParams.gravity = Gravity.CENTER;

                FABParams = new LinearLayout.LayoutParams((int) activity.getResources().getDimension(R.dimen.FAB_Width), (int)activity.getResources().getDimension(R.dimen.FAB_Height));
                FABParams.weight = 1;
                FABParams.gravity = Gravity.CENTER;

                if(type.equals("Sandwich"))
                {
                    Space space = new Space(activity);
                    space.setLayoutParams(spaceParams);
                    mainLinearLayout.addView(space);

                    LinearLayout linearLayout = new LinearLayout(activity);
                    linearLayout.setLayoutParams(linearLayoutParams);
                    linearLayout.setOrientation(LinearLayout.VERTICAL);
                    mainLinearLayout.addView(linearLayout);

                    ImageView imageView = new ImageView(activity);
                    imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    imageView.setLayoutParams(imageParams);
                    imageView.setImageURI(arrayListMenu.get(i).getImage());
                    imageView.setBackground(ContextCompat.getDrawable(activity, R.drawable.round_outline));
                    imageView.setClipToOutline(true);
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Item item = new Item(name, Toppings_Sandwich.Traditional, 0, price);

                            Customize_Dialog_Sandwich dialog = new Customize_Dialog_Sandwich();
                            dialog.showInitialDialog(activity, item, lstOrder, btnCheckOut);
                        }
                    });
                    linearLayout.addView(imageView);

                    TextView textView = new TextView(activity);
                    textView.setLayoutParams(textViewParams);
                    textView.setText(arrayListMenu.get(i).getItemName() + ": $" + arrayListMenu.get(i).getPrice().toString());
                    textView.setTextColor(Color.parseColor("#FFFFFF"));
                    Typeface typeface = ResourcesCompat.getFont(activity, R.font.amaranth);
                    textView.setTypeface(typeface, Typeface.BOLD);
                    textView.setGravity(Gravity.CENTER);
                    textView.setTextSize(activity.getResources().getDimension(R.dimen.textView_TextSize));
                    linearLayout.addView(textView);

                    FloatingActionButton floatingActionButton = new FloatingActionButton(activity);
                    floatingActionButton.setLayoutParams(FABParams);
                    floatingActionButton.setSize(FloatingActionButton.SIZE_MINI);
                    floatingActionButton.setImageResource(android.R.drawable.ic_menu_info_details);
                    floatingActionButton.setRippleColor(Color.parseColor("#1F000000"));
                    floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.DarkGray)));
                    floatingActionButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            txtinfo.setText(info);
                        }
                    });
                    linearLayout.addView(floatingActionButton);
                }
            }

            else if (itemType.equals("Drink"))
            {
                spaceParams = new LinearLayout.LayoutParams((int) activity.getResources().getDimension(R.dimen.Space_Width), (int)activity.getResources().getDimension(R.dimen.Space_Height));

                linearLayoutParams = new LinearLayout.LayoutParams((int) activity.getResources().getDimension(R.dimen.LinearLayout_Width), (int)activity.getResources().getDimension(R.dimen.LinearLayout_Height));
                linearLayoutParams.weight = 1;

                imageParams = new LinearLayout.LayoutParams((int) activity.getResources().getDimension(R.dimen.itemImageDrink_Width), (int)activity.getResources().getDimension(R.dimen.itemImageDrink_Height));
                imageParams.weight = 1;

                textViewParams = new LinearLayout.LayoutParams((int) activity.getResources().getDimension(R.dimen.textView_Width), (int)activity.getResources().getDimension(R.dimen.textView_Height));
                textViewParams.weight = 1;
                textViewParams.gravity = Gravity.CENTER;

                FABParams = new LinearLayout.LayoutParams((int) activity.getResources().getDimension(R.dimen.FAB_Width), (int)activity.getResources().getDimension(R.dimen.FAB_Height));
                FABParams.weight = 1;
                FABParams.gravity = Gravity.CENTER;

                if(type.equals("Drink"))
                {
                    Space space = new Space(activity);
                    space.setLayoutParams(spaceParams);
                    mainLinearLayout.addView(space);

                    LinearLayout linearLayout = new LinearLayout(activity);
                    linearLayout.setLayoutParams(linearLayoutParams);
                    linearLayout.setOrientation(LinearLayout.VERTICAL);
                    mainLinearLayout.addView(linearLayout);

                    ImageView imageView = new ImageView(activity);
                    imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    imageView.setLayoutParams(imageParams);
                    imageView.setImageURI(arrayListMenu.get(i).getImage());
                    imageView.setBackground(ContextCompat.getDrawable(activity, R.drawable.round_outline));
                    imageView.setClipToOutline(true);
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Item item = new Item(name, Toppings_Sandwich.Traditional, 0, price);

                            Customize_Dialog_Sandwich dialog = new Customize_Dialog_Sandwich();
                            dialog.showInitialDialog(activity, item, lstOrder, btnCheckOut);
                        }
                    });
                    linearLayout.addView(imageView);

                    TextView textView = new TextView(activity);
                    textView.setLayoutParams(textViewParams);
                    textView.setText(arrayListMenu.get(i).getItemName() + ": $" + arrayListMenu.get(i).getPrice().toString());
                    textView.setTextColor(Color.parseColor("#FFFFFF"));
                    Typeface typeface = ResourcesCompat.getFont(activity, R.font.amaranth);
                    textView.setTypeface(typeface, Typeface.BOLD);
                    textView.setGravity(Gravity.CENTER);
                    textView.setTextSize(activity.getResources().getDimension(R.dimen.textView_TextSize));
                    linearLayout.addView(textView);

                    FloatingActionButton floatingActionButton = new FloatingActionButton(activity);
                    floatingActionButton.setLayoutParams(FABParams);
                    floatingActionButton.setSize(FloatingActionButton.SIZE_MINI);
                    floatingActionButton.setImageResource(android.R.drawable.ic_menu_info_details);
                    floatingActionButton.setRippleColor(Color.parseColor("#1F000000"));
                    floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.DarkGray)));
                    floatingActionButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            txtinfo.setText(info);
                        }
                    });
                    linearLayout.addView(floatingActionButton);
                }
            }

            else if (itemType.equals("Dessert"))
            {
                spaceParams = new LinearLayout.LayoutParams((int) activity.getResources().getDimension(R.dimen.Space_Width), (int)activity.getResources().getDimension(R.dimen.Space_Height));

                linearLayoutParams = new LinearLayout.LayoutParams((int) activity.getResources().getDimension(R.dimen.LinearLayout_Width), (int)activity.getResources().getDimension(R.dimen.LinearLayout_Height));
                linearLayoutParams.weight = 1;

                imageParams = new LinearLayout.LayoutParams((int) activity.getResources().getDimension(R.dimen.itemImageDessert_Width), (int)activity.getResources().getDimension(R.dimen.itemImageDessert_Height));
                imageParams.weight = 1;

                textViewParams = new LinearLayout.LayoutParams((int) activity.getResources().getDimension(R.dimen.textView_Width), (int)activity.getResources().getDimension(R.dimen.textView_Height));
                textViewParams.weight = 1;
                textViewParams.gravity = Gravity.CENTER;

                FABParams = new LinearLayout.LayoutParams((int) activity.getResources().getDimension(R.dimen.FAB_Width), (int)activity.getResources().getDimension(R.dimen.FAB_Height));
                FABParams.weight = 1;
                FABParams.gravity = Gravity.CENTER;

                if(type.equals("Dessert"))
                {
                    Space space = new Space(activity);
                    space.setLayoutParams(spaceParams);
                    mainLinearLayout.addView(space);

                    LinearLayout linearLayout = new LinearLayout(activity);
                    linearLayout.setLayoutParams(linearLayoutParams);
                    linearLayout.setOrientation(LinearLayout.VERTICAL);
                    mainLinearLayout.addView(linearLayout);

                    ImageView imageView = new ImageView(activity);
                    imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    imageView.setLayoutParams(imageParams);
                    imageView.setImageURI(arrayListMenu.get(i).getImage());
                    imageView.setBackground(ContextCompat.getDrawable(activity, R.drawable.round_outline));
                    imageView.setClipToOutline(true);
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Item item = new Item(name, Toppings_Sandwich.Traditional, 0, price);

                            Initial_Dialog_Sandwich dialog = new Initial_Dialog_Sandwich();
                            dialog.showDialog(activity, item, lstOrder);
                        }
                    });
                    linearLayout.addView(imageView);

                    TextView textView = new TextView(activity);
                    textView.setLayoutParams(textViewParams);
                    textView.setText(arrayListMenu.get(i).getItemName() + ": $" + arrayListMenu.get(i).getPrice().toString());
                    textView.setTextColor(Color.parseColor("#FFFFFF"));
                    Typeface typeface = ResourcesCompat.getFont(activity, R.font.amaranth);
                    textView.setTypeface(typeface, Typeface.BOLD);
                    textView.setGravity(Gravity.CENTER);
                    textView.setTextSize(activity.getResources().getDimension(R.dimen.textView_TextSize));
                    linearLayout.addView(textView);

                    FloatingActionButton floatingActionButton = new FloatingActionButton(activity);
                    floatingActionButton.setLayoutParams(FABParams);
                    floatingActionButton.setSize(FloatingActionButton.SIZE_MINI);
                    floatingActionButton.setImageResource(android.R.drawable.ic_menu_info_details);
                    floatingActionButton.setRippleColor(Color.parseColor("#1F000000"));
                    floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(activity, R.color.DarkGray)));
                    floatingActionButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            txtinfo.setText(info);
                        }
                    });
                    linearLayout.addView(floatingActionButton);
                }
            }
        }
    }
}
