package info.bluefoot.scripts.id3name;

public class Main {

    public static void main(String[] args) {
        if(args.length!=1) {
            throw new RuntimeException("Must provide a path");
        }
        new Parser(args[0]).parse();
    }

}
