package flik;

/** An Integer tester created by Flik Enterprises.
 * @author Josh Hug
 * 这里的错误是由自动装箱机制引发的。
 * 原本两个integer之间比较就应该不相等，但是因为Java
 * autoBoxing提前将-128-127的数字对象装入缓存中，所有这里的
 * 1-127的对象实际上没有创建而是直接从缓存中提取的。
 *
 * 如何解决：
 * 很简单，可以使用integer的equals方法来比较两个数字是否相等。
 * 或者提前deboxing，将连个integer转化为int再来比较。
 * */
public class Flik {
    /** @param a Value 1
     *  @param b Value 2
     *  @return Whether a and b are the same */
    public static boolean isSameNumber(Integer a, Integer b) {
        return a.equals(b);
    }
}
