import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

import java.io.IOException;

public class Menu {
    private static final String[] Menu_options = {"PLAY", "SCORE", "LEVELS"};
    private int selectedOption = -1;
    private int score = 0;
    private int selectedLevel = 0;

    public static void main(String[] args) {
        try {
            Menu menu = new Menu();
            menu.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() throws IOException {
        Screen screen = new DefaultTerminalFactory().createScreen();
        screen.startScreen();
        screen.setCursorPosition(null);
        screen.clear();

        try {
            boolean running = true;
            while (running) {
                drawMenu(screen);
                screen.refresh();

                KeyStroke keyStroke = screen.readInput();
                if(keyStroke != null){
                    switch(keyStroke.getKeyType()){
                        case ArrowUp:
                            moveUp();
                            break;
                        case ArrowDown:
                            moveDown();
                            break;
                        case Enter:
                            running = handleSelection();
                            screen.stopScreen();
                            break;
                        case Escape:
                            screen.stopScreen();
                            System.exit(0);
                            break;
                    }
                }
            }
        } finally {
            screen.stopScreen();
        }

    }

    private void drawMenu(Screen screen) {
        TextGraphics graphics = screen.newTextGraphics();
        graphics.setBackgroundColor(TextColor.ANSI.BLUE);
        screen.clear();

        for (int i = 0; i < Menu_options.length; i++) {
            int y = 5 + i * 3;

            if(i == selectedOption){
                graphics.setBackgroundColor(TextColor.ANSI.MAGENTA);
                graphics.putString(10, y, "> " + Menu_options[i]);
            }else{
                graphics.setForegroundColor(TextColor.ANSI.WHITE);
                graphics.putString(15, y," " + Menu_options[i]);
            }
        }
    }
    private void moveUp(){
        if(selectedOption > 0){
            selectedOption--;
        }
    }
    private void moveDown(){
        if(selectedOption < Menu_options.length -1){
            selectedOption++;
        }
    }
    private boolean handleSelection() throws IOException {
        switch (Menu_options[selectedOption]) {
            case "PLAY":
                startGame();
                break;
            case "SCORE":
                showScore();
                break;
            case "LEVELS":
                chooseLevel();
                break;
        }
        return false;
    }

    private void startGame() {
        try {
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
            Screen screen = terminalFactory.createScreen();
            screen.startScreen();
            TerminalSize terminalSize = screen.getTerminalSize();
            Jogo jogo = new Jogo(screen, terminalSize.getColumns(), terminalSize.getRows());
            jogo.startGame(screen, terminalSize);
            score = jogo.getScore();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void showScore() {
        System.out.println(" Your score is: " + score + " coins.");
    }

    private void chooseLevel() throws IOException {
        Screen screen = new DefaultTerminalFactory().createScreen();
        screen.startScreen();
        screen.setCursorPosition(null);
        screen.clear();
        String[] levels = {"Level 1"};
        boolean choosingLevel = true;
        while(choosingLevel){
            screen.clear();
            TextGraphics graphics = screen.newTextGraphics();
            graphics.setForegroundColor(TextColor.ANSI.WHITE);

            for(int i = 0; i< levels.length; i++){
                int y = 5 + i * 3;
                if(i == selectedLevel){
                    graphics.setBackgroundColor(TextColor.ANSI.MAGENTA);
                    graphics.putString(10, y, "> " + levels[i]);
                }else{
                    graphics.setForegroundColor(TextColor.ANSI.WHITE);
                    graphics.putString(10, y, " " + levels[i]);
                }
            }
            screen.refresh();

            KeyStroke keyStroke = screen.readInput();

            if(keyStroke != null){
                switch(keyStroke.getKeyType()){
                    case ArrowUp:
                        if(selectedLevel > 0){
                            selectedLevel --;
                        }
                        break;
                    case ArrowDown:
                        if(selectedLevel < levels.length -1){
                            selectedLevel++;
                        }
                        break;
                    case Enter:
                        choosingLevel = false;
                        startGame();
                        screen.stopScreen();
                        break;
                    case Escape:
                        choosingLevel = false;
                        screen.stopScreen();
                        break;
                }
            }
        }
    }


    public void addScore (int coins){
        this.score += coins;
    }
}
