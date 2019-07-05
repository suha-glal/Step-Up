#include "CSensorDataFilter.h"
 
CSensorDataFilter* CSensorDataFilter::NewL()
    {
    CSensorDataFilter* self = new (ELeave) CSensorDataFilter();
    CleanupStack::PushL(self);
    self->ConstructL();
    CleanupStack::Pop(self);
    return self;
    }
 
CSensorDataFilter::CSensorDataFilter()
    {
    // No implementation required
    }
 
void CSensorDataFilter::ConstructL()
    {
    // Allocate a ring buffer to hold the sensor data
    iRingBuffer = new (ELeave) TInt[KDataBufferSize];
    
    // Clear the buffer
    memset(iRingBuffer, '\0', KDataBufferSize * sizeof(TInt));
    
    // Set the buffer pointer to the beginning of the buffer 
    iRingBufferPointer = iRingBuffer;
    }
 
CSensorDataFilter::~CSensorDataFilter()
    {
    delete iRingBuffer;
    }
 
TInt CSensorDataFilter::FilterSensorData(TInt aNewValue)
    {
    // Add the new value to the buffer, at the current position
    *iRingBufferPointer = aNewValue;
    
    // Move the pointer, circulating it to the beginning if the buffer is full
    iRingBufferPointer++;
    if (iRingBufferPointer >= (iRingBuffer + (KDataBufferSize - 1)))
        {
        iRingBufferPointer = iRingBuffer;
        }
    
    // Calculate the average of the buffer values
    TInt sum = 0;
    for (TInt i = 0; i < KDataBufferSize; i++)
        {
        sum = sum + iRingBuffer[i];
        }
    return (sum / KDataBufferSize);
    }
