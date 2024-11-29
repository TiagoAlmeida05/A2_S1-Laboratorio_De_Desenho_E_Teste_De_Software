import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import java.io.IOException;

public class Jogo {
    private Personagem player;
    private Gravidade gravity;
    private int score = 0;

    public Jogo(Screen screen, int terminalWidth, int terminalHeight){
        int groundLevel = terminalHeight -1;
        player = new Personagem(terminalWidth/2, groundLevel -1, '@', TextColor.ANSI.MAGENTA);
        gravity = new Gravidade(groundLevel);
    }
    public void startGame(Screen screen, TerminalSize terminalSize) throws InterruptedException, IOException {
        boolean isPlaying = true;

        while (isPlaying){
            screen.clear();

            KeyStroke keyStroke = screen.pollInput();
            if(keyStroke != null){
                if(keyStroke.getCharacter()!= null){
                    char key = keyStroke.getCharacter();
                    switch(key){
                        case 'w' -> gravity.jump(player);
                        case 'a' -> player.moveLeft();
                        case 'd' -> player.moveRight();
                        case 'q' -> isPlaying = false;
                    }
                }else if(keyStroke.getKeyType() == KeyType.Escape){
                    screen.stopScreen();
                    System.exit(0);
                }
            }
            if(keyStroke == null){
                player.stopMovement();
            }
            player.updatePosition(terminalSize.getColumns(), terminalSize.getRows());
            gravity.updatePosition(player);
            player.draw(screen);
            score++;
            screen.refresh();
            Thread.sleep(10);
        }
        showScore();
    }
    private  void showScore(){
        System.out.println("Your final score is: " + score + " coins!");
    }
    public int getScore(){
        return score;
    }
}
