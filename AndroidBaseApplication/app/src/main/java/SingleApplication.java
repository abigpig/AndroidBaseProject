import android.support.multidex.MultiDexApplication;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

public class SingleApplication extends MultiDexApplication {

    //MultiDexApplication  :: 方法数越界的解决方案 https://www.cnblogs.com/chenxibobo/p/6076459.html

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @GlideModule
    public final class MyAppGlideModule extends AppGlideModule {
        // init Glide
    }
}
