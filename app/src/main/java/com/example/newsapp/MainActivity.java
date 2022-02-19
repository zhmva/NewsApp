package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.SearchManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.newsapp.common.OnSearchListener;
import com.example.newsapp.databinding.ActivityMainBinding;

import dagger.hilt.android.AndroidEntryPoint;

import android.os.Bundle;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private NavHostFragment navHostFragment;
    private NavController controller;
    private OnSearchListener listener;

    public void setListener(OnSearchListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host);
        controller = navHostFragment.getNavController();
        setSupportActionBar(binding.toolbar);
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(controller.getGraph()).build();
        NavigationUI.setupWithNavController(binding.toolbar,controller,appBarConfiguration);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                listener.onSubmit(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listener.onTextChanged(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}

