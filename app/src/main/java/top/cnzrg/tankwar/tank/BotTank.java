package top.cnzrg.tankwar.tank;

import android.app.Activity;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;

import java.util.Random;

import top.cnzrg.tankwar.data.GameData;

public class BotTank
        extends Tank {
    private static final String TAG = "BotTank";
    private int direct = 0;

    private final Handler mHandler = new Handler();
    private int moveCount = 0;
    private final int moveDist = 20;
    private final int moveInterval = 50;
    private final Random random = new Random();
    private boolean start = false;
    private final int[] lbr = {2, 3, 4};
    private final int[] ltr = {1, 2, 3};
    private final int[] tlb = {1, 2, 4};
    private final int[] trb = {1, 3, 4};

    public BotTank(View paramView, Activity paramActivity) {
        super(paramView, paramActivity);

        tankView.getLayoutParams().width = 200;
        tankView.getLayoutParams().height = 200;
    }

    public boolean isBot() {
        return true;
    }

    public void moveBottom(int dist) {
        move(0, dist);
    }

    public void moveLeft(int dist) {
        move(-dist, 0);
    }

    public void moveRight(int dist) {
        move(dist, 0);
    }

    public void moveTop(int dist) {
        move(0, -dist);
    }

    public void quitScene() {
        super.quitScene();
    }

    public void start() {
        Log.i("BotTank", "start() start = " + start);
        if (start) {
            return;
        }
        start = true;
        new Thread(() -> {
            while (!isQuitScene() && !GameData.QUITGAME) {
                int i = 0;
                direct = random.nextInt(4) + 1;
                moveCount = random.nextInt(15) + 5;
                while (i < moveCount) {
                    i++;
                    mHandler.post(() -> {
                        if ((tankView.getX() <= 0.0F) && (direct == 2)) {
                            direct = trb[random.nextInt(3)];
                            Log.i("BotTank", "碰到左边");
                        }
                        if ((BotTank.this.tankView.getY() <= 0.0F) && (BotTank.this.direct == 1)) {
                            direct = lbr[random.nextInt(3)];
                            Log.i("BotTank", "碰到上边");
                        }
                        if ((BotTank.this.tankView.getY() >= GameData.screenHeight - tankView.getHeight()) && (direct == 4)) {
                            direct = ltr[random.nextInt(3)];
                            Log.i("BotTank", "碰到下边");
                        }
                        if ((BotTank.this.tankView.getX() >= GameData.screenWidth - tankView.getWidth()) && (direct == 3)) {
                            direct = tlb[random.nextInt(3)];
                            Log.i("BotTank", "碰到右边");
                        }
                        switch (direct) {
                            default:
                                break;
                            case 4:
                                moveBottom(moveDist);
                                break;
                            case 3:
                                moveRight(moveDist);
                                break;
                            case 2:
                                moveLeft(moveDist);
                                break;
                            case 1:
                                moveTop(moveDist);
                        }

                    });

                    SystemClock.sleep(moveInterval);
                }

            }
        }).start();

        new Thread(() -> {
            while (!isQuitScene() && !GameData.QUITGAME) {
                mHandler.post(() -> openFire());
                SystemClock.sleep(500);
            }
        }).start();
    }
}
