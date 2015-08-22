package by.drobmax.stopwatchtesttask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import by.drobmax.stopwatchtesttask.adapters.TimeListAdapter;
import by.drobmax.stopwatchtesttask.database.TimeListDataProvider;
import by.drobmax.stopwatchtesttask.interfaces.OnDataChangedListener;
import by.drobmax.stopwatchtesttask.models.TimeListModel;
import by.drobmax.teeeeeeeest.R;


public class MainActivity extends ActionBarActivity implements OnDataChangedListener {
    private Button btnClick;
    private TextView tvTime;
    private ListView listViewTimes;
    private ScrollView scrollView;
    private long tickTime = 0;
    private Handler handler;
    private Runnable saveTimeList;
    private Vibrator vibrator;
    private int mAction = 0, mKeyCode = 0;
    private long time = 0;
    private Chronometer chr;
    private boolean isFirst = false;
    private TimeListDataProvider dataProvider;
    private TimeListAdapter adapter;

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
                        String text = tvTime.getText().toString();
                        tvTime.setText("");
                        dataProvider = new TimeListDataProvider(MainActivity.this);
                        dataProvider.saveTimes(text.replaceAll("\n", "; "));
                        onDataChange();
                        vibrator.vibrate(500);
                        chr.stop();
                        chr.setBase(SystemClock.elapsedRealtime());
                        isFirst = true;
                    }
                });
            }
        };
        btnClick = (Button) findViewById(R.id.btnClick);
        tvTime = (TextView) findViewById(R.id.textView);
        chr = (Chronometer) findViewById(R.id.chronometer);
        listViewTimes = (ListView) findViewById(R.id.listView);
        scrollView = (ScrollView)findViewById(R.id.scrollView);

        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnClick.setEnabled(false);
                Toast.makeText(MainActivity.this, "select click button",
                        Toast.LENGTH_SHORT).show();
            }
        });
        onDataChange();
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();

        //if click button not setted
        if (!btnClick.isEnabled()) {
            setClickButton(action, keyCode);
            return true;
        }

        if ((action == mAction) && (keyCode == mKeyCode)) {
            chr.setBase(SystemClock.elapsedRealtime());
            chr.start();
            tickTime = (System.currentTimeMillis() - time);
            time = System.currentTimeMillis();
            if (isFirst) {
                Log.d("logs", "isFirst");
                isFirst = false;
            } else {
                handler.removeCallbacks(saveTimeList);
                Log.d("logs", "not isFirst");
                tvTime.append(tickTime + "\n");
                scrollView.smoothScrollTo(0,tvTime.getBottom());
            }
            handler.postDelayed(saveTimeList, 10000);
            return true;
        }

        return super.dispatchKeyEvent(event);

    }

    private void setClickButton(int action, int keyCode) {
        mAction = action;
        mKeyCode = keyCode;
        vibrator.vibrate(500);
        Toast.makeText(this, "selected!", Toast.LENGTH_SHORT).show();
        isFirst = true;
        btnClick.setEnabled(true);
        btnClick.setVisibility(View.GONE);
    }

    @Override
    public void onDataChange() {
        dataProvider = new TimeListDataProvider(this);
        dataProvider.getTimes(new TimeListDataProvider.TimesRequestCallbacks() {
            @Override
            public void onLocksLoaded(final ArrayList<TimeListModel> times) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new TimeListAdapter(times, MainActivity.this);
                        listViewTimes.setAdapter(adapter);
                        listViewTimes.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
                    }
                });
            }
        });
    }
}
