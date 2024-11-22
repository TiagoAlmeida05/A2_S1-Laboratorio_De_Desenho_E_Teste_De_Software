
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

public class Jogo {
    public static void main(String[] args){
        try{
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
            Screen screen = new TerminalScreen(terminalFactory.createTerminal());
            screen.startScreen();

            TerminalSize terminalSize = screen.getTerminalSize();
            Personagem player = new Personagem(terminalSize.getColumns()/2, terminalSize.getRows()/2,'@' );

            boolean running = true;
            while(running){
                screen.clear();
                player.draw(screen);
                screen.refresh();
                KeyStroke keyStroke = screen.pollInput();
                if(keyStroke != null){
                    switch(keyStroke.getKeyType()){
                        case ArrowUp -> player.move(0,-1,terminalSize);
                        case ArrowDown -> player.move(0,1,terminalSize);
                        case ArrowLeft -> player.move(-1,0,terminalSize);
                        case ArrowRight -> player.move(1,0,terminalSize);
                        case Escape, Character -> running = false;
                    }
                }
                Thread.sleep(25);
            }
            screen.startScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
