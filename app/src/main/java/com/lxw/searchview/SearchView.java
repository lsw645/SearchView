package com.lxw.searchview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * <pre>
 *     author : lxw
 *     e-mail : lsw@tairunmh.com
 *     time   : 2017/06/08
 *     desc   :
 * </pre>
 */

public class SearchView extends View {
    private Paint mPaint;
    private int mState;
    private   final static int STATE_ANIM_NONE=0;
    public   final static int STATE_ANIM_START=1;
    public  final static int STATE_ANIM_STOP=2;
    private final  static int DEFAULT_DURATION=3000;
    private final static  int DEFAULT_LINE_LENGTH=70;
    private RectF mRectF;
    private int mCircleX;
    private int mCircleY;
    private int mRadius;
    private float fraction;

    public SearchView(Context context) {
        super(context);
        init();
    }

    public SearchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mState=STATE_ANIM_NONE;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        mPaint.setColor(Color.WHITE);
        mRectF=new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.parseColor("#3ac869"));
        mCircleX=getWidth()/2;
        mCircleY=getHeight()/2;
        mRadius=50;
        if(mState==STATE_ANIM_NONE){
            normalAnimationView(canvas);
        }else if(mState==STATE_ANIM_START){
            startAnimationView(canvas);
        }else if(mState==STATE_ANIM_STOP){
            startAnimationView(canvas);
        }
    }

    private void startAnimationView(Canvas canvas) {
        if(fraction<=0.5){
            canvas.save();
            mCircleX= (int) (getWidth()/2+fraction*300);
            canvas.rotate(45,mCircleX,mCircleY);
            mRectF.left=mCircleX-mRadius;
            mRectF.top=mCircleY-mRadius;
            mRectF.right=mRectF.left+2*mRadius;
            mRectF.bottom=mRectF.top+2*mRadius;
            canvas.drawArc( mRectF,0,-(1-fraction*2)*360,false,mPaint);
            canvas.drawLine(mRectF.right,
                    mCircleY,
                    mRectF.right+DEFAULT_LINE_LENGTH,
                    mCircleY,
                    mPaint);
            canvas.restore();
        }else{
            canvas.save();
            mCircleX= (int) (getWidth()/2+fraction*300);
            canvas.rotate(45,mCircleX,mCircleY);
            mRectF.left=mCircleX-mRadius;
            mRectF.right=mRectF.left+2*mRadius;
            canvas.drawLine(mRectF.right+DEFAULT_LINE_LENGTH,
                    mCircleY,
                    mRectF.right+DEFAULT_LINE_LENGTH*2*(fraction-0.5f),
                    mCircleY,
                    mPaint);
            canvas.restore();
        }

        widthFirst= (int) (mCircleX+(DEFAULT_LINE_LENGTH+mRadius)/Math.sqrt(2));
        widthEnd =getWidth()-widthFirst;
        offset=widthFirst-widthEnd;
        canvas.drawLine(widthFirst,
                (float) (getHeight()/2+(DEFAULT_LINE_LENGTH+mRadius)/Math.sqrt(2)),
                widthEnd+offset*(1-fraction),
                (float) (getHeight()/2+(DEFAULT_LINE_LENGTH+mRadius)/Math.sqrt(2)),
                mPaint);
    }
    int offset;
    int widthFirst,widthEnd ;

    private void normalAnimationView(Canvas canvas) {
        canvas.save();
        canvas.rotate(45,mCircleX,mCircleY);
        mRectF.left=mCircleX-mRadius;
        mRectF.top=mCircleY-mRadius;
        mRectF.right=mRectF.left+2*mRadius;
        mRectF.bottom=mRectF.top+2*mRadius;
        canvas.drawArc(mRectF,0,360,false,mPaint);
        canvas.drawLine(mRectF.right,mCircleY,mRectF.right+DEFAULT_LINE_LENGTH,mCircleY,mPaint);
        canvas.restore();
    }

   public void  startAnimation(int state){
       mState=state;
       ValueAnimator animator;
       if(mState==STATE_ANIM_START){
           animator=ValueAnimator.ofFloat(0,1);
       }else {
           animator=ValueAnimator.ofFloat(1,0);
       }

       animator.setDuration(DEFAULT_DURATION);
       animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
           @Override
           public void onAnimationUpdate(ValueAnimator animation) {
              fraction= (float) animation.getAnimatedValue();
               invalidate();
           }
       });
       animator.start();
   }
}
