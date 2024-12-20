public class Gravidade {
    private double verticalVelocity = 0;
    private final double gravity = 0.035;
    private final double jumpStrength = -1.22;
    private final int groundLevel;

    public Gravidade(int groundLevel){
        this.groundLevel = groundLevel;
    }

    public void updatePosition(Personagem player) {
        verticalVelocity += gravity;
        int spriteHeight = player.getSpriteHeight();
        int newY = player.getY() + (int) verticalVelocity;
        if(newY >= groundLevel - spriteHeight){
            newY = groundLevel - spriteHeight;
            verticalVelocity = 0;
        }
        player.setY(newY);
    }
    public void jump(){
            verticalVelocity = jumpStrength;

    }
    public void resetVerticalVelocity(){
        verticalVelocity = 0;
    }
    public int getGroundLevel(){
        return groundLevel;
    }
}