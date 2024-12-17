public class PlatformPhysics {
    private final Gravidade gravidade;
    private final int platformY;
    private final int platformStartX;
    private final int platformEndX;

    public PlatformPhysics(int groundLevel, int platformY, int platformStartX, int platformEndX) {
        this.gravidade = new Gravidade(groundLevel);
        this.platformY = platformY;
        this.platformStartX = platformStartX;
        this.platformEndX = platformEndX;
    }

    public void updatePosition(Personagem player) {
        gravidade.updatePosition(player);

        handlePlatformCollision(player);
    }

    private void handlePlatformCollision(Personagem player) {
        int playerY = player.getY();
        int playerX = player.getX();
        int spriteHeight = player.getSpriteHeight();

        if (playerX >= platformStartX && playerX <= platformEndX) {
            if (playerY + spriteHeight >= platformY && playerY + spriteHeight <= platformY + 1) {
                player.setY(platformY - spriteHeight);
                gravidade.resetVerticalVelocity();
            }
        }
    }

    public void jump(Personagem player) {
        gravidade.jump(player);
    }
}