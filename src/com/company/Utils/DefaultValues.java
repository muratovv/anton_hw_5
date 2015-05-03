package com.company.Utils;

/**
 * Created by mfv on 5/2/15.
 */
public class DefaultValues
{
	private static final String DEFAULT_BOOLEAN = "false";
	private static final String DEFAULT_BYTE = "0";
	private static final String DEFAULT_SHORT = "0";
	private static final String DEFAULT_INT = "0";
	private static final String DEFAULT_LONG = "0";
	private static final String DEFAULT_FLOAT = "0f";
	private static final String DEFAULT_DOUBLE = "0.0";
	private static final String DEFAULT_VOID = "";

	public static String getDefaultValue(Class aClass)
	{
		if(aClass.equals(boolean.class))
		{
			return DEFAULT_BOOLEAN;
		}
		else if(aClass.equals(byte.class))
		{
			return DEFAULT_BYTE;
		}
		else if(aClass.equals(short.class))
		{
			return DEFAULT_SHORT;
		}
		else if(aClass.equals(int.class))
		{
			return DEFAULT_INT;
		}
		else if(aClass.equals(long.class))
		{
			return DEFAULT_LONG;
		}
		else if(aClass.equals(float.class))
		{
			return DEFAULT_FLOAT;
		}
		else if(aClass.equals(double.class))
		{
			return DEFAULT_DOUBLE;
		}
		else
		{
			if(aClass.equals(void.class))
			{
				return DEFAULT_VOID;
			}
		}

		return "null";
	}
}
