package top.cnzrg.tankwar.fire;

import android.graphics.Rect;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import top.cnzrg.tankwar.data.GameData;
import top.cnzrg.tankwar.tank.Tank;

public class FireBall {
    private static final String TAG = "FireBall";
    public int dx;
    public int dy;
    private final ImageView imageView;
    private final Handler mHandler = new Handler();
    private final Rect rect1;
    private Tank tank;

    public FireBall(ImageView paramImageView) {
        this.imageView = paramImageView;
        this.rect1 = new Rect();
        moveRect();
    }

    private void isColl() {
        ArrayList localArrayList = GameData.getTanks();
        for (int i = 0; i < localArrayList.size(); i++) {
            Tank localTank = (Tank) localArrayList.get(i);
            if (this.tank == localTank)
                break;

            if ((!localTank.isQuitScene()) && (this.rect1.intersect(localTank.rect))) {
                Log.i("FireBall", "击中了");
                localTank.quitScene();
                GameData.newBotTankStart(localTank.activity);

                localArrayList.remove(localTank);
                i--;
            }
        }
    }

    private void move(int paramInt) {
        double d1 = Math.toDegrees(Math.atan2(this.dx, this.dy));
        if (d1 == 0.0D) {
            move(0, -200);
            return;
        }
        double d2 = Math.toRadians(d1);
        int i = (int) (paramInt * Math.sin(d2));
        int j = (int) (paramInt * Math.cos(d2));
        if (this.dx > 0) {
            i = Math.abs(i);
        }
        if (this.dx < 0) {
            i = -Math.abs(i);
        }
        if (this.dy > 0) {
            j = Math.abs(j);
        }
        if (this.dy < 0) {
            j = -Math.abs(j);
        }
        move(i, j);
    }

    private void move(int paramInt1, int paramInt2) {
        RelativeLayout.LayoutParams localLayoutParams = (RelativeLayout.LayoutParams) this.imageView.getLayoutParams();
        localLayoutParams.leftMargin = (paramInt1 + (int) this.imageView.getX());
        localLayoutParams.topMargin = (paramInt2 + (int) this.imageView.getY());
        this.imageView.setLayoutParams(localLayoutParams);
    }

    private void moveRect() {
        this.rect1.set((int) this.imageView.getX(), (int) this.imageView.getY(), (int) this.imageView.getX() + this.imageView.getWidth(), (int) this.imageView.getY() + this.imageView.getHeight());
        isColl();
    }

    public void move(Tank.FireBallCallBack fireBallCallBack) {
        new Thread(() -> {
            for (int i = 0; i < 50; i++) {
                FireBall.this.mHandler.post(() -> {
                    FireBall.this.move(30);
                    FireBall.this.moveRect();
                });
                SystemClock.sleep(30);
            }
            fireBallCallBack.onFinish();
        }).start();
    }

    public void setTank(Tank tank) {
        this.tank = tank;
    }
}
