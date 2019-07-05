#ifndef __LISTENER_H__
#define __LISTENER_H__
 

#include <in_sock.h>
#include <flogger.h>


class CAccelometerReadings;
class Listener :public CActive
	{
public:
	
 Listener();
 ~Listener();
 void Startlistener();
 
 void RunL();
 void DoCancel();

 
private:

CAccelometerReadings *acclometer;	
TInt callerStatus;
		 enum TLoadStates
		  {
		  EAccept,
		  EReceiving,
		  ERecConfi,
		  EClose,
		  ESending
		  	};
////////////
RSocketServ socketServ;
RSocket listener;
RSocket blank;
TSockXfrLength dummyLength;
TBuf8<256> buffer;
//RFileLogger iLog;
};
#endif 
