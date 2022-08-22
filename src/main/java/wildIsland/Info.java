package wildIsland;

public class Info implements Runnable {

    public static final int iterationTime = 1000;           // Параметр, отвечающий за шаг симуляции(частота вывода статистики и ходов существ)

    public static int startPlantsCount = 4000;              //  Параметры, задающие стартовое количество различных существ
    public static int startHorsesCount = 200;                //  Важно их адекватно подобрать под размеры острова
    public static int startDeerCount = 220;
    public static int startRabbitsCount = 500;
    public static int startCaterpillarsCount = 5000;
    public static int startMousesCount = 800;
    public static int startGoatsCount = 400;
    public static int startSheepCount = 400;
    public static int startBoarsCount = 500;
    public static int startBuffaloesCount = 1000;
    public static int startDucksCount = 2000;
    public static int startWolvesCount = 300;
    public static int startBoasCount = 500;
    public static int startFoxesCount = 1000;
    public static int startBearsCount = 250;
    public static int startEaglesCount = 150;


    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(iterationTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Количество растений: " + count(0));
            System.out.printf("%-26s\t|\t%-20s\n", "Травоядные:", "Хищники:");
            System.out.printf("%-20s %-5d\t|\n","Количество лошадей:", count(1));
            System.out.printf("%-20s %-5d\t|%-21s %-5d\n","Количество оленей:", count(2), "\tКоличество волков:", count(11));
            System.out.printf("%-20s %-5d\t|\n", "Количество кроликов:", count(3));
            System.out.printf("%-20s %-5d\t|%-21s %-5d\n", "Количество гусениц:", count(4), "\tКоличество удавов:", count(12));
            System.out.printf("%-20s %-5d\t|\n", "Количество мышей:", count(5));
            System.out.printf("%-20s %-5d\t|%-21s %-5d\n", "Количество коз:", count(6), "\tКоличество лис:", count(13));
            System.out.printf("%-20s %-5d\t|\n", "Количество овец:", count(7));
            System.out.printf("%-20s %-5d\t|%-20s %-5d\n", "Количество кабанов:", count(8), "\tКоличество медведей:", count(14));
            System.out.printf("%-20s %-5d\t|\n", "Количество буйволов:", count(9));
            System.out.printf("%-20s %-5d\t|%-21s %-5d\n", "Количество уток:", count(10), "\tКоличество орлов:", count(15));
            System.out.println("==========================================");
        }
    }

    public static int count(int a) {                        //  Метод считает количество определенных существ на всем острове(в зависимости от аргумента a)
        int realThreadCount = 0;
        for (int i = 0; i < Island.length; i++) {
            for (int j = 0; j < Island.width; j++) {
                realThreadCount += Island.field[i][j].get(a).size();
            }
        }
        if (realThreadCount <= 0) {
            Island.stop();
        }                                                                    //  сейчас симуляция останавливается, если хоть какой-то вид
        return realThreadCount;
    }                                                                        //  исчезает(в том числе и растения)
}

