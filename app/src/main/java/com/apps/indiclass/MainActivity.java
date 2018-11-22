package com.apps.indiclass;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.apps.indiclass.fragment.ClassFragment;
import com.apps.indiclass.fragment.HomeFragment;
import com.apps.indiclass.fragment.ProfileFragment;
import com.apps.indiclass.fragment.TutorFragment;
import com.apps.indiclass.util.BottomNavigationBehavior;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    final Fragment fragment1 = new HomeFragment();
    final Fragment fragment2 = new ClassFragment();
    final Fragment fragment3 = new TutorFragment();
    final Fragment fragment4 = new ProfileFragment();
    final FragmentManager fm = getSupportFragmentManager();
    private final int[] colors = {R.color.bgBottomNavigation, R.color.bgBottomNavigation, R.color.bgBottomNavigation};
    Fragment active = fragment2;
    BottomNavigationView navigation;
    FloatingActionButton fab;
    boolean current1 = true;
    boolean current2 = false;
    boolean current3 = false;
    public boolean isTV = false;
    private ActionBar toolbar;
    private AHBottomNavigation bottomNavigation;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
//                case R.id.navigation_home:
//                    toolbar.setTitle(R.string.title_home);
//
//                    fm.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).hide(active).show(fragment1).commit();
//                    active = fragment1;
//                    fab.setVisibility(View.GONE);
////                    loadFragment(new BlankFragment());
//                    return true;
                case R.id.navigation_dashboard:
                    toolbar.setTitle(R.string.title_dashboard);

                    fm.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).hide(active).show(fragment2).commit();
                    active = fragment2;
                    fab.setVisibility(View.VISIBLE);
//                    loadFragment(new ClassFragment());
                    return true;
                case R.id.navigation_tutor:
                    toolbar.setTitle(R.string.title_tutor);

                    fm.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).hide(active).show(fragment3).commit();
                    active = fragment3;
                    fab.setVisibility(View.GONE);
//                    loadFragment(new ClassFragment());
                    return true;
                case R.id.navigation_notifications:
                    toolbar.setTitle(R.string.title_notifications);

                    fm.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).hide(active).show(fragment4).commit();
                    active = fragment4;
                    fab.setVisibility(View.GONE);
//                    loadFragment(new NotificationFragment());
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = getSupportActionBar();

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        disableShiftMode(navigation);

        // attaching bottom sheet behaviour - hide / show on scroll
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, ReqClassActivity.class));

            }
        });

        fm.beginTransaction().add(R.id.frame_container, fragment4, "4").hide(fragment4).commit();
        fm.beginTransaction().add(R.id.frame_container, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.frame_container, fragment2, "2").commit();
//        fm.beginTransaction().add(R.id.frame_container,fragment1, "1").commit();
        fab.setVisibility(View.VISIBLE);
        toolbar.setTitle(R.string.title_home);
