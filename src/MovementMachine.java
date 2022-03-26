import java.util.ArrayList;

class Translation {
    private final int x;
    private final int y;
    private final boolean isDead;
    private final boolean changeColor;
    private final boolean isPregnant;

    public Translation(int x, int y, boolean isDead, boolean changeColor, boolean isPregnant) {
        this.x = x;
        this.y = y;
        this.isDead = isDead;
        this.changeColor = changeColor;
        this.isPregnant = isPregnant;
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

    public boolean isPregnant() {
        return isPregnant;
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
    }

    ArrayList<Translation> makeMovement(Integer age) {
        int index = age%32;
        char movement_type = this.seed.charAt(index);
        ArrayList<Translation> pattern = new ArrayList<>();
        int mov_length = index-1;
        if(mov_length==-1){
            mov_length = 31;
        }
        char mov_char = this.seed.charAt(mov_length);
        int movement = Integer.parseInt(Character.toString(mov_char),16);
//        if(movement>14){
//            movement = 3;
//        } else if (movement>7){
//            movement = 2;
//        } else {
//            movement = 1;
//        }
        movement = 1;
        ArrayList<Translation> pauses = new ArrayList<>();
        for(int i = 0; i<(10-(movement*2)); i++){
            pauses.add(new Translation(0,0,false,false, false));
        }
        switch (movement_type){
            case '0':
                pattern.add(new Translation(0, -movement,false, false, false));
                break;
            case '1':
                pattern.add(new Translation(0, movement,false, false, false));
                break;
            case '2':
                pattern.add(new Translation(-movement, 0,false, false, false));
                break;
            case '3':
                pattern.add(new Translation(movement, 0,false, false, false));
                break;
            case '4':
                for(int i=0; i<movement+1; i++){
                    pattern.add(new Translation(1, 0,false, false, false));
                }
                pattern.addAll(pauses);
                for(int i=0; i<movement+1; i++){
                    pattern.add(new Translation(0, 1,false, false, false));
                }
                pattern.addAll(pauses);
                for(int i=0; i<movement+1; i++){
                    pattern.add(new Translation(-1, 0,false, false, false));
                }
                pattern.addAll(pauses);
                for(int i=0; i<movement+1; i++){
                    pattern.add(new Translation(0, -1,false, false, false));
                }
                pattern.addAll(pauses);
                break;
            case '5':
                for(int i=0; i<movement+1; i++){
                    pattern.add(new Translation(0, 1,false, false, false));
                }
                pattern.addAll(pauses);
                for(int i=0; i<movement+1; i++){
                    pattern.add(new Translation(1, 0,false, false, false));
                }
                pattern.addAll(pauses);
                for(int i=0; i<movement+1; i++){
                    pattern.add(new Translation(0, -1,false, false, false));
                }
                pattern.addAll(pauses);
                for(int i=0; i<movement+1; i++){
                    pattern.add(new Translation(-1, 0,false, false, false));
                }
                pattern.addAll(pauses);
                break;
            case '6':
                pattern.add(new Translation(movement, -movement,false, false, false));
                break;
            case '7':
                pattern.add(new Translation(-movement, -movement,false, false, false));
                break;
            case '8':
                pattern.add(new Translation(movement, movement,false, false, false));
                break;
            case '9':
                pattern.add(new Translation(-movement, movement,false, false, false));
                break;
            case 'a':
                pattern.add(new Translation(0, 0,false, true, false));
                break;
            case 'f':
                if(age>0){
                    pattern.add(new Translation(0, 0,true, false, false));
                }
                break;
            default:
                break;
        }
        int distance_index = index+1;
        if(distance_index==32){
            distance_index=0;
        }
        char distance_char = this.seed.charAt(distance_index);
        int distance = Integer.parseInt(Character.toString(distance_char),16);
        ArrayList<Translation> movements = new ArrayList<>();
        if(!pattern.isEmpty()){
            if(pattern.get(0).isPregnant() || pattern.get(0).isDead() || pattern.get(0).isChangeColor()){
                movements.addAll(pattern);
            }else{
                for (int i = 0; i < distance; i++) {
                    movements.addAll(pattern);
                    movements.addAll(pauses);
                }
            }
        }
        if(age>96){
            movements.clear();
            movements.add(new Translation(0,0,true,false, false));
        }
        return movements;
    }
}
