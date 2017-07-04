package eu.swipefit.swipefit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mzule.fantasyslide.SideBar;
import com.github.mzule.fantasyslide.SimpleFantasyListener;
import com.github.mzule.fantasyslide.Transformer;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(getApplicationContext(),"onHover",Toast.LENGTH_LONG).show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final DrawerArrowDrawable indicator = new DrawerArrowDrawable(this);
        indicator.setColor(-1);
        getSupportActionBar().setHomeAsUpIndicator(indicator);
        setListener();
        this.drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        this.drawerLayout.setScrimColor(0);
        this.drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            public void onDrawerSlide(View drawerView, float slideOffset) {
                if (((ViewGroup) drawerView).getChildAt(1).getId() == R.id.leftSideBar) {
                    indicator.setProgress(slideOffset);
                }
            }
        });
        Toast.makeText(this,"onHover",Toast.LENGTH_LONG);
        SideBar leftSideBar = (SideBar) findViewById(R.id.leftSideBar);
        leftSideBar.setFantasyListener(new SimpleFantasyListener() {

            public boolean onHover(@Nullable View view) {
                Toast.makeText(getApplicationContext(),"onHover",Toast.LENGTH_LONG).show();
                return true;
            }

            public boolean onSelect(View view) {
                Toast.makeText(getApplicationContext(),"onSelect",Toast.LENGTH_LONG).show();
                return true;
            }


            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(),"onCancel",Toast.LENGTH_LONG).show();
            }
        });

    }
        private void setListener() {
            final TextView localTextView = (TextView)findViewById(R.id.tipView);
            ((SideBar)findViewById(R.id.leftSideBar)).setFantasyListener(new SimpleFantasyListener()
            {
                public void onCancel()
                {
                    localTextView.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(),"onCancel",Toast.LENGTH_LONG);
                }

                public boolean onHover(@Nullable View paramAnonymousView)
                {
                    Toast.makeText(getApplicationContext(),"onHover",Toast.LENGTH_LONG);
                    localTextView.setVisibility(View.VISIBLE);
                    if ((paramAnonymousView instanceof TextView)) {
                        localTextView.setText(((TextView)paramAnonymousView).getText());
                    }
                    for (;;)
                    {
                        //return false;
                        if ((paramAnonymousView != null) && (paramAnonymousView.getId() == R.id.userInfo)) {
                            localTextView.setText("TEST");
                        } else {
                            localTextView.setText(null);
                        }
                    }
                }

                public boolean onSelect(View paramAnonymousView)
                {
                    localTextView.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(),"onSelect",Toast.LENGTH_LONG);
                    return false;
                }
            });
        }

    public boolean onOptionsItemSelected(MenuItem paramMenuItem)
    {
        /*if (paramMenuItem.getItemId() == 16908332)
        {
            if (!this.drawerLayout.isDrawerOpen(8388611)) {
                break label34;
            }
            this.drawerLayout.closeDrawer(8388611);
        }
        for (;;)
        {
            return true;
            label34:
            this.drawerLayout.openDrawer(8388611);
        }*/
        Toast.makeText(getApplicationContext(),"onSelect",Toast.LENGTH_LONG);
        return true;
    }

    /*private void setTransformer()
    {
        //final float f = getResources().getDimensionPixelSize();
        ((SideBar)findViewById(R.id.leftSideBar)).setTransformer(new Transformer()
        {
            private View lastHoverView;

            private void animateIn(View paramAnonymousView)
            {
                float[] arrayOfFloat = new float[2];
                arrayOfFloat[0] = 0.0F;
                arrayOfFloat[1] = (-1.0f);
                ObjectAnimator localObjectAnimator = ObjectAnimator.ofFloat(paramAnonymousView, "translationX", arrayOfFloat);
                localObjectAnimator.setDuration(200L);
                localObjectAnimator.start();
            }

            private void animateOut(View paramAnonymousView)
            {
                if (paramAnonymousView == null) {}
                float[] arrayOfFloat = new float[2];
                arrayOfFloat[0] = (-1.0f);
                arrayOfFloat[1] = 0.0F;
                ObjectAnimator localObjectAnimator = ObjectAnimator.ofFloat(paramAnonymousView, "translationX", arrayOfFloat);
                localObjectAnimator.setDuration(200L);
                localObjectAnimator.start();

            }

            public void apply(ViewGroup paramAnonymousViewGroup, View paramAnonymousView, float paramAnonymousFloat1, float paramAnonymousFloat2, boolean paramAnonymousBoolean)
            {
                if ((paramAnonymousView.isPressed()) && (this.lastHoverView != paramAnonymousView))
                {
                    animateIn(paramAnonymousView);
                    animateOut(this.lastHoverView);
                    this.lastHoverView = paramAnonymousView;
                }
            }
        });*/





    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}

class DefaultTransformer implements Transformer {
    private float maxTranslationX;

    DefaultTransformer(float maxTranslationX) {
        this.maxTranslationX = maxTranslationX;
    }

    @Override
    public void apply(ViewGroup sideBar, View itemView, float touchY, float slideOffset, boolean isLeft) {
        float translationX;
        int centerY = itemView.getTop() + itemView.getHeight() / 2;
        float distance = Math.abs(touchY - centerY);
        float scale = distance / sideBar.getHeight() * 3;
        if (isLeft) {
            translationX = Math.max(0, maxTranslationX - scale * maxTranslationX);
        } else {
            translationX = Math.min(0, maxTranslationX - scale * maxTranslationX);
        }
        itemView.setTranslationX(translationX * slideOffset);
    }
}
