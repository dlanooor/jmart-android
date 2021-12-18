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
import com.ronaldJmartBO.jmart_android.fragment.AccountInvoice;
import com.ronaldJmartBO.jmart_android.fragment.Products;
import com.ronaldJmartBO.jmart_android.fragment.StoreInvoice;
import com.ronaldJmartBO.jmart_android.model.Product;

/**
 * The type Invoice activity.
 * @author Ronald Grant
 * @version 2.0
 * @since 19 December 2021
 */
public class InvoiceActivity extends AppCompatActivity {
    private static final int NUM_PAGES = 2;
    /**
     * The constant viewPager.
     */

    //The pager widget, which handles animation and allows swiping horizontally to access previous and next wizard steps.
    public static ViewPager2 viewPager;
    // The pager adapter, which provides the pages to the view pager widget.
    private FragmentStateAdapter pagerAdapter;
    // Arrey of strings FOR TABS TITLES
    private String[] titles = new String[]{"Account Invoice", "Store Invoice"};
    // tab titles

    /**
     * onCreateOptionsMenu
     * set All InvoiceActivity options Menu
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        menu.findItem(R.id.invoice).setVisible(false);

        if(getLoggedAccount().store != null)
            menu.findItem(R.id.add_box).setVisible(true);
        else
            menu.findItem(R.id.add_box).setVisible(false);

        return true;
    }

    /**
     * onCreate
     * set All InvoiceActivity needed
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("Invoice");

        viewPager = findViewById(R.id.mypager);
        pagerAdapter = new InvoiceActivity.MyPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        //inflating tab layout
        TabLayout tabLayout =( TabLayout) findViewById(R.id.tab_layout);
        //displaying tabs
        new TabLayoutMediator(tabLayout, viewPager,(tab, position) -> tab.setText(titles[position])).attach();

    }

    /**
     * onOptionsItemSelected
     * set All InvoiceActivity onOptionsItems needed
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.add_box:
                startActivity(new Intent(InvoiceActivity.this, CreateProductActivity.class));
                return true;
            case R.id.person:
                startActivity(new Intent(InvoiceActivity.this, AboutMeActivity.class));
                return true;
            case R.id.phoneTopUp:
                startActivity(new Intent(InvoiceActivity.this, PhoneTopUpActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * MyPagerAdapterClass
     * set All PagerAdapter in InvoiceActivity needed
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
                    return AccountInvoice.newInstance("accountInvoice");
                }
                case 1: {
                    return StoreInvoice.newInstance("storeInvoice");
                }
                default:
                    return Products.newInstance("accountInvoice, Default");
            }
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }

}