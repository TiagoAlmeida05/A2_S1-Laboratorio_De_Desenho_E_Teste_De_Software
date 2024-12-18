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

        if (isOnPlatform(player)) {
            gravidade.resetVerticalVelocity();
            player.setY(platformY - player.getSpriteHeight());
        }
    }

    private boolean isOnPlatform(Personagem player) {
        return player.getY() == platformY - player.getSpriteHeight() &&
                player.getX() >= platformStartX && player.getX() <= platformEndX;
    }

    private boolean isOnGround(Personagem player) {
        return player.getY() == gravidade.getGroundLevel() - player.getSpriteHeight();
    }

    public void jump(Personagem player) {
        if (isOnPlatform(player) || isOnGround(player)) {
            gravidade.jump();
        }
    }

}