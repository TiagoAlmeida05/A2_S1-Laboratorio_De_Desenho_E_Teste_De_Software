import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

import java.io.IOException;

public class Menu {
    private static final String[] Menu_options = {"PLAY", "LEVELS", "EXIT"};
    private int selectedOption = -1;
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
                            System.exit(0);
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
            case "LEVELS":
                chooseLevel();
                break;
            case "EXIT":
                System.exit(0);
                break;
        }
        return false;
    }

    private void startGame() {
        try {
            DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(new TerminalSize(100, 50));
            Screen screen = terminalFactory.createScreen();
            screen.startScreen();
            TerminalSize terminalSize = screen.getTerminalSize();

            if (selectedLevel == 0) {
                Level1 level1 = new Level1(screen, terminalSize.getColumns(), terminalSize.getRows());
                level1.startGame(screen, terminalSize);
            } else if (selectedLevel == 1) {
                Level2 level2 = new Level2(screen, terminalSize.getColumns(), terminalSize.getRows());
                level2.startGame(screen, terminalSize);
            } else if (selectedLevel == 2) {
                Level3 level3 = new Level3(screen, terminalSize.getColumns(), terminalSize.getRows());
                level3.startGame(screen, terminalSize);
            } else if (selectedLevel == 3) {
                Level4 level4 = new Level4(screen, terminalSize.getColumns(), terminalSize.getRows(), 0);
                level4.startGame(screen, terminalSize);
            } else if (selectedLevel == 4) {
                Level5 level5 = new Level5(screen, terminalSize.getColumns(), terminalSize.getRows(), 0);
                level5.startGame(screen, terminalSize);
            } else if (selectedLevel == 5) {
                Level6 level6 = new Level6(screen, terminalSize.getColumns(), terminalSize.getRows(), 0);
                level6.startGame(screen, terminalSize);
            } else if (selectedLevel == 6) {
                Level7 level7 = new Level7(screen, terminalSize.getColumns(), terminalSize.getRows(), 0);
                level7.startGame(screen, terminalSize);
            } else if (selectedLevel == 7) {
                Level8 level8 = new Level8(screen, terminalSize.getColumns(), terminalSize.getRows(), 0);
                level8.startGame(screen, terminalSize);
            } else if (selectedLevel == 8) {
                Level9 level9 = new Level9(screen, terminalSize.getColumns(), terminalSize.getRows(), 0);
                level9.startGame(screen, terminalSize);
            }else if (selectedLevel == 9) {
                FinalLevel finalLevel = new FinalLevel(screen, terminalSize.getColumns(), terminalSize.getRows(), 0);
                finalLevel.startGame(screen, terminalSize);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void chooseLevel() throws IOException {
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory().setInitialTerminalSize(new TerminalSize(100, 50));  // Custom size like game screen
        Screen screen = terminalFactory.createScreen();
        screen.startScreen();
        screen.setCursorPosition(null);
        screen.clear();

        String[] levels = {"Level 1", "Level 2", "Level 3", "Level 4", "Level 5", "Level 6", "Level 7", "Level 8", "Level 9", "Final Level"};
        int maxColumns = screen.getTerminalSize().getColumns();
        int maxRows = screen.getTerminalSize().getRows();


        while (true) {
            screen.clear();
            TextGraphics graphics = screen.newTextGraphics();
            graphics.setForegroundColor(TextColor.ANSI.WHITE);

            int startingY = maxRows / 4;
            int optionHeight = 3;
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

            screen.refresh();

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
                        startGame();
                        screen.stopScreen();
                        System.exit(0);
                        break;
                    case Escape:
                        selectedLevel = 0;
                        start();
                        System.exit(0);
                }
            }
        }
    }
}
