import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;

public class Personagem {
    private int x, y;
    private final char symbol;
    private final TextColor color;
    private double velocityX = 0;

    public Personagem(int startX, int startY, char symbol, TextColor color){
        this.x = startX;
        this.y = startY;
        this.symbol = symbol;
        this.color = color;
    }
    public void moveLeft(){
        velocityX = -1;
    }
    public void moveRight(){
        velocityX = 1;
    }
    public void stopMovement(){
        velocityX = 0;
    }
    public void updatePosition(int terminalWidth, int terminalHeight){
        x += velocityX;
        x = Math.max(0, Math.min(terminalWidth -1, x));
    }
    public void draw(Screen screen){
        screen.setCharacter(x, y, new TextCharacter(symbol, color, TextColor.ANSI.BLACK));
    }
    public int getY(){
        return y;
    }
    public void setY(int y){
        this.y = y;
    }
}
