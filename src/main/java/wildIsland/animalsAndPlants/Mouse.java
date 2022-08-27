package wildIsland.animalsAndPlants;

import wildIsland.Info;
import wildIsland.Island;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class Mouse extends Herbivore {
    private static ThreadLocalRandom random = ThreadLocalRandom.current();
    public static int cageMaxCount = 500;

    public Mouse (int i1, int j1) {
        this.i1 = i1;
        this.j1 = j1;
        this.mass = 0.05;
        this.speed = 1;
        this.hanger = 0.1;
        this.sex = random.nextInt(2);
    }

    public Mouse (int i1, int j1, boolean isChild) {
        this.i1 = i1;
        this.j1 = j1;
        this.mass = 0.05;
        this.speed = 1;
        this.hanger = 0.1;
        this.sex = random.nextInt(2);
        this.isChild = isChild;
    }

    @Override
    public void run() {
        ArrayList<Thread> list = Island.field[this.i1][this.j1].get(5);
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
            if (random.nextInt(2) == 1) {
                this.multiply();
            }
            if (age >= 2) {
                if (random.nextInt(50) <= (1 + 0.2 * age)) {
                    list.remove(current);
                    current.stop();
                }
            }
            if (random.nextInt(2) == 0) {
                this.eat();
            } else {
                this.eatCaterpillar();
                this.move();
            }

            if ((age > 3) && (this.hanger >= 0.1)) {
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
            if (hanger < 0.1) {
                hanger += 0.03;
            }
        }
    }

    @Override
    public void multiply() {
        synchronized (Island.field) {

            ArrayList<Thread> list = Island.field[this.i1][this.j1].get(5);

            AtomicInteger cageCount = new AtomicInteger(list.size());

            for (int i = 0; i < cageCount.get(); i++) {
                if (cageCount.get() < cageMaxCount) {
                    Mouse mouse = (Mouse) list.get(i);
                    if (mouse.sex != this.sex) {
                        Mouse mouse1 = new Mouse(this.i1, this.j1, true);
                        list.add(mouse1);
                        mouse1.start();
                        break;
                    }
                }
            }
        }
    }

    public synchronized void eatCaterpillar() {
        synchronized (Island.field) {
            if (random.nextInt(10) != 9) {
                if (this.hanger > 0 && Island.field[this.i1][this.j1].get(4).size() > 0) {
                    Thread thread = Island.field[this.i1][this.j1].get(4).get(0);
                    Island.field[this.i1][this.j1].get(4).remove(thread);
                    this.hanger -= 0.01;
                }
            }
        }
    }

    public static void createStartMouses () {
        int n = Info.startMousesCount;
        while (n > 0) {
            int i = random.nextInt(0, Island.width);
            int j = random.nextInt(0, Island.length);

            ArrayList<Thread> list = Island.field[i][j].get(5);
            int threadCount = list.size();

            if (threadCount < cageMaxCount) {
                Mouse mouse = new Mouse(i, j);
                list.add(mouse);
                mouse.start();
                n--;
            } else {
                return;
            }
        }
    }
}

