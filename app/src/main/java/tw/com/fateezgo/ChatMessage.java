package tw.com.fateezgo;

/**
 * Created by Administrator on 2016/8/25.
 */
public class ChatMessage {
    private String from;    // from userid
    private String type;
    private String content;
    private String timestamp;

    public ChatMessage() {
    }

    public ChatMessage(String from, String type, String content, String timestamp) {
        this.from = from;
        this.type = type;
        this.content = content;
        this.timestamp = timestamp;
    }

    String getFrom() { return from; }
    void setFrom(String from) { this.from = from; }

    String getType() { return type; }
    void setType(String type) { this.type = type; }

    String getContent() { return content; }
    void setContent(String content) { this.content = content; }

    String getTimestamp() { return timestamp; }
    void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}
