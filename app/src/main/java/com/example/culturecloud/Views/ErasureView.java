package com.example.culturecloud.Views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.example.culturecloud.MyTools.PicTool;
import com.example.culturecloud.R;

import java.util.HashMap;

/**
 * Created by DELL on 2018/8/30.
 */

public class ErasureView extends ImageView {
    private Paint paint;
    Path path;
    private calc_pic calc_task;
    private int[] pixels ;

    private Bitmap front_Bitmap,back_Bitmap;
    private Bitmap fore_bitmap;

    HashMap<Integer,PointBean> points=new HashMap<Integer, PointBean>();
    private Canvas canvasTemp;//声明画布类

    private void initPaint() {
        paint =new Paint();
        paint.setColor(Color.RED);//设置画笔颜色
        paint.setStrokeWidth(25);//设置描边宽度
        BlurMaskFilter bmf = new BlurMaskFilter(40, BlurMaskFilter.Blur.NORMAL);//指定了一个模糊的样式和半径来处理Paint的边缘。
        paint. setMaskFilter(bmf);//为Paint分配边缘效果。
        paint.setStyle(Paint.Style.FILL);//让画出的图形是空心的
        //paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));//它的作用是用此画笔后，画笔划过的痕迹就变成透明色了。画笔设置好了后，就可以调用该画笔进行橡皮痕迹的绘制了
        paint.setStrokeJoin(Paint.Join.ROUND);//设置结合处的样子 Miter:结合处为锐角， Round:结合处为圆弧：BEVEL：结合处为直线。
        paint.setStrokeCap(Paint.Cap.ROUND);//画笔笔刷类型   方形形状
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        path = new Path();//实例化画图类

    }

    public ErasureView(Context context) {
        super(context);
        initPaint();
        calc_task = new calc_pic();
    }


    public ErasureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        calc_task = new calc_pic();
    }

    public Bitmap resizeBitmap(Bitmap bitmap,int bitmap_width,int bitmap_height){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float scaleWidth = (float)bitmap_width/width;
        float scaleHeight =(float)bitmap_height/height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newbm = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix,true);
        return newbm;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int pointerCount = event.getPointerCount();
        HashMap<Integer,PointBean> new_points=new HashMap<Integer, PointBean>();
        int id = 0;
        long time=System.currentTimeMillis();
        if (canvasTemp != null) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    break;
                case MotionEvent.ACTION_MOVE:
                    for (int i = 0; i < pointerCount; i++) {
                        int point_id=(int) event.getPointerId(i);
                        int x = (int) event.getX(i);
                        int y = (int) event.getY(i);
                        new_points.put(point_id,new PointBean(x,y,time));
                        if(points.containsKey(point_id)) {
                            PointBean old_point=points.get(point_id);

                            if(time-old_point.mCurTime<150) canvasTemp.drawLine(old_point.x,old_point.y,x,y,paint);
                        }
                    }
                    points=new_points;
                    break;
                case MotionEvent.ACTION_UP:
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    break;
            }
        }
        postInvalidate();//刷新界面
        return true;
    }

    public void setResourcesBitMap(Bitmap front_Bitmap,Bitmap back_Bitmap){
        this.front_Bitmap = resizeBitmap(front_Bitmap,1350,800);
        this.back_Bitmap =resizeBitmap(back_Bitmap,1350,800);
        this.setImageBitmap(back_Bitmap);
        initCanvas();
    }

    private void initCanvas(){
        int width = 1350; // 获取宽度
        int height = 800; // 获取高度
        pixels = new int[height*width];
        fore_bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        canvasTemp = new Canvas(fore_bitmap);
        canvasTemp.drawColor(Color.TRANSPARENT);
        front_Bitmap = resizeBitmap(front_Bitmap,width ,height);
        canvasTemp.drawBitmap(front_Bitmap,0,0,null);
        calc_task.execute();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(canvasTemp!=null){
            canvas.drawBitmap(this.fore_bitmap,0,0,null);
        }
    }

    public class PointBean {
        public long mCurTime=0;
        public int x;
        public int y;

        public PointBean(int x, int y, long mCurTime)
        {
            this.x=x;
            this.y=y;
            this.mCurTime=mCurTime;
        }
    }

    private class calc_pic extends AsyncTask<Void,Integer,Boolean>{
        @Override
        protected Boolean doInBackground(Void... voids) {
            int transparency = 100 ;
            while (true){
                if(isCancelled()){
                    return null;
                }
                if(canvasTemp!=null){
                    int sum=0;
                    int width =fore_bitmap.getWidth();
                    int height =fore_bitmap.getHeight();
                    fore_bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
                    for(int i=0;i<pixels.length;i++){
                        int color=pixels[i];
                        int A= Color.alpha(color);
                        sum+=A;
                    }
                    transparency  = sum/(pixels.length);
                    if(transparency>10&&transparency<160){
                        break;
                    }
                }
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            canvasTemp.drawRect(0,0,1350,800,paint);
        }
    }

    public void stop(){
        if(calc_task !=null && calc_task.getStatus() == AsyncTask.Status.RUNNING){
            calc_task.cancel(true);
        }
    }
}
