package tw.com.fateezgo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class CardActivity extends AppCompatActivity {
    private LinearLayout gallery;
    private LayoutInflater mInflater;
    public static final int[] imgIds = {
            R.drawable.tarot00, R.drawable.tarot01, R.drawable.tarot02, R.drawable.tarot03,
            R.drawable.tarot04, R.drawable.tarot05, R.drawable.tarot06, R.drawable.tarot07,
            R.drawable.tarot08, R.drawable.tarot09, R.drawable.tarot10, R.drawable.tarot11,
            R.drawable.tarot12, R.drawable.tarot13, R.drawable.tarot14, R.drawable.tarot15,
            R.drawable.tarot16, R.drawable.tarot17, R.drawable.tarot18, R.drawable.tarot19,
            R.drawable.tarot20, R.drawable.tarot21, R.drawable.tarot22, R.drawable.tarot23,
            R.drawable.tarot24, R.drawable.tarot25, R.drawable.tarot26, R.drawable.tarot27,
            R.drawable.tarot28, R.drawable.tarot29, R.drawable.tarot30, R.drawable.tarot31,
            R.drawable.tarot32, R.drawable.tarot33, R.drawable.tarot34, R.drawable.tarot35,
            R.drawable.tarot36, R.drawable.tarot37, R.drawable.tarot38, R.drawable.tarot39,
            R.drawable.tarot40, R.drawable.tarot41, R.drawable.tarot42, R.drawable.tarot43,
            R.drawable.tarot44, R.drawable.tarot45, R.drawable.tarot46, R.drawable.tarot47,
            R.drawable.tarot48, R.drawable.tarot49, R.drawable.tarot50, R.drawable.tarot51,
            R.drawable.tarot52, R.drawable.tarot53, R.drawable.tarot54, R.drawable.tarot55,
            R.drawable.tarot56, R.drawable.tarot57, R.drawable.tarot58, R.drawable.tarot59,
            R.drawable.tarot60, R.drawable.tarot61, R.drawable.tarot62, R.drawable.tarot63,
            R.drawable.tarot64, R.drawable.tarot65, R.drawable.tarot66, R.drawable.tarot67,
            R.drawable.tarot68, R.drawable.tarot69, R.drawable.tarot70, R.drawable.tarot71,
            R.drawable.tarot72, R.drawable.tarot73, R.drawable.tarot74, R.drawable.tarot75,
            R.drawable.tarot76, R.drawable.tarot77
    };
    int[] cards;    // random sort while shuffle the cards
    ArrayList<ImageView> imgList = new ArrayList<ImageView>();  // card images
    ArrayList<ImageView> imgListSpread = new ArrayList<ImageView>();    // spread images
    private LinearLayout result;
    int[] cardPattern;
    SpreadLayout spreadLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        mInflater = LayoutInflater.from(this);
        cards = new int[imgIds.length];

        findViews();

        Intent intent = getIntent();
        setSpread(intent.getIntExtra("spread", 0));
    }

    private void findViews() {
        gallery = (LinearLayout) findViewById(R.id.gallery);
        result = (LinearLayout) findViewById(R.id.result);
    }

    private void setSpread(int spread) {
        spreadLayout = new SpreadLayout(spread);
        cardPattern = spreadLayout.getSpreadImgResIds();
        result.addView(getLayoutInflater().inflate(spreadLayout.getSpreadLayoutResId(), null));

        shuffleCards();
        initCards();
        Log.d("CARD", "spread: " + spread);
        for (int i = 0; i < cardPattern.length; i++) {
            ImageView img = (ImageView) findViewById(cardPattern[i]);
            imgListSpread.add(img);
        }
    }

    private void shuffleCards() {
        int count = 0;
        while (count < imgIds.length) {
            int randNum = (int)(Math.random()*Double.valueOf(imgIds.length));
            int check = 0;
            while (check < count) {
                if (cards[check] == randNum) { // re-rand
                    randNum = (int)(Math.random()*Double.valueOf(imgIds.length));
                    check = 0;
                }
                else {
                    check++;
                }
            }
            cards[count] = randNum;
            count++;
        }
        /*System.out.print("cards: ");
        for (int i = 0; i < imgIds.length; i++)
            System.out.print(cards[i] + " ");
        System.out.println();*/
    }

    private void initCards()
    {
        for (int i = 0; i < imgIds.length; i++)
        {
            View view = mInflater.inflate(R.layout.gallery_layout,
                    gallery, false);
            ImageView img = (ImageView)view.findViewById(R.id.imageView);
            img.setImageResource(R.drawable.back_80_140);
            img.setTag(cards[i]);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (imgList.size() < cardPattern.length) {
                        ImageView img = (ImageView) v;
                        for (int i = 0; i < imgList.size(); i++) {
                            if (imgList.get(i) == img) {
                                // this card is already selected.
                                return; // do noting
                            }
                        }
                        int id = (int)img.getTag();
                        img.setImageResource(imgIds[id]);
                        imgListSpread.get(imgList.size()).setImageResource(imgIds[id]);
                        imgListSpread.get(imgList.size()).setVisibility(View.VISIBLE);
                        imgList.add(img);
                    }
                    if (imgList.size() == cardPattern.length) {
                        Intent intent = getIntent();
                        intent.putExtra("spread", spreadLayout.getSpread());
                        String strCards = "";
                        for (int i = 0; i < imgList.size(); i++) {
                            ImageView img = (ImageView)imgList.get(i);
                            strCards += ((int)img.getTag() + " ");
                        }
                        intent.putExtra("cards", strCards);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
            });
            gallery.addView(view);
        }
    }
}
