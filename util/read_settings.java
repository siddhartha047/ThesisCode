/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmetal.util;
import java.io.*;
import java.util.StringTokenizer;
/**
 *
 * @author andy
 */

public class read_settings {
     public static String netbeans_path;
     public static String generation_filename;
     public static String generation_path;
     public static String pf_path;
     public static String srcfile_path;
     public static String true_hypervolume_path;
     
     public read_settings(){
         FileReader fr=null;
        try {
            fr = new FileReader("externalsettings");
            BufferedReader br=new BufferedReader(fr);
            String s;
            while((s=br.readLine())!=null){
                //System.out.println(s);
                
            	
            	StringTokenizer st=new StringTokenizer(s,"*");
                if (st.hasMoreTokens()){
                    String key=st.nextToken();
                    String val=st.nextToken();
                    
                    System.out.println(val);
                    
                    if(key.compareToIgnoreCase("netbeans path")==0){
                    	 netbeans_path=val;
                    }
                    else if(key.compareToIgnoreCase("src path")==0){
                    	srcfile_path=val;
                    }
                    else if(key.compareToIgnoreCase("generation path")==0){
                    	generation_path=val;
                    }
                    else if(key.compareToIgnoreCase("generation filename")==0){
                    	generation_filename=val;
                    }
                    else if(key.compareToIgnoreCase("pf path")==0){
                    	pf_path=val;
                    }
                    else if(key.compareTo("hypervolume")==0){
                    	true_hypervolume_path=val;
                    }
                    else{
                    	System.exit(0);
                    }
                    	
                    	/*
                    switch(key){
                        case "netbeans path":
                            netbeans_path=val;
                            System.out.println(val);
                            break;
                        case "src path":
                            srcfile_path=val;
                            break;
                        case "generation path":
                            generation_path=val;
                            break;
                        case "generation filename":
                            generation_filename=val;
                            break;
                        case "pf path":
                            pf_path=val;
                            break;
                        default:
                            break;
                    }
                    */
                   // System.out.println("key: "+key+" val: "+val);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                fr.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
     }
    
}
