package cn.modificator.floatcorevalues;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.TypedValue;

import cn.modificator.floatingcorevalues.FloatingCoreValuesView;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                FloatingCoreValuesView.apply(activity)
                        .setFontSize(16)
                        //.setMoveDistance(TypedValue.COMPLEX_UNIT_DIP, 256)
                        .setMoveDistance(200);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }
}
