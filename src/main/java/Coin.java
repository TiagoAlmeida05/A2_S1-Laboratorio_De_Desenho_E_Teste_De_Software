import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;

public class Coin {
    private int x;
    private int y;

    public Coin(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Screen screen) {
        screen.setCharacter(x, y, new TextCharacter('@', TextColor.ANSI.YELLOW, TextColor.ANSI.BLACK));
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}