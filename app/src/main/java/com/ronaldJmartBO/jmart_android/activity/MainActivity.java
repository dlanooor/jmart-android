package com.ronaldJmartBO.jmart_android.activity;

import static com.ronaldJmartBO.jmart_android.activity.LoginActivity.getLoggedAccount;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.ronaldJmartBO.R;
import com.ronaldJmartBO.jmart_android.fragment.Filter;
import com.ronaldJmartBO.jmart_android.fragment.Products;

/**
 * The type Main activity.
 */
public class MainActivity extends AppCompatActivity {
    private static final int NUM_PAGES = 2;
    /**
     * The constant viewPager.
     */
    //The pager widget, which handles animation and allows swiping horizontally to access previous and next wizard steps.
    public static ViewPager2 viewPager;
    // The pager adapter, which provides the pages to the view pager widget.
    private FragmentStateAdapter pagerAdapter;
    // Arrey of strings FOR TABS TITLES
    private String[] titles = new String[]{"Products", "Filter"};
    // tab titles

    /**
     * The constant loggedId.
     */
    public static String loggedId = String.valueOf(getLoggedAccount().id);

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        if(getLoggedAccount().store != null)
            menu.findItem(R.id.add_box).setVisible(true);
        else
            menu.findItem(R.id.add_box).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.add_box:
                startActivity(new Intent(MainActivity.this, CreateProductActivity.class));
                return true;
            case R.id.person:
                startActivity(new Intent(MainActivity.this, AboutMeActivity.class));
                return true;
            case R.id.phoneTopUp:
                startActivity(new Intent(MainActivity.this, PhoneTopUpActivity.class));
                return true;
            case R.id.invoice:
                startActivity(new Intent(MainActivity.this, InvoiceActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("jMart");

        viewPager = findViewById(R.id.mypager);
        pagerAdapter = new MyPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        //inflating tab layout
        TabLayout tabLayout =( TabLayout) findViewById(R.id.tab_layout);
        //displaying tabs
        new TabLayoutMediator(tabLayout, viewPager,(tab, position) -> tab.setText(titles[position])).attach();

    }

    private class MyPagerAdapter extends FragmentStateAdapter {

        /**
         * Instantiates a new My pager adapter.
         *
         * @param fa the fa
         */
        public MyPagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int pos) {
            switch (pos) {
                case 0: {
                    return Products.newInstance("products");
                }
                case 1: {
                    return Filter.newInstance("filter");
                }
                default:
                    return Products.newInstance("products, Default");
            }
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loggedId = String.valueOf(getLoggedAccount().id);
    }
}