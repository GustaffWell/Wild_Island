package wildIsland.animalsAndPlants;

import wildIsland.Info;
import wildIsland.Island;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class Bear extends Predator {
    private static ThreadLocalRandom random = ThreadLocalRandom.current();

    public Bear(int i1, int j1) {
        this.i1 = i1;
        this.j1 = j1;
        this.mass = 500;
        this.speed = 2;
        this.hanger = 80;
        this.sex = random.nextInt(2);
    }

    public Bear(int i1, int j1, boolean isChild) {
        this.i1 = i1;
        this.j1 = j1;
        this.mass = 500;
        this.speed = 2;
        this.hanger = 80;
        this.sex = random.nextInt(2);
        this.isChild = isChild;
    }

    @Override
    public void run() {
        ArrayList<Thread> list = Island.field[this.i1][this.j1].get(14);
        Thread current = Thread.currentThread();
        int age = 1;

        if (isChild) {
            try {
                Thread.sleep(5 * Info.iterationTime);
            } catch (InterruptedException e) {
                list.remove(current);
                current.stop();
            }
        }
        while(!current.isInterrupted()) {
            if (age % 5 == 0 && random.nextInt(10) == 1) {
                this.multiply();
            }
            if (age >= 15) {
                if (random.nextInt(100) <= (1 + 0.1 * age)) {
                    list.remove(current);
                    current.stop();
                }
            }

            this.eat();
            this.move();

            if ((age > 10) && (this.hanger >= 80)) {
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
            if (hanger < 80) {
                hanger += 2;
            }
        }
    }

    @Override
    public void eat() {
        AtomicInteger fate = new AtomicInteger(random.nextInt(10));
        switch (fate.get()) {
            case 0:
                if (random.nextInt(100) < 80) {
                    eatBoa();
                }
                break;
            case 1:
                if (random.nextInt(100) < 40) {
                    eatHorse();
                }
                break;
            case 2:
                if (random.nextInt(100) < 80) {
                    eatDeer();
                }
                break;
            case 3:
                if (random.nextInt(100) < 80) {
                    eatRabbit();
                }
                break;
            case 4:
                if (random.nextInt(100) < 90) {
                    eatMouse();
                }
                break;
            case 5:
                if (random.nextInt(100) < 70) {
                    eatGoat();
                }
                break;
            case 6:
                if (random.nextInt(100) < 70) {
                    eatSheep();
                }
                break;
            case 7:
                if (random.nextInt(100) < 50) {
                    eatBoar();
                }
                break;
            case 8:
                if (random.nextInt(100) < 20) {
                    eatBuffalo();
                }
                break;
            case 9:
                if (random.nextInt(100) < 10) {
                    eatDuck();
                }
        }
    }

    @Override
    public void multiply() {
        synchronized (Island.field) {

            ArrayList<Thread> list = Island.field[this.i1][this.j1].get(14);

            AtomicInteger cageCount = new AtomicInteger(list.size());

            for (int i = 0; i < cageCount.get(); i++) {
                if (cageCount.get() < 5) {
                    Bear bear = (Bear) list.get(i);
                    if (bear.sex != this.sex) {
                        Bear bear1 = new Bear(this.i1, this.j1, true);
                        list.add(bear1);
                        bear1.start();
                        break;
                    }
                }
            }
        }
    }


    public static void createStartBears() {
        int n = Info.startBearsCount;
        while (n > 0) {
            int i = random.nextInt(0, Island.width);
            int j = random.nextInt(0, Island.length);

            ArrayList<Thread> list = Island.field[i][j].get(14);
            int threadCount = list.size();

            if (threadCount < 5) {
                Bear bear = new Bear(i, j);
                list.add(bear);
                bear.start();
                n--;
            } else {
                return;
            }
        }
    }
}
