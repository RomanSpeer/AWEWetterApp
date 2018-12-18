/**
 * WeatherFragment
 * <p>
 * Fragment, zur Darstellung der Tabbar,
 * sowie die Interaktionen mit dieser.
 *
 * @author Steffen Höltje
 * @version 1.0
 */

package de.fhdw.steffen.awewetter.activitys;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import de.fhdw.steffen.awewetter.R;

public class WeatherFragment extends Fragment {

    /**
     * Erstellen der WeatherFragment-View
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return WeatherFragment mit allen Darstellungen und Informationen
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        //Setzen des ViewPager für jeden Tab
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        // Setzen der Tabs in der Toolbar
        TabLayout tabs = (TabLayout) view.findViewById(R.id.result_tabs);
        tabs.setupWithViewPager(viewPager);


        return view;

    }

    /**
     * Hinzufügen der Fragmente zu den Tabs
     *
     * @param viewPager
     */
    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getChildFragmentManager());
        adapter.addFragment(new WeatherListFragment(), getResources().getString(R.string.fragment_weather_list));
        adapter.addFragment(new WeatherGraphicFragment(), getResources().getString(R.string.fragment_weather_graphic));
        viewPager.setAdapter(adapter);
    }

    /**
     * Adapter für die Fragmente
     */
    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
