package tw.com.fateezgo;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;



public class EvaluationActivity extends BasicActivity {
    /** Called when the activity is first created. */
    //1.宣告物件
    private Button BClear,BOK,BEnd;
    private ImageView imageView;
    private RatingBar ratingBar;
    private TextView txtRatingValue;
    private Button btnSubmit;
    String[] Balls = {"塔羅","八字", "占星", "其他"};
    private Spinner Sp01;

//    private TextView Tspinner;
    String str_taro = "塔羅好棒!";
    String str_eight = "八字太神奇了" ;
    String str_astrology = "占星真美好!";
    String str_others = "世界真美妙!";
    private EditText ET01;
    private CheckBox CB1;
    private CompoundButton.OnCheckedChangeListener mylistener;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);
        //2.連結元件
        Sp01 = (Spinner) findViewById(R.id.spinner);
//        Tspinner = (TextView) findViewById(R.id.textviewspinner);
        ArrayAdapter<String> adapterBalls = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, Balls);

        adapterBalls.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Sp01.setAdapter(adapterBalls);

        ET01 = (EditText) findViewById(R.id.message);
        CB1 = (CheckBox) findViewById(R.id.cb1);
        String str_message;
//        BClear=(Button)   this.findViewById(R.id.bClear);
//        BOK=(Button)   this.findViewById(R.id.bOK);
//        BEnd=(Button)   this.findViewById(R.id.bEnd);

        //3.建立事件

        Sp01.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub

                switch (position) {
                    case 0:
                        ET01.setText(str_taro);
                        break;

                    case 1:
                        ET01.setText(str_eight);
                        break;

                    case 2:
                        ET01.setText(str_astrology);
                        break;

                    case 3:
                        ET01.setText(str_others);
                        break;


                }


            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }

        });


        
        CB1.setOnCheckedChangeListener(mylistener);
        CheckBox.OnCheckedChangeListener mylistener = new CheckBox.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (CB1.isChecked()) {
                    boolean cb1_flag = true;
                    ET01.setText("select!");
                } else {
                    boolean cb1_flag = false;
                    ET01.setText("NO select!");
                }

            }
        };


//        BClear.setOnClickListener(new Button.OnClickListener(){
//
//            public void onClick(View arg0) {
//                // TODO Auto-generated method stub
//                //EDATM.setText("");
//                String s=EDATM.getText().toString();
//                if (s.length() !=0){
//                    s=s.substring(0, s.length()-1);
//                }
//                EDATM.setText(s);
//            }});
//        BOK.setOnClickListener(new Button.OnClickListener(){
//
//            public void onClick(View arg0) {
//                // TODO Auto-generated method stub
//                String s=EDATM.getText().toString();
//                if (s.equals("12345")){
////                    Toast.makeText(getApplicationContext(), "密碼正確!", Toast.LENGTH_LONG).show();
//                    Toast toast=new Toast(getApplicationContext());
//                    toast.setGravity(Gravity.BOTTOM,0,100);
//                    toast.setDuration(Toast.LENGTH_LONG);
//                    ImageView view = new ImageView(getApplicationContext());
//                    view.setImageResource(R.drawable.ok);
//                    toast.setView(view);
//                    toast.show();
//
//
//                }else{
////                    Toast.makeText(getApplicationContext(), "密碼錯誤!", Toast.LENGTH_LONG).show();
//                    Toast toast=new Toast(getApplicationContext());
//                    toast.setGravity(Gravity.BOTTOM,0,50);
//                    toast.setDuration(Toast.LENGTH_LONG);
//                    ImageView view = new ImageView(getApplicationContext());
//                    view.setImageResource(R.drawable.delete);
//                    toast.setView(view);
//                    toast.show();
//
//
//                }
//            }});
//
//
//
//        BEnd.setOnClickListener(new Button.OnClickListener(){
//
//            public void onClick(View arg0) {
//                // TODO Auto-generated method stub
//                new AlertDialog.Builder(MainActivity.this)
//                        .setTitle("確認視窗")
//                        .setMessage("是否結束?")
//                        .setPositiveButton("確定", new  DialogInterface.OnClickListener() {
//
//                            public void onClick(DialogInterface dialog, int which) {
//                                // TODO Auto-generated method stub
//                                finish();
//                            }
//                        })
//                        .setNegativeButton("取消", null)
//                        .show();
//            }});

        addListenerOnRatingBar();
        addListenerOnButton();


    }



    private void show() {
    }

    public void addListenerOnRatingBar() {

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        txtRatingValue = (TextView) findViewById(R.id.txtRatingValue);

        //if rating value is changed,
        //display the current rating value in the result (textview) automatically
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

//                txtRatingValue.setText(String.valueOf(rating));

            }
        });
    }

    public void addListenerOnButton() {

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        //if click on me, then display the current rating value.
        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String starvalue = String.valueOf(ratingBar.getRating());
//                lt.execute("http://192.168.1.102:8080/fateezgo-ee/weav?starno="+starvalue);
                Toast.makeText(EvaluationActivity.this,
                        String.valueOf(ratingBar.getRating()),
                        Toast.LENGTH_SHORT).show();

            }

        });
    }

    @Override
    void doViews() {

    }
}
