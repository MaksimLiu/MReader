package com.maksimliu.mreader;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.maksimliu.mreader.base.BaseActivity;
import com.maksimliu.mreader.utils.FileUtil;
import com.maksimliu.mreader.utils.MLog;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;

/**
 * Created by MaksimLiu on 2017/3/22.
 */

public class PhotoViewerActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.image)
    PhotoView image;


    private String imgUrl;

    private String desc;

    private Bitmap bitmap;


    @Override
    protected void initVariable() {

    }

    @Override
    protected void initListener() {

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setTitle("");

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);

        setDialogWindows();



        imgUrl = getIntent().getStringExtra("imgUrl");
        desc = getIntent().getStringExtra("desc");

        MLog.i(desc);
        Glide.with(this).load(imgUrl).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                image.setImageBitmap(resource);
                bitmap = resource;
            }
        });


    }

    private void setDialogWindows() {

        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay();  //获取屏幕宽、高
        WindowManager.LayoutParams p = getWindow().getAttributes();  //获取对话框当前的参数值
        p.height = (int) (d.getHeight() * 1.0);   //高度设置为屏幕的1.0
        p.width = (int) (d.getWidth() * 1.0);    //宽度设置为屏幕的1.0
        p.dimAmount = 0.8f;     //设置黑暗度
        getWindow().setAttributes(p);
    }



    @Override
    protected int getLayoutId() {
        return R.layout.activity_photo_viewer;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.photoviewer_main, menu);

        return true;
    }

    private Intent createShareIntent() {


        File file = FileUtil.saveImage(desc, bitmap);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        intent.setType("image/*");

        return intent;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_share:

                startActivity(Intent.createChooser(createShareIntent(),"分享图片"));
                break;
            case R.id.menu_save:

                FileUtil.saveImage(desc, bitmap);
                Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;


        }
        return super.onOptionsItemSelected(item);
    }

}
