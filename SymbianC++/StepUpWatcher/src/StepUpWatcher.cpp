/*
 ============================================================================
 Name		: StepUpWatcher.cpp
 Author	  : suha
 Copyright   : Your copyright notice
 Description : Exe source file
 ============================================================================
 */

//  Include Files  

#include "StepUpWatcher.h"

#include <e32base.h>
#include <e32std.h>
//#include <e32cons.h>			// Console

// I added those
#include <in_sock.h>
#include <es_sock.h>

//#include <flogger.h> //file logger

//RFileLogger iLog;
//  Local Functions

void startStepUpDeamon()
	{
	TInt err;
	_LIT (KExeName, "c:\\sys\\bin\\StepupDeamon.exe");
				_LIT( KArguments, "-argument" );
				RProcess processHandle;
				 err = processHandle.Create(KExeName, KArguments);
				if (err == KErrNone) 
			    { 
				processHandle.Resume(); 
				processHandle.Close();  
				}
	}

LOCAL_C void MainL()
	{
	//
	// add your program code here, example code below
	//
	//iLog.Connect();
   // iLog.CreateLog(_L("myLogs"),_L("MyLogFile"),EFileLoggingModeOverwrite);
		 
	
		TUint KTestPort=8555;
			TInetAddr addr(KInetAddrLoop, KTestPort);
		RSocketServ socketServ;
		RSocket listener;
		RSocket blank;
		User::LeaveIfError(socketServ.Connect());
		CleanupClosePushL(socketServ);//if function leaves ensure socket serv session closes
		User::LeaveIfError(listener.Open(socketServ, KAfInet,KSockStream, KProtocolInetTcp));
		User::LeaveIfError(listener.Bind(addr));  
		User::LeaveIfError(listener.Listen(1));
		TRequestStatus status;
		TSockXfrLength dummyLength;
		TBool running = ETrue;
		_LIT8(KBadCommand, "Bad command");
		_LIT8(KClosing, "Server closing");
		_LIT8(Kstart, "D");
		
		TBuf8<256> buffer;
		
		while(running) 
			{
			//iLog.Write(_L("0"));
			blank.Open(socketServ);
			listener.Accept(blank, status); 
			User::WaitForRequest(status);
			if(status != KErrNone) User::Leave(KErrGeneral);
			//console->Printf(_L("Accepted connection!!\n"));		
			blank.RecvOneOrMore(buffer, 0, status, dummyLength);
			User::WaitForRequest(status);	
			if(status != KErrNone) User::Leave(KErrGeneral);
			if(	(buffer[0] == 's') ) 
							{
							
							buffer.Copy(Kstart);
							} 	
			else if((buffer[0] == 'a')) 
					{
					
					startStepUpDeamon();
					buffer.Copy(_L("a"));
					}
			
			else if( (buffer[0] == 'c') ) 
				{
				//console->Printf(_L("Closing server!!\n"));
				running = EFalse;	
				buffer.Copy(KClosing);
				} 
			else 
				{
				//console->Printf(_L("Bad command!!\n"));
				buffer.Copy(KBadCommand);			
				}

			blank.Write(buffer, status);
			User::WaitForRequest(status); 
			if(status != KErrNone) User::Leave(KErrGeneral);
			blank.Close();		
			}//end of while loop

		CleanupStack::Pop(&socketServ);
		socketServ.Close();		
		
		//iLog.CloseLog();
		//iLog.Close();
	}

LOCAL_C void DoStartL()
	{
	// Create active scheduler (to run active objects)
	CActiveScheduler* scheduler = new (ELeave) CActiveScheduler();
	CleanupStack::PushL(scheduler);
	CActiveScheduler::Install(scheduler);

	MainL();

	// Delete active scheduler
	CleanupStack::PopAndDestroy(scheduler);
	}

//  Global Functions

GLDEF_C TInt E32Main()
	{
	// Create cleanup stack
	__UHEAP_MARK;
	CTrapCleanup* cleanup = CTrapCleanup::New();

	// Run application code inside TRAP harness, wait keypress when terminated
	TRAPD(mainError, DoStartL());
	
	delete cleanup;
	__UHEAP_MARKEND;
	return KErrNone;
	}

