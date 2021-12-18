package com.ronaldJmartBO.jmart_android.activity;

import static com.ronaldJmartBO.jmart_android.activity.LoginActivity.getLoggedAccount;
import static com.ronaldJmartBO.jmart_android.fragment.Products.productShare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.ronaldJmartBO.R;
import com.ronaldJmartBO.jmart_android.fragment.Filter;
import com.ronaldJmartBO.jmart_android.fragment.Products;
import com.ronaldJmartBO.jmart_android.model.Product;

/**
 * The type Main activity.
 * @author Ronald Grant
 * @version 2.0
 * @since 19 December 2021
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

    /**
     * onCreateOptionsMenu
     * set All MainActivity options Menu
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        if(getLoggedAccount().store != null)
            menu.findItem(R.id.add_box).setVisible(true);
        else
            menu.findItem(R.id.add_box).setVisible(false);

        androidx.appcompat.widget.SearchView search = (androidx.appcompat.widget.SearchView) menu.findItem(R.id.search).getActionView();

        //listView
        ListView listView = findViewById(R.id.listView);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ArrayAdapter<Product> adapter = new ArrayAdapter<Product>(MainActivity.this, android.R.layout.simple_list_item_1, productShare);
                listView.setAdapter(adapter);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayAdapter<Product> adapter = new ArrayAdapter<Product>(MainActivity.this, android.R.layout.simple_list_item_1, productShare);
                listView.setAdapter(adapter);
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }

    /**
     * onOptionsItemSelected
     * set All MainActivity onOptionsItems needed
     */
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

    /**
     * onCreate
     * set All MainActivity needed
     */
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

    /**
     * MyPagerAdapterClass
     * set All PagerAdapter in MainActivity needed
     */
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

    /**
     * onResume
     * set All MainAcitvity onResume needed
     */
    @Override
    protected void onResume() {
        super.onResume();
        loggedId = String.valueOf(getLoggedAccount().id);
    }
}