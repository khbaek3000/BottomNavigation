package com.example.bottomviewex.main;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.bottomviewex.BaseActivity;
import com.example.bottomviewex.R;
import com.example.bottomviewex.databinding.ActivityMainBinding;
import com.example.bottomviewex.game.GameFragment;
import com.example.bottomviewex.swap.SwapFragment;
import com.example.bottomviewex.wallet.WalletFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends BaseActivity {

    MainViewModel mainViewModel;
    BottomNavigationView bottomNavigationView;
    ActivityMainBinding activityMainBinding;

    Fragment walletFragment = new WalletFragment();
    Fragment swapFragment = new SwapFragment();
    Fragment gameFragment = new GameFragment();

    Fragment activeFragment = walletFragment;

    FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViewModel();

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityMainBinding.setMainViewModel(mainViewModel);

        bottomNavigationView = activityMainBinding.mainBottomNv;
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        fragmentManager.beginTransaction().add(R.id.main_content, gameFragment, "game").hide(gameFragment).commit();
        fragmentManager.beginTransaction().add(R.id.main_content, swapFragment, "swap").hide(swapFragment).commit();
        fragmentManager.beginTransaction().add(R.id.main_content, walletFragment, "wallet").commit();
    }

    void initViewModel() {
        mainViewModel = ViewModelProviders.of(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new MainViewModel();
            }
        }).get(MainViewModel.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.item_wallet_main:
                   changeFragment(walletFragment);
                    return true;

                case R.id.item_wallet_swap:
                    changeFragment(swapFragment);
                    return true;

                case R.id.item_wallet_game:
                    changeFragment(gameFragment);
                    return true;
            }

            return false;
        }
    };

    void changeFragment(Fragment targetFragment) {
        fragmentManager.beginTransaction().hide(activeFragment).show(targetFragment).commit();
        activeFragment = targetFragment;
    }
}
