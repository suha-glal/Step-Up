#ifndef __CSENSORDATAFILTER_H_
#define __CSENSORDATAFILTER_H_
 
#include <e32base.h>  // CBase

// Size of the data buffer. The smaller the value, the quicker the reaction
// speed to the changes in the sensor data. However, at the same time, the
// amplitude of noise from the sensor is higher.
const TInt KDataBufferSize = 16;
 
class CSensorDataFilter : public CBase
    {
    public:  // Constructors and destructor
        /**
         * Two-phased constructor.
         */
        static CSensorDataFilter* NewL();
        
        /**
         * Destructor.
         */
        ~CSensorDataFilter();
        
    private:  // Private constructors
        /**
         * Two-phased constructor.
         */
        void ConstructL();
 
        /**
         * Default constructor.
         */
        CSensorDataFilter();
        
    public:  // New functions
        /**
         * Calculates the average value from the buffer contents after adding
         * a new value to it.
         * @param aNewValue a new value to add to the buffer
         * @return the average value of the buffer contents
         */
        TInt FilterSensorData(TInt aNewValue);
        
    private:  // Data
        TInt* iRingBuffer;
        TInt* iRingBufferPointer;
    };
 
#endif /*__CSENSORDATAFILTER_H_*/
