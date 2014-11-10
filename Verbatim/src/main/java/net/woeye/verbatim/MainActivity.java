package net.woeye.verbatim;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import net.woeye.verbatim.db.CardDAO;
import net.woeye.verbatim.db.DatabaseHelper;
import net.woeye.verbatim.fragment.EditCardFragment;
import net.woeye.verbatim.fragment.OverviewFragment;
import net.woeye.verbatim.fragment.TrainingFragment;
import net.woeye.verbatim.model.Card;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity implements EditCardFragment.EditDialogListener {
    private ViewPager mViewPager;
    //private TabsAdapter mTabsAdapter;
    private CardDAO mCardDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        //ActionBar actionBar = getActionBar();
        //actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        //actionBar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);

        //mTabsAdapter = new TabsAdapter(this, mViewPager);
        //mTabsAdapter.addTab(actionBar.newTab().setText("Overview"),
        //    OverviewFragment.class, null);
        //mTabsAdapter.addTab(actionBar.newTab().setText("Training"),
        //    TrainingFragment.class, null);

        DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.main_drawer_layout);
        //drawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.whiteColor));

        ListView naviListView = (ListView)findViewById(R.id.navi_list_view);
        naviListView.setAdapter(new NaviListAdapter(this));

        DatabaseHelper helper = new DatabaseHelper(this);
        mCardDao = new CardDAO(helper.getWritableDatabase());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentManager fragmentManager = getFragmentManager();

        switch(item.getItemId()) {
            case R.id.action_add_card:
                EditCardFragment dialog = new EditCardFragment();
                dialog.show(fragmentManager, "dialog");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onAddOrSave(Card card) {
        if (card.isNew()) {
            mCardDao.insertCard(card);
        } else {
            mCardDao.updateCard(card);
        }
    }

    public static class NaviListAdapter extends BaseAdapter {
        private final Context context;
        private final LayoutInflater layoutInflater;
        private final String[] items = new String[] {
                "Your Collections",
                "Two",
                "Three"
        };

        public NaviListAdapter(Context context) {
            this.context = context;
            this.layoutInflater = LayoutInflater.from(this.context);
        }

        @Override
        public int getCount() {
            return items.length;
        }

        @Override
        public Object getItem(int position) {
            return items[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView = null;

            if (position == 0) {
                rowView = layoutInflater.inflate(R.layout.navi_group_header_item, parent, false);
                TextView tv = (TextView)rowView.findViewById(R.id.text_view);
                tv.setText(items[position]);
            } else {
                rowView = layoutInflater.inflate(R.layout.navi_row_item, parent, false);
                TextView tv = (TextView)rowView.findViewById(R.id.text_view);
                tv.setText(items[position]);
            }

            return rowView;
        }
    }


    /*public static class TabsAdapter extends FragmentPagerAdapter
        implements ActionBar.TabListener, ViewPager.OnPageChangeListener {
        private final Context mContext;
        private final ActionBar mActionBar;
        private final ViewPager mViewPager;
        private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();

        static final class TabInfo {
            private final Class<?> clss;
            private final Bundle args;

            TabInfo(Class<?> _class, Bundle _args) {
                clss = _class;
                args = _args;
            }
        }

        public TabsAdapter(Activity activity, ViewPager pager) {
            super(activity.getFragmentManager());
            mContext = activity;
            mActionBar = activity.getActionBar();
            mViewPager = pager;
            mViewPager.setAdapter(this);
            mViewPager.setOnPageChangeListener(this);
        }

        public void addTab(ActionBar.Tab tab, Class<?> clss, Bundle args) {
            TabInfo info = new TabInfo(clss, args);
            tab.setTag(info);
            tab.setTabListener(this);
            mTabs.add(info);
            mActionBar.addTab(tab);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mTabs.size();
        }

        @Override
        public Fragment getItem(int position) {
            TabInfo info = mTabs.get(position);
            return Fragment.instantiate(mContext, info.clss.getName(), info.args);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            mActionBar.setSelectedNavigationItem(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }

        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
            Object tag = tab.getTag();
            for (int i=0; i<mTabs.size(); i++) {
                if (mTabs.get(i) == tag) {
                    mViewPager.setCurrentItem(i);
                }
            }
        }

        @Override
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        }

        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
        }
    }*/
}
