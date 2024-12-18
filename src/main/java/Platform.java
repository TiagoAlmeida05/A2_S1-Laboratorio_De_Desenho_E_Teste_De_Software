public class Platform {
    private final int startX;
    private final int endX;
    private final int y;

    public Platform(int startX, int endX, int y) {
        this.startX = startX;
        this.endX = endX;
        this.y = y;
    }

    public int getStartX() {
        return startX;
    }

    public int getEndX() {
        return endX;
    }

    public int getY() {
        return y;
    }

    public boolean isPlayerOnPlatform(Personagem player) {
        return player.getY() == y - player.getSpriteHeight() &&
                player.getX() >= startX && player.getX() <= endX;
    }
}