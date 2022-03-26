import java.util.ArrayList;
import java.util.UUID;

class Translation {
    private final int x;
    private final int y;
    private final boolean isDead;
    private final boolean changeColor;

    public Translation(int x, int y, boolean isDead, boolean changeColor) {
        this.x = x;
        this.y = y;
        this.isDead = isDead;
        this.changeColor = changeColor;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isDead() {
        return isDead;
    }

    public boolean isChangeColor() {
        return changeColor;
    }

    @Override
    public String toString() {
        return "Translation{" +
                "x=" + x +
                ", y=" + y +
                ", isDead=" + isDead +
                ", changeColor=" + changeColor +
                '}';
    }
}

public class MovementMachine {
    String seed;

    MovementMachine(String seed){
        this.seed = seed;
        System.out.println(this.seed);
    }

    ArrayList<Translation> makeMovement(Integer age) {
        int index = age%32;
        char movement_type = this.seed.charAt(index);
        ArrayList<Translation> pattern = new ArrayList<>();
        //TODO: Add movement length calculation
        switch (movement_type){
            case '0':
                pattern.add(new Translation(0, -1,false, false));
                break;
            case '1':
                pattern.add(new Translation(0, 1,false, false));
                break;
            case '2':
                pattern.add(new Translation(-1, 0,false, false));
                break;
            case '3':
                pattern.add(new Translation(1, 0,false, false));
                break;
            case '4':
                //TODO: Figure out sizes
                pattern.add(new Translation(1, 0,false, false));
                pattern.add(new Translation(0, 1,false, false));
                pattern.add(new Translation(-1, 0,false, false));
                pattern.add(new Translation(0, -1,false, false));
                break;
            case '5':
                //TODO: Figure out sizes
                pattern.add(new Translation(0, 1,false, false));
                pattern.add(new Translation(1, 0,false, false));
                pattern.add(new Translation(0, -1,false, false));
                pattern.add(new Translation(-1, 0,false, false));
                break;
            case '6':
                pattern.add(new Translation(1, -1,false, false));
                break;
            case '7':
                pattern.add(new Translation(-1, -1,false, false));
                break;
            case '8':
                pattern.add(new Translation(1, 1,false, false));
                break;
            case '9':
                pattern.add(new Translation(-1, 1,false, false));
                break;
            case 'a':
            case 'b':
                pattern.add(new Translation(0, 0,false, true));
                break;
            case 'e':
            case 'f':
                pattern.add(new Translation(0, 0,true, false));
                break;
            default:
                break;
        }
        //TODO: Add speed (wait times)
        int distance_index = index+1;
        if(distance_index==32){
            distance_index=0;
        }
        char distance_char = this.seed.charAt(distance_index);
        int distance = Integer.parseInt(Character.toString(distance_char),16);
        ArrayList<Translation> movement = new ArrayList<>();
        for (int i = 0; i < distance; i++){
            movement.addAll(pattern);
        }
        return movement;
    }

    public static void main(String[] args){
        MovementMachine movementMachine = new MovementMachine("262808d7b61a478688d55570d1e65017");
        System.out.println(movementMachine.makeMovement(33));
    }

}
