import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.MouseAction;
import com.googlecode.lanterna.input.MouseActionType;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

import java.io.IOException;

public class Menu {
    private static final String[] Menu_options = {"PLAY", "SCORE", "LEVELS"};
    private int selectedOption = -1;
    private int score = 0;

    public void start() throws IOException {
        Screen screen = new DefaultTerminalFactory().createScreen();
        screen.startScreen();
        screen.setCursorPosition(null);
        screen.clear();

        try{
            boolean running = true;
            while (running){
                drawMenu(screen);
                screen.refresh();

                var input = screen.readInput();
                if (input instanceof MouseAction) {
                    MouseAction mouse = (MouseAction) input;
                    running = handleMouseInput(mouse);
                }
            }
        } finally{
            screen.stopScreen();
        }

    }
private void drawMenu(Screen screen){
        TextGraphics graphics = screen.newTextGraphics();
        graphics.setBackgroundColor(TextColor.ANSI.BLUE);
        screen.clear();

        for (int i = 0; i < Menu_options.length; i++){
            int y = 5 + i * 3; //posicao, ou seja, para ficar com espacamento de 3 linhas entre cada opção

            graphics.setBackgroundColor(TextColor.ANSI.MAGENTA);
            graphics.putString(10, y - 1, " ");

            graphics.setForegroundColor(TextColor.ANSI.BLACK);
            graphics.putString(15,y,Menu_options[i]);}
}

private boolean handleMouseInput (MouseAction mouse){
        if (mouse.getActionType() == MouseActionType.CLICK_DOWN){
            int mouseRow = mouse.getPosition().getRow();
            for (int i = 0; i < Menu_options.length; i++){
                int optionY = 5 + i * 3;
                if (mouseRow >= optionY - 1 && mouseRow <= optionY + 1){
                    selectedOption = i;
                    handleSelection();
                    return false;}

            }
        }
        return true;
}
private void handleSelection(){
        switch (Menu_options[selectedOption]){
            case "PLAY":
            try{
                Jogo jogo = new Jogo();
                Jogo.main(new String[0]);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            case "SCORE":
                showScore();
                break;
            case "LEVELS":
                chooseLevel();
                break;

        }
}
private void showScore(){
        System.out.println (" Your score is: " + score + " coins.");
}

private void chooseLevel(){
        System.out.println ("Choose a level: ");
        System.out.println(" LEVEL 1");
        System.out.println("LEVEL 2");

        ///int chosenLevel = 1;
        ///Jogo jogo = new Jogo();
        ///Jogo.setLevel(chosenLevel);
}

public void addScore (int coins){
        this.score += coins;
    }
}
