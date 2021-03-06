package compiler;

import java.util.ArrayList;
import java.util.*;

public class FunctionSum 
{
	public String name;
	public String type;
	public FunctionSum belongs;
	public ArrayList<FunctionSum> fun;
	public ArrayList<VarSum> arg;
	public ArrayList<VarSum> vars;
	public boolean dec;
	public int deapth;
	public FunctionSum(String n)
	{
		name=n;
		vars = new ArrayList<VarSum>();
		arg= new ArrayList<VarSum>();
		fun= new ArrayList<FunctionSum>();
	}

	public void calculate_deapths()
	{	
		for(FunctionSum function:this.fun)
		{
				function.deapth=function.belongs.deapth+1;
				function.calculate_deapths();
		}
	}
	public void print_deapths()
	{	
		System.out.println(this.name+" "+this.deapth);
		for(FunctionSum function:this.fun)
		{
				//System.out.println(function.name+" "+function.deapth);
				function.print_deapths();
		}
	}

	public FunctionSum get_function_from_Symboltable(String function_name)
	{
		if(this.name.replaceAll(" ","").equals(function_name.replaceAll(" ","")))
		{
			return this;
		}
		for(FunctionSum function:this.fun)
		{
				String  comp=function.name.replaceAll(" ","");
				if(comp.equals(function_name.replaceAll(" ","")))
				{
					return function;
				}
				else
				{
					FunctionSum te=function.get_function_from_Symboltable(function_name); 
					if(te!=null)
						return te;
				}
		}
		return null;

	}

	public boolean get_variable_ref(int var)
  	{
		Iterator<VarSum> itr=this.arg.iterator();
		int num=0;
		while(itr.hasNext())
		{
			VarSum ret=itr.next();
			if(num==var)
			{	
 				if(!ret.ref.replaceAll(" ","").equals(""))
 					return true;
 				else
 					return false;
 			}   
 			num++;
 		}
		return false;
	}

	public VarSum get_parameter(int num)
	{
		return this.arg.get(num);
	}

	public boolean findparameter(String var)
	{
		Iterator<VarSum> itr=this.arg.iterator();
		while(itr.hasNext())
		{
			VarSum ret=itr.next();
			String  comp=ret.name.replaceAll(" ","");
			if(comp.equals(var.replaceAll(" ","")))
			{
				return true;
			}   
		}
		return false;
	}
	public String findparametertype(String var)
	{
		Iterator<VarSum> itr=this.arg.iterator();
		while(itr.hasNext())
		{
			VarSum ret=itr.next();
			String comp=ret.name.replaceAll(" ","");
			if(comp.equals(var.replaceAll(" ","")))
			{
				return ret.type;
			}   
		}
		return "NULL";
	}

	public boolean is_ref(String name)
	{
		Iterator<VarSum> itr=this.arg.iterator();
		while(itr.hasNext())
		{
			VarSum ret=itr.next();
			if(ret.name.trim().equals(name.trim()))//
			{
				if(!ret.ref.trim().equals(""))
					return true;
				else
					return false;
			}   
		}
		Iterator<VarSum> itr2=this.belongs.arg.iterator();
		while(itr2.hasNext())
		{
			VarSum ret=itr2.next();
			if(ret.name.trim().equals(name.trim()))//
			{
				if(!ret.ref.trim().equals(""))
					return true;
				else
					return false;
			}   
		}
		return false;
	}

	public String findvariabletype(String var)
	{
		Iterator<VarSum> itr=this.vars.iterator();
		while(itr.hasNext())
		{
			VarSum ret=itr.next();
			if(ret.name.replaceAll(" ","").equals(var.replaceAll(" ","")))//
			{
				return ret.type;
			}   
		}
		return "NULL";
	}
	public String findvarsize(String var)
	{
		Iterator<VarSum> itr=this.vars.iterator();
		String t2=var.replace(" ","");
		
		while(itr.hasNext())
		{
			VarSum ret=itr.next();
			String t1=ret.name.replace(" ","");
			if(t1.equals(t2))
			{
				return ret.sizes;
			}   
		}
		itr=this.arg.iterator();
		while(itr.hasNext())
		{
			VarSum ret=itr.next();
			String t1=ret.name.replace(" ","");
			if(t1.equals(t2))
			{
				return ret.sizes;
			}   
		}
		return "NULL";
	}
	public boolean findvariable(String var)
	{
		Iterator<VarSum> itr=this.vars.iterator();
		while(itr.hasNext())
		{
			VarSum ret=itr.next();
			String comp=ret.name.replaceAll(" ","");
			if(comp.equals(var.replaceAll(" ","")))
			{
				return true;
			}   
		}
		return false;
	}
	
	public boolean findfunction(FunctionSum fun)
	{
		Iterator<FunctionSum> itr=this.fun.iterator();
		while(itr.hasNext())
		{
			FunctionSum ret=itr.next();
			if(ret.name.equals(fun.name))
			{
				return true;
			}   
		}
		return false;
	}
	public FunctionSum getFunction(String name)
	{
		Iterator<FunctionSum> itr=this.fun.iterator();
		while(itr.hasNext())
		{
			FunctionSum ret=itr.next();
			String fun=ret.name.replaceAll(" ","");
			if(fun.equals(name.replaceAll(" ","")))
			{
				return ret;
			}   
		}
		return null;
	}

	public int get_parameter_num(String name)
	{
		Iterator<VarSum> itr=this.arg.iterator();
		int num=0;
		while(itr.hasNext())
		{
			VarSum ret=itr.next();
			String var=ret.name.replaceAll(" ","");
			if(var.equals(name.replaceAll(" ","")))
			{
				return num;
			}   
			num++;
		}
		return -1;
	}
	public boolean exist_name(String na)
	{
		String na2=na.replaceAll(" ","");
		if(name.equals(na))
			return true;
		for(FunctionSum fa:fun)
		{
			String comp=fa.name.replaceAll(" ","");
			if(comp.equals(na2))
			{
				return true;
			}
		}
		return false;
	}

}
