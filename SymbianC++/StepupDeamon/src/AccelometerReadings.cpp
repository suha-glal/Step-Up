#include"AccelometerReadings.h"
#include"MotionClassifier.h"
#include"Listener.h"
#include<e32math.h>

 CAccelometerReadings::CAccelometerReadings(Listener* l):CActive(CActive::EPriorityStandard)
{   
        listenAcc=l;
		x=0;
		y=0;
		z=0;
		//avgZ=0;
		count1=0;
		count2=0;
		turn=1;
		for (TInt i=0;i<120;i++)
		    	{
		        Array1[i]=0;
		        Array2[i]=0;
		    	}
		iSensorDataFilterX = CSensorDataFilter::NewL();
		iSensorDataFilterY = CSensorDataFilter::NewL();
		iSensorDataFilterZ = CSensorDataFilter::NewL();
		
		motionClassifer= new CMotionClassifier();	
		motionClassifer->ConnectL();
		
		// Initialize and register accelerometer sensors  
					RegisterSensors();
		
		iLogX.Connect();
		iLogX.CreateLog(_L("myLogs"),_L("accX"),EFileLoggingModeOverwrite);
		iLogX.SetDateAndTime(EFalse, EFalse);
		
		iLogY.Connect();
		iLogY.CreateLog(_L("myLogs"),_L("accY"),EFileLoggingModeOverwrite);
		iLogY.SetDateAndTime(EFalse, EFalse);
				
	    iLogZ.Connect();
		iLogZ.CreateLog(_L("myLogs"),_L("accZ"),EFileLoggingModeOverwrite);
		iLogZ.SetDateAndTime(EFalse, EFalse);
		
		// Add to active scheduler
		CActiveScheduler::Add(this);
	
		
}//Listener
void CAccelometerReadings::HandleDataEventL(TRRSensorInfo aSensor, TRRSensorEvent aEvent) {
		if (aSensor.iSensorId == Kacc) 
			{
			
			x = iSensorDataFilterX->FilterSensorData(aEvent.iSensorData1);
			y = iSensorDataFilterY->FilterSensorData(aEvent.iSensorData2);
			z = iSensorDataFilterZ->FilterSensorData(aEvent.iSensorData3);
			/*TBuf<8> xtxt;
			TBuf<8> ytxt;
			TBuf<8> ztxt;
			xtxt.AppendNum(x);
			ytxt.AppendNum(y);
			ztxt.AppendNum(z);
			iLogX.Write(xtxt);
			iLogY.Write(ytxt);
			iLogZ.Write(ztxt);*/
		TInt accvalue;
		if(Max(x,y,z)==1)
		{
		accvalue=(y+z)/2;
		}
		else if(Max(x,y,z)==2)
		{
		accvalue=(x+z)/2;
		}
		else if(Max(x,y,z)==3)
		{
		accvalue=(y+x)/2;
		}//else
		else
		accvalue=x;

		 switch(turn)
		           {
		           case 1:
		        	   {
		                   if(count1<120)
		                	{
		                	
		                	Array1[count1]=accvalue;
		                	
		                  	
		                  	if(count1>=100)
		                  		{
		                  		Array2[count2]=accvalue;
		                  		count2++;
		                  		}
		                  	count1++;
		                	}
		                   else if(count1==120)
		                	{
		                	     count1=0;
		                	     motionClassifer->StartClassifing(Array1);
		                		 turn=2;
		                	}
		                   
		                    }//case1
		        	        break;
		           case 2:
		                 {
		                    if(count2<120)
		                           	{
		                           	
		                           	Array2[count2]=accvalue;
		                           	
		                           	
									   if(count2>=100)
												{
												   Array1[count1]=accvalue;
												  count1++;
												 }
									   count2++;
		                           	}
		                     else if (count2==120)
		                           	{
		                           	count2=0;
		                           	motionClassifer->StartClassifing(Array2);
		                            turn=1;
		                           	}
		                    
		                  }//case2
		                   	        break;
		               default:
		                     break;
		                    
		                }//switch
			
			}//Kacc
		
	
	}//HandleDataEventL
CAccelometerReadings::~CAccelometerReadings()
{

Cancel();

UnregisterSensors();
}
TInt CAccelometerReadings::Max(TInt x,TInt y,TInt z)
	{
	if(x>y && x>z)
		return 1;
	
	else if(y>x && y>z)
	return 2;
	
	else if(z>x && z>y)
		return 3;
	else 
		return 4;

	}//max
/*** Initializes and registers accelerometer sensors***/
void CAccelometerReadings::RegisterSensors()   
	{   
	RArray<TRRSensorInfo> sensorList;   
	CleanupClosePushL(sensorList);   
	// Retrieve list of available sensors   
	CRRSensorApi::FindSensorsL(sensorList);  
	// Get number of sensors available   
	TInt sensorCount = sensorList.Count(); 
	for (TInt i = 0; i < sensorCount; i++) 
		{    
		// We are interested only in the accelerometer sensor now    
		if (sensorList[i].iSensorId == Kacc)  
			{     
			iAccelerometerSensor = CRRSensorApi::NewL(sensorList[i]);    
			
			// Register this control as accelerometer data listener    
			iAccelerometerSensor->AddDataListener(this);
			break;   
			}//if    
		} //for  
	CleanupStack::PopAndDestroy(); //sensorList    
		
}
/*** Unregisters accelerometer sensors*/
void CAccelometerReadings::UnregisterSensors()   
	{    
	// Unregister accelerometer data listener  
	iAccelerometerSensor->RemoveDataListener();   
	delete iAccelerometerSensor;   
     iAccelerometerSensor = NULL;  
    }
void CAccelometerReadings::RunL()
{
		
}//runl

void CAccelometerReadings::DoCancel()
{
Cancel();
}
