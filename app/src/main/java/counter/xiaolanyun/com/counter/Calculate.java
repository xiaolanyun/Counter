package counter.xiaolanyun.com.counter;
import java.math.BigDecimal;

import android.R.integer;
/**
 * Created by 小烂云 on 2017/8/8.
 */
public class Calculate {
    private int scale = 10;
    // 测量


    public static double add(BigDecimal num1,BigDecimal num2) {
        return num1.add(num2).doubleValue();
    }

    public static double sub(BigDecimal num1,BigDecimal num2) {
        return num1.subtract(num2).doubleValue();
    }

    public static double mul(BigDecimal num1,BigDecimal num2) {
        return num1.multiply(num2).doubleValue();
    }

    public static double div(BigDecimal num1,BigDecimal num2, int scale) {

        return num1.divide(num2, scale    , BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static double round(Double result, int scale) {
        BigDecimal b = new BigDecimal(Double.toString(result));//使用转换成字符串会确保数据准确性
        BigDecimal one = new BigDecimal(1);
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
