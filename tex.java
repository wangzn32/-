package four;

import java.math.BigDecimal;
import java.util.Random;
import java.util.Scanner;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class tex{
	public static void main(String[] args) {
		IntFourArithmeticOperations1(10000, 10);
	}
	@SuppressWarnings("resource")
	public static void IntFourArithmeticOperations1(int number, int bound) {
		Random random = new Random();
		boolean tf = false;
		Scanner sc = new Scanner(System.in);
		String [] StrArray = {"+", "×", "-", "÷", "/"};//最后一个代表分数
		String Str1 = new String();
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");
		for(int i = 0; i < number; i++) {
			int n = random.nextInt(4)+1;//随机产生四则运算符的个数(1-4)
			int [] num = new int[n+1];
			String Str = new String();
			for(int k = 0; k <= n; k++) {
				num[k] = random.nextInt(bound)+1;//产生n+1 个随机的数
			}
			for(int j = 0; j < n; j++) {
				//如果n == 1 就不让它产生分数
				if(n == 1) {
					Str1 = StrArray[random.nextInt(4)];
				}else if(Str.endsWith("/") || Str.endsWith("÷")){
					//不让它产生除号一起的情况或者除号后面有分数的情况
					Str1 = StrArray[random.nextInt(3)];
				}else{
					Str1 = StrArray[random.nextInt(5)];
					//要求是只有真分数 所以每到分数符号做一次判断大小
					if(Str1.equals("/")) {
						  while(num[j] == num[j+1]) { 
							  num[j+1] = random.nextInt(bound);
						  }
						int temp = Math.max(num[j], num[j+1]);
						num[j] = Math.min(num[j], num[j+1]);
						num[j+1] = temp;
					}
				}
				Str = Str + String.valueOf(num[j]) + Str1;
			}
			Str = Str + num[n];
			try {
				String s1 = Str.replace('÷', '/');
				String s2 = s1.replace('×', '*');
				Object obj = engine.eval(s2);
				while( obj.toString().contains("-")) {
					s2 = Str.replaceAll("-", StrArray[random.nextInt(3)]);
					Str = s2;
					s2 = s2.replace('×', '*');
					s2 = s2.replace('÷', '/');
					obj = engine.eval(s2);
				}

				System.out.print(Str + "=");

				String obj1 = sc.next();
				Object obj2 = engine.eval(obj1);
				if(obj.equals(obj2)) {
				tf = true;
				System.out.println(tf);
				}else {
					tf = false;
					if(Double.valueOf(obj.toString()) % 1 != 0) {
						System.out.println(tf + "   正确答案是:"+obj);
					}else {
						double f1 = Double.valueOf(obj.toString());
						int f2 = (int)f1;
						System.out.println(tf + "   正确答案是:"+f2);
					}
				}
			} catch (ScriptException e) {
				e.printStackTrace();
			}
		}
	}
}