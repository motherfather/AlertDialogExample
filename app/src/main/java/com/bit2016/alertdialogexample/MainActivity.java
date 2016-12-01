package com.bit2016.alertdialogexample;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private boolean[] choices = {false, false, false, false, false, false}; // MultiChoice에서 사용중
    private int indexSingleChoiceSelected = 0; // SingleChoice에서 사용

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void dialogMessage( View view ) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this)
//        builder.setTitle("알림");
//        builder.setIcon(android.R.drawable.ic_dialog_alert);
//        builder.setMessage("Hello World\nHello World\n");

//        builder.show();

        // 체인으로 한방에 하기
        new AlertDialog.Builder(this)
                .setTitle("알림")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage("Hello\nWorld\n")
                .show();
    }

    public void dialogColseButton( View view ) {
        new AlertDialog.Builder(this)
                .setTitle("알림")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage("Hello\nWorld\n")
                .setCancelable(true)
                .setNegativeButton("닫기", null)
                .show();
    }

    public void dialogOKCancelButton( View view ) {
        new AlertDialog.Builder(this)
                .setTitle("알림")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage("Yes\nNo\nCancel")
                .setCancelable(true)
                .setNegativeButton("NO", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("NegativeButton", String.valueOf(which));
                    }
                })
                .setPositiveButton("YES", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("PositiveButton", String.valueOf(which));
                    }
                })
                .setNeutralButton("Calcel", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("NeuralButton", String.valueOf(which));
                    }
                })
                .show();
    }

    public void dialogList( View view ) {
        new AlertDialog.Builder(this)
                .setTitle("선택하세요")
                .setItems(R.array.lists, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("dialogList", "" + which);
                    }
                })
                .show();
    }

    public void dialogProgress( View vPew ) {
        // Builder는 alertDialog에서... 여기서 하면 프로그레스바? 안뜸!!
//        new ProgressDialog.Builder(this)
//                .setTitle("처리중")
//                .setIcon(android.R.drawable.ic_popup_reminder)
//                .setMessage("잠시만 기다려주세요")
//                .show();

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("처리중");
        progressDialog.setIcon(android.R.drawable.ic_popup_reminder);
        progressDialog.setMessage("잠시만 기다려주세요");
        progressDialog.show();
    }

    public void dialogSingleChoice( View view ) {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.stat_sys_vp_phone_call)
                .setTitle("Single Choice")
                .setSingleChoiceItems(R.array.lists, indexSingleChoiceSelected, new DialogInterface.OnClickListener(){ // 0은 디폴트값(하나는 꼭 선택되어야 하므로)

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("DialogSingleChioce", "" + which);
                        indexSingleChoiceSelected = which;
                    }
                })
                .setCancelable(true)
                .setPositiveButton("확인", null)
                .show();
    }

    public void dialogMultipleChoice( View view ) {
//        choices[0] = true; // 초기 설정
//        choices[1] = true; // 초기 설정
        new AlertDialog.Builder(this)
            .setIcon(android.R.drawable.stat_sys_vp_phone_call)
            .setTitle("Multi Choice")
            .setMultiChoiceItems(R.array.lists, choices, new DialogInterface.OnMultiChoiceClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                    Log.d("DialogMultiChioce", which + ":" + isChecked);
                    choices[which] = isChecked;
                }
            })
            .setPositiveButton("확인", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    for (boolean choice : choices) {
                        Log.d("---->", "" + choice);
                    }
                }
            })
            .show();
    }

    public void dialogCustomLayout( View view ) {
        LayoutInflater inflater = getLayoutInflater(); // inflater는 xml파일을 통해 객체를 생성 하는 방식으로 setContentView와 같은 방식이다!!
                                                       // 참고 http://blog.naver.com/PostView.nhn?blogId=ogh2003&logNo=120185734164
        final View customView = inflater.inflate(R.layout.dialog_custom, null); // 내부의 지역변수로 보낼 때는 수정이 되지 않도록 하기 위해서 final을 달아야 한다
        new AlertDialog.Builder(this)
                .setTitle("Custom Layout")
                .setView(customView)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText editText = (EditText)customView.findViewById(R.id.password);
                        Log.d("dialogCustomLayout", editText.getText().toString());
                    }
                })
                .show();
    }

    public void dialogCustomLayout2( View view ) {
        // 다이얼로그 내부 레이아웃 인플레이터
        LayoutInflater inflater = getLayoutInflater();
        final View customView = inflater.inflate(R.layout.dialog_custom, null); // 내부의 지역변수로 보낼 때는 수정이 되지 않도록 하기 위해서 final을 달아야 한다

        // 화면에 다이얼로그 표시
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog alertDialog = builder.setView(customView).show();

        customView.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText)customView.findViewById(R.id.password);
                Log.d("dialogCustomLayout", editText.getText().toString());
                alertDialog.cancel(); // 다이얼로그 닫기
            }
        });
    }
}
