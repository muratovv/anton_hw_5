package com.company;

import com.company.Reflector.ClassDiffer;
import com.company.Reflector.ClassReflector;
import com.company.TestClassPackage.TestClass;

public class Main {

    public static void main(String[] args) {
	// write your code here
	    TestClass<Integer, Comparable<Number>, Void> a = new TestClass<>(null);
	    System.out.println(new ClassDiffer(a.getClass(), a.getClass()).diff());
//	    String resultCode = new ClassReflector(a.getClass()).buildCode();
//	    System.out.println(resultCode);
    }
}
