#include"Listener.h"
#include"AccelometerReadings.h"
 Listener::Listener(): CActive(CActive::EPriorityStandard)
{   
		



	 //  iLog.Connect();
      // iLog.CreateLog(_L("myLogs"),_L("MySer"),EFileLoggingModeOverwrite);
    
      
       //567 driver
            TUint KTestPort=8567;
       	    TInetAddr addr(KInetAddrLoop, KTestPort);
       		User::LeaveIfError(socketServ.Connect());
       		CleanupClosePushL(socketServ);//if function leaves ensure socket serv session closes
       		User::LeaveIfError(listener.Open(socketServ, KAfInet,KSockStream, KProtocolInetTcp));
       		User::LeaveIfError(listener.Bind(addr));  
       		User::LeaveIfError(listener.Listen(1));
       					
		
		// Add to active scheduler
		CActiveScheduler::Add(this);
	
		
}//Listener

 Listener::~Listener()
{
Cancel();//cancel outstanding request
}

void Listener::Startlistener()
{

callerStatus=EReceiving;
		
    //iLog.Write(_L("A"));
    blank.Open(socketServ);
	listener.Accept(blank, iStatus); 
	acclometer = new CAccelometerReadings(this);	
	SetActive();
}


void Listener::RunL()
	{

	_LIT8(KClosing, "closing");
	TBuf<80> h;
	h.AppendNum(iStatus.Int());
	//iLog.Write(h);
	if(iStatus==KErrNone)
	 switch(callerStatus)
		{
	case EAccept:
			{	
			blank.Close();
			callerStatus=EReceiving;
			//iLog.Write(_L("B"));
			blank.Open(socketServ);
			listener.Accept(blank, iStatus); 
			
				SetActive();
									   
			}break;
	case EReceiving:
		{	
		//iLog.Write(_L("C"));
	     blank.RecvOneOrMore(buffer, 0,iStatus, dummyLength);
	     callerStatus=ERecConfi;
	     SetActive();
								   
		}break;
	case ERecConfi:
		{
		//iLog.Write(_L("D"));
	if(	(buffer[0] == 's') ) 
	{
	callerStatus=EAccept;
	 buffer.Copy(_L("s"));	
	 //iLog.Write(buffer);
	 blank.Write(buffer, iStatus);
 
	 	
	
	  
	}
	else if(buffer[0]=='e')
		{
		callerStatus=EClose;
		blank.Write(KClosing, iStatus);
		
		}//else						
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
				

			
		
			
}//RunL

void Listener::DoCancel()
{
Cancel();

}
