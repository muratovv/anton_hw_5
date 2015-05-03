package com.company.Reflector;

import com.company.Utils.StringHelper;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;

public class FieldReflector
{
	String returnType = "";
	String name = "";
	ArrayList<String> modificators = new ArrayList<>();

	Field field;

	public FieldReflector(Field field)
	{
		this.field = field;
		getReturnType();
		getModificators();
		getName();
	}

	private void getModificators()
	{
		modificators = ReflectorHelper.getModificators(field.getModifiers());
	}

	private void getName()
	{
		name = field.getName();
	}

	private void getReturnType()
	{
		returnType = field.getGenericType().getTypeName();
		if(field.getGenericType() instanceof ParameterizedType)
		{
			returnType = ReflectorHelper.getParametrezedType(field.getGenericType());
		}
		returnType = ReflectorHelper.replaceInnerModificator(returnType);
	}

	@Override
	public String toString()
	{
		if(!field.isSynthetic())
		{
			return StringHelper.join(modificators, "", "", " ", " ") + returnType + " " + name + ";";
		}
		else return "";
	}

	@Override
	public boolean equals(Object o)
	{
		if(this == o) { return true; }
		if(o == null || getClass() != o.getClass()) { return false; }

		FieldReflector that = (FieldReflector) o;

		if(modificators != null ? !modificators.equals(that.modificators) : that.modificators != null)
		{ return false; }
		if(name != null ? !name.equals(that.name) : that.name != null) { return false; }
		if(returnType != null ? !returnType.equals(that.returnType) : that.returnType != null)
		{ return false; }

		return true;
	}

	@Override
	public int hashCode()
	{
		int result = returnType != null ? returnType.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (modificators != null ? modificators.hashCode() : 0);
		return result;
	}
}
