import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;

public class HumanSprite {
    private final TextCharacter[][] sprite;

    public HumanSprite(){
        sprite = new TextCharacter[][]{
                {   new TextCharacter(' ', TextColor.ANSI.DEFAULT, TextColor.ANSI.BLACK),
                    new TextCharacter('O', TextColor.ANSI.MAGENTA, TextColor.ANSI.BLACK),
                    new TextCharacter(' ', TextColor.ANSI.DEFAULT, TextColor.ANSI.BLACK) },

                {   new TextCharacter('/', TextColor.ANSI.MAGENTA, TextColor.ANSI.BLACK),
                    new TextCharacter('|', TextColor.ANSI.MAGENTA, TextColor.ANSI.BLACK),
                    new TextCharacter('\\', TextColor.ANSI.MAGENTA, TextColor.ANSI.BLACK) },

                {   new TextCharacter('/', TextColor.ANSI.MAGENTA, TextColor.ANSI.BLACK),
                    new TextCharacter(' ', TextColor.ANSI.DEFAULT, TextColor.ANSI.BLACK),
                    new TextCharacter('\\', TextColor.ANSI.MAGENTA, TextColor.ANSI.BLACK) }
        };
    }
    public TextCharacter[][] getSprite(){
        return sprite;
    }
}

