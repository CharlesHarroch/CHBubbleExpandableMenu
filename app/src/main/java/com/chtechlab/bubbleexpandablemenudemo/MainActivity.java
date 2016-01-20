package com.chtechlab.bubbleexpandablemenudemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.chtechlab.bubbleexpandablemenu.BubbleExpandableMenu;

public class MainActivity extends Activity {

    private boolean isShow = false;
    private BubbleExpandableMenu bubbleMenu;
    private Button openButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bubbleMenu = (BubbleExpandableMenu)findViewById(R.id.bubble_view);
        openButton = (Button)findViewById(R.id.button);
        openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bubbleMenu.show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        bubbleMenu.setMenuListener(new BubbleExpandableMenu.OnBubbleMenuListener() {
            @Override
            public void openMenu() {
                openButton.setVisibility(View.GONE);
            }
            @Override
            public void closeMenu() {
                openButton.setVisibility(View.VISIBLE);
            }
            @Override
            public void bubbleItemMenuClicked(Object tag) {
                Toast.makeText(getApplicationContext(),"Bubble " + tag, Toast.LENGTH_SHORT).show();            }
        });

        bubbleMenu.addItem(R.mipmap.ic_launcher, 1);
        bubbleMenu.addItem(R.mipmap.ic_launcher, 2);
        bubbleMenu.addItem(R.mipmap.ic_launcher, 3);
        bubbleMenu.addItem(R.mipmap.ic_launcher, 4);
        bubbleMenu.addItem(R.mipmap.ic_launcher, 5);
    }
}
