package system;

public class testing {
    public static void main(String[] args) {
        register test = new register("ahmed", "ouch");
        if (test.verifyRegistration() == 1) {
            System.out.println("finally it works");
        } else {
            System.out.println("fuck this shit");
        }
    }
}
