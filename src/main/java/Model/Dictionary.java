package Model;

public class Dictionary {

    private static Dictionary instance;

    private Dictionary(){
        instance = this;
    }

    public static Dictionary getInstance(){
        return instance;
    }
}
