public class Gravidade {
    private double verticalVelocity = 0;
    private final double gravity = 0.05;
    private final double jumpStrength = -1.2;
    private final int groundLevel;

    public Gravidade(int groundLevel){
        this.groundLevel = groundLevel;
    }

    public void updatePosition(Personagem player) {
        verticalVelocity += gravity;
        int newY = player.getY() + (int) verticalVelocity;
        if(newY >= groundLevel -1){
            newY = groundLevel -1;
            verticalVelocity = 0;
        }
        player.setY(newY);
    }
    public void jump(Personagem player){
        if(player.getY() >= groundLevel -1){
            verticalVelocity = jumpStrength;
        }
    }
}