/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package stepPackage;

/**
 *
 * @author Suha
 */
public class StringOperation {

    String[]Spliter(String str, String sp)
    {
    int spc=0;
    int begindx=0;
    int endindx=0;
    begindx=str.indexOf(sp, begindx);

    while(begindx!=-1)
    {
    spc++;
    
    begindx= str.indexOf(sp, begindx+1);

    }//while

    String [] splistr= new String[spc+1];
    begindx=0;
    for(int i=0;i<spc;i++)
    {
        endindx=str.indexOf(sp, begindx);
        if(endindx!=-1 && begindx!=-1)
        splistr[i]=str.substring(begindx, endindx);
        begindx=endindx+1;

    }//i
    splistr[spc]=str.substring(begindx, str.length());
    return splistr;
    }//Spliter
    String[]ProgressStr(String answer)
    {

    //String answer="qqq@www@RRR@....ttt$yyy$uuu#zzz#";
       int splitedsize=0;

           String []progstr=Spliter(answer,"@");
           splitedsize=progstr.length+3;
           String[]membstr=Spliter(progstr[progstr.length-1],"$");
           String[]grbstr=Spliter(membstr[membstr.length-1],"#");

           String[] splitedstr= new String[splitedsize];
           int i;
           for(i=0;i<progstr.length-1;i++)
           {
           splitedstr[i]=progstr[i];
           }
           for(int j=0;j<2;j++)
           {
               splitedstr[i]=membstr[j];
                       i++;
           }
        for(int j=0;j<2;j++)
           {
               splitedstr[i]=grbstr[j];
                       i++;
           }
        return splitedstr;
    }//ProgressStr


}
