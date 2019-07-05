#ifndef __MOTIONCLASSIFIER_H__
#define __MOTIONCLASSIFIER_H__

#include <flogger.h>
 #include"e32base.h"
#include <in_sock.h>
class Listener;
class CMotionClassifier :public CActive
	{
public:
	
CMotionClassifier();
~CMotionClassifier();
void StartClassifing(TFixedArray<TInt,120>&);
 void RunL();
 void DoCancel();
 void ConnectL();
 enum TState
 	{
 	
 	EAccept,
 	EReceiving,
 	ERecConfi,
 	EClose,
 	ESend			  
 			  	
 	};

 private:
 	TInt	iState;	

private:
TInt Steps;
RFileLogger iLog;
////////////////
TInt connect;

RSocketServ socketServ;
RSocket listener;
RSocket blank;
TSockXfrLength dummyLength;
TBuf8<256> buffer;

};
#endif 
