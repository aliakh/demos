package demo;

public class Delay {

    private Delay() {
    }

    public static void millis(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
