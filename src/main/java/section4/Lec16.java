package section4;

public class Lec16 {
    public static void main(String[] args) {
        StringFilter filter = new StringFilter() {
            @Override
            public boolean predicate(String str) {
                return str.startsWith("A");
            }
        };

        StringFilter filter1 = s -> s.startsWith("A");
    }
}
