package top.cnzrg.tankwar.tank;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import java.util.Random;

import top.cnzrg.tankwar.data.GameData;

public class BotTank
        extends Tank {
    private static final String TAG = "BotTank";
    private int direct = 0;

    private Handler mHandler = new Handler();
    private int moveCount = 0;
    private int moveDist = 20;
    private int moveInterval = 50;
    private Random random = new Random();
    private boolean start = false;
    private int[] lbr = {2, 3, 4};
    private int[] ltr = {1, 2, 3};
    private int[] tlb = {1, 2, 4};
    private int[] trb = {1, 3, 4};

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
        new Thread(new Runnable() {
            public void run() {
                while (!isQuitScene() && !GameData.QUITGAME) {
                    int i = 0;
                    direct = random.nextInt(4) + 1;
                    moveCount = random.nextInt(15) + 5;
                    while (i < moveCount) {
                        i++;
                        mHandler.post(new Runnable() {
                            public void run() {
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

                            }
                        });
                        try {
                            Thread.sleep(moveInterval);
                        } catch (InterruptedException localInterruptedException) {
                            localInterruptedException.printStackTrace();
                        }
                    }

                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isQuitScene() && !GameData.QUITGAME) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            openFire();
                        }
                    });

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


            }
        }).start();
    }
}
