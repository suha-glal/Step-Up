/*
 ============================================================================
 Name		: StepupDeamon.cpp
 Author	  : suha
 Copyright   : Your copyright notice
 Description : Exe source file
 ============================================================================
 */

//  Include Files  

#include "StepupDeamon.h"
#include"Listener.h"
#include <e32base.h>
#include <e32std.h>


//  Local Functions
LOCAL_C void MainL()
	{
	Listener *l=new Listener();
	l->Startlistener();
	
	}//MainL

void ConsoleMainL() 
	{
	
	// Create active scheduler (to run active objects)
		CActiveScheduler* scheduler = new (ELeave) CActiveScheduler();
		CleanupStack::PushL(scheduler);
		CActiveScheduler::Install(scheduler);
		
		MainL();
		scheduler->Start();
		// Delete active scheduler
		CleanupStack::PopAndDestroy(scheduler);
	
	
	}


GLDEF_C TInt E32Main() 
	{
	__UHEAP_MARK;
	CTrapCleanup* cleanupStack = CTrapCleanup::New();
	TRAPD(err, ConsoleMainL());
	delete cleanupStack;
	__UHEAP_MARKEND;
	return KErrNone; 
	}

