package com.sevennine.Delivery;
import com.sevennine.Delivery.Orders.NewOrderTab;
import com.sevennine.Delivery.Orders.ProcessingTab;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


/**
 * Created by Belal on 2/3/2016.
 */
//Extending FragmentStatePagerAdapter
public class Pager extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public Pager(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {

            case 0:
                System.out.println("pager"+position);

                NewOrderTab tab1 = new NewOrderTab();
                return tab1;
            case 1:
                ProcessingTab scheduledTabFragment=new ProcessingTab();
                return scheduledTabFragment;
            case 2:
                NewOrderTab tab4=new NewOrderTab();
                return tab4;
            case 3:
                NewOrderTab tab5=new NewOrderTab();
                return  tab5;

            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }
}