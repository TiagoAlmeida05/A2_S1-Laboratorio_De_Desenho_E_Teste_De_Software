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
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(new TerminalSize(100, 50));  // Custom size
        Screen screen = terminalFactory.createScreen();
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

        String title = "Jumping Jack";
        graphics.setForegroundColor(TextColor.ANSI.WHITE);
        graphics.putString((screen.getTerminalSize().getColumns() - title.length()) / 2, 2, title);

        for (int i = 0; i < Menu_options.length; i++) {
            int y = 5 + i * 3;

            if(i == selectedOption){
                graphics.setBackgroundColor(TextColor.ANSI.MAGENTA);
                graphics.putString(10, y, "> " + Menu_options[i]);
            }else{
                graphics.setBackgroundColor(TextColor.ANSI.BLUE);
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
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(new TerminalSize(100, 50));  // Same size as the menu
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
        // Create a screen with the same size as the game and menu screens
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(new TerminalSize(100, 50));  // Custom size like game screen
        Screen screen = terminalFactory.createScreen();
        screen.startScreen();
        screen.setCursorPosition(null);  // Hide cursor
        screen.clear();

        String[] levels = {"Level 1", "Level 2", "Level 3"};  // You can add more levels if needed
        boolean choosingLevel = true;
        int maxColumns = screen.getTerminalSize().getColumns();
        int maxRows = screen.getTerminalSize().getRows();

        // Adjust the y-position to be more centered or adjusted based on screen size
        while (choosingLevel) {
            screen.clear();  // Clear the screen at the beginning of each loop
            TextGraphics graphics = screen.newTextGraphics();
            graphics.setForegroundColor(TextColor.ANSI.WHITE);

            // Calculate vertical starting point to center the levels
            int startingY = maxRows / 4; // Start a little higher on the screen for more space
            int optionHeight = 3;  // Vertical spacing between level options
            for (int i = 0; i < levels.length; i++) {
                int y = startingY + i * optionHeight;
                if (i == selectedLevel) {
                    graphics.setBackgroundColor(TextColor.ANSI.MAGENTA);
                    graphics.putString(maxColumns / 4, y, "> " + levels[i]);
                } else {
                    graphics.setBackgroundColor(TextColor.ANSI.BLUE);
                    graphics.setForegroundColor(TextColor.ANSI.WHITE);
                    graphics.putString(maxColumns / 4, y, " " + levels[i]);
                }
            }

            // Refresh the screen to update the changes
            screen.refresh();

            // Read the key input to navigate and choose the level
            KeyStroke keyStroke = screen.readInput();
            if (keyStroke != null) {
                switch (keyStroke.getKeyType()) {
                    case ArrowUp:
                        if (selectedLevel > 0) {
                            selectedLevel--;
                        }
                        break;
                    case ArrowDown:
                        if (selectedLevel < levels.length - 1) {
                            selectedLevel++;
                        }
                        break;
                    case Enter:
                        choosingLevel = false;  // Exit the loop when a level is selected
                        startGame();  // Start the game with the selected level
                        screen.stopScreen();  // Stop the screen once done
                        break;
                    case Escape:
                        choosingLevel = false;  // Exit the loop if Escape is pressed
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