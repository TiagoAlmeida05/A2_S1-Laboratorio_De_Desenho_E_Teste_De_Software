import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Level1 {
    private final Personagem player;
    private final Gravidade gravity;
    private final int doorY;
    private final int doorX;
    private final DoorSprite doorSprite;
    private final List<Platform> platforms;

    public Level1(Screen screen, int terminalWidth, int terminalHeight) {
        platforms = new ArrayList<>();
        int groundLevel = terminalHeight - 1;
        HumanSprite humanSprite = new HumanSprite();
        player = new Personagem(1, groundLevel - 1, humanSprite.getSprite());
        gravity = new Gravidade(groundLevel);

        // Create a door sprite and place the door at a specific x coordinate
        doorSprite = new DoorSprite();
        doorX = terminalWidth - 6;
        doorY = terminalHeight -4;
    }

    public void startGame(Screen screen, TerminalSize terminalSize) throws InterruptedException, IOException {

        while (true) {
            screen.clear();

            KeyStroke keyStroke = screen.pollInput();
            if (keyStroke != null) {
                if (keyStroke.getKeyType() != null) {
                    switch (keyStroke.getKeyType()) {
                        case ArrowUp -> {
                            if (player.getY() == terminalSize.getRows() - player.getSpriteHeight() - 1 ||
                                    platforms.stream().anyMatch(platform -> platform.isPlayerOnPlatform(player))) {
                                gravity.jump();
                            }
                        }
                        case ArrowLeft -> player.moveLeft();
                        case ArrowRight -> player.moveRight();
                        case Escape -> System.exit(0);
                    }
                }
            }

            if (keyStroke == null) {
                player.stopMovement();
            }

            player.updatePosition(terminalSize.getColumns(), terminalSize.getRows());
            gravity.updatePosition(player);

            drawDoor(screen, terminalSize.getRows() - 5);
            drawFloor(screen, terminalSize.getColumns());
            player.draw(screen);
            drawInstructions(screen, terminalSize);

            if (player.getX() >= doorX && player.getY() == doorY) {
                startLevel2(screen, terminalSize);
                return;
            }

            screen.refresh();
            Thread.sleep(10);
        }
    }

    private void drawDoor(Screen screen, int doorY) {
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

    private void drawInstructions(Screen screen, TerminalSize terminalSize) {
        TextGraphics graphics = screen.newTextGraphics();
        graphics.setForegroundColor(TextColor.ANSI.WHITE);

        String[] instructions = {
                "Usa as setas para te moveres",
                "-> para te moveres para a direita",
                "<- para te moveres para a esquerda"
        };

        int startY = terminalSize.getRows() - 30;
        for (int i = 0; i < instructions.length; i++) {
            String line = instructions[i];
            int x = (terminalSize.getColumns() - line.length()) / 2;
            graphics.putString(x, startY + i, line);
        }
    }
    private void startLevel2(Screen screen, TerminalSize terminalSize) {
        Level2 level2 = new Level2(screen, terminalSize.getColumns(), terminalSize.getRows());
        try {
            level2.startGame(screen, terminalSize);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}