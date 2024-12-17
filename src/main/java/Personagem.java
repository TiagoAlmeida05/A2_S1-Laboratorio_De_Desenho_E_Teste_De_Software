import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;

public class Personagem {
    private int x, y;
    private final char symbol;
    private final TextColor color;
    private double velocityX = 0;
    private String estado = "parado"; // Estado inicial é "parado"

    public Personagem(int startX, int startY, char symbol, TextColor color){
        this.x = startX;
        this.y = startY;
        this.symbol = symbol;
        this.color = color;
    }

    // Métodos para mover a personagem
    public void moveLeft(){
        velocityX = -1;
        estado = "tras"; // Personagem está a andar para trás
    }

    public void moveRight(){
        velocityX = 1;
        estado = "frente"; // Personagem está a andar para a frente
    }

    public void jump(){
        estado = "salto"; // Personagem está a saltar
    }

    public void stopMovement(){
        velocityX = 0;
        if (!estado.equals("salto")) {
            estado = "parado"; // Se não está a saltar, personagem fica parada
        }
    }

    // Atualiza a posição da personagem
    public void updatePosition(int terminalWidth, int terminalHeight){
        x += velocityX;
        x = Math.max(0, Math.min(terminalWidth - 1, x)); // Limita a posição da personagem
    }

    // Desenha o sprite na tela
    public void draw(Screen screen){
        TextColor[][] sprite;

        // Seleciona o sprite baseado no estado
        switch (estado) {
            case "salto":
                sprite = Sprites.spriteJUMP; // Sprite para salto
                break;
            case "frente":
                sprite = Sprites.spriteFRONT; // Sprite para andar para a frente
                break;
            case "tras":
                sprite = Sprites.spriteBEHIND; // Sprite para andar para trás
                break;
            default:
                sprite = Sprites.spriteJUMP; // Se não estiver em movimento, usa o sprite de salto por padrão
                break;
        }

        // Desenha o sprite
        for (int row = 0; row < sprite.length; row++) {
            for (int col = 0; col < sprite[row].length; col++) {
                if (sprite[row][col] != null) {
                    screen.setCharacter(x + col, y + row, new TextCharacter(' ', sprite[row][col], TextColor.ANSI.BLACK));
                }
            }
        }
    }

    // Métodos para obter e definir a posição Y
    public int getY(){
        return y;
    }

    public void setY(int y){
        this.y = y;
    }
}
