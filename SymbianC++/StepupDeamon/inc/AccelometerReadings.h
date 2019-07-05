#ifndef __ACCELOMETERREADINGS_H__
#define __ACCELOMETERREADINGS_H__
 
#include <rrsensorapi.h>
#include <e32base.h>
#include<CSensorDataFilter.h>
#include <flogger.h>

class Listener;
class CMotionClassifier;

const TInt Kacc = 0x10273024; 

class CAccelometerReadings :public CActive, public MRRSensorDataListener
	{
public:
	
		TInt x;
		TInt y;
		TInt z;
		//TInt avgZ;
		TInt turn;
		TFixedArray<TInt,120> Array1;
		TFixedArray<TInt,120> Array2;
		
		TInt count1;
		TInt count2;
		CMotionClassifier *motionClassifer;
	    Listener* listenAcc;
CAccelometerReadings(Listener*);
TInt Max(TInt x,TInt y,TInt z);
~CAccelometerReadings();

 
 void RunL();
 void DoCancel();


 

private: 
	RFileLogger iLogX;
	RFileLogger iLogY;
	RFileLogger iLogZ;
	void HandleDataEventL(TRRSensorInfo aSensor, TRRSensorEvent aEvent); 
private:      
		/**        
		 * Initializes and registers accelerometer sensors.   
		 */      
		void RegisterSensors();    
		/* Unregisters accelerometer sensors.*/ 
		void UnregisterSensors(); 
private:  // Data     
    CRRSensorApi* iAccelerometerSensor;
    CSensorDataFilter* iSensorDataFilterX;
    CSensorDataFilter* iSensorDataFilterY;
    CSensorDataFilter* iSensorDataFilterZ;

};
#endif 
