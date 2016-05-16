package com.example.asus.pooldemos;

import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Button btn_newqqmsg;
    private SoundPool pool;
    private Map<String, Integer> poolMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_newqqmsg = (Button) findViewById(R.id.btn_newqqmsg);

        poolMap = new HashMap<String, Integer>();//容器
        // 第一个参数为音频池最多支持装载多少个音频，就是音频池的大小；
        // 第二个参数指定声音的类型，在AudioManager类中以常量的形式定义，
        // 一般指定为AudioManager.STREAM_MUSIC即可；
        // 第三个参数为音频的质量，默认为0，这个参数为预留参数，现在没有实际意义，为扩展预留字段，一般传0即可
        pool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        // 装载音频进音频池，并且把ID记录在Map中
        poolMap.put("newweibotoast", pool.load(this, R.raw.ceshi, 1));

        pool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {

            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId,
                                       int status) {
                //    每次装载完成均会回调
                // 当前装载完成ID为map的最大值，即为最后一次装载完成
                if (sampleId == poolMap.size()) {
                    btn_newqqmsg.setOnClickListener(click);
                    // 进入应用播放四次声音
                    //第一个参数是歌曲的ID，第二个和第三个参数是左右声道的音量，值为0f--1.0f
                    //第四一个参数是音频的质量，暂时没有什么意义，给0就可以
                    //第五个参数是循环的次数，0为播放一次，-1为循环播放，如给3就是播放4次
                    //第六个参数是播放的速率，值为0--2之间，1表示正常播放
                    pool.play(poolMap.get("newweibotoast"), 1.0f, 1.0f, 0, 3,
                            1.0f);
                }
            }
        });
    }

    private View.OnClickListener click = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.btn_newqqmsg:
                    if (pool != null) {
                        pool.play(poolMap.get("newweibotoast"), 1.0f, 1.0f, 0, 0, 1.0f);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        // 销毁的时候释放SoundPool资源
        if (pool != null) {
            pool.release();
            pool = null;
        }
        super.onDestroy();
    }
}
