package com.example.raych.nhu;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.ToxicBakery.viewpager.transforms.ZoomOutSlideTransformer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FindHitUp extends AppCompatActivity implements Store_Top_Free.OnFragmentInteractionListener
, Store_Top_Paid.OnFragmentInteractionListener, Store_Popular.OnFragmentInteractionListener, Event_Info_frag.OnFragmentInteractionListener{
    int limit = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_hit_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.store_toolbar);
        setSupportActionBar(toolbar);
        ViewPager viewPager = (ViewPager)findViewById(R.id.Store_Viewpager);
        Store_TabAdapter adapter= new Store_TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(true, new ZoomOutSlideTransformer());
       // viewPager.setOffscreenPageLimit(limit);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.toolbar_home:
                Intent zero = new Intent(this,Home.class);
                startActivity(zero);
                return true;
            case R.id.toolbar_create:
                Intent one = new Intent(this,CreateEvent.class);
                startActivity(one);
                return true;
            case R.id.toolbar_host:
                Intent two = new Intent(this,HostingEvent.class);
                startActivity(two);
                return true;
            case R.id.toolbar_join:
                Intent three = new Intent(this,JoinedEvents.class);
                startActivity(three);
                return true;
            case R.id.toolbar_hitup:
                Intent four = new Intent(this,FindHitUp.class);
                startActivity(four);
                return true;
            case R.id.toolbar_logout:
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.signOut();
                Intent logout = new Intent(this,login.class);
                startActivity(logout);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
