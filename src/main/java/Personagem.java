import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.screen.Screen;

public class Personagem {
    private int x, y;
    private final TextCharacter[][] sprite;
    private int velocityX = 0;

    public Personagem(int startX, int startY, TextCharacter[][] sprite){
        this.x = startX;
        this.y = startY;
        this.sprite = sprite;
    }
    public void moveLeft(){
        velocityX = -1;
    }
    public void moveRight(){
        velocityX = 1;
    }
    public void stopMovement(){ velocityX = 0; }
    public void updatePosition(int terminalWidth, int terminalHeight){
        x += velocityX;
        x = Math.max(0, Math.min(terminalWidth -sprite[0].length, x));
        y = Math.max(0, Math.min(terminalHeight - sprite.length, y));
    }
    public void draw(Screen screen){
        for(int row = 0; row < sprite.length; row++){
            for(int col = 0; col < sprite[row].length; col++){
                screen.setCharacter(x + col, y + row, sprite[row][col]);
            }
        }
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public void setY(int y){
        this.y = y;
    }
    public int getSpriteHeight(){
        return sprite.length;
    }
}