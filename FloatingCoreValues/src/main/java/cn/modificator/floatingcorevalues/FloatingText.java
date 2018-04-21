package cn.modificator.floatingcorevalues;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;

/**
 * @author yunshangcn@gmail.com
 */
public class FloatingText {
    /**
     * 显示的文字
     */
    private String text;
    /**
     * 文字起始 Y 坐标
     */
    private float startPointY;
    /**
     * 文字位置 X 坐标
     */
    private float pointX;
    /**
     * 文字位置 Y 坐标
     */
    private float pointY;
    /**
     * 文字移动距离
     */
    private float moveDistance = 400;
    /**
     * 文字透明度
     */
    private int alpha;
    /**
     * 动画完成，是否可以移除
     */
    private boolean isFinish = false;
    /**
     * 文字动画
     */
    private ValueAnimator textAnim;
    /**
     * 文字动画时间
     */
    private static final int FLOATING_TEXT_ANIM_DURATION = 1500;
    /**
     * View 刷新回调
     */
    private Runnable invalidateCallback;

    private FloatingText() {
    }

    public static FloatingText create() {
        return new FloatingText();
    }

    public FloatingText setStartPoint(float pointX, float pointY) {
        this.pointX = pointX;
        this.startPointY = this.pointY = pointY;
        return this;
    }

    public FloatingText setText(String text) {
        this.text = text;
        return this;
    }

    public FloatingText setMoveDistance(float moveDistance) {
        this.moveDistance = moveDistance;
        return this;
    }

    public FloatingText setInvalidateCallback(Runnable invalidateCallback) {
        this.invalidateCallback = invalidateCallback;
        return this;
    }

    public FloatingText start() {
        //动画改变文字效果
        textAnim = ValueAnimator.ofFloat(0, 1);
        textAnim.setDuration(FLOATING_TEXT_ANIM_DURATION);
        textAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float pct = (float) animation.getAnimatedValue();
                //改变文字的位置
                pointY = startPointY - moveDistance * pct;
                //改变文字的透明度
                alpha = (int) ((1 - pct) * 255);
                //回调View刷新
                if (invalidateCallback != null) {
                    invalidateCallback.run();
                }
            }
        });
        textAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //标记结束，以便删除此对象
                isFinish = true;
            }
        });
        textAnim.setInterpolator(new LinearOutSlowInInterpolator());
        textAnim.start();

        return this;
    }

    public void cancel() {
        if (textAnim == null) {return;}
        textAnim.cancel();
        isFinish = true;
    }

    public void draw(Canvas canvas, Paint paint) {
        //绘制文字
        paint.setAlpha(alpha);
        canvas.drawText(text, pointX, pointY, paint);
    }

    public boolean isFinish() {
        return isFinish;
    }
}
