package com.asia_eagle.money.erqing.util;

import android.os.Bundle;
import android.util.Log;

import com.hisense.pos.spiprinter.SpiPrinter;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.Set;

public class LakalaDebug{
	
	public final static boolean bDebugFlag = true;
	public final static boolean bPrintFlag = false;
	private final static String TAG = LakalaDebug.class.getCanonicalName(); 
	private final static byte[] hex = "0123456789ABCDEF".getBytes();  
	
	
	public static void WriteLogFile(String strcontent)
    {
		if(bDebugFlag){
      //每次写入时，都换行写
      String strContent=strcontent+"\n";
      try {
           File file = new File("/storage/emulated/0/Log_text.txt");
           if (!file.exists()) {
            file.createNewFile();
           }
           RandomAccessFile raf = new RandomAccessFile(file, "rw");
           raf.seek(file.length());
           raf.write(strContent.getBytes());
           raf.close();
       } catch (Exception e) {
        
      }
	 }
    }
	
	public static void WriteLogFile(byte[] bDataArray)
    {
		if(bDebugFlag){
      //每次写入时，都换行写
      String strContent=Bytes2HexString(bDataArray)+"\n";
      try {
           File file = new File("/storage/emulated/0/Log_text.txt");
           if (!file.exists()) {
            file.createNewFile();
           }
           RandomAccessFile raf = new RandomAccessFile(file, "rw");
           raf.seek(file.length());
           raf.write(strContent.getBytes());
           raf.close();
       } catch (Exception e) {
        
      }
	 }
    }
	
	public static void connectPDATest(){
		
		
	}
	
	public static  void printBundle(Bundle bundle) {
		if(bDebugFlag){
		Set<String> keySet = bundle.keySet();
		//UMSServiceDebug.LogD("printBundle keySet.size", keySet.size());
		for (String key : keySet) {
			Object value = bundle.get(key);
			LakalaDebug.LogD("printBundle", "key = " + key + " value = " + value);
		}
	   }
		return ;
	}
	

	
	
	public static void Prnt(String title,int i){
		if(bPrintFlag){
		 SpiPrinter printer = new SpiPrinter();

		 int ret = printer.Printer_init();
		 if (ret == 0) {
				printer.Printer_TextStr(title+": "+i+"\n", 1, 0, 0);
		 }
	     ret = printer.Printer_Start();
		 if (ret == 0) {
				//printer.Printer_cutPaper();
		 }
		}
	}
	public static void Prnt(String title,String strData){
		if(bPrintFlag){
		 SpiPrinter printer = new SpiPrinter();

		 int ret = printer.Printer_init();
		 if (ret == 0) {
				printer.Printer_TextStr(title+": "+strData+"\n", 1, 0, 0);
		 }
	     ret = printer.Printer_Start();
		 if (ret == 0) {
				//printer.Printer_cutPaper();
		 }
	  }
	}
	
	public static void Prnt(String title,byte []data){
		if(bPrintFlag){
		 if(data==null)return;
		 
		 SpiPrinter printer = new SpiPrinter();
		 String sData="";
	      for(int i=0; i<data.length; i++)
		  {
	    	  sData+=String.format("%02X ", data[i]);
		  }
		 
	      LakalaDebug.LogD("sData", sData);
		 int ret = printer.Printer_init();
		 if (ret == 0) {
				printer.Printer_TextStr(title+": "+sData+"\n", 1, 0, 0);
		 }
	     ret = printer.Printer_Start();
		 if (ret == 0) {
				//printer.Printer_cutPaper();
		 }
		}
		
	}
	
	public static void LogD(String title, boolean msg){
		if(bDebugFlag){
		 Log.d(TAG, BooleanTransition(title,msg));
		}
	}
	
	public static void LogD(String title, char msg){
		if(bDebugFlag){
		Log.d(TAG, title + " = " + msg);
		}
	}
	
	public static void LogD(String title, int msg){
		if(bDebugFlag){
		Log.d(TAG, title + " = " + msg);
		}
	}
	
	public static void LogD(String title, int []msg){
		if(bDebugFlag){
			
			if(msg!=null && msg.length>0){
			 for(int i=0;i<msg.length;i++){
				
				 Log.d(TAG, title + " = " + msg[i]);
			 }
			}
		 
		}
	}
	
	
	public static void LogD(String title, String msg){
		if(bDebugFlag){
		Log.d(TAG, title + " = " + msg);
		}
	}
	
	
	
	public static void LogD(String title, String []msg){
		if(bDebugFlag){
			
			if(msg!=null && msg.length>0){
			 for(int i=0;i<msg.length;i++){
				
				 Log.d(TAG, title + " = " + msg[i]);
			 }
			}
		 
		}
	}
	
	
	public static void LogD(String title, byte msg){
		if(bDebugFlag){
		Log.d(TAG, title + " = 0x" + Byte2HexString(msg));
		}
	}
	
	public static void LogD(String title, byte msg[]){
		if(bDebugFlag){
			Log.d(TAG, BytesToStr(title,msg));
		}
	}
	
	
	public static void LogI(String title, boolean msg){
		if(bDebugFlag){
		 Log.i(TAG, BooleanTransition(title,msg));
		}
	}
	
	public static void LogI(String title, char msg){
		if(bDebugFlag){
		Log.i(TAG, title + " = " + msg);
		}
	}
	
	public static void LogI(String title, int msg){
		if(bDebugFlag){
		Log.i(TAG, title + " = " + msg);
		}
	}
	
