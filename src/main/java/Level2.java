import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;

public class Level2 {
    private final Personagem player;
    private final Gravidade gravity;
    private int score = 0;
    private final int doorX;  // X coordinate for the door
    private final DoorSprite doorSprite;  // Door sprite

    public Level2(Screen screen, int terminalWidth, int terminalHeight) {
        int groundLevel = terminalHeight - 1;
        HumanSprite humanSprite = new HumanSprite();
        player = new Personagem(1, groundLevel - 1, humanSprite.getSprite());
        gravity = new Gravidade(groundLevel);

        // Create a door sprite and place the door at a specific x coordinate
        doorSprite = new DoorSprite();
        doorX = terminalWidth - 6;  // Position the door near the end of the level
    }

    public void startGame(Screen screen, TerminalSize terminalSize) throws InterruptedException, IOException {
        boolean isPlaying = true;

        while (isPlaying) {
            screen.clear();

            KeyStroke keyStroke = screen.pollInput();
            if (keyStroke != null) {
                if (keyStroke.getKeyType() != null) {
                    switch (keyStroke.getKeyType()) {
                        case ArrowUp -> gravity.jump(player);
                        case ArrowLeft -> player.moveLeft();
                        case ArrowRight -> player.moveRight();
                        case Escape -> System.exit(0);
                    }
                }
            }

            if (keyStroke == null) {
                player.stopMovement();
            }

            // Update player position and gravity
            player.updatePosition(terminalSize.getColumns(), terminalSize.getRows());
            gravity.updatePosition(player);

            // Draw the door at the ground level (one row above the floor)
            drawDoor(screen, terminalSize.getRows() - 5);  // Place the door one row above the floor
            // Draw the floor and player
            drawFloor(screen, terminalSize.getColumns());
            player.draw(screen);
            drawInstructions(screen, terminalSize);

            // Check for door collision
            if (player.getX() >= doorX && player.getX() <= doorX + 5 && player.getY() == terminalSize.getRows() - 2) {
                isPlaying = false; // End the game when player touches the door
                showScore(); // Show score when level is completed
            }

            if (player.getX() > 40 && player.getX() < 60 && player.getY() > terminalSize.getRows()-6) {
                showFallMessage(screen, terminalSize);  // Restart the level
            }

            // Refresh the screen
            screen.refresh();
            score++;
            Thread.sleep(10);
        }
    }

    private void drawDoor(Screen screen, int doorY) {
        // Draw the door sprite at the doorX position and at the ground level (one row higher than the floor)
        for (int y = 0; y < doorSprite.getSprite().length; y++) {
            for (int x = 0; x < doorSprite.getSprite()[y].length; x++) {
                screen.setCharacter(doorX + x, doorY + y, doorSprite.getSprite()[y][x]);
            }
        }
    }

    private void drawFloor(Screen screen, int width) {
        for (int i = 0; i < width; i++) {
            if(i > 40 && i < 60){
                screen.setCharacter(i, screen.getTerminalSize().getRows() - 1, new TextCharacter(' ', TextColor.ANSI.WHITE, TextColor.ANSI.BLACK));
            }else{
                screen.setCharacter(i, screen.getTerminalSize().getRows() - 1, new TextCharacter('#', TextColor.ANSI.WHITE, TextColor.ANSI.BLACK));
            }
        }
    }

    private void showScore() {
        System.out.println("Your final score is: " + score + " coins!");
    }

    public int getScore() {
        return score;
    }

    private void drawInstructions(Screen screen, TerminalSize terminalSize) {
        TextGraphics graphics = screen.newTextGraphics();
        graphics.setForegroundColor(TextColor.ANSI.WHITE);

        String[] instructions = {
                "Usa a seta para cima para saltares"
        };

        int startY = terminalSize.getRows() - 30;  // Place near the bottom
        for (int i = 0; i < instructions.length; i++) {
            String line = instructions[i];
            int x = (terminalSize.getColumns() - line.length()) / 2;
            graphics.putString(x, startY + i, line);
        }
    }
    private void showFallMessage(Screen screen, TerminalSize terminalSize) throws IOException, InterruptedException {
        screen.clear();
        TextGraphics graphics = screen.newTextGraphics();
        graphics.setForegroundColor(TextColor.ANSI.RED);
        graphics.putString(terminalSize.getColumns() / 2 - 5, terminalSize.getRows() / 2, "You Fell!");
        screen.refresh();
        Thread.sleep(2000);
        Level2 level2 = new Level2(screen, terminalSize.getColumns(), terminalSize.getRows());
        level2.startGame(screen, terminalSize);
    }
}