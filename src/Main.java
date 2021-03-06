import processing.core.PApplet;
import processing.core.PShape;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class Main extends PApplet {
    interface AnimatedPShape {
        void display();
        void step();
    }

    int randColor() {
        int r = ThreadLocalRandom.current().nextInt(0, 255 + 1);
        int g = ThreadLocalRandom.current().nextInt(0, 255 + 1);
        int b = ThreadLocalRandom.current().nextInt(0, 255 + 1);
        return color(r,g,b);
    }

    class Robot implements AnimatedPShape {
        PShape rbt;
        int x;
        int y;
        String seed;
        Integer age;
        MovementMachine movementMachine;
        ArrayList<Translation> pendingMovements;

        public Robot(int x, int y, UUID seed) {
            this.x = x;
            this.y = y;
            this.seed = seed.toString().replace("-", "");
            this.age = 0;
            rbt = createShape(RECT, 0, 0, 10, 20);
            rbt.setFill(randColor());
            movementMachine = new MovementMachine(this.seed);
            this.pendingMovements = new ArrayList<>();
        }

        void calcNextPos() {
            //TODO: Takes UUID and converts it into a movement pattern
            if(this.pendingMovements.isEmpty()){
                this.pendingMovements = this.movementMachine.makeMovement(this.age);
                this.age++;
            }
            else {
                Translation translation = this.pendingMovements.get(0);
                if(translation.isDead()){
                    //TODO: KILL ROBOT
                    System.out.println("ROBOT IS DEAD");
                }
                if(translation.isChangeColor()){
                    rbt.setFill(randColor());
                }
                this.x += translation.getX();
                this.y += translation.getY();
                this.pendingMovements.remove(0);
            }
        }

        @Override
        public void display() {
            pushMatrix();
            translate(this.x, this.y);
            shape(rbt);
            popMatrix();
        }

        @Override
        public void step() {
            calcNextPos();
        }
    }

    ArrayList<Robot> robots = new ArrayList<>();

    public void settings(){
        size(600,600);
    }

    public void setup(){
        for(int i=0;i<20;i++){
            robots.add(new Robot(300,300, UUID.randomUUID()));
        }
        background(0);
    }
    public void draw(){
        clear();
        for(Robot r: robots){
            r.display();
            r.step();
        }
    }

    public static void main(String[] args) {
        PApplet.main("Main");
    }

}