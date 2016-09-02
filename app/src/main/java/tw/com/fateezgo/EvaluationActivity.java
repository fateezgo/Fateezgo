package tw.com.fateezgo;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    String[] Balls = {"星座", "塔羅","占星","其他"};
    private Spinner Sp01;
//    private TextView Tspinner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);
        //2.連結元件
        Sp01 =(Spinner) findViewById(R.id.spinner);
//        Tspinner = (TextView) findViewById(R.id.textviewspinner);
        ArrayAdapter<String> adapterBalls=new ArrayAdapter<String>
                (this,android.R.layout.simple_spinner_item,Balls);

        adapterBalls.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Sp01.setAdapter(adapterBalls);


        ;
//        BClear=(Button)   this.findViewById(R.id.bClear);
//        BOK=(Button)   this.findViewById(R.id.bOK);
//        BEnd=(Button)   this.findViewById(R.id.bEnd);

        //3.建立事件

        Sp01.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                //Tspinner.setText(" 選擇的項目是："+Sp01.getSelectedItem().toString());
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }

        });


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

                txtRatingValue.setText(String.valueOf(rating));

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

                Toast.makeText(EvaluationActivity.this,
                        String.valueOf(ratingBar.getRating()),
                        Toast.LENGTH_SHORT).show();

            }

        });

    }



}
