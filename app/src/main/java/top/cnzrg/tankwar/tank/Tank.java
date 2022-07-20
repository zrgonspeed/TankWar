package top.cnzrg.tankwar.tank;import android.app.Activity;import android.graphics.Rect;import android.util.Log;import android.view.View;import android.view.ViewGroup;import android.widget.ImageView;import android.widget.RelativeLayout;import java.util.Random;import top.cnzrg.tankwar.R;import top.cnzrg.tankwar.data.GameData;import top.cnzrg.tankwar.fire.FireBall;public class Tank {    private static final String TAG = "Tank";    public Activity activity;    private int dx;    private int dy;    private boolean quitScene = false;    public Rect rect;    private final RelativeLayout sceneLayout;    protected View tankView;    public Tank(View paramView, Activity paramActivity) {        this.tankView = paramView;        this.activity = paramActivity;        this.rect = new Rect();        this.sceneLayout = paramActivity.findViewById(R.id.al_game_scene);    }    private void moveRect() {        this.rect.set((int) this.tankView.getX(), (int) this.tankView.getY(), (int) this.tankView.getX() + this.tankView.getWidth(), (int) this.tankView.getY() + this.tankView.getHeight());    }    public View getTankView() {        return this.tankView;    }    public void initPosition() {        moveRect();    }    public boolean isBot() {        return false;    }    public boolean isQuitScene() {        return this.quitScene;    }    public void move(int dx, int dy) {        int left = tankView.getLeft() + dx;        int top = tankView.getTop() + dy;        int right = tankView.getRight() + dx;        int bottom = tankView.getBottom() + dy;        if (left < 0) {            left = 0;            right = left + tankView.getWidth();        }        if (right > GameData.screenWidth) {            right = GameData.screenWidth;            left = right - tankView.getWidth();        }        if (top < 0) {            top = 0;            bottom = top + tankView.getHeight();        }        if (bottom > GameData.screenHeight) {            bottom = GameData.screenHeight;            top = bottom - tankView.getHeight();        }        tankView.layout(left, top, right, bottom);        RelativeLayout.LayoutParams localLayoutParams = (RelativeLayout.LayoutParams) tankView.getLayoutParams();        localLayoutParams.leftMargin = ((int) tankView.getX());        localLayoutParams.topMargin = ((int) tankView.getY());        tankView.setLayoutParams(localLayoutParams);        double d = Math.toDegrees(Math.atan2(dx, dy));        moveRect();//        if (isBot()) {//            tankView.setRotation((float) (180.0D - d));//        }        if (d == 0.0D) {            return;        }//        if (!isBot()) {        tankView.setRotation((float) (180.0D - d));//        }        this.dx = dx;        this.dy = dy;    }    private final Random random = new Random();    public void openFire() {        Log.i("Tank", "开火");        ImageView localImageView = new ImageView(activity);        int ren = random.nextInt(4) + 1;        switch (ren) {            case 1:                localImageView.setBackground(activity.getResources().getDrawable(R.drawable.tank_zrg).getConstantState().newDrawable());                break;            case 2:                localImageView.setBackground(activity.getResources().getDrawable(R.drawable.tank_qyl).getConstantState().newDrawable());                break;            case 3:                localImageView.setBackground(activity.getResources().getDrawable(R.drawable.tank_mzy).getConstantState().newDrawable());                break;            case 4:                localImageView.setBackground(activity.getResources().getDrawable(R.drawable.tank_lrc).getConstantState().newDrawable());                break;        }        sceneLayout.addView(localImageView);        RelativeLayout.LayoutParams localLayoutParams = (RelativeLayout.LayoutParams) localImageView.getLayoutParams();        localLayoutParams.leftMargin = ((int) this.tankView.getX());        localLayoutParams.topMargin = ((int) this.tankView.getY());        localImageView.setLayoutParams(localLayoutParams);        localLayoutParams.width = 150;        localLayoutParams.height = 150;        FireBall localFireBall = new FireBall(localImageView);        localFireBall.setTank(this);        localFireBall.dx = this.dx;        localFireBall.dy = this.dy;        localFireBall.move();    }    public void quitScene() {        this.quitScene = true;        this.rect.setEmpty();        ViewGroup localViewGroup = (ViewGroup) this.tankView.getParent();        if (localViewGroup != null) {            localViewGroup.removeView(this.tankView);        }    }}