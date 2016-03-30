package com.blstream.studybox.components;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.blstream.studybox.R;
import com.blstream.studybox.activities.LoginActivity;
import com.blstream.studybox.login.LoginUtils;

public class DrawerAdapter extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //TODO
    //Delete Toast messages and contexts after providing better tests for drawer

    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    Context context;

    public DrawerAdapter(NavigationView navigationView, DrawerLayout drawerLayout, Toolbar toolbar, Context context) {
        this.navigationView = navigationView;
        this.drawerLayout = drawerLayout;
        this.toolbar = toolbar;
        this.context = context;
    }

    public void attachDrawer() {
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.nav_open_drawer, R.string.nav_close_drawer);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        item.setChecked(true);

        int id = item.getItemId();
        drawerLayout.closeDrawer(GravityCompat.START);

        switch (id) {
            case R.id.my_account:
                break;
            case R.id.my_decks:
                break;
            case R.id.create_flashcard:
                break;
            case R.id.show_deck:
                break;
            case R.id.statistics:
                break;
            case R.id.logout:
                LoginUtils.deleteUser(context);
                Intent intent = new Intent(context, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);
                break;
        }

        // Only for testing
        Toast.makeText(context, "Selected: " + item.getTitle(), Toast.LENGTH_SHORT).show();
        return true;
    }

}