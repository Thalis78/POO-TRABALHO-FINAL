package Models;

import com.fasterxml.jackson.annotation.JsonValue;

public class EmojiConverter {
    private String emoji;

    public EmojiConverter() {
    }

    public EmojiConverter(String emoji) {
        this.emoji = emoji;
    }

    public String[] lerEmoji() {
        return new String[] {
            "😀", "😁", "😂", "🤣", "😃", "😄", "😅", "😆", "😉", "😊",
            "😋", "😎", "😍", "😘", "😗", "😙", "😚", "🙂", "🤗", "🤔",
            "😐", "😑", "😶", "🙄", "😏", "😣", "😥", "😮", "🤐", "😯",
            "😪", "😫", "😴", "😌", "😛", "😜", "😝", "🤤", "😒", "😓",
            "😔", "😕", "🙃", "🤑", "😲", "☹️", "🙁", "😖", "😞", "😟"
        };
    }

    @JsonValue // Para serialização
    public String getEmoji() {
        return emoji;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }

    @Override
    public String toString() {
        return this.emoji;
    }
}