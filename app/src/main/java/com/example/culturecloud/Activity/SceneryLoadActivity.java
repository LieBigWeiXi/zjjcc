package com.example.culturecloud.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.example.culturecloud.Bean.PicturesBean;
import com.example.culturecloud.R;
import com.example.culturecloud.StaticResources.NetworkInfo;
import com.example.culturecloud.Views.ErasureView;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SceneryLoadActivity extends BaseActivity {
    public static Scenery m_scenery;
    LinearLayout erasureView;
    Button       return_btn;
    ErasureView  ev_scenery;
    download_bitmap task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenery_load);
        ev_scenery =(ErasureView)findViewById(R.id.ev_scenery) ;
        return_btn = (Button)findViewById(R.id.btn_id);
        task = new download_bitmap();
        return_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(task !=null && task.getStatus() == AsyncTask.Status.RUNNING){
                    task.cancel(true);
                }
                finish();
            }
        });
        PicturesBean picturesBean = (PicturesBean)getIntent().getSerializableExtra("picture");
        //执行异步线程任务
        task.execute(picturesBean);
    }

    private class download_bitmap extends AsyncTask<PicturesBean,Integer,Scenery>{
        public Bitmap getImageBitmap(String url) {
            URL imgUrl = null;
            Bitmap bitmap = null;
            try {
                imgUrl = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) imgUrl.openConnection();
                conn.setDoInput(true);
                conn.connect();
                InputStream is = conn.getInputStream();
                bitmap = BitmapFactory.decodeStream(is);
                is.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }
        //执行任务前自动调用
        @Override
        protected void onPreExecute() {
            Toast.makeText(SceneryLoadActivity.this,"开始加载",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(Scenery scenery) {
            if(scenery ==null){
                Toast.makeText(SceneryLoadActivity.this,"加载失败",Toast.LENGTH_SHORT).show();
            }else {
                //ShowImageView imageView = new ShowImageView(getApplicationContext(),1350,800,scenery);
                //erasureView.addView(imageView);
                ev_scenery.setResourcesBitMap(scenery.bp_old,scenery.bp_new);
            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
        }

        @Override
        protected Scenery doInBackground(PicturesBean... picturesBeans) {
            PicturesBean picturesBean = picturesBeans[0];
            Scenery scenery = new Scenery();
            scenery.bp_new = getImageBitmap(NetworkInfo.ip_address+picturesBean.getCi_new());
            if (scenery.bp_new == null){
                return null;
            }
            if(isCancelled()){
                return null;
            }
            publishProgress(1);
            scenery.bp_old = getImageBitmap(NetworkInfo.ip_address+picturesBean.getCi_old());
            if (scenery.bp_old == null){
                return null;
            }
            if(isCancelled()){
                return null;
            }
            publishProgress(2);
            return  scenery;
        }
    }

    public static class Scenery{
        public Bitmap bp_new;
        public Bitmap bp_old;
    }

    @Override
    protected void onDestroy() {
        ev_scenery.stop();
        super.onDestroy();
    }
}
