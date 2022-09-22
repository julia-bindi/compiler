package Lexical;

public class Token {
    public final int tag;
    public final String translatedTag;

    public Token (int t){
        tag = t;
        if(tag >= 256){
            translatedTag = Tag.translationArray[tag-256];
        } else {
            translatedTag = "" + tag;
        }
    }
    
    public String toString(){
        return "<" + translatedTag + ">";
    }
}
