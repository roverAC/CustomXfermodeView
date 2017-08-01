package test.zhuangbi.com.porterduffxfermode_demo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 项目名称:zhuangbi
 * 创建人:hpf
 * 创建时间:2017/8/1 11:10
 */

public class XfferModeView extends View {

    private Path mPath;
    private Canvas mCanvas;
    private Bitmap mFgBitmap;
    private Bitmap mBgBitmap;
    private Paint mPaint;

    public XfferModeView(Context context) {
        this(context,null);
    }

    public XfferModeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public XfferModeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //设置画笔
        mPaint = new Paint();
        mPaint.setAlpha(0);  //为了显示擦除的效果
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        //设置画笔的xfermode
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

        //创建bitmap
        mBgBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_liao);
        mFgBitmap = Bitmap.createBitmap(mBgBitmap.getWidth(), mBgBitmap.getHeight(), Bitmap.Config.ARGB_8888);

        //创建画布
        mCanvas = new Canvas(mFgBitmap);
        mCanvas.drawColor(Color.GRAY);

        //绘制路径（手指移动路径）
        mPath = new Path();
    }

    //当手指移动的时候，进行重绘（达到刮卡效果）

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPath.reset();
                mPath.moveTo(event.getX(),event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                mPath.lineTo(event.getX(),event.getY());
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        mCanvas.drawPath(mPath,mPaint);
        invalidate();   //执行onDraw，进行重绘
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBgBitmap,0,0,null);
        canvas.drawBitmap(mFgBitmap,0,0,null);
    }
}
