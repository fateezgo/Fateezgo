package tw.com.fateezgo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FateEveryDayActivity extends BasicActivity {

    private static final int FUNC_CARD = 100;
    private ImageView imgCard;

    private String[] strings = {
            "今天一天過的有些迷迷糊糊、漫不經心唷！！",
            "有想法是好事，但要記得實踐前要多與身邊的人溝通。",
            "要相信自己的潛能，對可能的事物保持開放的態度。",
            "今天適合多聽聽身邊周圍人的聲音或是意見，做決定前多溝通喔！",
            "目標明確，就勇往直前吧，相信自己，就會獲得成功。",
            "參加團體活動，會有意想不到的好事唷！",
            "今天適合探索或是嘗試新鮮的事物喔！",
            "面對困難與挑戰，要努力堅持下去，不放棄就會成功。",
            "今天不適合躁進，小心守城，你實力應該是足以應付挑戰的。",
            "想要做的事情，在今天容易被延遲或是拖延。",
            "今天是新的開始的一天，有好兆頭的一天唷！",
            "你所付出的會有對等的回收，所以不要害怕付出唷！！",
            "今天適合保守行動，不宜躁進，進行中的事情會先暫時被宕延。",
            "不要害怕未知，也許勇敢接受改變後，會有更好的事情發生。",
            "試著把兩種差異性的意見、說法和在一起，有助於事情的順利進行。",
            "過重的得失心，會讓自己像被魔鬼禁錮一般，試著喘口氣，放輕鬆。",
            "完全顛覆你想像的一天，事情會有出乎意料的發展。",
            "現在的每一步雖，步伐雖小，但卻是未來遠景的計畫中的一部份。",
            "今天情緒起伏大，需注意自己情緒得缺口喔！！",
            "天開始會有新的挑戰與際遇，努力向前不要猶豫。",
            "今天很適合做一番掃除，不論是心理還是環境上的，告別不需要的東西吧！",
            "今天是平順的一天，照以往的進度即可。",
            "有些事情沒經驗過，也就不會受到過去經驗的約束與羈絆，可以盡情去嘗試。",
            "很多事情魚與熊掌無法兼得，定下心，才能真正看清楚自己想要的是什麼？",
            "百工斯為備，處理事情如能獲得團體或是同儕的支持，定會進行的更為順利，人際關係的順暢，會讓事情更順利。",
            "在穩定中求發展，安穩扎實中前進。",
            "有競爭才追求進步，正向的競爭會帶動成長。",
            "透過分享會讓你的人生會越來越富足。",
            "解決問題的方法就是面對與處理",
            "行動的速度會決定未來的方向與改變的程度",
            "休息是為了走更長遠的路，適當的休息會對事情的進展更有幫助。",
            "偶爾放鬆一下，不要太免強自己，做點自己喜歡的事情，放鬆一下。",
            "思緒清楚，適合規劃、計畫事情的一天。",
            "什麼都想兼顧的的話，容易變成兩者兼失，先好好選擇一個方向進行吧！",
            "把自己穩住的話，外來的打擊與傷害才不會輕易的把你烙倒。",
            "事情不面對是不會解決的，暫時的休息後還是需要面對",
            "有時候承認失敗，才能看見新的契機。",
            "注意力太過放在自己情緒的時候，就會無法真正看見事件的全貌。",
            "太快速的事情往往不夠真實，也不持久。",
            "過多的煩惱讓你動彈不得，請檢視讓你無法行動的是事件還是你的心？",
            "放下，才能真正向前走",
            "轉身之後，更能奔向真正的自由",
            "試著不要求回到的付出，會讓今天的你更美好",
            "溝通是拉近彼此距離的好方法，試著把想法與對方溝通，今天會更美好",
            "今天會有很多活動的邀約或是活動很多唷！",
            "今天的你容易三心二意、舉棋不定",
            "悲觀就會失去看見機會的降臨，低頭就會失去見到陽光的希望",
            "平和穩定的一點，卻乏了些許的積極",
            "生活就是腳踏實地的每一天，萬丈高樓平地起",
            "不滿足於現狀，想做些改變的時候",
            "人助自助而後天助",
            "平實美好踏實的一天",
            "適合訂定計畫規範的一天，好的開始是成功的一半",
            "千里之行始於足下，成功來自每天的累積",
            "今天容易遇到志同道合的人，可以談論你的計畫",
            "生活工作陷入一成不變，試著自己找些樂趣發揮",
            "成功不會不到，只是遲到，在多堅持一下吧！",
            "試著打個電話回家或是跟家人聊聊天吧",
            "平凡的生活就是幸福",
            "魔鬼藏在細節裡，細節的注意很重要",
            "安排一段小旅行當做給自己的驚喜吧！",
            "今天會是個穩定安穩的一天，保持平常心即可",
            "試著接受挑戰，去承擔責任，生活會變得不一樣唷！",
            "積極朝目標前進是好事，但偶爾也該聽聽身邊人的聲音",
            "透過分享，今天的你會更美好",
            "持續才是勝負的關鍵",
            "今天很適合學習新知，不要害怕接觸新的東西",
            "做事或出門以前要先規劃或是檢查東西是否有遺漏喔！",
            "不要被情緒所驅役，要當自己的主人",
            "為生活注入一點熱情，喝杯喜歡的飲品，讓自己沈靜歡愉",
            "不放棄希望，希望就會為你而存在，今天的你充滿了各種可能性",
            "不要太在意別人的目光，做自己好自在",
            "偶爾試著表達自己的想法與意見也不錯",
            "今天的你容易陷入優柔寡斷的狀況，試著大膽的做出決定吧！",
            "蹲低低，跳高高。今天的努力是未來的累積。",
            "試著再多堅持一下，成功快要降臨了",
            "試著嘗試不同打扮，會有不錯的效果唷！",
            "與人溝通會讓你增加個人魅力，與親和力唷！！",};
    private TextView tvMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fate_every_day);

        imgCard = (ImageView) findViewById(R.id.img_card);
        tvMsg = (TextView) findViewById(R.id.tv_msg);
        Intent intent = new Intent(this, CardActivity.class);
        intent.putExtra("spread", SpreadLayout.SPREAD_EVERY_DAY);
        startActivityForResult(intent,  FUNC_CARD);

        // test
        //member.getMemberData(this);
        //Log.d("FATE_ED", "mem name: " + member.name());
        //member.setMemberData(this, true, 2, "belle", "123321", "0937000000", "ed@g.com", false);
        //Log.d("FATE_ED", "mem name: " + member.name());
    }

    @Override
    void doViews() {
        // do nothing
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FUNC_CARD) {
            if (resultCode == RESULT_OK) {
                LayoutInflater layoutInflater = getLayoutInflater();
                LinearLayout ll = new LinearLayout(getApplicationContext());
                SpreadLayout spreadLayout = new SpreadLayout(SpreadLayout.SPREAD_EVERY_DAY);
                View v = layoutInflater.inflate(spreadLayout.getSpreadLayoutResId(), ll, true);
                int[] imgIds = spreadLayout.getSpreadImgResIds();
                ImageView img = (ImageView) v.findViewById(imgIds[0]);
                Log.d("FATE_ED", "cards: " + data.getStringExtra("cards"));
                int card = Integer.valueOf(data.getStringExtra("cards"));
                img.setImageResource(CardActivity.imgIds[card]);
                Bitmap b = spreadLayout.createBitmap(v);
                imgCard.setImageBitmap(b);
                tvMsg.setText(strings[card]);
            }
            else {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }
}
