#include"MotionClassifier.h"
#include<e32cmn.h>
#include"Listener.h"
CMotionClassifier::CMotionClassifier():CActive(EPriorityStandard)
	{
	iLog.Connect();
   iLog.CreateLog(_L("myLogs"),_L("Motion"),EFileLoggingModeOverwrite);
               
                TUint KTestPort=8444;
           	    TInetAddr addr(KInetAddrLoop, KTestPort);
           		User::LeaveIfError(socketServ.Connect());
           		CleanupClosePushL(socketServ);//if function leaves ensure socket serv session closes
           		User::LeaveIfError(listener.Open(socketServ, KAfInet,KSockStream, KProtocolInetTcp));
           		User::LeaveIfError(listener.Bind(addr));  
           		User::LeaveIfError(listener.Listen(1));
           											
	
	Steps=0;
	CActiveScheduler::Add(this);
	}
CMotionClassifier::~CMotionClassifier()
	{
	Cancel();
	}
void CMotionClassifier::StartClassifing(TFixedArray<TInt,120>& arr)
	{
	
		TInt arrsize=120;
		TFixedArray<TInt,119> slopes;
		
		
	TInt i,steptemp=0;

	
	for(i=0;i<arrsize-1;i++)
	{
	slopes[i]=(arr[i+1]-arr[i]);
	}//i
	
	int pos=0,neg=0;
	for(TInt s=0;s<arrsize-1;s++)
	{
		if(slopes[s]>=0)
		{
			pos++;
			neg=0;
		}
		else if (pos>=6)
		{
			
		if(slopes[s]<0 && slopes[s+1]>=0)
		{
			neg++;
			if(neg>=6)
			{
			steptemp++;
			pos=0;
			}
			neg=0;
			
		}
		else if(slopes[s]<0)
		{
			neg++;
			
		}
		}//pos
		else pos=0;
		
	}//s
	if(steptemp >1 && steptemp <10)
		Steps=steptemp;
if(connect==1){

	 iState=ESend;
	TRequestStatus* status=&iStatus;
	User::RequestComplete(status, KErrNone);
	SetActive();
}//connect=1
else
	iLog.Write(_L("no"));
	}//StartClassifing
void CMotionClassifier::ConnectL()
    {
    

    iState=EReceiving;
    		
        iLog.Write(_L("a"));
        blank.Open(socketServ);
    	listener.Accept(blank, iStatus); 
    		
    	SetActive();
}
 void CMotionClassifier::RunL()
	 {
	 TBuf<10>statous;
	 statous.AppendNum(iStatus.Int());
	 iLog.Write(statous);
if(iStatus==KErrNone)
switch(iState)
	 		{
 case EAccept:
 		{	
 		blank.Close();
 		iState=EReceiving;
 		iLog.Write(_L("B"));
 		blank.Open(socketServ);
 	    listener.Accept(blank, iStatus); 
 		
 			SetActive();
 						           
 		}break;
 case EReceiving:
 	{	
 	iLog.Write(_L("b"));
 blank.RecvOneOrMore(buffer, 0,iStatus, dummyLength);
 iState=ERecConfi;
 SetActive();
 					           
 	}break;
 case ERecConfi:
 	{
 iLog.Write(_L("c"));
 if(buffer[0] == 'g') 
 {
 connect=1; 
 }
 else if(buffer[0]=='e')
 	{
 	iState=EClose;
 	
 	

 	}//else						
 					
 	}break;
 case ESend:
	 {
	 iState=EAccept;
	 connect=0;	
	 		TBuf8<10>buff;
	 		buff.AppendNum(Steps);
	 		Steps=0;
	 		blank.Write(buff,iStatus);
	 		iLog.Write(_L("send"));
	 		SetActive();
	 }break;
 case EClose:
 	{
 	//iLog.Write(_L("E"));
 	blank.Close();
 	socketServ.Close();		
 	User::Exit(KErrNone);
 	} break;
 }//switch
 			

 			
 		
	
	 }//Runl
 void CMotionClassifier::DoCancel()
	 {
	 Cancel();
	 }
