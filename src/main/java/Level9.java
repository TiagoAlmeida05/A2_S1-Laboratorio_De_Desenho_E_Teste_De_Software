import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Level9 {
    private final Personagem player;
    private int score;
    private final int previousScore;
    private final int doorX;
    private final int doorY;
    private final DoorSprite doorSprite;
    private final List<Coin> coins;
    private final List<Platform> platforms;
    private final Gravidade gravidade;

    public Level9(Screen screen, int terminalWidth, int terminalHeight, int initialScore) {
        this.score = initialScore;
        this.previousScore = initialScore;
        int groundLevel = terminalHeight - 1;
        gravidade = new Gravidade(groundLevel);
        HumanSprite humanSprite = new HumanSprite();
        player = new Personagem(1, groundLevel - 1, humanSprite.getSprite());
        doorSprite = new DoorSprite();
        doorX = terminalWidth - 6;
        doorY = terminalHeight - 4;
        coins = new ArrayList<>();
        coins.add(new Coin(10, 48));
        coins.add(new Coin(27, 48));
        coins.add(new Coin(37, 44));
        coins.add(new Coin(80, 39));
        coins.add(new Coin(25,38));
        coins.add(new Coin(12, 33));
        coins.add(new Coin(33, 28));
        coins.add(new Coin(63,23));
        coins.add(new Coin(92, 29));
        coins.add(new Coin(77, 35));
        platforms = new ArrayList<>();
        platforms.add(new Platform(35, 65, 45));
        platforms.add(new Platform(55, 93, 40));
        platforms.add(new Platform(10, 45, 39));
        platforms.add(new Platform(40,55, 34 ));
        platforms.add(new Platform(1,20, 34 ));
        platforms.add(new Platform(15,47, 29 ));
        platforms.add(new Platform(45,80, 24 ));
        platforms.add(new Platform(85,100, 30 ));
        platforms.add(new Platform(75,85, 36 ));
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


            if (player.getX() >= doorX && player.getY() == doorY) {
                startFinalLevel(screen, terminalSize);
                return;
            }
            if (player.getX() > 40 && player.getX() < 60 && player.getY() == terminalSize.getRows()-4) {
                showFallMessage(screen, terminalSize);
            }
            if (player.getX()+3 > 20 && player.getX()-1 < 23 && player.getY() == terminalSize.getRows()-4) {
                showFallMessage(screen, terminalSize);
            }
            if (player.getX()+3 > 60 && player.getX()-1 < 93 && player.getY() == terminalSize.getRows()-4) {
                showFallMessage(screen, terminalSize);
            }
            if (player.getX()+3 > 42 && player.getX()-1 < 65 && player.getY() == terminalSize.getRows()-8) {
                showFallMessage(screen, terminalSize);
            }
            if (player.getX()+3 > 55 && player.getX()-1 < 74 && player.getY() == terminalSize.getRows()-13) {
                showFallMessage(screen, terminalSize);
            }
            if (player.getX()+3 > 39 && player.getX()-1 < 45 && player.getY() == terminalSize.getRows()-14) {
                showFallMessage(screen, terminalSize);
            }
            if (player.getX()+3 > 40 && player.getX()-1 < 50 && player.getY() == terminalSize.getRows()-19) {
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
        Level9 level9 = new Level9(screen, terminalSize.getColumns(), terminalSize.getRows(), previousScore);
        level9.startGame(screen, terminalSize);
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
        for( int i = 42; i <=65; i++){
            graphics.setForegroundColor(TextColor.ANSI.RED);
            graphics.putString(i, 44, "w");
        }
        for( int i = 55; i <=74; i++){
            graphics.setForegroundColor(TextColor.ANSI.RED);
            graphics.putString(i, 39, "w");
        }
        for( int i = 39; i <=45; i++){
            graphics.setForegroundColor(TextColor.ANSI.RED);
            graphics.putString(i, 38, "w");
        }
        for( int i = 40; i <=55; i++){
            graphics.setForegroundColor(TextColor.ANSI.RED);
            graphics.putString(i, 33, "w");
        }
    }
    private void collectCoins() {
        for (int i = 0; i < coins.size(); i++) {
            Coin coin = coins.get(i);

            if ((player.getX()+2 == coin.getX() || player.getX()+1 == coin.getX()||player.getX() == coin.getX())&& player.getY() == coin.getY()-2) {
                coins.remove(i);
                score += 10;
                break;
            }
        }
    }

    private void startFinalLevel(Screen screen, TerminalSize terminalSize) {
        FinalLevel finalLevel = new FinalLevel(screen, terminalSize.getColumns(), terminalSize.getRows(), score);
        try {
            finalLevel.startGame(screen, terminalSize);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
