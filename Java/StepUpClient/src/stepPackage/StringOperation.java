/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package stepPackage;

/**
 *The class contians two functions that perform different string manipulations.
 * @author Suha
 */
public class StringOperation {
/**
 * The function gets string and splited to many small strings.
 * for example
 * If str="aaa!bbb!ccc" and
 * if sp= '!'
 * then return will be string array with the first element = aaa and the second element =bbb and the third element =ccc.
 * will be splited to aaa bbb ccc
 * @param str the string to be splited
 * @param sp the charater that specifiy where to splite the string
 * @return string array that contains the splited strings
 */
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
    /**
     * This function splits strings with the following formate only
     * String answer="qqq@www@RRR@....ttt$yyy$uuu#zzz#";
     * in this example the array will contain
     * <b>qqq
     * <b>www
     * <b>RRR
     * <b>...
     * <b>...
     * <b>ttt
     * <b>yyy
     * <b>uuu
     *<b>zzz
     * @param answer
     * @return array of strings
     */
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
