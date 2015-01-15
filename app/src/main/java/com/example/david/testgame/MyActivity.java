package com.example.david.testgame;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.david.json.ParseJson;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;


public class MyActivity extends ActionBarActivity {

    private TextView tipTextView;
    private EditText resultEditText;
    private SpeechRecognizer myRecognizer;
    private StringBuilder stringBuilder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        tipTextView=(TextView)findViewById(R.id.tipTextView);
        resultEditText = (EditText) findViewById(R.id.resultEidtView);
        SpeechUtility.createUtility(MyActivity.this, SpeechConstant.APPID + "=54ad0205");
    }

    public void StartPlay(View view) {
        tipTextView.setVisibility(View.VISIBLE);
        stringBuilder=new StringBuilder();
        myRecognizer = SpeechRecognizer.createRecognizer(MyActivity.this, myInitListener);
        setParameter();
        myRecognizer.startListening(myRecognizerListener);
    }

    public void StopPlay(View view) {

        if (tipTextView.getVisibility() == View.VISIBLE){
            myRecognizer.stopListening();
        }
        resultEditText.setText("");
    }

    private void setParameter() {
        myRecognizer.setParameter(SpeechConstant.PARAMS, null);
        myRecognizer.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        myRecognizer.setParameter(SpeechConstant.RESULT_TYPE, "json");
        myRecognizer.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        myRecognizer.setParameter(SpeechConstant.ACCENT, "mandarin");
        myRecognizer.setParameter(SpeechConstant.VAD_BOS, "4000");
        myRecognizer.setParameter(SpeechConstant.VAD_EOS, "1000");
    }

    private InitListener myInitListener = new InitListener() {
        @Override
        public void onInit(int i) {
            if (i != ErrorCode.SUCCESS) {
                resultEditText.setText("初始化失败");
            }
        }
    };

    private RecognizerListener myRecognizerListener = new RecognizerListener() {
        @Override
        public void onVolumeChanged(int i) {

        }

        @Override
        public void onBeginOfSpeech() {

        }

        @Override
        public void onEndOfSpeech() {

        }

        @Override
        public void onResult(RecognizerResult recognizerResult, boolean b) {
            ParseJson myParseJson =new ParseJson(recognizerResult.getResultString());
            if (myParseJson.isLast()){
                //接收数据完毕
                tipTextView.setVisibility(View.INVISIBLE);
                stringBuilder.append(myParseJson.getResultString());
                resultEditText.setText(stringBuilder.toString());
                stringBuilder=null;
            }else{
                //继续接收数据
                stringBuilder.append(myParseJson.getResultString());
            }
        }

        @Override
        public void onError(SpeechError speechError) {
            speechError.getPlainDescription(true);
        }

        @Override
        public void onEvent(int i, int i2, int i3, Bundle bundle) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myRecognizer.cancel();
        myRecognizer.destroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_exit) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
