package com.company.Reflector;

import com.company.Utils.StringHelper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;


public class ClassReflector
{

	ArrayList<String> modificators = new ArrayList<>();
	ArrayList<String> genericParameters = new ArrayList<>();
	String name;
	ArrayList<String> superClass = new ArrayList<>();

	ArrayList<FieldReflector> fields = new ArrayList<>();
	ArrayList<MethodReflector> methods = new ArrayList<>();
	ArrayList<MethodReflector> constructors = new ArrayList<>();
	ArrayList<ClassReflector> innerClasses = new ArrayList<>();
	ArrayList<String> implementedInterfaces = new ArrayList<>();


	Class<?> aClass;

	public ClassReflector(Class<?> aClass)
	{
		this.aClass = aClass;
		getModificators();
		getName();
		getGenericParameters();
		getInnerClasses();
		getImplementedInterfaces();
		if(!aClass.isInterface())
		{
			getSuperClass();
			getFields();
			getMethods();
			getConstructors();
		}

	}

	private void getModificators()
	{
		modificators = ReflectorHelper.getModificators(aClass.getModifiers());
	}

	private void getName()
	{
		name = aClass.getSimpleName();
	}

	private void getGenericParameters()
	{
		for(TypeVariable genericParam : aClass.getTypeParameters())
		{
			genericParameters.add(ReflectorHelper.getTypeVariableType(genericParam));
		}
	}

	private void getSuperClass()
	{
		if(aClass.getSuperclass() != null)
		{
			if(!aClass.getSuperclass().equals(Object.class))
			{
				if(aClass.getPackage().equals(aClass.getSuperclass().getPackage()))
				{
					superClass.add(aClass.getSuperclass().getSimpleName());
				}
				else
				{
					superClass.add(aClass.getSuperclass().getCanonicalName());
				}
			}
		}

	}

	private void getInnerClasses()
	{
		for(Class<?> aClass1 : aClass.getDeclaredClasses())
		{
			innerClasses.add(new ClassReflector(aClass1));
		}

	}

	private void getFields()
	{
		for(Field field : aClass.getDeclaredFields())
		{
			fields.add(new FieldReflector(field));
		}
	}

	private void getMethods()
	{
		for(Method method : aClass.getDeclaredMethods())
		{
			methods.add(new MethodReflector(method));
		}

	}

	private void getConstructors()
	{
		for(Constructor<?> constructor : aClass.getDeclaredConstructors())
		{
			constructors.add(new MethodReflector(constructor));
		}


	}

	private void getImplementedInterfaces()
	{
		for(Type type : aClass.getGenericInterfaces())
		{
			String interfaceTypeString = type.getTypeName().replace(aClass.getPackage().getName() + ".", "");
			implementedInterfaces.add(interfaceTypeString);
		}
	}

	public String buildCode()
	{
		return new ClassGenerator().buildCode();
	}

	@Override
	public String toString()
	{
		return buildCode();
	}

	private class ClassGenerator
	{
		int deep = 0;

		public String buildCode()
		{
			String result = makeClassDeclaration();
			result += StringHelper.makeCodeBlock(
					StringHelper.join(fields, "", "", "\n", "\n\n") +
							StringHelper.join(constructors, "", "", "\n\n", "\n\n") +
							StringHelper.join(methods, "", "", "\n\n", "\n\n") +
							StringHelper.join(innerClasses, "", "", "\n\n", "\n\n")
					, deep);
			return result;
		}

		private String makeClassDeclaration()
		{
			return StringHelper.join(modificators, "", "", " ", " ") +
					(!aClass.isInterface() ? "class " : "") +
					name + " " +
					StringHelper.join(genericParameters, "<", "", ", ", "> ") +
					StringHelper.join(superClass, "extends ", "", "", "") +
					StringHelper.join(implementedInterfaces,
							(aClass.isInterface() ? " extends " : " implements "), "", ", ", "");
		}
	}
}
