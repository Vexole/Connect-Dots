package com.example.bhupeshshrestha.connectdots;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    int activePlayer = 0;  // 0 = red, 1 = yellow
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};  // 2 = unplayed
    int[][] winningPositions = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};
    String winner = "";
    boolean gameIsActive = true;

    public void dropIn(View view){
        ImageView counter = (ImageView) view;
        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if(gameState[tappedCounter] == 2 && gameIsActive){
            gameState[tappedCounter] = activePlayer;

            if(activePlayer==0){
                counter.setImageResource(R.drawable.red);
                activePlayer=1;
            }else{
                counter.setImageResource(R.drawable.yellow);
                activePlayer=0;
            }
            counter.setTranslationY(-1000f);
            counter.animate().translationYBy(1000f).alpha(1f).rotation(36).setDuration(500);

            for(int[] winningPosition: winningPositions){
                if(gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                        gameState[winningPosition[1]] == gameState[winningPosition[2]] && gameState[winningPosition[0]] != 2){
                    if(gameState[winningPosition[0]] == 0){
                         winner = "RED";
                    }else{
                         winner = "YELLOW";
                    }
                    TextView message = (TextView) findViewById(R.id.message);
                    LinearLayout playAgainLayout = (LinearLayout) findViewById(R.id.playAgainLayout);
                    playAgainLayout.setVisibility(View.VISIBLE);
                    message.setText("The Winner is : " + winner);
                    gameIsActive = false;
                }else{
                    boolean gameIsOver = true;
                    for(int counterState: gameState){
                        if(counterState == 2) {
                            gameIsOver = false;
                        }
                    }
                    if(gameIsOver){
                        TextView message = (TextView) findViewById(R.id.message);
                        LinearLayout playAgainLayout = (LinearLayout) findViewById(R.id.playAgainLayout);
                        playAgainLayout.setVisibility(View.VISIBLE);
                        message.setText("It's a Draw!!!");
                        gameIsActive = false;
                    }
                }
            }
       }
    }

    public void playAgainBtn(View view){
        LinearLayout playAgainLayout = (LinearLayout) findViewById(R.id.playAgainLayout);
        playAgainLayout.setVisibility(View.INVISIBLE);

        activePlayer = 0;
        winner = "";
        for (int i = 0; i<gameState.length; i++){
            gameState[i] = 2;
        }

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        for(int i=0; i<gridLayout.getChildCount(); i++){
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }
        gameIsActive = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
