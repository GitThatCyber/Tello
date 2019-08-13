public class Main {

    public static void main(String args[]) {
       ThreadMng R1 = new ThreadMng( "Control Thread");
       R1.start();
       
       ThreadMng R2 = new ThreadMng( "Video Thread");
       R2.start();
    }   
 }