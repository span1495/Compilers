package compiler;


import java.io.PushbackReader;

import compiler.lexer.Lexer;
import compiler.node.Start;
import compiler.parser.Parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.FileDescriptor;

class Main { 

	public static void main(String args[]) throws FileNotFoundException
	{ 
		if(args.length <1)
        {	
      			System.err.println("Not enough arguments");
      			System.exit(1);
      	}
		PrintStream output = new PrintStream(new FileOutputStream("output.txt"));
		PrintStream output_ir = new PrintStream(new FileOutputStream("output_ir.txt"));
		int i;
		for(i=0;i<args.length;i++)		
		{		
			FileInputStream myfile = null;
			myfile=new FileInputStream(args[i]);
			Parser p = new Parser(new Lexer(new PushbackReader(new InputStreamReader(myfile), 1024)));
        	try {
    			Start tree = p.parse();
    			Collector symboltable= new Collector();
    			symboltable.create_standar_library();
    			tree.apply(symboltable);
    			symboltable.print_errors();
    			if(symboltable.error.equals(""))
    			{
                    symboltable.current.deapth=1;
                    symboltable.current.calculate_deapths();
                    //symboltable.current.print_deapths();
    				LoweringCode converter= new LoweringCode(symboltable.current,symboltable.standar_library);
    				tree.apply(converter);
    				converter.help.print_instructions(output_ir);
    				output_ir.print("\n");
    				AssemblyCreator ascreator= new AssemblyCreator(converter.help.instruction_list,symboltable.current,symboltable.standar_library,converter.library_calls);
    				ascreator.produce();
                    Helper.print_final(output,ascreator.final_code,ascreator.data);
                    output.print("\n");
    			}
    			else
    				System.out.println("Couldn't convert code,because there were semantic errors !\n");
        	} catch (Exception e) 
    		{	
    		    e.printStackTrace();
    		}

		}
		output.close();
		output_ir.close();
		System.exit(0);
	}
}
