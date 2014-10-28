/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmetal.util;
public class pseudorandom_test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        // ALWAYS SET SEED BETWEEN 0 AND 1
        for (int i = 0; i < 20; i++){
            double seed = (double)(20-i)/20;
            System.out.println("seed"+seed);
            PseudoRandom.setseed(seed);
            for (int j = 0; j < 10; j++) {
                System.out.println(PseudoRandom.randDouble());
            }
            System.out.println("end------->"+i);
        }
        PseudoRandom.setseed(1);
        for (int j = 0; j < 10; j++) {
                System.out.println(PseudoRandom.randDouble());
            }

    }
}
