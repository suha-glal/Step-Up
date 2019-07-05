/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package stepPackage;



import javax.microedition.rms.*;

class DBmanger
{
   RecordStore rs = null;
  String REC_STORE;

  public DBmanger(String dbname)
  {

   REC_STORE=dbname;
    openRecStore();   // Create the record store

  } 
public int getNumOfRec()
{
int n=0;
        try {
            n = rs.getNumRecords();
        } catch (RecordStoreNotOpenException ex) {
            ex.printStackTrace();
        }
return n;
}
  public void openRecStore()
  {
    try
    {
      // The second parameter indicates that the record store
      // should be created if it does not exist
      rs = RecordStore.openRecordStore(REC_STORE, true );
    }
    catch (Exception e)
    {
      db(e.toString());
    }
  }

  public void closeRecStore()
  {
    try
    {
      rs.closeRecordStore();
    }
    catch (Exception e)
    {
      db(e.toString());
    }
  }

  public void deleteRecStore()
  {
    if (RecordStore.listRecordStores() != null)
    {
         closeRecStore();
      try
      {

        RecordStore.deleteRecordStore(REC_STORE);
      }
      catch (Exception e)
      {
        db(e.toString());
      }
    }
  }

  public void writeRecord(String str)
  {
    byte[] rec = str.getBytes();

    try
    {
      rs.addRecord(rec, 0, rec.length);
    }
    catch (Exception e)
    {
      db(e.toString());
    }
  }
public void UpdateRecord(int rn,String str)
{
  byte[] rec = str.getBytes();

    try
    {
      rs.setRecord(rn,rec, 0, rec.length);
    }
    catch (Exception e)
    {
      db(e.toString());
    }

}//emptyRecordStore

  String readRecord(int i)
  {
    try
    {
      // Intentionally make this too small to test code below
      byte[] recData = new byte[5];
      int len;

      //for (int i = 1; i <= rs.getNumRecords(); i++)
      //{
        //  System.out.println("Record size" + rs.getRecordSize(i)+"\n");
        if (rs.getRecordSize(i) > recData.length)
          recData = new byte[rs.getRecordSize(i)];

        len = rs.getRecord(i, recData, 0);
        return ( new String(recData, 0, len));
        
      //}//for
    }
    catch (Exception e)
    {
      db(e.toString());
      return null;
    }
  }

  /*--------------------------------------------------
  * Simple message to console for debug/errors
  * When used with Exceptions we should handle the
  * error in a more appropriate manner.
  *-------------------------------------------------*/
  private void db(String str)
  {
    System.err.println("Msg: " + str);
  }
}
