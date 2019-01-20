package com.tinayang.game2048;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class GameView extends Activity {
    int[][] xu = {{R.id.tv1,R.id.tv2,R.id.tv3,R.id.tv4},
        {R.id.tv5,R.id.tv6,R.id.tv7,R.id.tv8},
        {R.id.tv9,R.id.tv10,R.id.tv11,R.id.tv12},
        {R.id.tv13,R.id.tv14,R.id.tv15,R.id.tv16}};
    TextView[][] card = new TextView[4][4];
    TextView tvScore;
    Button btn_exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.gameview);
        InitGame();
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });
    }



    /**
     * 手指移动
     */
    float startX=0,startY=0,offX=0,offY=0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                offX = event.getX()-startX;
                offY = event.getY()-startY;
                if(Math.abs(offX)>Math.abs(offY)){
                    if(offX>5){
                        Right();
                    }else if(offX<-5){
                        Left();
                    }
                }else if(Math.abs(offX)<Math.abs(offY)){
                    if(offY>5) {
                        Down();
                    }else if(offY<-5){
                        Up();
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 上下左右
     */
    public void Right(){
        boolean flag = false;
        for(int x=0;x<4;x++){
            for(int y=3;y>=0;y--){
                for(int y1=y-1;y1>=0;y1--){
                    if(card[x][y1].getText()!=""){
                        int num = Integer.parseInt(card[x][y1].getText().toString());
                        if(card[x][y].getText()==""){
                            card[x][y].setText(num+"");
                            card[x][y1].setText("");
                            y++;
                            flag=true;
                            break;
                        }else if(equals(card[x][y],card[x][y1])){
                            num*=2;
                            card[x][y].setText(num+"");
                            card[x][y1].setText("");
                            int score = Integer.parseInt(tvScore.getText().toString());
                            score+=num;
                            tvScore.setText(score+"");
                            flag=true;
                            break;
                        }else{
                            break;
                        }
                    }
                }
            }
        }
        if(flag) {
            RandNum();
            StopGame();
        }
    }
    public void Left(){
        boolean flag=false;
        for(int x=0;x<4;x++){
            for(int y=0;y<4;y++){
                for(int y1=y+1;y1<4;y1++){
                    if(card[x][y1].getText()!=""){
                        int num = Integer.parseInt(card[x][y1].getText().toString());
                        if(card[x][y].getText()==""){
                            card[x][y].setText(num+"");
                            card[x][y1].setText("");
                            y--;
                            flag=true;
                            break;
                        }else if(equals(card[x][y],card[x][y1])){
                            num*=2;
                            card[x][y].setText(num+"");
                            card[x][y1].setText("");
                            int score = Integer.parseInt(tvScore.getText().toString());
                            score+=num;
                            tvScore.setText(score+"");
                            flag=true;
                            break;
                        }else{
                            break;
                        }
                    }
                }
            }
        }
        if(flag) {
            RandNum();
            StopGame();
        }

    }
    public void Up(){
        boolean flag=false;
        for(int y=0;y<4;y++){
            for(int x=0;x<4;x++){
                for(int x1=x+1;x1<4;x1++){
                    if(card[x1][y].getText()!=""){
                        int num = Integer.parseInt(card[x1][y].getText().toString());
                        if(card[x][y].getText()==""){
                            card[x][y].setText(num+"");
                            card[x1][y].setText("");
                            x--;
                            flag=true;
                            break;
                        }else if(equals(card[x][y],card[x1][y])){
                            num*=2;
                            card[x][y].setText(num+"");
                            card[x1][y].setText("");
                            int score = Integer.parseInt(tvScore.getText().toString());
                            score+=num;
                            tvScore.setText(score+"");
                            flag=true;
                            break;
                        }else{
                            break;
                        }
                    }
                }
            }
        }
        if(flag) {
            RandNum();
            StopGame();
        }
    }
    public void Down(){
        boolean flag=false;
        for(int y=0;y<4;y++){
            for(int x=3;x>=0;x--){
                for(int x1=x-1;x1>=0;x1--){
                    if(card[x1][y].getText()!=""){
                        int num = Integer.parseInt(card[x1][y].getText().toString());
                        if(card[x][y].getText()==""){
                            card[x][y].setText(num+"");
                            card[x1][y].setText("");
                            x++;
                            flag=true;
                            break;
                        }else if(equals(card[x][y],card[x1][y])){
                            num*=2;
                            card[x][y].setText(num+"");
                            card[x1][y].setText("");
                            int score = Integer.parseInt(tvScore.getText().toString());
                            score+=num;
                            tvScore.setText(score+"");
                            flag=true;
                            break;
                        }else{
                            break;
                        }
                    }
                }
            }
        }
        if(flag) {
            RandNum();
            StopGame();
        }
    }
    /**
     * 判断游戏结束
     */
    public void StopGame(){
        int flag=1;   //flag = 0;游戏继续，1游戏失败，2游戏胜利
        All:
        for(int x=0;x<4;x++){
            for(int y=0;y<4;y++){
                if(card[x][y].getText()==""||(x>1&&equals(card[x][y],card[x-1][y]))||
                        (x<3&&equals(card[x][y],card[x+1][y]))||(y>1&&equals(card[x][y],card[x][y-1]))
                        ||(y<3&&equals(card[x][y],card[x][y+1]))){
                    flag=0;
                }else if(card[x][y].getText().equals("2048")){
                    flag = 2;
                    break All;
                }
            }
        }
        if(flag == 1){
            new AlertDialog.Builder(this)
                    .setTitle("2048")
                    .setMessage("好可惜，失败了！")
                    .setNegativeButton("重玩", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                           InitGame();
                        }
                    })
                    .setNeutralButton("退出", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            System.exit(0);
                        }
                    })
                    .show();
        }else if(flag == 2){
            new AlertDialog.Builder(this)
                    .setTitle("2048")
                    .setMessage("恭喜你胜利啦！！！")
                    .setNegativeButton("重玩", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            InitGame();
                        }
                    })
                    .setNeutralButton("退出", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            System.exit(0);
                        }
                    })
                    .show();
        }
    }
    /**
     * 卡片宽
     * @return
     */
    public int CardWidth(){
        int ScreenWidth;
        int ScreenHeight;
        WindowManager window = getWindowManager();
        Display display = window.getDefaultDisplay();
        ScreenWidth = display.getWidth();
        ScreenHeight = display.getHeight();
        return (Math.min(ScreenWidth,ScreenHeight)-10)/4;
    }

    /**
     * 初始化游戏
     */
    public void InitGame(){
        findview();
        RandNum();
        RandNum();
    }
    /**
     * 关联
     */
    public void findview(){
        for(int x=0;x<4;x++){
            for(int y=0;y<4;y++){
                card[x][y] = (TextView) findViewById(xu[x][y]);
                card[x][y].setHeight(CardWidth());
                card[x][y].setWidth(CardWidth());
                card[x][y].setText("");
            }
        }
        tvScore = (TextView) findViewById(R.id.tvScore);
        btn_exit = (Button) findViewById(R.id.btn_exit);
    }

    /**
     * 随机产生一个数
     */
    public void RandNum(){
        Random random = new Random();
        int num = random.nextInt(10);
        if(num<1){
            num=4;
        }else{
            num=2;
        }
        setNum(num);
    }

    /**
     * 设置数字
     * @param num
     */
    public void setNum(int num){
        Random random = new Random();
        while(true){
            int x = random.nextInt(4);
            int y = random.nextInt(4);
            if(card[x][y].getText()==""){
                card[x][y].setText(num+"");
                break;
            }
        }
        for(int x=0;x<4;x++){
            for(int y=0;y<4;y++){
                if (card[x][y].getText()==""||card[x][y].getText().equals("2")){
                    int id = getResources().getColor(R.color.whitegray);
                    card[x][y].setBackgroundColor(id);
                } else if (card[x][y].getText().equals("4")) {
                    int id = getResources().getColor(R.color.c4);
                    card[x][y].setBackgroundColor(id);
                } else if (card[x][y].getText().equals("8")) {
                    int id = getResources().getColor(R.color.c8);
                    card[x][y].setBackgroundColor(id);
                } else if (card[x][y].getText().equals("16")) {
                    int id = getResources().getColor(R.color.c16);
                    card[x][y].setBackgroundColor(id);
                } else if (card[x][y].getText().equals("32")) {
                    int id = getResources().getColor(R.color.c32);
                    card[x][y].setBackgroundColor(id);
                } else if (card[x][y].getText().equals("64")) {
                    int id = getResources().getColor(R.color.c64);
                    card[x][y].setBackgroundColor(id);
                }else{
                    int id = getResources().getColor(R.color.c其他);
                    card[x][y].setBackgroundColor(id);
                }
            }
        }
    }

    public boolean equals(TextView a,TextView b) {
        return a.getText().equals(b.getText());
    }
}
