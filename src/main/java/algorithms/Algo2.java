package algorithms;

//https://www.codewars.com/kata/541c8630095125aba6000c00/train/java
public class Algo2 {
    public static int digital_root(int n) {
        while(n % 10 != n) {
            int sum = 0;
            while (n != 0){
                sum += n % 10;
                n /= 10;
            }
            n = sum;
        }
        return n;
    }
}
