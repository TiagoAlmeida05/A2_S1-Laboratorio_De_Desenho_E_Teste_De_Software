import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;

public class Personagem {
    private int x;
    private int y;
    private final char symbol;

    public Personagem(int startX, int startY, char symbol){
        this.x = startX;
        this.y = startY;
        this.symbol = symbol;
    }
    public void draw(Screen screen){
        screen.setCharacter(x,y,new TextCharacter(symbol, TextColor.ANSI.GREEN, TextColor.ANSI.BLACK));
    }
    public void move(int dx, int dy, TerminalSize terminalSize){
        x = Math.max(0, Math.min(x+dx, terminalSize.getColumns()-1));
        y = Math.max(0, Math.min(y+dy, terminalSize.getRows()-1));
    }
}
