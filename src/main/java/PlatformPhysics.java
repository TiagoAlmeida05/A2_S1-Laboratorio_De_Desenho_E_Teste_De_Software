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
        boolean onPlatform = player.getY() == platformY - player.getSpriteHeight() &&
                player.getX() >= platformStartX && player.getX() <= platformEndX;
        return onPlatform;
    }

    private boolean isOnGround(Personagem player) {
        boolean onGround = player.getY() == gravidade.getGroundLevel() - player.getSpriteHeight();
        return onGround;
    }

    public void jump(Personagem player) {
        if (isOnPlatform(player) || isOnGround(player)) {
            gravidade.jump(player);
        }
    }
}