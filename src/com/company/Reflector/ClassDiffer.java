package com.company.Reflector;

import java.util.Objects;

/**
 * Created by mfv on 5/3/15.
 */
public class ClassDiffer
{
	ClassReflector left;
	ClassReflector right;

	public ClassDiffer(ClassReflector left, ClassReflector right)
	{
		this.left = left;
		this.right = right;
	}

	public ClassDiffer(Class<?> left, Class<?> right)
	{
		this(new ClassReflector(left), new ClassReflector(right));
	}

	public String diff()
	{
		String diff = "";
		diff = diffFields();
		diff += diffMethods();
		if(Objects.equals(diff, ""))
		{
			diff = "Classes are equal";
		}
		return diff;
	}

	private String diffMethods()
	{
		String result = "";
		for(MethodReflector method : left.methods)
		{
			if(!right.methods.contains(method))
			{
				result += left.name + ":\n" + "method: " + "++" + method.name;
			}
		}
		for(MethodReflector method : right.methods)
		{
			if(!left.methods.contains(method))
			{
				result += right.name + ":\n" + "method: " + "--" + method.name;
			}
		}
		return result;
	}

	private String diffFields()
	{
		String result = "";
		for(FieldReflector field : left.fields)
		{
			if(!right.fields.contains(field))
			{
				result += left.name + ":\n" + "field: " + "++" + field.name;
			}
		}
		for(FieldReflector field : right.fields)
		{
			if(!left.fields.contains(field))
			{
				result += right.name + ":\n" + "field: " + "--" + field.name;
			}
		}
		return result;
	}
}
