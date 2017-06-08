package fi.haagahelia.mkamka.camerablunoapp;


// import android.content.Context;
import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity  extends BlunoLibrary {
    private Button buttonScan;
    // private Button buttonSerialSend;
    // private EditText serialSendText;
    private TextView serialReceivedText;
    RelativeLayout main;
    RelativeLayout devicefound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onCreateProcess(); //onCreate Process by BlunoLibrary
        main = (RelativeLayout)findViewById(R.id.main);
        devicefound=(RelativeLayout)findViewById(R.id.devicefound);
        serialBegin(115200);													//set the Uart Baudrate on BLE chip to 115200

        serialReceivedText=(TextView) findViewById(R.id.serialReveicedText);	//initial the EditText of the received data

        buttonScan = (Button) findViewById(R.id.buttonScan);					//initial the button for scanning the BLE device
        buttonScan.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                buttonScanOnClickProcess();										//Alert Dialog for selecting the BLE device
            }
        });
        ImageView up = (ImageView) findViewById(R.id.up);
        //set the listener
        up.setOnTouchListener(new View.OnTouchListener() {
            private Handler mHandler = new Handler();

            private Runnable mAction = new Runnable() {
                @Override public void run() {
                    serialSend("u");
                    mHandler.postDelayed(this, 500);
                }
            };

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mHandler.postDelayed(mAction, 500);
                        return true;
                    // Do something
                    case MotionEvent.ACTION_UP:
                        mHandler.removeCallbacks(mAction);
                        return true;
                    case MotionEvent.ACTION_CANCEL:
                        mHandler.removeCallbacks(mAction);
                        return true;
                }
                return false;
            }
        });
        ImageView down = (ImageView) findViewById(R.id.down);
        //set the listener
        down.setOnTouchListener(new View.OnTouchListener() {
            private Handler mHandler = new Handler();

            private Runnable mAction = new Runnable() {
                @Override public void run() {
                    serialSend("d");
                    mHandler.postDelayed(this, 500);
                }
            };

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mHandler.postDelayed(mAction, 500);
                        return true;
                    // Do something
                    case MotionEvent.ACTION_UP:
                        mHandler.removeCallbacks(mAction);
                        return true;
                    case MotionEvent.ACTION_CANCEL:
                        mHandler.removeCallbacks(mAction);
                        return true;
                }
                return false;
            }
        });
        ImageView left = (ImageView) findViewById(R.id.left);
        //set the listener
        left.setOnTouchListener(new View.OnTouchListener() {
            private Handler mHandler = new Handler();

            private Runnable mAction = new Runnable() {
                @Override public void run() {
                    serialSend("l");
                    mHandler.postDelayed(this, 500);
                }
            };

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mHandler.postDelayed(mAction, 500);
                        return true;
                    // Do something
                    case MotionEvent.ACTION_UP:
                        mHandler.removeCallbacks(mAction);
                        return true;
                    case MotionEvent.ACTION_CANCEL:
                        mHandler.removeCallbacks(mAction);
                        return true;
                }
                return false;
            }
        });

        ImageView right = (ImageView) findViewById(R.id.right);
        //set the listener
        /*
		right.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				serialSend("r");				//send the data to the BLUNO
			}
		});
        */
        right.setOnTouchListener(new View.OnTouchListener() {
            private Handler mHandler = new Handler();

            private Runnable mAction = new Runnable() {
                @Override public void run() {
                    serialSend("r");
                    mHandler.postDelayed(this, 500);
                }
            };

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mHandler.postDelayed(mAction, 500);
                        return true;
                    // Do something
                    case MotionEvent.ACTION_UP:
                        mHandler.removeCallbacks(mAction);
                        return true;
                    case MotionEvent.ACTION_CANCEL:
                        mHandler.removeCallbacks(mAction);
                        return true;
                }
                return false;
            }
        });
    }

    protected void onResume(){
        super.onResume();
        System.out.println("BlUNOActivity onResume");
        onResumeProcess();														//onResume Process by BlunoLibrary
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        onActivityResultProcess(requestCode, resultCode, data);					//onActivityResult Process by BlunoLibrary
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        super.onPause();
        onPauseProcess();														//onPause Process by BlunoLibrary
    }

    protected void onStop() {
        super.onStop();
        onStopProcess();														//onStop Process by BlunoLibrary
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onDestroyProcess();														//onDestroy Process by BlunoLibrary
    }

    @Override
    public void onConectionStateChange(connectionStateEnum theConnectionState) {//Once connection state changes, this function will be called
        switch (theConnectionState) {											//Four connection state
            case isConnected:
                buttonScan.setText("Yhdistetty");
                devicefound.setVisibility(View.VISIBLE);
                break;
            case isConnecting:
                buttonScan.setText("Yhdistetään");
                break;
            case isToScan:
                buttonScan.setText("Etsi");
                devicefound.setVisibility(View.GONE);
                break;
            case isScanning:
                buttonScan.setText("Etsitään...");
                break;
            case isDisconnecting:
                buttonScan.setText("Katkaistaan Yhteyttä");
                break;
            default:
                break;
        }
    }

    @Override
    public void onSerialReceived(String theString) {							//Once connection data received, this function will be called
        // TODO Auto-generated method stub
        serialReceivedText.append(theString);							//append the text into the EditText
        //The Serial data from the BLUNO may be sub-packaged, so using a buffer to hold the String is a good choice.
        ((ScrollView)serialReceivedText.getParent()).fullScroll(View.FOCUS_DOWN);
    }

}