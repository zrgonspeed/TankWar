package top.cnzrg.tankwar.data;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Random;

import top.cnzrg.tankwar.R;
import top.cnzrg.tankwar.tank.BotTank;
import top.cnzrg.tankwar.tank.Tank;

public class GameData {
    public static boolean QUITGAME = false;
    public static int screenHeight;
    public static int screenWidth;
    private static final ArrayList<Tank> tanks = new ArrayList();

    public static void addOtherTank(Tank paramTank) {
        tanks.add(paramTank);
    }

    public static ArrayList<Tank> getTanks() {
        return tanks;
    }

    public static void newBotTankStart(Activity activity) {
        ImageView imageView = new ImageView(activity);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        imageView.setLayoutParams(layoutParams);
        imageView.setBackground(activity.getResources().getDrawable(R.drawable.tank_gzl).getConstantState().newDrawable());

        layoutParams.width = 200;
        layoutParams.height = 200;

        BotTank tank = new BotTank(imageView, activity);
        GameData.addOtherTank(tank);

        RelativeLayout layout = activity.findViewById(R.id.al_game_scene);
        layout.addView(imageView);

        // 新BOT位置
        layoutParams.leftMargin = new Random().nextInt(GameData.screenWidth - layoutParams.width);
        layoutParams.topMargin = new Random().nextInt(GameData.screenHeight - layoutParams.height);

        tank.initPosition();
        tank.start();
    }
}
