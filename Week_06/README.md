# java8特性

## 1.Lambda

### 1.是什么?

Lambda匿名函数，基于数学λ得名

### 2.Lambda代码

- 不同的参数使用方式

```java
public class LambdaDemo1 {
    public static void main(String[] args) {
        //1.不需要参数，返回值为5
        Cal cal = () -> 5;
        System.out.println(cal.add());

        //2.接收一个参数，返回值为 x *2
        Cal1 cal1 = x -> x * 2;
        System.out.println(cal1.mul(2));

        //3.接收两个参数，返回差值
        Cal2 c1 = (a, b) -> a - b;
        System.out.println(c1.sub(1, 2));

        //4.返回两数的和
        Cal2 c2 = (a, b) -> a + b;
        System.out.println(c2.sub(1, 2));

        //5.打印String 不返回任何值
        Print p = s -> System.out.println(s);
        p.print("abcd");

        Print p2 = System.out::print;
        p2.print("def");
    }

    interface Cal {
        int add();
    }

    interface Cal1 {
        int mul(int x);
    }

    interface Cal2 {
        int sub(int a, int b);
    }

    interface Print {
        void print(String str);
    }
}
```

- 传入不同实现实现不同的效果

```java
public class LambdaDemo2 {
    public static void main(String[] args) {
        MathOpt<Integer> add = (a, b) -> a + b;
        MathOpt<Integer> sub = (a, b) -> a - b;
        MathOpt<Integer> mul = (a, b) -> a * b;
        MathOpt<Integer> div = (a, b) -> a / b;
        MathOpt<Double> db = (a, b) -> Math.pow(a, b);

        System.out.println(operate(1, 2, add));
        System.out.println(operate(5, 3, sub));
        System.out.println(operate(1, 2, mul));
        System.out.println(operate(4, 2, div));
        System.out.println(operate(2, 2, db));
    }

    interface MathOpt<T> {
        T operation(int a, int b);
    }

    public static <T> T operate(int a, int b, MathOpt<T> mathOpt) {
        return mathOpt.operation(a, b);
    }
}
```

## 2.java8函数式

- 接口中只要一个抽象的接口方法

```java
@FunctionalInterface
public interface Runnable {
    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see     java.lang.Thread#run()
     */
    public abstract void run();
}
```

- java.util.function包下的函数式接口

```java
@FunctionalInterface
//一个参数，boolean返回值
public interface Predicate<T> { //断言 test方法

    /**
     * Evaluates this predicate on the given argument.
     *
     * @param t the input argument
     * @return {@code true} if the input argument matches the predicate,
     * otherwise {@code false}
     */
    boolean test(T t);
```

```java
@FunctionalInterface
//一个参数，有返回值
public interface Function<T, R> { //函数，功能  apply方法

    /**
     * Applies this function to the given argument.
     *
     * @param t the function argument
     * @return the function result
     */
    R apply(T t);
```

```java
@FunctionalInterface
//一个参数，无返回值
public interface Consumer<T> { //消费 accept方法

    /**
     * Performs this operation on the given argument.
     *
     * @param t the input argument
     */
    void accept(T t);
```

```java
@FunctionalInterface
//无参数 有返回值
public interface Supplier<T> { //提供

    /**
     * Gets a result.
     *
     * @return a result
     */
    T get();
}

```

```java
public class FunInterDemo1 {
    public static void main(String[] args) {
        //1.Predicate 断言，有参数 无返回值
        Predicate<String> predicate = str -> "".equals(str);
        System.out.println(predicate.test("a"));

        //2.Function 函数 ，一个参数 有返回值
        Function<Integer, Double> function = x -> Math.pow(x, 2);
        System.out.println(function.apply(3));

        //3.Consumer 消费 有参数 ，无返回值
        Consumer<String> consumer = str -> System.out.println(str);
        consumer.accept("HelloWorld");

        //4.Supplier 无参数有返回值 get()
        Supplier<String> supplier = () -> "java8 in action";
        System.out.println(supplier.get());
    }
}

```

## 3.Stream流式编程

### 1.构建流

```java
public class StreamDemo {
    public static void main(String[] args) {
        //流的创建
        //1
        Stream<Integer> stream1 = Stream.of(1, 2, 3);

        int[] arr = new int[]{1, 2, 3, 5};
        Stream<int[]> stream2 = Stream.of(arr);

        //2
        IntStream stream3 = Arrays.stream(arr);

        //3
        List<Integer> list = Arrays.asList(1, 2, 3);
        list.stream();
    }
}
```



- 参考资料：
- **[谷歌编码规范]( https://google.github.io/styleguide/javaguide.html )**
- **[阿里巴巴规范](https://github.com/alibaba/p3c)**
- **[唯品会编码规范](https://vipshop.github.io/vjtools/#/standard/)**





