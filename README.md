# 嗯，FloatingCoreValuesView

FloatingCoreValuesView 是用来显示社会主义核显价值观的View，大概就是按下的时候显示一个核心价值观，之前在web上有看到，在Android上实现了这效果，不影响点击效果

![App preview](http://p7h38jxfd.bkt.clouddn.com/blogpic/2018-04-21-Kapture%202018-04-22%20at%201.15.58.gif)

## Getting Start

在页面onCreate 中添加`FloatingCoreValuesView.apply(this);就可以了

``` java
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingCoreValuesView.apply(this);
    }
}
```

如果要全局添加则在 Application 中添加

```java
//...
@Override
public void onCreate() {
    //...
    registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            FloatingCoreValuesView.apply(activity)
                .setFontSize(16)//设置字体大小，单位sp
                //.setMoveDistance(TypedValue.COMPLEX_UNIT_DIP, 256)
                .setMoveDistance(200);//设置移动距离，默认dp
        }
   //...
```



加入你的App吧，据说可以辟 ….