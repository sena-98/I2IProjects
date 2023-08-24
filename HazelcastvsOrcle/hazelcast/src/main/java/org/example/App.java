package org.example;
import com.hazelcast.collection.IList;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import java.util.Random;
import org.apache.commons.lang3.time.StopWatch;
import java.sql.*;


public class App 
{
    public static HazelcastInstance hazelcast = Hazelcast.newHazelcastInstance ();
    public static IList<Integer> hzStock = hazelcast.getList("RANDOM_NUMBERS");
//connection yap
    public static long hzPut(int n){
        Random rand = new Random();
        StopWatch stopWatch = new StopWatch();

        stopWatch.start();
        for(int i = 0; i<n; i++) {
            int random = rand.nextInt();
            hzStock.add(random);
        }

        stopWatch.stop();

        return stopWatch.getTime();
    }
    public static long hzGet(int n){
        Random rand = new Random();
        StopWatch stopWatch = new StopWatch();

        stopWatch.start();

        for(int i = 0; i<hzStock.size(); i++) {
            hzStock.get(rand.nextInt(n-1));
        }

        stopWatch.stop();

        return stopWatch.getTime();
    }

    public static long orcPut(int n){

        Random rand = new Random();
        StopWatch stopWatch = new StopWatch();
        String orcURL="jdbc:oracle:thin:@TEST:1521:xe";
        String orcUser= "SYS";
        String orcPassword ="Sena1978";

        try {
            Connection con = DriverManager.getConnection(
                    orcURL, orcUser, orcPassword);
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO RANDOM_NUMBERS VALUES(?,?)");

            stopWatch.start();
            for(int i = 0; i<n; i++) {
                int random = rand.nextInt();
                hzStock.get(rand.nextInt(n-1));

                pstmt.setInt(i, random);
                pstmt.execute();
            }

            stopWatch.stop();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return stopWatch.getTime();

    }
    public static long orcGet(int n){
        Random rand = new Random();
        StopWatch stopWatch = new StopWatch();
        String orcURL="jdbc:oracle:thin:@TEST:1521:xe";
        String orcUser= "SYS";
        String orcPassword ="Sena1978";

        try {
            Connection con = DriverManager.getConnection(
                    orcURL, orcUser, orcPassword);
            int limit= n;

            stopWatch.start();
            for(int i = 0; i<n; i++) {
                PreparedStatement pstmt = con.prepareStatement("SELECT * FROM RANDOM_NUMBER WHERE RANDOM_NUMBERS[0]= "+i);
                int random = rand.nextInt();
                pstmt.setInt(i, random);
                pstmt.execute();
            }

            stopWatch.stop();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return stopWatch.getTime();
    }
    public static void main( String[] args )
    {
        System.out.println("Hazelcast 20000 numbers put:"+ hzPut(20000));
        System.out.println("Hazelcast 20000 numbers get:"+hzGet(20000));
        System.out.println("Oracle 20000 numbers put:"+orcPut(20000));
        System.out.println("Oracle 20000 numbers get:"+orcGet(20000));

        System.out.println("Hazelcast 100000 numbers put:"+ hzPut(100000));
        System.out.println("Hazelcast 100000 numbers get:"+hzGet(100000));
        System.out.println("Oracle 100000 numbers put:"+orcPut(100000));
        System.out.println("Oracle 100000 numbers get:"+orcGet(100000));


    }
}
