import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scr = new Scanner(System.in);
        String exp = scr.nextLine();
        if (exp.isEmpty()) {//Проверка что пользователь ввел строку
            throw new Exception("Пустая строка");
        }
        System.out.println(calc(exp));
    }

    public static String calc(String exp) throws Exception {
        String[] action = {"+", "-", "*", "/"};
        String[] indexaction = {"\\+", "-", "\\*", "/"};
        String[] exzeprion={"0","1","2","3","4","5","6","7","8","9","I","V","X"};

        boolean f = false;
        for (String s : exzeprion) {//Проверка на коректность ввода
            if (exp.contains(s)) {
                f = true;
                break;
            }
        }
        if (!f) {throw new Exception("Не корретный ввод");}
        int index = -1;
        //Поиск символа
        for (int i = 0; i < action.length; i++) {
            if (exp.contains(action[i])) {
                if (index == -1) {
                    index = i;
                } else {
                    //Проверка если разные символы
                    throw new Exception("Больше 1 символа");
                }
            }
        }
        //Проверка есть ли какой нибудь символ в выражении
        if (index == -1) {
            throw new Exception("В строке отсутсвует математический знак");
        }
        String[] data = exp.split(indexaction[index]);
        //Проверка если одинаковые символы операции или нет 2 числа
        if ((data.length > 2) || (data.length == 1)) {
            throw new Exception("Не корретный ввод");
        }

        //Проверка в одной ли системе находятся числа
        if ((isRoman(data[0])) != (isRoman(data[1]))) {
            throw new Exception("Используются одновременно разные системы счисления");
        }
        if (!isRoman(data[0])) {//Проверка с римскими или арабскими цифрами работаем
            //Арабские цифры
            //Переменные для вычисления
            int a = Integer.parseInt(data[0]);
            int b = Integer.parseInt(data[1]);
            //вычисление
            int c=result(a, b, action[index]);
            return Integer.toString(c);
        } else {//Римские цифры
                for (String s : data) {//Проверка на корректность римского числа к примеру IIII не должн обыть
                char[] ch=s.toCharArray();
                int[] n1={0,0,0};
                for(char c1:ch){
                    if(c1=='I'){n1[0]++;}
                    if(c1=='V'){n1[1]++;}
                    if(c1=='X'){n1[2]++;}
                }
                //Проверка чтобы пользователь не ввел 10 как VV или VIIIII или 4 как IIII
                if ((n1[0]>3)||(n1[1]>1)||(n1[2]>1)){
                    throw new Exception("Не корретно записано римское число");
                }
            }
            int a=romanToInt(data[0]);
            int b= romanToInt(data[1]);
            int c=result(a,b,action[index]);
            if(c<1){
                throw new Exception("Результат операции с римскими числами <1");
            }else {
                return intToRoman(c);
            }
        }
    }

    private static String intToRoman(int x){
        TreeMap<Integer,String> arrabianKeyMap= new TreeMap<>();
        arrabianKeyMap.put(100,"C");
        arrabianKeyMap.put(90,"XC");
        arrabianKeyMap.put(50,"L");
        arrabianKeyMap.put(40,"XL");
        arrabianKeyMap.put(10,"X");
        arrabianKeyMap.put(9,"IX");
        arrabianKeyMap.put(5,"V");
        arrabianKeyMap.put(4,"IV");
        arrabianKeyMap.put(1,"I");

        StringBuilder roman= new StringBuilder();
        int arrabianKey;
        do{
         arrabianKey=arrabianKeyMap.floorKey(x);
         roman.append(arrabianKeyMap.get(arrabianKey));
         x-=arrabianKey;
        }while (x!=0);
        return roman.toString();
    }
    private static int result(int a,int b,String action) throws Exception {
        if ((a > 10) || (a < 1) || (b > 10) || (b < 1)) {//Проверка что число заданое пользователем находится в диапозоне от 1 до 10 включительно
            throw new Exception("Не корретный ввод");
        }
        switch (action) {
            case "+" -> {
                return (a + b);
            }
            case "-" -> {
                return (a - b);
            }
            case "*" -> {
                return (a * b);
            }
            case "/" -> {
                return (Math.abs(a / b));
            }
        }
        return 0;
    }
    private static int romanToInt( String x) {//Перевод римского числа в арабское
        Map<Character,Integer> map = new HashMap<>();
        {
            map.put('I',1);
            map.put('V',5);
            map.put('X',10);
        }
        int result=0;
        int n2=0;
        for (int i=x.length()-1;i>=0;i--){
            int n1= map.get(x.charAt(i));
            if(n1<n2){
                result -= n1;
            }else{
                result += n1;
            }
            n2=n1;
        }
        return result;
    }
    private static boolean isRoman(String number){//Проверка является ли число римским
            Map<Character,Integer> romanKeyMap = new HashMap<>();
            {
                romanKeyMap.put('I',1);
                romanKeyMap.put('V',5);
                romanKeyMap.put('X',10);
            }
       return romanKeyMap.containsKey(number.charAt(0));
    }
}