**NOTE: This reference currently covers the XML format that is supported by [revision 180](https://code.google.com/p/junitext/source/detail?r=180).  Not all that is described below may be available in XMLParameterizedRunner prior to [revision 180](https://code.google.com/p/junitext/source/detail?r=180) (including v0.2.4).**

# Introduction #


This page covers the default XML syntax that can be used with `XMLParameterizedRunner`.

`XMLParameterizedRunner` is a custom JUnit 4 test runner that allows tests to be parameterized via an input XML file.  Stated another way, `XMLParameterizedRunner` allows test dependencies to be declared in an XML file, much like how Inversion of Control containers like [Spring](http://www.springframework.org) provide.

In fact, the default format is based on Spring's XML bean declaration syntax.  However, it should be noted that this was done simply because the author had no desire to invent yet another XML format. `XMLParameterizedRunner` has no dependencies on Spring and can be used on projects completely unrelated to Spring.

If you are familiar with Spring, then you will have no trouble picking up the XML format described below.  In fact, you might want to skip to the section titled "Differences between `XMLParameterizedRunner` XML and Spring XML".

# Basic Syntax #

## Creating Primitives (Boxed and Unboxed) ##

Many tests only need to be parameterized using primitives or their object equivalents.  To demonstrate how to do this with `XMLParameterizedRunner`, take a look at the `FibonacciTest` that JUnit uses to demonstrate how to use the original `Parameterized` test runner:

```
@RunWith(Parameterized.class)
public class FibonacciTest {
  @Parameters
  public static Collection data() {
    return Arrays.asList(new Object[][] { { 0, 0 }, { 1, 1 }, { 2, 1 },
      { 3, 2 }, { 4, 3 }, { 5, 5 }, { 6, 8 } });
  }`

  private int fInput;
  private int fExpected;

  public FibonacciTest(int input, int expected) {
    fInput= input;
    fExpected= expected;
  }

  @Test public void test() {
    assertEquals(fExpected, Fibonacci.compute(fInput));
  }
}
```

When the above test is run, a separate instance of the test is created using the parameters returned from the static method `data`.  The same effect can be achieved using `XMLParameterizedRunner` instead.  Below is a modified version of the `FibonacciTest`:

```
@RunWith(XMLParameterizedRunner.class)
public class FibonacciTest {

  private int fInput;
  private int fExpected;

  @XMLParameters("input.xml")
  public FibonacciTest(int input, int expected) {
    fInput= input;
    fExpected= expected;
  }

  @Test public void test() {
    assertEquals(fExpected, Fibonacci.compute(fInput));
  }
}
```

Of course, the test data is now missing from the test code itself.  It has been moved into an XML file:

```
<?xml version="1.0" encoding="UTF-8" ?>
<tests>
  <test>
     <value type="java.lang.Ingeter" >0</value>
     <value type="java.lang.Ingeter" >0</value>
  </test>
  <test>
     <value type="java.lang.Ingeter" >1</value>
     <value type="java.lang.Ingeter" >1</value>
  </test>
  <test>
     <value type="java.lang.Ingeter" >2</value>
     <value type="java.lang.Ingeter" >1</value>
  </test>
  <test>
     <value type="java.lang.Ingeter" >3</value>
     <value type="java.lang.Ingeter" >2</value>
  </test>
  <test>
     <value type="java.lang.Ingeter" >4</value>
     <value type="java.lang.Ingeter" >3</value>
  </test>
  <test>
     <value type="java.lang.Ingeter" >5</value>
     <value type="java.lang.Ingeter" >5</value>
  </test>
  <test>
     <value type="java.lang.Ingeter" >6</value>
     <value type="java.lang.Ingeter" >8</value>
  </test>
</tests>
```

The result of running the test with `XMLParameterizedRunner` and the above input XML is exactly the same as running it with `Parameterized`.

Each seperate set of parameters are nested within a `<test>` tag.  The actual parameters themselves are declared using the `<value>` tag.

Note that the type of the `<value>` tag is specified.  If we didn't specify a type, then all of the values would have been converted to `String`, and the tests would fail to run (the constructor takes `int`s not `String`s).

You will also notice that we specified a type of `java.lang.Integer`, but that the test class takes `int` parameters.  Thanks to Java 5 autoboxing (and unboxing), this is perfectly legal.  Java takes care of converting `Integer` objects to `int` primatives.

Using the `type` attribute it is possible to create any of the 8 boxed primitives.

## null Values ##

If you need to pass in a `null` value, you can use the `<null/>` tag:

```
<?xml version="1.0" encoding="UTF-8" ?>
<tests>
  <test>
     <!-- a null test parameter -->
     <value><null/></value>
  </test>
</tests>
```

The `<null/>` tag can be nested under any `<value>` tag, no matter where the tag exists.

# Bean Declaration Syntax #

The ability to create basic value objects for test parameters is useful, but sometimes you need to create complex objects to use in your tests.  This is where the bean declaration syntax comes into play.

_Coming Soon_

## Declaring Collections ##

_Coming Soon_

## Bean Declaration Shortcuts ##

_Coming Soon_

# Differences between `XMLParameterizedRunner` XML and Spring XML #

_Coming Soon_