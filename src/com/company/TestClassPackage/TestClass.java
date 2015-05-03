package com.company.TestClassPackage;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;

public class TestClass<P1 extends Number, P2 extends Comparable<Number>, P3>  implements Runnable
{
	private String privateStringField;
	public List<? extends Comparable<Number>> publicListWithWildCard;
	protected volatile static transient int rowTypeWithMoreModificators;
	boolean[][] boolean_arr;

	private TestClass()
	{

	}

	public TestClass(List<?> l)
	{

	}

	public void simpleMethod() throws NegativeArraySizeException
	{

	}

	public <T extends Number> T genericMethod(Comparable<T> comparable, List<? extends Number> lst)
	{
		return null;
	}

	public P1 genericClassParametersMethod(P2 p2)
	{
		return null;
	}

	public <T> T combine(P1 p1, P2 p2)
	{
		return null;
	}

	@Override
	public void run()
	{

	}

	private static class NestedClass
	{

	}


	public interface InnerInterface extends AutoCloseable, Closeable, Iterable<Float>
	{

	}

	private class InnerClassWithExtends extends TestClass implements Runnable
	{

		@Override
		public void run()
		{

		}
	}

	private class InnerClassWithExtends1 extends InnerClassWithExtends
	{

	}
}
