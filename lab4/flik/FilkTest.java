package flik;
import org.junit.Test;
import static org.junit.Assert.*;

public class FilkTest {
    @Test
    public void Test128(){
        int i = 128;
        int j = 128;
        Boolean isEquals = Flik.isSameNumber(i, j);
        assertTrue(isEquals);
    }

    @Test
    public void Test127(){
        int i = 127;
        int j = 127;
        Boolean isEquals = Flik.isSameNumber(i, j);
        assertTrue(isEquals);
    }

    @Test
    public void TestInteger(){
        Integer i = 127;
        Integer j = 127;
        assertTrue(i == j);
    }


    @Test
    public void TestIntegerEquals(){
        Integer i = 128;
        Integer j = 128;
        assertTrue(Flik.isSameNumber(i, j));
    }


}
