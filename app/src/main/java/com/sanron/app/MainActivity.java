package com.sanron.app;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.sanron.app.R;
import com.sanron.shortcuthelper.ShortcutHelper;

public class MainActivity extends AppCompatActivity {

    Intent intent = new Intent();

    {
        intent.setComponent(new ComponentName(getPackageName(), MainActivity.class.getName()));
        intent.setAction(Intent.ACTION_VIEW);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public void create(View view) {
        if (ShortcutHelper.isShortcutExist(this, "测试", intent)) {
            Toast.makeText(this, "已存在", Toast.LENGTH_SHORT).show();
            return;
        }

        ShortcutHelper.addShortcut(this, "测试", intent, true,
                BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
    }

    public void isExist(View view) {
        if (ShortcutHelper.isShortcutExist(this, "测试", intent)) {
            Toast.makeText(this, "已存在", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "不存在", Toast.LENGTH_SHORT).show();
        }
    }

    public void delete(View view) {
        ShortcutHelper.removeShortcut(this, "测试", intent);
    }
}
