package wildIsland.animalsAndPlants;

import wildIsland.Info;
import wildIsland.Island;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class Caterpillar extends Herbivore {
    private static ThreadLocalRandom random = ThreadLocalRandom.current();

    public Caterpillar (int i1, int j1) {
        this.i1 = i1;
        this.j1 = j1;
        this.mass = 0.01;
        this.speed = 0;
        this.hanger = 0;
        this.sex = random.nextInt(2);
    }

    public Caterpillar (int i1, int j1, boolean isChild) {
        this.i1 = i1;
        this.j1 = j1;
        this.mass = 0.01;
        this.speed = 0;
        this.hanger = 0;
        this.sex = random.nextInt(2);
        this.isChild = isChild;
    }

    @Override
    public void run() {
        ArrayList<Thread> list = Island.field[this.i1][this.j1].get(4);
        Thread current = Thread.currentThread();

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
            if (random.nextInt(5) == (1)) {
                list.remove(current);
                current.stop();
            }

            try {
                Thread.sleep(Info.iterationTime);
            } catch (InterruptedException e) {
                list.remove(current);
                current.stop();
            }
        }
    }

    @Override
    public void multiply() {
        synchronized (Island.field) {
            try {
                ArrayList<Thread> list = Island.field[this.i1][this.j1].get(4);

                AtomicInteger cageCount = new AtomicInteger(list.size());

                for (int i = 0; i < cageCount.get(); i++) {
                    if (cageCount.get() < 1000) {
                        Caterpillar caterpillar = (Caterpillar) list.get(i);
                        if (caterpillar.sex != this.sex) {
                            Caterpillar caterpillar1 = new Caterpillar(this.i1, this.j1, true);
                            list.add(caterpillar1);
                            caterpillar1.start();
                            break;
                        }
                    }
                }
            }catch (NullPointerException e) {
            }
        }
    }

    public static void createStartCaterpillars () {
        int n = Info.startCaterpillarsCount;
        while (n > 0) {
            int i = random.nextInt(0, Island.width);
            int j = random.nextInt(0, Island.length);

            ArrayList<Thread> list = Island.field[i][j].get(4);
            int threadCount = list.size();

            if (threadCount < 1000) {
                Caterpillar caterpillar = new Caterpillar(i, j);
                list.add(caterpillar);
                caterpillar.start();
                n--;
            } else {
                return;
            }
        }
    }
}

