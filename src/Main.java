import processing.core.PApplet;
import processing.core.PShape;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class Main extends PApplet {

    interface AnimatedPShape {
        void display();
        void step();
        void backStep();
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
        int age;
        int age_step;
        MovementMachine movementMachine;
        ArrayList<Translation> pendingMovements;
        boolean isDead = false;
        boolean isPregnant = false;

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

        @Override
        public void display() {
            pushMatrix();
            translate(this.x, this.y);
            shape(rbt);
            popMatrix();
        }

        @Override
        public void step() {
            //Takes UUID and converts it into a movement pattern
            if(this.age_step == this.pendingMovements.size() || this.pendingMovements.isEmpty()){
                this.pendingMovements = this.movementMachine.makeMovement(this.age);
                this.age++;
                this.age_step = 0;
            }
            else {
                Translation translation = this.pendingMovements.get(this.age_step);
                if(translation.isDead()){
                    this.isDead = true;
                }
                if(translation.isChangeColor()){
                    //TODO: Make predictable
                    rbt.setFill(randColor());
                }
                if(translation.isPregnant()){
                    this.isPregnant = true;
                    System.out.println("GIVE BIRTH.");
                }
                this.x += translation.getX();
                this.y += translation.getY();
                this.age_step++;
            }
        }

        @Override
        public void backStep() {
            //Takes UUID and converts it into a movement pattern
            if(this.age_step == 0 || this.pendingMovements.isEmpty()){
                this.pendingMovements = this.movementMachine.makeMovement(this.age);
                this.age--;
                this.age_step = this.pendingMovements.size()-1;
            }
            else {
                Translation translation = this.pendingMovements.get(this.age_step);
                if(translation.isDead()){
                    this.isDead = false;
                }
                if(translation.isChangeColor()){
                    //TODO: Make predictable
                    rbt.setFill(randColor());
                }
                if(translation.isPregnant()){
                    this.isPregnant = false;
                    System.out.println("UN-BIRTH.");
                }
                this.x -= translation.getX();
                this.y -= translation.getY();
                this.age_step--;
            }
        }
    }

    ArrayList<Robot> robots = new ArrayList<>();
    boolean isPaused = false;
    boolean isForward = true;

    void gameStep(){
        clear();
        ArrayList<Robot> robots_cpy = (ArrayList<Robot>) robots.clone();
        for(Robot r: robots_cpy){
            if(!r.isDead){
                r.step();
                r.display();
                gameStep_helper(r);
            }
        }
    }

    void gameBackStep(){
        clear();
        ArrayList<Robot> robots_cpy = (ArrayList<Robot>) robots.clone();
        for(Robot r: robots_cpy){
            r.backStep();
            r.display();
            gameStep_helper(r);
        }
    }

    private void gameStep_helper(Robot r) {
        if(r.isPregnant){
            r.isPregnant = false;
            int x = ThreadLocalRandom.current().nextInt(0, 600 + 1);
            int y = ThreadLocalRandom.current().nextInt(0, 600 + 1);
            robots.add(new Robot(x,y, UUID.randomUUID()));
        }
    }

    public void settings(){
        size(600,600);
    }

    public void setup(){
        for(int i=0;i<1;i++){
            int x = ThreadLocalRandom.current().nextInt(0, 600 + 1);
            int y = ThreadLocalRandom.current().nextInt(0, 600 + 1);
            robots.add(new Robot(300,300, UUID.fromString("0b0b0b0f-0b0b-0b0b-0b0b-0b0b0b0b0b0f")));
        }
        background(0);
    }

    public void keyPressed() {
        final int k = keyCode;
        if(isPaused){
            if(k==39){
                redraw();
            }
            else if(k==37){
                this.isForward = false;
                redraw();
            }
        }
        if (k == 'P') {
            if (looping) {
                noLoop();
                this.isPaused = true;
            } else {
                loop();
                this.isPaused = false;
            }
        }
    }

    public void draw(){
        if(isForward){
            gameStep();
        }
        else {
            gameBackStep();
            this.isForward = true;
        }
    }

    public static void main(String[] args) {
        PApplet.main("Main");
    }

}