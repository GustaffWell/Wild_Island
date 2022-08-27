package wildIsland.animalsAndPlants;

import wildIsland.Info;
import wildIsland.Island;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class Eagle extends Predator{
    private static ThreadLocalRandom random = ThreadLocalRandom.current();
    public static int cageMaxCount = 20;

    public Eagle(int i1, int j1) {
        this.i1 = i1;
        this.j1 = j1;
        this.mass = 6;
        this.speed = 3;
        this.hanger = 1;
        this.sex = random.nextInt(2);
    }

    public Eagle(int i1, int j1, boolean isChild) {
        this.i1 = i1;
        this.j1 = j1;
        this.mass = 6;
        this.speed = 3;
        this.hanger = 1;
        this.sex = random.nextInt(2);
        this.isChild = isChild;
    }

    @Override
    public void run() {
        ArrayList<Thread> list = Island.field[this.i1][this.j1].get(15);
        Thread current = Thread.currentThread();
        int age = 1;

        if (isChild) {
            try {
                Thread.sleep(3 * Info.iterationTime);
            } catch (InterruptedException e) {
                list.remove(current);
                current.interrupt();
            }
        }
        while(!current.isInterrupted()) {
            if (age % 3 == 0 && random.nextInt(10) == 1) {
                this.multiply();
            }
            if (age >= 6) {
                if (random.nextInt(100) <= (1 + 0.1 * age)) {
                    list.remove(current);
                    current.interrupt();
                }
            }

            this.eat();
            this.move();

            if ((age > 6) && (this.hanger >= 1)) {
                list.remove(current);
                current.interrupt();
            }

            try {
                Thread.sleep(Info.iterationTime);
            } catch (InterruptedException e) {
                list.remove(current);
                current.interrupt();
            }
            age++;
            if (hanger < 1) {
                hanger += 0.3;
            }
        }
    }

    @Override
    public void eat() {
        AtomicInteger fate = new AtomicInteger(random.nextInt(10));
        switch (fate.get()) {
            case 0:
                if (random.nextInt(100) < 10) {
                    eatFox();
                }
                break;
            case 1:
                if (random.nextInt(100) < 90) {
                    eatRabbit();
                }
                break;
            case 2:
                if (random.nextInt(100) < 90) {
                    eatMouse();
                }
                break;
            case 3:
                if (random.nextInt(100) < 80) {
                    eatDuck();
                }
        }
    }

    @Override
    public void multiply() {
        synchronized (Island.field) {

            ArrayList<Thread> list = Island.field[this.i1][this.j1].get(15);

            AtomicInteger cageCount = new AtomicInteger(list.size());

            for (int i = 0; i < cageCount.get(); i++) {
                if (cageCount.get() < cageMaxCount) {
                    Eagle eagle = (Eagle) list.get(i);
                    if (eagle.sex != this.sex) {
                        Eagle eagle1 = new Eagle(this.i1, this.j1, true);
                        list.add(eagle1);
                        eagle1.start();
                        break;
                    }
                }
            }
        }
    }


    public static void createStartEagles() {
        int n = Info.startEaglesCount;
        while (n > 0) {
            int i = random.nextInt(0, Island.width);
            int j = random.nextInt(0, Island.length);

            ArrayList<Thread> list = Island.field[i][j].get(15);
            int threadCount = list.size();

            if (threadCount < cageMaxCount) {
                Eagle eagle = new Eagle(i, j);
                list.add(eagle);
                eagle.start();
                n--;
            } else {
                return;
            }
        }
    }
}
