package tw.com.fateezgo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ConsultOnlineActivity extends BasicActivity {

    private static final int FUNC_CARD = 100;
    private static final int FUNC_CARD_SPREAD_SEL = 101;
    private DatabaseReference mDatabase;
    private String roomNo;
    private String name;
    private EditText edMsg;
    private RecyclerView msgRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private FirebaseRecyclerAdapter<ChatMessage, MessageViewHolder> mAdapter;

    ImageView lastCardFunc;
    private boolean isMaster = false;
    private String sn = "";

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public TextView tvTime;
        public TextView tvText;
        public ImageView imgIcon;
        public ImageView imgFunc;

        public MessageViewHolder(View v) {
            super(v);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            tvText = (TextView) itemView.findViewById(R.id.tv_text);
            imgIcon = (ImageView) itemView.findViewById(R.id.img_icon);
            imgFunc = (ImageView) itemView.findViewById(R.id.img_func);
        }
        public void setGravity(int gravity) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.gravity = gravity;
            LinearLayout linear = (LinearLayout) itemView.findViewById(R.id.linear_layout);
            linear.setLayoutParams(params);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult_online);
        findViews();

        Intent intent = getIntent();
        roomNo = intent.getIntExtra("order_no", 0) + "";
        sn = intent.getStringExtra("order_sn");
        isMaster = intent.getBooleanExtra("isMaster", false);
        name = member.name();
        if (isMaster == false) {
            Button b = (Button) findViewById(R.id.b_func);
            b.setText("傳送憑證");
        }

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAdapter = new FirebaseRecyclerAdapter<ChatMessage, MessageViewHolder>(
                ChatMessage.class,
                R.layout.item_message,
                MessageViewHolder.class,
                mDatabase.child("messages-"+ roomNo)) {
            @Override
            protected void populateViewHolder(MessageViewHolder viewHolder, ChatMessage model, int position) {
                Log.d("ADP", "type: " + model.getType());
                Log.d("ADP", "content: " + model.getContent());
                if (model.getType().equals("MSG")) {
                    viewHolder.tvText.setText(model.getContent());
                    //long time = Long.valueOf(model.getTimestamp());
                    long time = System.currentTimeMillis();
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                    Date resultdate = new Date(time);
                    //System.out.println(sdf.format(resultdate));
                    viewHolder.tvTime.setText(sdf.format(resultdate));
                    viewHolder.imgFunc.setImageDrawable(null);
                }
                else if (model.getType().equals("CARD")) {
                    Log.d("ADP", "CARD " + model.getContent());
                    if (lastCardFunc != null) {
                        lastCardFunc.setOnClickListener(null);
                    }
                    lastCardFunc = viewHolder.imgFunc;
                    viewHolder.tvText.setText("Please press!");
                    viewHolder.imgFunc.setImageResource(R.drawable.back_40_70);
                    viewHolder.imgFunc.setTag(Integer.valueOf(model.getContent())); // set spread into imgFunc's tag
                    if (!model.getFrom().equals(name)) {
                        viewHolder.imgFunc.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(getApplicationContext(), CardActivity.class);
                                intent.putExtra("spread", (Integer) view.getTag());
                                startActivityForResult(intent, FUNC_CARD);
                            }
                        });
                    }
                }
                else if (model.getType().equals("CARD_RES")) {
                    Log.d("ADP", "CARD_RES " + model.getContent());
                    if (lastCardFunc != null) {
                        lastCardFunc.setOnClickListener(null);
                    }
                    viewHolder.tvText.setText("Card Result!");

                    Log.d("ADP", "msg: " + model.getContent());
                    String[] strings = model.getContent().split(" ");
                    LayoutInflater layoutInflater = getLayoutInflater();
                    LinearLayout ll = new LinearLayout(getApplicationContext());
                    SpreadLayout spreadLayout = new SpreadLayout(Integer.valueOf(strings[0]));
                    View v = layoutInflater.inflate(spreadLayout.getSpreadLayoutResId(), ll, true);
                    int[] imgIds = spreadLayout.getSpreadImgResIds();
                    for (int i = 0; i < imgIds.length; i++) {
                        ImageView img = (ImageView) v.findViewById(imgIds[i]);
                        img.setImageResource(CardActivity.imgIds[Integer.valueOf(strings[i+1])]);
                    }
                    Bitmap b = spreadLayout.createBitmap(v);
                    viewHolder.imgFunc.setImageBitmap(b);
                }

                if (model.getFrom().equals(name)) {
                    viewHolder.tvName.setText("");
                    viewHolder.imgIcon.setImageDrawable(null);
                    viewHolder.setGravity(Gravity.RIGHT);
                }
                else {
                    viewHolder.tvName.setText(model.getFrom());
                    viewHolder.setGravity(Gravity.LEFT);
                }
            }
        };

        mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int friendlyMessageCount = mAdapter.getItemCount();
                int lastVisiblePosition =
                        mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                // If the recycler view is initially being loaded or the
                // user is at the bottom of the list, scroll to the bottom
                // of the list to show the newly added message.
                if (lastVisiblePosition == -1 ||
                        (positionStart >= (friendlyMessageCount - 1) &&
                                lastVisiblePosition == (positionStart - 1))) {
                    msgRecyclerView.scrollToPosition(positionStart);
                }
            }
        });

        msgRecyclerView.setLayoutManager(mLinearLayoutManager);
        msgRecyclerView.setAdapter(mAdapter);
    }

    private void findViews() {
        edMsg = (EditText) findViewById(R.id.ed_msg);
        msgRecyclerView = (RecyclerView) findViewById(R.id.msg_recycler_view);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setStackFromEnd(true);
        msgRecyclerView.setLayoutManager(mLinearLayoutManager);
    }

    public void sendMsg(View v) {
        ChatMessage chatMessage = new ChatMessage(name, "MSG", edMsg.getText().toString(), System.currentTimeMillis()+"");
        mDatabase.child("messages-"+ roomNo).push().setValue(chatMessage);
        edMsg.setText("");
    }

    public void sendFunc(View v) {
        if (isMaster == false) {
            ChatMessage chatMessage = new ChatMessage(name, "MSG", "憑證序號："+sn, System.currentTimeMillis()+"");
            mDatabase.child("messages-"+ roomNo).push().setValue(chatMessage);
        }
        else {
            Intent intent = new Intent(this, SpreadActivity.class);
            startActivityForResult(intent, FUNC_CARD_SPREAD_SEL);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == FUNC_CARD) && (resultCode == RESULT_OK)) {
            String strCardRes = data.getIntExtra("spread", 0) + " " + data.getStringExtra("cards");
            ChatMessage chatMessage = new ChatMessage(name, "CARD_RES", strCardRes, System.currentTimeMillis()+"");
            mDatabase.child("messages-"+ roomNo).push().setValue(chatMessage);
        }
        else if ((requestCode == FUNC_CARD_SPREAD_SEL) && (resultCode == RESULT_OK)) {
            ChatMessage chatMessage = new ChatMessage(name, "CARD", data.getIntExtra("spread", 0)+"", System.currentTimeMillis() + "");
            mDatabase.child("messages-" + roomNo).push().setValue(chatMessage);
        }
    }

    @Override
    void doViews() {
        // do nothing
    }
}

