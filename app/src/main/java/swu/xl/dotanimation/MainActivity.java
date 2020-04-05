package swu.xl.dotanimation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    //资源数组
    private int[] resIDs = new int[]{
            R.id.ib_b,
            R.id.ib_c,
            R.id.ib_d,
            R.id.ib_e,
            R.id.ib_f,
            R.id.ib_g,
            R.id.ib_h
    };

    //记录菜单是否打开
    private boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.ib_a).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //找到每一个控件
                for (int i = 0; i < resIDs.length; i++) {
                    //每一个控件进行动画
                    animate(i,isOpen);
                }

                //改变菜单的状态
                isOpen = !isOpen;
            }
        });

    }

    /**
     * 某一个ImageButton组合动画
     * @param resIDIndex ImageButton的资源ID在资源数组中的索引
     * @param isOpen 当前菜单的状态 用来指示动画是打开还是关闭
     */
    private void animate(int resIDIndex,boolean isOpen){
        //计算平分之后的度数
        double angle = (Math.PI / (resIDs.length + 1)) * (resIDIndex+1);

        //获取对应的控件
        ImageButton ib = findViewById(resIDs[resIDIndex]);

        //计算动画两点之间横向纵向的距离
        //length_x 0~90:正 90~180:负
        //length_y 0~180:负 向上动画 y是减小
        float length_x = (float) (Math.cos(angle) * 400);
        float length_y = - (float) (Math.sin(angle) * 400);

        //动画的起始点
        float fromX;
        float fromY;
        float toX;
        float toY;

        //插值器 控制动画速度
        Interpolator interpolator;

        //根据不同的动画设置不同的值
        if (isOpen){
            //平移的值
            fromX = length_x;
            toX = 0;
            fromY = length_y;
            toY = 0;

            //估值器 先退后在加速前进
            interpolator = new AnticipateInterpolator();
        }else {
            //平移的值
            fromX = 0;
            toX = length_x;
            fromY = 0;
            toY = length_y;

            //估值器 事后回弹
            interpolator = new BounceInterpolator();
        }

        //平移动画
        TranslateAnimation translateAnimation = new TranslateAnimation(fromX, toX,
                fromY, toY);
        //持续时间
        translateAnimation.setDuration(1000);
        //插值器
        translateAnimation.setInterpolator(interpolator);

        //旋转动画
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360 * 3,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        //持续时间
        rotateAnimation.setDuration(1000);

        //组合动画
        AnimationSet animationSet = new AnimationSet(false);
        //必须先添加旋转动画
        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(translateAnimation);
        //设置动画后不回复原样
        animationSet.setFillAfter(true);

        //开启动画
        ib.startAnimation(animationSet);
    }
}
