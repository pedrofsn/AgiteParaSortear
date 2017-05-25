package agite.para.sortear.utils;

import junit.framework.Assert;

import java.util.Random;

/**
 * Created by pedrofsn on 25/05/2017.
 */

public class RandomNumberGenerator extends Random {

    /**
     * @param min generated value. Can't be > then max
     * @param max generated value
     * @return values in closed range [min, max].
     */
    public int nextInt(int min, int max) {
        Assert.assertFalse("min can't be > then max; values:[" + min + ", " + max + "]", min > max);
        if (min == max) {
            return max;
        }

        return nextInt(max - min + 1) + min;
    }

}
