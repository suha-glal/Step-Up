/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package stepPackage;

import javax.microedition.lcdui.*;

/**
 *
 * @author Suha
 */
public class AccProccessingCopy {
    
     AAAStepUp stepupMidlet;
     int steps ;
     int serversteps;
      AccProccessingCopy(AAAStepUp d)
      {
       steps=0;
       serversteps=0;
       stepupMidlet=d;
      }

         
 
void CountSteps(int []arr)
{
   	int []x=avgWindowFilter(arr,8,1);
	int xsize=x.length,noise=0;
	int [] slopes= new int[xsize-1];


int i;


for(i=0;i<xsize-1;i++)
{
slopes[i]=(x[i+1]-x[i]);
}

int pos=0,neg=0,steptemp=0;
for(i=0;i<xsize-2;i++)
{
	if(slopes[i]>=0)
	{
		pos++;
		neg=0;
	}
	else if (pos>=3)
	{

	       if(slopes[i]<0 && slopes[i+1]>0)
	        {
		neg++;
		if(neg>=3)
		{
		steptemp++;
		pos=0;
		}
		neg=0;

	           }
	     else if(slopes[i]<0)
	           {
	         	neg++;

	            }
	}//pos
	else pos=0;

}//i


if(steptemp >1 && steptemp <6)
{
   steps+=steptemp;
   serversteps+=steptemp;

}

new Thread()
{
    public void run()
    {
	stepupMidlet.stepview.DisblyStepsDistanceAndCalories(steps);
    }
}.start();
new Thread()
{
    public void run()
    {
/*Date now= new Date();
long millsec2=now.getTime();
long millsec1=stepupMidlet.lastsenddate.getTime();
long result=millsec2-millsec1;
  result>=30/*000&&*/
if(serversteps>2)
{
//stepupMidlet.lastsenddate.setTime(now.getTime());
handelSocketConnection();
}//if

    }//run
}.start();

}//CountSteps
void handelSocketConnection()
{
new Thread()
{
    public void run()
    {
        Socketconnection groupsocket= new Socketconnection(stepupMidlet,stepupMidlet.stepview.stepsform);

        String steptosend="s"+stepupMidlet.username+","+stepupMidlet.userpassword+"?"+serversteps;
        String answer=groupsocket.SendRecive(steptosend.toLowerCase());
        if(answer.equals("S"))
        {
            serversteps=0;
          }
        else if(answer.equals("F"))
        {
             Alert a=  new Alert("Step Counter", "Unable to update database with your number of steps!",null,AlertType.WARNING);
          a.setTimeout(Alert.FOREVER);
          stepupMidlet.display.setCurrent(a,stepupMidlet.stepview.stepsform);
        }
        else if(answer.equals("P"))
        {
             Alert a=  new Alert("Step Counter", "Unable to update database,wrong user password!",null,AlertType.WARNING);
          a.setTimeout(Alert.FOREVER);
          stepupMidlet.display.setCurrent(a,stepupMidlet.stepview.stepsform);
        }
        else if (answer.equals("N"))
        {
             Alert a=  new Alert("Step Counter", "Unable to update database,wrong user name!",null,AlertType.WARNING);
          a.setTimeout(Alert.FOREVER);
          stepupMidlet.display.setCurrent(a,stepupMidlet.stepview.stepsform);
        }//else if answer=N

    }//run

}.start();
}//handelSocketConnection

int[] avgWindowFilter(int[]x,int win,int step)
    {

                int xSize=x.length;
		int ySize=((xSize-win)/step)+1;
		int y[]=new int[ySize];
		int i,j,z=0;
		int avg=0,sum=0;

		for(i=0;i<xSize-win;i+=step)
		{
			for(j=i;j<i+win;j++)
			{
				sum+=x[j];
			}//j
			avg=sum/win;
            y[z]=avg;
			sum=0;
            z++;
		}//i

   return y;
    }//avgWindowFilter

int InTheCar(int []arr)
	{

	int xsize=120;

	int [] slopes= new int[119];
int state=0;

int i,absslopsum=0,slopch=0;
for(i=0;i<xsize-1;i++)
{
slopes[i]=(arr[i+1]-arr[i]);
absslopsum+=(int)Math.abs(slopes[i]);
}

for(i=0;i<xsize-3;i++)
{
    if(slopes[i]<slopes[i+1]&&slopes[i+1]>slopes[i+2])
slopch++;

}

if(absslopsum>=650)
{
if(slopch<=33&&slopch>=20)
    state=2;
else if(slopch>=34&&slopch<=50)
    state=4;
else
    state=3;
}
else if(absslopsum>=240&&absslopsum<=630)
{
state=1;
}
else
{
    state=3;
}
return state;
	}//IntheCar

}
