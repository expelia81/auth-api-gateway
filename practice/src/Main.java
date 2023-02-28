public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        System.out.println(ttt.first.name);




    }

    public static enum ttt{
        first("1번",111),
        second("2번", 222),
        third("3번", 333);


        private final String name;
        private final int size;

        ttt(String name, int size) {
            this.name = name;
            this.size = size;
        }
    }
}