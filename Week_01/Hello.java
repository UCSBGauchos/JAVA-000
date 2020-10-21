package com.cutie.geek.java0.week1;

public class Hello {
    public static void main(String[] args) {
        int add = 1+2;
        long subtract = 1 - 2;
        float multiply = 1 * 2;
        double divide = 1 / 2;

        for (int i = 0; i < 5; i++) {
            if (i == 3){
                System.out.println("hello world");
            }
        }
    }
}
/*
cutiedeMacBook-Pro:practice cutie$ javap -c target/classes/com/cutie/javabasics/clazzLoader/Hello.class
Compiled from "Hello.java"
public class com.cutie.javabasics.clazzLoader.Hello {
  public com.cutie.javabasics.clazzLoader.Hello();
    Code:
       0: aload_0
       1: invokespecial #1                  // Method java/lang/Object."<init>":()V
       4: return
  public static void main(java.lang.String[]);
    Code:
       0: iconst_3  【常量3压入操作数栈：直接加会直接进行计算？】
       1: istore_1  【弹出操作数栈栈顶元素，保存到局部变量表第1个位置】
       2: ldc2_w        #2                  // long -1l 【将long或double型常量值从常量池中推送至栈顶】
       5: lstore_2  【弹出操作数栈栈顶元素，保存到局部变量表第2个位置】
       6: fconst_2  【常量2压入操作数栈】
       7: fstore        4 【将栈顶float型数值存入指定本地变量】
       9: dconst_0          【将double型(0)推送至栈顶】
      10: dstore        5   【将栈顶double型数值存入指定本地变量】
      12: iconst_0          【将int型(0)推送至栈顶】
      13: istore        7   【将栈顶int型数值存入指定本地变量】
      15: iload         7   【将指定的int型本地变量推送至栈顶】
      17: iconst_5          【将int型(5)推送至栈顶】
      18: if_icmpge     41  【int比较大小】
      21: iload         7   【将指定的int型本地变量推送至栈顶】
      23: iconst_3          【将int型(3)推送至栈顶】
      24: if_icmpne     35  【int比较大小】
      27: getstatic     #4                  // Field java/lang/System.out:Ljava/io/PrintStream; 【实例标量】
      30: ldc           #5                  // String awesome   【将一个常量加载到操作数栈】
      32: invokevirtual #6                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V 【指令用于调用对象的实例方法，根据对象的实际类型进行分派（虚方法分派），这也是Java语言中最常见的方法分派方式。】
      35: iinc          7, 1    【局部变量自增指令】
      38: goto          15      【跳转15行】
      41: return                【返回，程序结束】
}
*/