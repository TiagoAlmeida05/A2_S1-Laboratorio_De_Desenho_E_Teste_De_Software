import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;

public class DoorSprite {
    private final TextCharacter[][] sprite;

    public DoorSprite() {
        sprite = new TextCharacter[][]{
                {   new TextCharacter('#', TextColor.ANSI.CYAN, TextColor.ANSI.BLACK),
                        new TextCharacter('#', TextColor.ANSI.CYAN, TextColor.ANSI.BLACK),
                        new TextCharacter('#', TextColor.ANSI.CYAN, TextColor.ANSI.BLACK),
                        new TextCharacter('#', TextColor.ANSI.CYAN, TextColor.ANSI.BLACK),
                        new TextCharacter('#', TextColor.ANSI.CYAN, TextColor.ANSI.BLACK) },

                {   new TextCharacter('#', TextColor.ANSI.CYAN, TextColor.ANSI.BLACK),
                        new TextCharacter(' ', TextColor.ANSI.DEFAULT, TextColor.ANSI.BLACK),
                        new TextCharacter(' ', TextColor.ANSI.DEFAULT, TextColor.ANSI.BLACK),
                        new TextCharacter(' ', TextColor.ANSI.DEFAULT, TextColor.ANSI.BLACK),
                        new TextCharacter('#', TextColor.ANSI.CYAN, TextColor.ANSI.BLACK) },

                {   new TextCharacter('#', TextColor.ANSI.CYAN, TextColor.ANSI.BLACK),
                        new TextCharacter(' ', TextColor.ANSI.DEFAULT, TextColor.ANSI.BLACK),
                        new TextCharacter(' ', TextColor.ANSI.DEFAULT, TextColor.ANSI.BLACK),
                        new TextCharacter(' ', TextColor.ANSI.DEFAULT, TextColor.ANSI.BLACK),
                        new TextCharacter('#', TextColor.ANSI.CYAN, TextColor.ANSI.BLACK) },
                {   new TextCharacter('#', TextColor.ANSI.CYAN, TextColor.ANSI.BLACK),
                        new TextCharacter(' ', TextColor.ANSI.DEFAULT, TextColor.ANSI.BLACK),
                        new TextCharacter(' ', TextColor.ANSI.DEFAULT, TextColor.ANSI.BLACK),
                        new TextCharacter(' ', TextColor.ANSI.DEFAULT, TextColor.ANSI.BLACK),
                        new TextCharacter('#', TextColor.ANSI.CYAN, TextColor.ANSI.BLACK) }
        };
    }

    public TextCharacter[][] getSprite() {
        return sprite;
    }
}