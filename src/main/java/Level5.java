import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Level5 {
    private final Personagem player;
    private int score = 0;
    private final int doorX;
    private final DoorSprite doorSprite;
    private final List<Coin> coins;
    private final List<Platform> platforms;
    private final Gravidade gravidade;

    public Level5(Screen screen, int terminalWidth, int terminalHeight) {
        int groundLevel = terminalHeight - 1;
        gravidade = new Gravidade(groundLevel);
        HumanSprite humanSprite = new HumanSprite();
        player = new Personagem(1, groundLevel - 1, humanSprite.getSprite());
        doorSprite = new DoorSprite();
        doorX = terminalWidth - 6;
        coins = new ArrayList<>();
        coins.add(new Coin(10, 48));
        coins.add(new Coin(27, 48));
        coins.add(new Coin(50, 44));
        coins.add(new Coin(70, 39));
        platforms = new ArrayList<>();
        platforms.add(new Platform(35, 65, 45));
        platforms.add(new Platform(60, 95, 40));
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
            drawSpikes(screen);
            drawPlatforms(screen);

            for(Coin coin:coins){
                coin.draw(screen);
            }

            collectCoins();


//            if (player.getX() >= doorX && player.getY() == doorY) {
//                startLevel4(screen, terminalSize);
//                return;
//            }

            if (player.getX() > 40 && player.getX() < 60 && player.getY() > terminalSize.getRows()-6) {
                showFallMessage(screen, terminalSize);
            }
            if (player.getX()+3 > 20 && player.getX()-1 < 23 && player.getY() > terminalSize.getRows()-6) {
                showFallMessage(screen, terminalSize);
            }
            if (player.getX()+3 > 60 && player.getX()-1 < 93 && player.getY() > terminalSize.getRows()-6) {
                showFallMessage(screen, terminalSize);
            }

            screen.refresh();
            score++;
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
    private void showFallMessage(Screen screen, TerminalSize terminalSize) throws IOException, InterruptedException {
        screen.clear();
        TextGraphics graphics = screen.newTextGraphics();
        graphics.setForegroundColor(TextColor.ANSI.RED);
        graphics.putString(terminalSize.getColumns() / 2 - 5, terminalSize.getRows() / 2, "You Died!");
        screen.refresh();
        Thread.sleep(2000);
        Level5 level5 = new Level5(screen, terminalSize.getColumns(), terminalSize.getRows());
        level5.startGame(screen, terminalSize);
    }

    private void drawSpikes(Screen screen) {
        TextGraphics graphics = screen.newTextGraphics();

        for (int i = 20; i <=23 ; i++) {
            graphics.setForegroundColor(TextColor.ANSI.RED);
            graphics.putString(i, 48, "w");
        }
        for (int i = 60; i <=93 ; i++) {
            graphics.setForegroundColor(TextColor.ANSI.RED);
            graphics.putString(i, 48, "w");
        }
    }
    private void collectCoins() {
        for (int i = 0; i < coins.size(); i++) {
            Coin coin = coins.get(i);

            if (player.getX() == coin.getX() && player.getY() == coin.getY()-2) {
                coins.remove(i);
                score += 1;
                break;
            }
        }
    }

//    private void startLevel4(Screen screen, TerminalSize terminalSize) {
//        Level4 level4 = new Level3(screen, terminalSize.getColumns(), terminalSize.getRows());
//        try {
//            level4.startGame(screen, terminalSize);
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

}