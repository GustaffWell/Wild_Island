package wildIsland.animalsAndPlants;

/*
Общий класс-предок для хищников. Определяет методы для поедания различных животных.
Внутри самих классов есть реализации метода поесть, использующие нужные методы из тех, что определены здесь
 */

import wildIsland.Island;

public abstract class Predator extends Animal {

    public void eatHorse() {
        synchronized (Island.field) {
            if (this.hanger > 0 && Island.field[this.i1][this.j1].get(1).size() > 0) {
                Thread thread = Island.field[this.i1][this.j1].get(1).get(0);
                Island.field[this.i1][this.j1].get(1).remove(thread);

                if (this.hanger - 400 < 0) {
                    this.hanger = 0;
                } else {
                    this.hanger -= 400;
                }
            }
        }
    }

    public void eatDeer() {
        synchronized (Island.field) {
            if (this.hanger > 0 && Island.field[this.i1][this.j1].get(2).size() > 0) {
                Thread thread = Island.field[this.i1][this.j1].get(2).get(0);
                Island.field[this.i1][this.j1].get(2).remove(thread);

                if (this.hanger - 300 < 0) {
                    this.hanger = 0;
                } else {
                    this.hanger -= 300;
                }
            }
        }
    }

    public void eatRabbit() {
        synchronized (Island.field) {
            if (this.hanger > 0 && Island.field[this.i1][this.j1].get(3).size() > 0) {
                Thread thread = Island.field[this.i1][this.j1].get(3).get(0);
                Island.field[this.i1][this.j1].get(3).remove(thread);

                if (this.hanger - 2 < 0) {
                    this.hanger = 0;
                } else {
                    this.hanger -= 2;
                }
            }
        }
    }

    public void eatCaterpillar() {
        synchronized (Island.field) {
            if (this.hanger > 0 && Island.field[this.i1][this.j1].get(4).size() > 0) {
                Thread thread = Island.field[this.i1][this.j1].get(4).get(0);
                Island.field[this.i1][this.j1].get(4).remove(thread);

                if (this.hanger - 0.01 < 0) {
                    this.hanger = 0;
                } else {
                    this.hanger -= 0.01;
                }
            }
        }
    }

    public void eatMouse() {
        synchronized (Island.field) {
            if (this.hanger > 0 && Island.field[this.i1][this.j1].get(5).size() > 0) {
                Thread thread = Island.field[this.i1][this.j1].get(5).get(0);
                Island.field[this.i1][this.j1].get(5).remove(thread);

                if (this.hanger - 0.05 < 0) {
                    this.hanger = 0;
                } else {
                    this.hanger -= 0.05;
                }
            }
        }
    }

    public void eatGoat() {
        synchronized (Island.field) {
            if (this.hanger > 0 && Island.field[this.i1][this.j1].get(6).size() > 0) {
                Thread thread = Island.field[this.i1][this.j1].get(6).get(0);
                Island.field[this.i1][this.j1].get(6).remove(thread);

                if (this.hanger - 60 < 0) {
                    this.hanger = 0;
                } else {
                    this.hanger -= 60;
                }
            }
        }
    }

    public void eatSheep() {
        synchronized (Island.field) {
            if (this.hanger > 0 && Island.field[this.i1][this.j1].get(7).size() > 0) {
                Thread thread = Island.field[this.i1][this.j1].get(7).get(0);
                Island.field[this.i1][this.j1].get(7).remove(thread);

                if (this.hanger - 70 < 0) {
                    this.hanger = 0;
                } else {
                    this.hanger -= 70;
                }
            }
        }
    }

    public void eatBoar() {
        synchronized (Island.field) {
            if (this.hanger > 0 && Island.field[this.i1][this.j1].get(8).size() > 0) {
                Thread thread = Island.field[this.i1][this.j1].get(8).get(0);
                Island.field[this.i1][this.j1].get(8).remove(thread);

                if (this.hanger - 400 < 0) {
                    this.hanger = 0;
                } else {
                    this.hanger -= 400;
                }
            }
        }
    }

    public void eatBuffalo() {
        synchronized (Island.field) {
            if (this.hanger > 0 && Island.field[this.i1][this.j1].get(9).size() > 0) {
                Thread thread = Island.field[this.i1][this.j1].get(9).get(0);
                Island.field[this.i1][this.j1].get(9).remove(thread);

                if (this.hanger - 700 < 0) {
                    this.hanger = 0;
                } else {
                    this.hanger -= 700;
                }
            }
        }
    }

    public void eatDuck() {
        synchronized (Island.field) {
            if (this.hanger > 0 && Island.field[this.i1][this.j1].get(10).size() > 0) {
                Thread thread = Island.field[this.i1][this.j1].get(10).get(0);
                Island.field[this.i1][this.j1].get(10).remove(thread);

                if (this.hanger - 1 < 0) {
                    this.hanger = 0;
                } else {
                    this.hanger -= 1;
                }
            }
        }
    }

    public void eatBoa() {
        synchronized (Island.field) {
            if (this.hanger > 0 && Island.field[this.i1][this.j1].get(12).size() > 0) {
                Thread thread = Island.field[this.i1][this.j1].get(12).get(0);
                Island.field[this.i1][this.j1].get(12).remove(thread);

                if (this.hanger - 15 < 0) {
                    this.hanger = 0;
                } else {
                    this.hanger -= 15;
                }
            }
        }
    }

    public void eatFox() {
        synchronized (Island.field) {
            if (this.hanger > 0 && Island.field[this.i1][this.j1].get(13).size() > 0) {
                Thread thread = Island.field[this.i1][this.j1].get(13).get(0);
                Island.field[this.i1][this.j1].get(13).remove(thread);

                if (this.hanger - 8 < 0) {
                    this.hanger = 0;
                } else {
                    this.hanger -= 8;
                }
            }
        }
    }
}
