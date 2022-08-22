package wildIsland.animalsAndPlants;

import wildIsland.Info;
import wildIsland.Island;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class Rabbit extends Herbivore {
    private static ThreadLocalRandom random = ThreadLocalRandom.current();

    public Rabbit (int i1, int j1) {
        this.i1 = i1;
        this.j1 = j1;
        this.mass = 2;
        this.speed = 2;
        this.hanger = 0.45;
        this.sex = random.nextInt(2);
    }

    public Rabbit (int i1, int j1, boolean isChild) {
        this.i1 = i1;
        this.j1 = j1;
        this.mass = 2;
        this.speed = 2;
        this.hanger = 0.45;
        this.sex = random.nextInt(2);
        this.isChild = isChild;
    }

    @Override
    public void run() {
        ArrayList<Thread> list = Island.field[this.i1][this.j1].get(3);
        Thread current = Thread.currentThread();
        int age = 1;

        if (isChild) {
            try {
                Thread.sleep(2 * Info.iterationTime);
            } catch (InterruptedException e) {
                list.remove(current);
                current.stop();
            }
        }
        while(!current.isInterrupted()) {
            if (random.nextInt(5) == 1) {
                this.multiply();
                this.multiply();
                this.multiply();
            }
            if (age >= 5) {
                if (random.nextInt(100) <= (1 + 0.1 * age)) {
                    list.remove(current);
                    current.stop();
                }
            }
            if (random.nextInt(2) == 0) {
                    this.eat();
            } else {
                this.move();
            }

            if ((age > 5) && (this.hanger >= 0.45)) {
                list.remove(current);
                current.stop();
            }

            try {
                Thread.sleep(Info.iterationTime);
            } catch (InterruptedException e) {
                list.remove(current);
                current.stop();
            }
            age++;
            if (hanger < 0.45) {
                hanger += 0.1;
            }
        }
    }

    @Override
    public void multiply() {
        synchronized (Island.field) {

            ArrayList<Thread> list = Island.field[this.i1][this.j1].get(3);

            AtomicInteger cageCount = new AtomicInteger(list.size());

            for (int i = 0; i < cageCount.get(); i++) {
                if (cageCount.get() < 150) {
                    Rabbit rabbit = (Rabbit) list.get(i);
                    if (rabbit.sex != this.sex) {
                        Rabbit rabbit1 = new Rabbit(this.i1, this.j1, true);
                        list.add(rabbit1);
                        rabbit1.start();
                        break;
                    }
                }
            }
        }
    }

    public static void createStartRabbits () {
        int n = Info.startRabbitsCount;
        while (n > 0) {
            int i = random.nextInt(0, Island.width);
            int j = random.nextInt(0, Island.length);

            ArrayList<Thread> list = Island.field[i][j].get(3);
            int threadCount = list.size();

            if (threadCount < 150) {
                Rabbit rabbit = new Rabbit(i, j);
                list.add(rabbit);
                rabbit.start();
                n--;
            } else {
                return;
            }
        }
    }
}
