package folu;

import java.util.HashMap;
import java.util.Map;

public class MathStuff {
    public static void main(String[] args) {
        System.out.println(sumOfDigits(123));
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.put("foo", 1);
        map.put("bar", 2);
        map.put("baz", 2);
        map.put("qux", 3);
        System.out.println(bestKey(map));
    }
    public static int sumOfDigits(Integer digits){
        int sum = 0;
        // converts the digits to string and then it splits it apart >> 1234 >> "1234" >> ["1" ...]
        String[] digitsAsString = digits.toString().split("");
        for (String digit : digitsAsString){
            //DataType.parseInt() converts the string to integer "
            sum += Integer.parseInt(digit);
        }
        return sum;
    }

    public static String bestKey(HashMap<String, Integer> items){
        int maxValue = 0;
        String owner = "";
        for (Map.Entry<String, Integer> entry : items.entrySet()){
            Integer value = entry.getValue();
            if (value > maxValue){
                maxValue = value;
                owner = entry.getKey();
            }
        }
        return owner;
    }
}
