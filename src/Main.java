import processing.core.PApplet;
import processing.core.PShape;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class Main extends PApplet {
    interface AnimatedPShape {
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
        UUID seed;

        public Robot(int x, int y, UUID seed) {
            this.x = x;
            this.y = y;
            this.seed = seed;
            rbt = createShape(RECT, 0, 0, 10, 20);
            rbt.setFill(randColor());
        }

        void display() {
            pushMatrix();
            translate(this.x, this.y);
            shape(rbt);
            popMatrix();
        }

        void calcNextPos() {
            //TODO: Takes UUID and converts it into a movement pattern
            System.out.println(this.seed);
        }

        @Override
        public void step() {
            calcNextPos();
        }
    }

    Robot testRbt;

    public void settings(){
        size(600,600);
    }

    public void setup(){
         testRbt = new Robot(50, 50, UUID.randomUUID());
         background(0);
    }
    public void draw(){
        clear();
        testRbt.display();
        testRbt.step();
    }

    public static void main(String[] args) {
        PApplet.main("Main");
    }

}