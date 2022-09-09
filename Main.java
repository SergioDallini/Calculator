package rimNum;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
        public static String calc (String input)throws Exception{
        String result = "";
            Pattern pattern1 = Pattern.compile ("\\d\\ *[\\Q+-*/\\E]\\ *\\d");
            Matcher matcher1 = pattern1.matcher(input);
            Pattern pattern2 = Pattern.compile ("[IVX]+\\ *[\\Q+-*/\\E]\\ *[IVX]+");
            Matcher matcher2 = pattern2.matcher(input);
                if(matcher1.find()){
                    result = calcProcess(getABArab(input), input);
                }else if (matcher2.find()){
                    result = decToRim(calcProcess(getABRim(input), input));
                } else {
                throw new Exception("В одном выражении используй только Римские или Арабские цифры и знаки + - * /!!!");
            }
        return result;
    }
    //----------------------------------------------------------------------------------------
    static String[] getABArab(String s) throws Exception {

        Pattern patternA = Pattern.compile("\\d+");
        Matcher matchA = patternA.matcher(s);

        String[] dataAB = new String[2];
        int i = 0;
        while (matchA.find()) {
            dataAB[i] = matchA.group();
            i++;
        }
        return dataAB;
    }
//---------------------------------------------------------------------------------------
static String[] getABRim(String s) throws Exception {

    Pattern patternB = Pattern.compile("[IVX]+");
    Matcher matchB = patternB.matcher(s);

    String[] dataAB = new String[2];
    String[] dataABNew = new String[2];
    int i = 0;
    while (matchB.find()) {
        dataAB[i] = matchB.group();
        i++;
    }
    String a = dataAB[0];
    String b = dataAB[1];
    dataABNew[0] = String.valueOf(rimToDec(a));
    dataABNew[1] = String.valueOf(rimToDec(b));
     return dataABNew;
}
    //---------------------------------------------------------------------------------------
    static String calcProcess(String[] dataAB, String s) throws Exception {
        int res = 0;
        String a = dataAB[0];
        String b = dataAB[1];
        String sign = "";
        //Определение типа арифм.  опер. (Знака)
        Pattern pattern = Pattern.compile ("[\\Q+-*/\\E]");
        Matcher matcher = pattern.matcher(s);
        if(matcher.find()) {
            sign = matcher.group();
        }
        res = switch (sign){
            case "+" -> Integer.parseInt(a) + Integer.parseInt(b);
            case "-" -> Integer.parseInt(a) - Integer.parseInt(b);
            case "*" -> Integer.parseInt(a) * Integer.parseInt(b);
            case "/" -> Integer.parseInt(a) / Integer.parseInt(b);
            default -> throw new Exception("Допустимы только арифметические знаки + - * / !!!");
        };
        return String.valueOf(res);
    }

    static int rimToDec(String str){
        int dig = 0;
        dig = switch (str) {
            case "I" -> 1;
            case "II" -> 2;
            case "III" -> 3;
            case "IV" -> 4;
            case "V" -> 5;
            case "VI" -> 6;
            case "VII" -> 7;
            case "VIII" -> 8;
            case "IX" -> 9;
            case "X" -> 10;
            default -> Integer.parseInt(str);
        };
        return dig;
    }
//-----------------------------------------------------------------------------------
static String decToRim(String str){
    char[] mas = str.toCharArray();
    String dig="";
    String symb = "";
    int num = Integer.parseInt(str);

    if(mas.length > 1){
        if(num > 0 && num < 10){
            symb = "";
        } else if(num >= 10 && num < 20){
            symb = "X";
        } else if (num >= 20 && num < 30){
            symb = "XX";
        }else if (num >= 30 && num < 40){
            symb = "XXX";
        }else if (num >= 40 && num < 50){
            symb = "XL";
        }else if (num >= 50 && num < 60){
            symb = "L";
        }else throw new IllegalStateException("Unexpected value: " + num);
    }
    dig = switch (String.valueOf(mas[mas.length-1])) {
        case "0" -> "";
        case "1" -> "I";
        case "2" -> "II";
        case "3" -> "III";
        case "4" -> "IV";
        case "5" -> "V";
        case "6" -> "VI";
        case "7" -> "VII";
        case "8" -> "VIII";
        case "9" -> "IX";
        case "10" -> "X";
        default -> throw new IllegalStateException("Unexpected value: " + num);
    };
        symb += dig;
    return symb;
}
//-------------------------------------------------------------------------
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите выражение:");
        String str = sc.nextLine();
            System.out.println(calc(str));
    }
}