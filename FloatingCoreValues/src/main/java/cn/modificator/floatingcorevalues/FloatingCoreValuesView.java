package cn.modificator.floatingcorevalues;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author yunshangcn@gmail.com
 */
public class FloatingCoreValuesView extends View {
    private String[] coreValues = new String[]{"富强", "民主", "文明", "和谐", "自由", "平等", "公正", "法治", "爱国", "敬业", "诚信", "友善"};
    private Paint paint;
    private float fontSize = 40;
    private int textIndex = 0;
    /**
     * 移动距离
     */
    private float moveDistance = 0;
    /**
     * 显示在屏幕上的文字集合
     */
    private List<FloatingText> floatingTexts;
    /**
     * 刷新回调
     */
    private Runnable invalidateCallback;


    public FloatingCoreValuesView(Context context) {
        super(context);
        init();
    }

    private void init() {
        fontSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, getResources().getDisplayMetrics());
        moveDistance = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, getResources().getDisplayMetrics());

        paint = new Paint();
        paint.setColor(0xFFff6651);
        paint.setTextSize(fontSize);
        paint.setTextAlign(Paint.Align.CENTER);

        floatingTexts = new ArrayList<>();

        invalidateCallback = new Runnable() {
            @Override
            public void run() {
                //不立即执行刷新，等待下一帧再刷新
                postInvalidateOnAnimation();
            }
        };
    }

    public FloatingCoreValuesView setFontSize(float fontSize) {
        this.fontSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, fontSize, getResources().getDisplayMetrics());
        paint.setTextSize(fontSize);
        return this;
    }

    public FloatingCoreValuesView setMoveDistance(float distance) {
        return setMoveDistance(TypedValue.COMPLEX_UNIT_SP, distance);
    }

    public FloatingCoreValuesView setMoveDistance(int unit, float distance) {
        this.moveDistance = TypedValue.applyDimension(unit, distance, getResources().getDisplayMetrics());
        paint.setTextSize(fontSize);
        return this;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Iterator<FloatingText> iterator = floatingTexts.iterator();
        //绘制，移出已完成的文字对象
        while (iterator.hasNext()) {
            FloatingText text = iterator.next();
            if (text.isFinish()) {
                iterator.remove();
                continue;
            }
            text.draw(canvas, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //点击添加文字对象到绘制列表
            floatingTexts.add(
                    FloatingText.create()
                            .setStartPoint(event.getX(), event.getY())
                            .setText(coreValues[textIndex])
                            .setMoveDistance(moveDistance)
                            .setInvalidateCallback(invalidateCallback)
                            .start()
            );
            //下一次显示的文字
            textIndex++;
            if (textIndex >= coreValues.length) {
                textIndex = 0;
            }
        }
        //点击穿透，不消费touch事件
        return false;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //资源回收
        invalidateCallback = null;
        Iterator<FloatingText> iterator = floatingTexts.iterator();

        while (iterator.hasNext()) {
            FloatingText text = iterator.next();
            text.cancel();
            iterator.remove();
        }
    }

    public static FloatingCoreValuesView apply(Activity activity) {
        FloatingCoreValuesView coreValuesView = new FloatingCoreValuesView(activity);
        ViewGroup rootView = (ViewGroup) activity.getWindow().getDecorView();
        rootView.addView(coreValuesView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return coreValuesView;
    }
}