import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;

public class Jogo {
    private Personagem player;
    private Gravidade gravity;
    private int score = 0;
    private int doorX;  // X coordinate for the door
    private DoorSprite doorSprite;  // Door sprite

    public Jogo(Screen screen, int terminalWidth, int terminalHeight) {
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
            drawDoor(screen, terminalSize.getRows() - 4);  // Place the door one row above the floor
            // Draw the floor and player
            drawFloor(screen, terminalSize.getColumns());
            player.draw(screen);

            // Check for door collision
            if (player.getX() >= doorX && player.getX() <= doorX + 5 && player.getY() == terminalSize.getRows() - 2) {
                isPlaying = false; // End the game when player touches the door
                showScore(); // Show score when level is completed
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
            screen.setCharacter(i, screen.getTerminalSize().getRows() - 1, new TextCharacter('#', TextColor.ANSI.WHITE, TextColor.ANSI.BLACK));
        }
    }

    private void showScore() {
        System.out.println("Your final score is: " + score + " coins!");
    }

    public int getScore() {
        return score;
    }
}