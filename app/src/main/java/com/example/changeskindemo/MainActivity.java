package com.example.changeskindemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import ren.solid.skinloader.base.SkinBaseActivity;
import ren.solid.skinloader.listener.ILoaderListener;
import ren.solid.skinloader.load.SkinManager;
import ren.solid.skinloader.util.FileUtils;

/**
 * @author :Sea
 * Date :$ DATE $TIME
 * PackageName:$PACKAGE_NAME
 * Desc:
 */
public class MainActivity extends SkinBaseActivity {
    private static String SKIN_DIR;

    private TextView tvChangeSkin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SKIN_DIR = FileUtils.getSkinDirPath(MainActivity.this);

        tvChangeSkin = findViewById(R.id.tvChangeSkin);

        SkinManager.getInstance().restoreDefaultTheme();

        tvChangeSkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeBlackSkin();
            }

            private void changeBlackSkin() {
                String skinFullName = SKIN_DIR + File.separator + "skin_black.skin";
                FileUtils.moveRawToDir(MainActivity.this, "skin_black.skin", skinFullName);
                File skin = new File(skinFullName);
                if (!skin.exists()) {
                    Toast.makeText(MainActivity.this, "请检查" + skinFullName + "是否存在", Toast.LENGTH_SHORT).show();
                    return;
                }
                SkinManager.getInstance().load(skin.getAbsolutePath(),
                        new ILoaderListener() {
                            @Override
                            public void onStart() {
                                Log.i("==============", "loadSkinStart");
                            }

                            @Override
                            public void onSuccess() {
                                Log.i("==============", "loadSkinSuccess");
                                Toast.makeText(MainActivity.this, "切换成功", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailed() {
                                Log.i("==============", "loadSkinFail");
                                Toast.makeText(MainActivity.this, "切换失败", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}
