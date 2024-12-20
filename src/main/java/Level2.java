import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Level2 {
    private final Personagem player;
    private final Gravidade gravidade;
    private final int doorX;
    private final int doorY;
    private final DoorSprite doorSprite;
    private final List<Platform> platforms;


    public Level2(Screen screen, int terminalWidth, int terminalHeight) {
        int groundLevel = terminalHeight - 1;
        HumanSprite humanSprite = new HumanSprite();
        player = new Personagem(1, groundLevel - 1, humanSprite.getSprite());
        doorSprite = new DoorSprite();
        doorX = terminalWidth - 6;
        doorY = terminalHeight -4;
        gravidade = new Gravidade(groundLevel);
        platforms = new ArrayList<>();
        platforms.add(new Platform(35, 65, 45));
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
                                gravidade.jump();
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
            gravidade.updatePosition(player);

            boolean onPlatform = false;
            for (Platform platform : platforms) {
                if (platform.isPlayerOnPlatform(player)) {
                    gravidade.resetVerticalVelocity();
                    player.setY(platform.getY() - player.getSpriteHeight());
                    onPlatform = true;
                }
            }

            if (!onPlatform && player.getY() >= terminalSize.getRows() - player.getSpriteHeight() - 1) {
                player.setY(terminalSize.getRows() - player.getSpriteHeight() - 1);
            }

            drawDoor(screen, terminalSize.getRows() - 5);
            drawFloor(screen, terminalSize.getColumns());
            player.draw(screen);
            drawInstructions(screen, terminalSize);
            drawPlatforms(screen);

            if (player.getX() >= doorX && player.getY() == doorY) {
                startLevel3(screen, terminalSize);
                return;
            }

            if (player.getX() > 40 && player.getX() < 60 && player.getY() > terminalSize.getRows()-6) {
                showFallMessage(screen, terminalSize);
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
            if(i > 40 && i < 60){
                screen.setCharacter(i, screen.getTerminalSize().getRows() - 1, new TextCharacter(' ', TextColor.ANSI.WHITE, TextColor.ANSI.BLACK));
            }else{
                screen.setCharacter(i, screen.getTerminalSize().getRows() - 1, new TextCharacter('#', TextColor.ANSI.WHITE, TextColor.ANSI.BLACK));
            }
        }
    }

    private void drawInstructions(Screen screen, TerminalSize terminalSize) {
        TextGraphics graphics = screen.newTextGraphics();
        graphics.setForegroundColor(TextColor.ANSI.WHITE);

        String[] instructions = {
                "Usa a seta para cima para saltares"
        };

        int startY = terminalSize.getRows() - 30;
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

    private void drawPlatforms(Screen screen) {
        TextGraphics graphics = screen.newTextGraphics();
        graphics.setForegroundColor(TextColor.ANSI.GREEN);

        for (Platform platform : platforms) {
            for (int x = platform.getStartX(); x <= platform.getEndX(); x++) {
                screen.setCharacter(x, platform.getY(),
                        new TextCharacter('#', TextColor.ANSI.GREEN, TextColor.ANSI.BLACK));
            }
        }
    }
    private void startLevel3(Screen screen, TerminalSize terminalSize) {
        Level3 level3 = new Level3(screen, terminalSize.getColumns(), terminalSize.getRows());
        try {
            level3.startGame(screen, terminalSize);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}