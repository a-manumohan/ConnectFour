package com.connect.connectfour.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.connect.connectfour.R;
import com.connect.connectfour.game.ConnectFour;
import com.connect.connectfour.view.BoardView;


public class MainActivity extends ActionBarActivity implements ConnectFour.ConnectFourListener, BoardView.BoardViewListener {
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

        switch (id) {
            case R.id.action_reset:
                mConnectFour.reset();
                mBoardView.postInvalidate();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void gameOver() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Game Over");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mConnectFour.reset();
                mBoardView.postInvalidate();
            }
        });
        builder.setMessage("Draw!");
        builder.show();
    }

    @Override
    public void won(ConnectFour.Player player) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Game Over");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mConnectFour.reset();
                mBoardView.postInvalidate();
            }
        });
        if (player == ConnectFour.Player.FIRST_PLAYER) {
            builder.setMessage("First player won!");
        } else {
            builder.setMessage("Second player won!");
        }
        builder.show();
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
