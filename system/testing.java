package system;

public class testing {
    public static void main(String[] args) {
        RegisterCustomer test = new RegisterCustomer();
        if (test.Register("mffm", "fuckthisshit", "@gmaiffl.com", "ahmed", "wesam") == 1) {
            System.out.println("finally it works");
        } else {
            System.out.println("fuck this shit");
        }
//        login l = new login();
//        if (l.Login("@gmaiffl.com", "fuckthisshit")) {
//            System.out.println("finally it works");
//        } else {
//            System.out.println("fuck this shit");
//        }
    }
}
