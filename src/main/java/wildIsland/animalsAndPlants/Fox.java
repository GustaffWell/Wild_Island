package wildIsland.animalsAndPlants;

import wildIsland.Info;
import wildIsland.Island;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class Fox extends Predator{
    private static ThreadLocalRandom random = ThreadLocalRandom.current();
    public static int cageMaxCount = 30;

    public Fox(int i1, int j1) {
        this.i1 = i1;
        this.j1 = j1;
        this.mass = 8;
        this.speed = 2;
        this.hanger = 2;
        this.sex = random.nextInt(2);
    }

    public Fox(int i1, int j1, boolean isChild) {
        this.i1 = i1;
        this.j1 = j1;
        this.mass = 8;
        this.speed = 2;
        this.hanger = 2;
        this.sex = random.nextInt(2);
        this.isChild = isChild;
    }

    @Override
    public void run() {
        ArrayList<Thread> list = Island.field[this.i1][this.j1].get(13);
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
            if (age % 2 == 0 && random.nextInt(10) == 1) {
                this.multiply();
            }
            if (age >= 8) {
                if (random.nextInt(100) <= (1 + 0.01 * age)) {
                    list.remove(current);
                    current.stop();
                }
            }

            this.eat();
            this.move();

            if ((age > 10) && (this.hanger >= 2)) {
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
            if (hanger < 2) {
                hanger += 0.4;
            }
        }
    }

    @Override
    public void eat() {
        AtomicInteger fate = new AtomicInteger(random.nextInt(4));
        switch (fate.get()) {
            case 0:
                if (random.nextInt(100) < 70) {
                    eatRabbit();
                }
                break;
            case 1:
                if (random.nextInt(100) < 90) {
                    eatMouse();
                }
                break;
            case 2:
                if (random.nextInt(100) < 60) {
                    eatDuck();
                }
                break;
            case 3:
                if (random.nextInt(100) < 40) {
                    eatCaterpillar();
                }
        }
    }

    @Override
    public void multiply() {
        synchronized (Island.field) {

            ArrayList<Thread> list = Island.field[this.i1][this.j1].get(13);

            AtomicInteger cageCount = new AtomicInteger(list.size());

            for (int i = 0; i < cageCount.get(); i++) {
                if (cageCount.get() < cageMaxCount) {
                    Fox fox = (Fox) list.get(i);
                    if (fox.sex != this.sex) {
                        Fox fox1 = new Fox(this.i1, this.j1, true);
                        list.add(fox1);
                        fox1.start();
                        break;
                    }
                }
            }
        }
    }


    public static void createStartFoxes() {
        int n = Info.startFoxesCount;
        while (n > 0) {
            int i = random.nextInt(0, Island.width);
            int j = random.nextInt(0, Island.length);

            ArrayList<Thread> list = Island.field[i][j].get(13);
            int threadCount = list.size();

            if (threadCount < cageMaxCount) {
                Fox fox = new Fox(i, j);
                list.add(fox);
                fox.start();
                n--;
            } else {
                return;
            }
        }
    }

}
