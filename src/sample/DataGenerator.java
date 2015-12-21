package sample;

/**
 * Created by root on 8/12/15.
 */
public class DataGenerator {
    public static Category generateSpendings()
    {
        Category child1 = new Category("ROWER", true, 100.0,200.0);
        Category child2 = new Category("MPK", true, 95.0,50.0);
        Category child3 = new Category("TARCZE", true, 1000.0,1000.0);
        Category child4 = new Category("KLOCKI", true, 300.0,250.0);
        Category child5 = new Category("HAMULCE", true, 0.0,100.0);
        Category child6 = new Category("SAMOCHOD", true, 500.0,600.0);
        Category child7 = new Category("TRANSPORT", true, 1000.0,1000.0);
        Category root = new Category("Wydatek", true, 0.0,0.0);
        child5.addChildren(child4);
        child5.addChildren(child3);
        child6.addChildren(child5);
        child7.addChildren(child6);
        child7.addChildren(child1);
        child7.addChildren(child2);
        root.addChildren(child7);
        return root;

    }

    public static Category generateEarnings()
    {
        Category root = new Category("Przychod", false, 0.0,0.0);
        Category child1 = new Category("PENSJA", false, 3000.0,5000.0);
        Category child2 = new Category("PREMIA", false, 1000.0,500.0);
        Category child3 = new Category("BIZNES", false, 10000.0,4000.0);
        root.addChildren(child1);
        root.addChildren(child2);
        root.addChildren(child3);
        return root;
    }

}