//        loadFragment(new BlankFragment());

        if (findViewById(R.id.bottom_navigation) != null) {
            bottomNavigation = findViewById(R.id.bottom_navigation);
            setupBottomNavBehaviors();
            setupBottomNavStyle();

            addBottomNavigationItems();

            changeClick();
            cc();
            c2();
            isTV = true;
        }
    }

    public void btClick(View v) {
        if (v.getId() == R.id.bottom_navigation_container1) {
            current1 = true;
            current2 = false;
            current3 = false;
            changeClick();
            cc();
            c2();
            navigation.setSelectedItemId(R.id.navigation_dashboard);
        } else if (v.getId() == R.id.bottom_navigation_container2) {

            current1 = false;
            current2 = true;
            current3 = false;
            changeClick();
            cc();
            c2();
            navigation.setSelectedItemId(R.id.navigation_tutor);
        } else {

            current1 = false;
            current2 = false;
            current3 = true;
            changeClick();
            cc();
            c2();
            navigation.setSelectedItemId(R.id.navigation_notifications);
        }
    }

    void changeClick() {

        ImageView icon1 = findViewById(R.id.bottom_navigation_item_icon1);
        animateImageView(icon1, current1);
//        icon1.setImageDrawable(AHHelper.getTintDrawable(icon1.getDrawable(),
//                current1 ? fetchColor(R.color.white) : fetchColor(R.color.bottomtab_item_resting), false));

        TextView title1 = findViewById(R.id.bottom_navigation_item_title1);
        title1.setTextColor(current1 ? fetchColor(R.color.white) : fetchColor(R.color.bottomtab_item_resting));
        title1.setTextSize(TypedValue.COMPLEX_UNIT_SP, current3 ? 14 : 12);
    }

    void cc() {

        // dua
        ImageView icon2 = findViewById(R.id.bottom_navigation_item_icon2);
        animateImageView(icon2, current2);
//        icon2.setImageDrawable(AHHelper.getTintDrawable(icon2.getDrawable(),
//                current2 ? fetchColor(R.color.white) : fetchColor(R.color.bottomtab_item_resting), false));

        TextView title2 = findViewById(R.id.bottom_navigation_item_title2);
        title2.setTextColor(current2 ? fetchColor(R.color.white) : fetchColor(R.color.bottomtab_item_resting));
        title2.setTextSize(TypedValue.COMPLEX_UNIT_SP, current3 ? 14 : 12);
    }

    void c2() {

        // tiga
        ImageView icon3 = findViewById(R.id.bottom_navigation_item_icon3);
        animateImageView(icon3, current3);
//        icon3.setImageDrawable(AHHelper.getTintDrawable(icon3.getDrawable(),
//                current3 ? fetchColor(R.color.white) : fetchColor(R.color.bottomtab_item_resting), false));

        TextView title3 = findViewById(R.id.bottom_navigation_item_title3);
        title3.setTextColor(current3 ? fetchColor(R.color.white) : fetchColor(R.color.bottomtab_item_resting));
        title3.setTextSize(TypedValue.COMPLEX_UNIT_SP, current3 ? 14 : 12);
    }

    public void animateImageView(final ImageView v, boolean os) {
        final int orange;
        if (os) {
            TypedValue typedValue = new TypedValue();

            TypedArray a = this.obtainStyledAttributes(typedValue.data, new int[]{R.attr.colorPrimaryDark});
            orange = getResources().getColor(R.color.white);
            a.recycle();
        } else {
            orange = getResources().getColor(R.color.bottomtab_item_resting);
        }
//        final int orange = getResources().getColor(R.color.colorPrimaryDark);

        final ValueAnimator colorAnim = ObjectAnimator.ofFloat(0f, 1f);
        colorAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float mul = (Float) animation.getAnimatedValue();
                int alphaOrange = adjustAlpha(orange, mul);
                v.setColorFilter(orange, PorterDuff.Mode.SRC_IN);

            }
        });

        colorAnim.setDuration(100);
        colorAnim.start();

    }

    public int adjustAlpha(int color, float factor) {
        Log.e(TAG, "adjustAlpha: " + Color.alpha(color));
        int alpha = Color.alpha(color);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    // Method for disabling ShiftMode of BottomNavigationView
    @SuppressLint("RestrictedApi")
    private void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        Log.e(TAG, "onBackPressed: " + fm.findFragmentByTag("2").isHidden());
        if (fm.findFragmentByTag("2").isHidden()) {
            if (isTV)
                btClick(findViewById(R.id.bottom_navigation_container1));
            else
                navigation.setSelectedItemId(R.id.navigation_dashboard);
        } else {
            super.onBackPressed();
        }
    }

    public void setupBottomNavBehaviors() {
//        bottomNavigation.setBehaviorTranslationEnabled(false);

        /*
        Before enabling this. Change MainActivity theme to MyTheme.TranslucentNavigation in
        AndroidManifest.
        Warning: Toolbar Clipping might occur. Solve this by wrapping it in a LinearLayout with a top
        View of 24dp (status bar size) height.
         */
        bottomNavigation.setTranslucentNavigationEnabled(false);
    }

    /**
     * Adds styling properties to {@link AHBottomNavigation}
     */
    private void setupBottomNavStyle() {
        /*
        Set Bottom Navigation colors. Accent color for active item,
        Inactive color when its view is disabled.
        Will not be visible if setColored(true) and default current item is set.
         */
        bottomNavigation.setDefaultBackgroundColor(Color.WHITE);
        bottomNavigation.setAccentColor(fetchColor(R.color.bottomtab_0));
        bottomNavigation.setInactiveColor(fetchColor(R.color.bottomtab_item_resting));

        // Colors for selected (active) and non-selected items.
        bottomNavigation.setColoredModeColors(Color.WHITE,
                fetchColor(R.color.bottomtab_item_resting));

        //  Enables Reveal effect
        bottomNavigation.setColored(true);

        //  Displays item Title always (for selected and non-selected items)
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
    }

    private void addBottomNavigationItems() {
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.title_dashboard, R.drawable.ic_web_asset_black_24dp, colors[0]);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.title_tutor, R.drawable.ic_contacts_black_24dp, colors[1]);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.title_notifications, R.drawable.ic_person_black_24dp, colors[2]);

        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
    }

    /**
     * Simple facade to fetch color resource, so I avoid writing a huge line every time.
     *
     * @param color to fetch
     * @return int color value.
     */
    private int fetchColor(@ColorRes int color) {
        return ContextCompat.getColor(this, color);
    }
}