	public static void LogI(String title, String msg){
		if(bDebugFlag){
		Log.i(TAG, title + " = " + msg);
		}
	}
	
	public static void LogI(String title, String[] msg){
		if(bDebugFlag){
			Log.i(TAG, StringsTransition(title,msg));
		}
	}
	
	public static void LogI(String title, byte msg){
		if(bDebugFlag){
		Log.i(TAG, title + " = 0x" + Byte2HexString(msg));
		}
	}
	
	public static void LogI(String title, byte msg[]){
		if(bDebugFlag){
			Log.i(TAG, BytesToStr(title,msg));
		}
	}
	
	
	public static void LogE(String title, boolean msg){
		if(bDebugFlag){
		 Log.e(TAG, BooleanTransition(title,msg));
		}
	}
	
	public static void LogE(String title, char msg){
		if(bDebugFlag){
		Log.e(TAG, title + " = " + msg);
		}
	}
	
	public static void LogE(String title, int msg){
		if(bDebugFlag){
		Log.e(TAG, title + " = " + msg);
		}
	}
	
	public static void LogE(String title, String msg){
		if(bDebugFlag){
		Log.e(TAG, title + " = " + msg);
		}
	}
	
	public static void LogE(String title, String[] msg){
		if(bDebugFlag){
			Log.e(TAG, StringsTransition(title,msg));
		}
	}
	
	public static void LogE(String title, byte msg){
		if(bDebugFlag){
		Log.e(TAG, title + " = 0x" + Byte2HexString(msg));
		}
	}
	
	public static void LogE(String title, byte msg[]){
		if(bDebugFlag){
			Log.e(TAG, BytesToStr(title,msg));
		}
	}
	
	public static void LogW(String title, boolean msg){
		if(bDebugFlag){
		 Log.w(TAG, BooleanTransition(title,msg));
		}
	}
	
	public static void LogW(String title, char msg){
		if(bDebugFlag){
		Log.w(TAG, title + " = " + msg);
		}
	}
	
	public static void LogW(String title, int msg){
		if(bDebugFlag){
		Log.w(TAG, title + " = " + msg);
		}
	}
	
	public static void LogW(String title, String msg){
		if(bDebugFlag){
		Log.w(TAG, title + " = " + msg);
		}
	}
	
	public static void LogW(String title, String[] msg){
		if(bDebugFlag){
			Log.w(TAG, StringsTransition(title,msg));
		}
	}
	
	public static void LogW(String title, byte msg){
		if(bDebugFlag){
		Log.w(TAG, title + " = 0x" + Byte2HexString(msg));
		}
	}
	
	public static void LogW(String title, byte msg[]){
		if(bDebugFlag){
			Log.w(TAG, BytesToStr(title,msg));
		}
	}
	
	
	/**
	 * boolean数据转换
	 * @param title
	 * @param msg
	 * @return
	 */
	public static String BooleanTransition(String title, boolean msg){
		String text = "false";
		if (msg) {
			text = "true";
		}
		return title + " = " + text;
	}
	

	
	/**
	 * String数组转换
	 */
	public static String StringsTransition(String title, String[] msg){
		String str = new String();
		if (msg == null) {
			str =  title + " = null";
		} else if (msg.length == 0) {
			str = title + " = length is 0";
		} else {
			for (int i = 0; i < msg.length; i++) {
				str = title + i + " = " + msg[i] + "\n";
			}
		}
		return str;
	}
	
	
	
	public static String BytesToStr(String title, byte msg[]){
		String str = null;
		if (msg == null) {
			 str = title + " = null";
		}else if (msg.length == 0) {
			 str = title + " = length is 0";
		}else {
			str = title + " = " + Bytes2HexString(msg);
		}
		return str;
	}
	
	
	public static String Bytes2HexString(byte[] b) {  
	    byte[] buff = new byte[2 * b.length];  
	    for (int i = 0; i < b.length; i++) {  
	        buff[2 * i] = hex[(b[i] >> 4) & 0x0f];  
	        buff[2 * i + 1] = hex[b[i] & 0x0f];  
	    }  
	    return new String(buff);  
	}  

	public static String Byte2HexString(byte b) {  
	    byte[] buff = new byte[2];  
        buff[0] = hex[(b >> 4) & 0x0f];  
        buff[1] = hex[b & 0x0f];  
	    return new String(buff);  
	}	
	
	private static byte toByte(char c) { 
	    byte b = (byte) "0123456789abcdef".indexOf(c); 
	    return b; 
	}
	
	public static void memcpy(byte[] des,int start, String hexString,int len)
	{
		if(len != hexString.length())
		{
			char[] achar = hexString.toLowerCase().toCharArray(); 
		    for (int i = 0; i < len&&i<(des.length-start)&&(i*2+1)<achar.length; i++) { 
			     int pos = i * 2; 
			     des[start+i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1])); 
			} 
		}
		else
		{
			byte[] source = hexString.getBytes();
			for(int i=0;i<len&&i<(des.length-start)&&i<source.length;i++)des[start+i]=source[i];
		}
	}
	public static void memcpy(byte[] des,int start, byte[] source,int len)
	{
		memcpy(des,start,source,0,len);
	}
	
	public static void memcpy(byte[] des,int dstart, byte[] source,int sstart,int len)
	{
		int i;
    	for(i=0;(i<len)&&i<(des.length-dstart)&&i<(source.length-sstart);i++)
    		des[i+dstart]=source[i+sstart];
	}
	
	public static void memcpy(byte[] des, String hexString,int len)
	{
		memcpy(des,0,hexString,len);
	}
}