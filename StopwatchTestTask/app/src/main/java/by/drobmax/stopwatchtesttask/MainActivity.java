package by.drobmax.stopwatchtesttask;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import by.drobmax.teeeeeeeest.R;


public class MainActivity extends ActionBarActivity {
    private Button btnClick;
    private TextView tvTime;
    private long tickTime = 0;
    private Handler handler;
    private Runnable saveTimeList;
    private Vibrator vibrator;
    private int mAction = 0, mKeyCode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
    }

    private void initComponents() {
        handler = new Handler(Looper.getMainLooper());
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        saveTimeList = new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvTime.setText("");
                        vibrator.vibrate(500);
                        chr.stop();
                        chr.setBase(SystemClock.elapsedRealtime());
                        first = true;
                    }
                });
            }
        };
        btnClick = (Button) findViewById(R.id.btnClick);
        tvTime = (TextView) findViewById(R.id.textView);
        chr = (Chronometer) findViewById(R.id.chronometer);
        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnClick.setEnabled(false);
                Toast.makeText(MainActivity.this, "select click button",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private long time = 0;
    private Chronometer chr;
    private boolean first = false;

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();

        if (!btnClick.isEnabled()) {
            mAction = action;
            mKeyCode = keyCode;
            vibrator.vibrate(500);
            Toast.makeText(this, "selected!", Toast.LENGTH_SHORT).show();
            first = true;
            btnClick.setEnabled(true);
            btnClick.setVisibility(View.GONE);
            return true;
        }
        if ((action == mAction) && (keyCode == mKeyCode)) {
            if (first) {
                Log.d("logs", "first");
                first = false;
            } else {
                handler.removeCallbacks(saveTimeList);
                Log.d("logs", "not first");
                tickTime = (System.currentTimeMillis() - time);
                tvTime.append(tickTime + "\n");
            }
            handler.postDelayed(saveTimeList,10000);
            chr.setBase(SystemClock.elapsedRealtime());
            chr.start();
            time = System.currentTimeMillis();
            return true;
        } else {
            return super.dispatchKeyEvent(event);
        }
    }
}
