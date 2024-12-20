import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FinalLevel {
    private final Personagem player;
    private int score;
    private final int previousScore;
    private final int doorX;
    private final int doorY;
    private final DoorSprite doorSprite;
    private final List<Coin> coins;
    private final List<Platform> platforms;
    private final Gravidade gravidade;

    public FinalLevel(Screen screen, int terminalWidth, int terminalHeight, int initialScore) {
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
        coins.add(new Coin(3, 44));
        coins.add(new Coin(14, 39));
        coins.add(new Coin(25, 34));
        coins.add(new Coin(17, 29));
        coins.add(new Coin(25,24));
        coins.add(new Coin(33, 19));
        coins.add(new Coin(60, 29));
        coins.add(new Coin(85,34));
        coins.add(new Coin(68, 39));
        coins.add(new Coin(82, 44));
        platforms = new ArrayList<>();
        platforms.add(new Platform(0, 10, 45));
        platforms.add(new Platform(11, 21, 40));
        platforms.add(new Platform(22, 32, 35));
        platforms.add(new Platform(11,21, 30 ));
        platforms.add(new Platform(22,45, 25 ));
        platforms.add(new Platform(30,35, 20 ));
        platforms.add(new Platform(55,75, 30 ));
        platforms.add(new Platform(80,100, 35 ));
        platforms.add(new Platform(60,70, 40 ));
        platforms.add(new Platform(80,90, 45 ));
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
                if(score == 470){
                    trueEndgame(screen, terminalSize);
                }else {
                    badEndgame(screen, terminalSize);
                }
                return;
            }
            if (player.getX() > 5 && player.getX() < 93 && player.getY() == terminalSize.getRows()-4) {
                showFallMessage(screen, terminalSize);
            }
            if (player.getX()+3 > 8 && player.getX()-1 < 10 && player.getY() == terminalSize.getRows()-8) {
                showFallMessage(screen, terminalSize);
            }
            if (player.getX()+3 > 88 && player.getX()-1 < 90 && player.getY() == terminalSize.getRows()-8) {
                showFallMessage(screen, terminalSize);
            }
            if (player.getX()+3 > 19 && player.getX()-1 < 21 && player.getY() == terminalSize.getRows()-13) {
                showFallMessage(screen, terminalSize);
            }
            if (player.getX()+3 > 60 && player.getX()-1 < 62 && player.getY() == terminalSize.getRows()-13) {
                showFallMessage(screen, terminalSize);
            }
            if (player.getX()+3 > 28 && player.getX()-1 < 32 && player.getY() == terminalSize.getRows()-18) {
                showFallMessage(screen, terminalSize);
            }
            if (player.getX()+3 > 88 && player.getX()-1 < 100 && player.getY() == terminalSize.getRows()-18) {
                showFallMessage(screen, terminalSize);
            }
            if (player.getX()+3 > 11 && player.getX()-1 < 14 && player.getY() == terminalSize.getRows()-23) {
                showFallMessage(screen, terminalSize);
            }
            if (player.getX()+3 > 70 && player.getX()-1 < 75 && player.getY() == terminalSize.getRows()-23) {
                showFallMessage(screen, terminalSize);
            }
            if (player.getX()+3 > 30 && player.getX()-1 < 45 && player.getY() == terminalSize.getRows()-28) {
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
            if(i < 5 || (i > 93 && i < 99)){
                screen.setCharacter(i, screen.getTerminalSize().getRows() - 1, new TextCharacter('#', TextColor.ANSI.WHITE, TextColor.ANSI.BLACK));
            }else{
                screen.setCharacter(i, screen.getTerminalSize().getRows() - 1, new TextCharacter(' ', TextColor.ANSI.WHITE, TextColor.ANSI.BLACK));
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
        FinalLevel finalLevel = new FinalLevel(screen, terminalSize.getColumns(), terminalSize.getRows(), previousScore);
        finalLevel.startGame(screen, terminalSize);
    }

    private void drawSpikes(Screen screen) {
        TextGraphics graphics = screen.newTextGraphics();

        for (int i = 8; i <=10 ; i++) {
            graphics.setForegroundColor(TextColor.ANSI.RED);
            graphics.putString(i, 44, "w");
        }
        for (int i = 19; i <=21 ; i++) {
            graphics.setForegroundColor(TextColor.ANSI.RED);
            graphics.putString(i, 39, "w");
        }
        for( int i = 28; i <=32; i++){
            graphics.setForegroundColor(TextColor.ANSI.RED);
            graphics.putString(i, 34, "w");
        }
        for( int i = 11; i <=14; i++){
            graphics.setForegroundColor(TextColor.ANSI.RED);
            graphics.putString(i, 29, "w");
        }
        for( int i = 30; i <=45; i++){
            graphics.setForegroundColor(TextColor.ANSI.RED);
            graphics.putString(i, 24, "w");
        }
        for( int i = 70; i <=75; i++){
            graphics.setForegroundColor(TextColor.ANSI.RED);
            graphics.putString(i, 29, "w");
        }
        for( int i = 88; i <=100; i++){
            graphics.setForegroundColor(TextColor.ANSI.RED);
            graphics.putString(i, 34, "w");
        }
        for( int i = 60; i <=62; i++){
            graphics.setForegroundColor(TextColor.ANSI.RED);
            graphics.putString(i, 39, "w");
        }
        for( int i = 88; i <=90; i++){
            graphics.setForegroundColor(TextColor.ANSI.RED);
            graphics.putString(i, 44, "w");
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

    private void trueEndgame(Screen screen, TerminalSize terminalSize) throws IOException, InterruptedException {
        screen.clear();
        TextGraphics graphics = screen.newTextGraphics();
        graphics.setForegroundColor(TextColor.ANSI.WHITE);
        graphics.putString(terminalSize.getColumns() / 2 - 8, terminalSize.getRows() / 2, "CONGRATULATIONS!!");
        screen.refresh();
        Thread.sleep(3000);
        screen.clear();
        graphics.putString(terminalSize.getColumns() / 2 - 13, terminalSize.getRows() / 2, "YOU COLLECTED ALL THE COINS");
        screen.refresh();
        Thread.sleep(3000);
        screen.clear();
        graphics.putString(terminalSize.getColumns() / 2 - 6, terminalSize.getRows() / 2, "YOUR REWARD?!");
        screen.refresh();
        Thread.sleep(3000);
        screen.clear();
        graphics.putString(terminalSize.getColumns() / 2 - 11, terminalSize.getRows() / 2, "A KISS ON THE CHEEK º3º");
        screen.refresh();
        Thread.sleep(3000);
        System.exit(0);
    }
    private void badEndgame(Screen screen, TerminalSize terminalSize) throws IOException, InterruptedException {
        screen.clear();
        TextGraphics graphics = screen.newTextGraphics();
        graphics.setForegroundColor(TextColor.ANSI.WHITE);
        graphics.putString(terminalSize.getColumns() / 2 - 8, terminalSize.getRows() / 2, "CONGRATULATIONS!!");
        screen.refresh();
        Thread.sleep(3000);
        screen.clear();
        graphics.putString(terminalSize.getColumns() / 2 - 13, terminalSize.getRows() / 2, "YOU FINISHED THE GAME");
        screen.refresh();
        Thread.sleep(300);
        screen.clear();
        graphics.putString(terminalSize.getColumns() / 2 - 12, terminalSize.getRows() / 2, "NOW, ABOUT THOSE COINS...");
        screen.refresh();
        Thread.sleep(3000);
        System.exit(0);
    }

}

