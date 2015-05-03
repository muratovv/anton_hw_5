package com.company.Reflector;

import com.company.Utils.StringHelper;

import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by mfv on 5/2/15.
 */
public class ReflectorHelper
{
	public static final String PUBLIC = "public";
	public static final String PRIVATE = "private";
	public static final String PROTECTED = "protected";
	public static final String ABSTRACT = "abstract";
	public static final String FINAL = "final";
	public static final String STATIC = "static";
	public static final String VOLATILE = "volatile";
	public static final String NATIVE = "native";
	public static final String STRICTFP = "strictfp";
	public static final String SYNCHRONIZED = "synchronized";
	public static final String TRANSIENT = "transient";
	public static final String INTERFACE = "interface";

	static ArrayList<String> getModificators(int mods)
	{
		//		String[] split = Modifier.toString(mods).split(" ");
		ArrayList<String> modificators = new ArrayList<>();
		if(Modifier.isPublic(mods))
		{
			modificators.add(PUBLIC);
		}
		if(Modifier.isPrivate(mods))
		{
			modificators.add(PRIVATE);
		}
		if(Modifier.isProtected(mods))
		{
			modificators.add(PROTECTED);
		}
		if(Modifier.isAbstract(mods))
		{
			modificators.add(ABSTRACT);
		}
		if(Modifier.isFinal(mods))
		{
			modificators.add(FINAL);
		}
		if(Modifier.isStatic(mods))
		{
			modificators.add(STATIC);
		}
		if(Modifier.isVolatile(mods))
		{
			modificators.add(VOLATILE);
		}
		if(Modifier.isNative(mods))
		{
			modificators.add(NATIVE);
		}
		if(Modifier.isStrict(mods))
		{
			modificators.add(STRICTFP);
		}
		if(Modifier.isSynchronized(mods))
		{
			modificators.add(SYNCHRONIZED);
		}
		if(Modifier.isTransient(mods))
		{
			modificators.add(TRANSIENT);
		}
		if(Modifier.isInterface(mods))
		{
			modificators.add(INTERFACE);
		}
		return modificators;
	}


	public static String getParametrezedType(Type type)
	{
		String typeString = "";
		if(type instanceof ParameterizedType)
		{
			ParameterizedType parameterizedType = (ParameterizedType) type;
			String rawTypeString = parameterizedType.getRawType().getTypeName();
			typeString += rawTypeString;
			typeString += StringHelper.join(
					new ArrayList<Type>(Arrays.asList(parameterizedType.getActualTypeArguments())),
					"<", "", ", ", ">");
		}
		else { throw new Error("wrong type"); }
		return typeString;
	}


	public static String getTypeVariableType(Type type)
	{
		String typeString = "";
		if(type instanceof TypeVariable)
		{
			TypeVariable typeVariable = (TypeVariable) type;
			typeString += ((TypeVariable) type).getName();
			ArrayList<String> typeStringBounds = new ArrayList<>();
			Arrays.stream(typeVariable.getBounds()).forEach(
					bound -> {
						if(!bound.equals(Object.class))
						{
							typeStringBounds.add(bound.getTypeName());
						}
					});
			typeString += StringHelper.join(typeStringBounds, " extends ", "", " & ", "");
		}
		else { throw new Error("wrong type"); }
		return typeString;
	}

	public static String replaceInnerModificator(String validTypeString)
	{
		if(validTypeString.contains("$"))
		{
			int index = validTypeString.indexOf("$");
			//validTypeString = validTypeString.substring(index + 1);
			validTypeString = validTypeString.replace("$", ".");
		}
		return validTypeString;
	}

}
