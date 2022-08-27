package wildIsland.animalsAndPlants;
/*
Класс-предок для всех травоядных.
Определяет общий метод (поесть траву) при условии, что существо голодно и трава есть на клетке
 + В некоторых классах травоядных есть специфический метод (съесть гусеницу).
*/

import wildIsland.Island;

public abstract class Herbivore extends Animal {

    @Override
    public void eat() {
        synchronized (Island.field) {
            if (this.hanger > 0 && Island.field[this.i1][this.j1].get(0).size() > 0) {
                Thread thread = Island.field[this.i1][this.j1].get(0).get(0);
                Island.field[this.i1][this.j1].get(0).remove(thread);
                thread.interrupt();
                this.hanger -= 1;
            }
        }
    }
}
