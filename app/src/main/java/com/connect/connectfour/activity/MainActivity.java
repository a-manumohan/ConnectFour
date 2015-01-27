package com.connect.connectfour.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.connect.connectfour.R;
import com.connect.connectfour.game.ConnectFour;
import com.connect.connectfour.view.BoardView;


public class MainActivity extends ActionBarActivity implements ConnectFour.ConnectFourListener,BoardView.BoardViewListener{
    private ConnectFour mConnectFour;
    private BoardView mBoardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBoardView = (BoardView) findViewById(R.id.board_view);
        mBoardView.setBoardViewListener(this);

        mConnectFour = ConnectFour.getInstance(this);
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

        switch (id){
            case R.id.action_start:break;
            case R.id.action_reset:mConnectFour.reset();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void gameOver() {
        Toast toast = Toast.makeText(this,"Game Over",Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void won(ConnectFour.Player player) {
        Toast toast = Toast.makeText(this,"Player won",Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void ready() {

    }

    @Override
    public ConnectFour getConnectFour() {
        return mConnectFour;
    }

    @Override
    public void playColumn(int column) {
       mConnectFour.play(column);
    }
}
