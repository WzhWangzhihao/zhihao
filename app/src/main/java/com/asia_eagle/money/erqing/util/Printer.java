package com.asia_eagle.money.erqing.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.RemoteException;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asia_eagle.money.erqing.R;
import com.hisense.pos.spiprinter.SpiPrinter;
import com.lkl.cloudpos.aidl.printer.AidlPrinterListener;
import com.lkl.cloudpos.aidl.printer.PrintItemObj;
import com.lkl.cloudpos.aidl.printer.PrintItemObj.ALIGN;
import com.lkl.cloudpos.data.AidlErrorCode;
import com.lkl.cloudpos.data.PrinterConstant;

import java.util.List;

public class Printer extends com.lkl.cloudpos.aidl.printer.AidlPrinter.Stub {
	private final String TAG = this.getClass().getSimpleName();
	//private static final int DEFAULT_FONT_SIZE = 24;
	private static Printer printer;
	private SpiPrinter spiPrinter;
	private Context context;
	private PrintThread printThread;
	public static Bitmap show;


	private Printer(Context context) {
		this.context = context;
		spiPrinter=new SpiPrinter();
	}
	public synchronized static Printer getInstance(Context context) {
		if (printer == null) {
			printer = new Printer(context);
		}
		LakalaDebug.LogD("Printer","getInstance");//参数的含义：title 和 msg
		return printer;
	}
	//	public boolean Open(){
//		if(spiPrinter.Printer_init()==0){
//
//	}
	@Override
	public int getPrinterState() throws RemoteException {
		spiPrinter.Printer_init();//打印机初始化
		LakalaDebug.LogD(TAG,"=====getPrinterState=====");
		int ret = spiPrinter.Printer_getStatus();//获取打印机的状态
		LakalaDebug.LogD("ret",ret+"");
		return converPrintStatus(ret);//0x03; // 转换打印机的状态
	}

	@Override
	//打印文本
	public void printText(List<PrintItemObj> data, AidlPrinterListener listener)
			throws RemoteException {
		LakalaDebug.LogD(TAG,"=====printText=====");
		if(null == listener){
			return ;
		}
		if(null == data){
			listener.onError(AidlErrorCode.Printer.ERROR_PRINT_ILLIGALARGUMENT);
		}
		LakalaDebug.LogD("Printer","printText");
		View view = LayoutInflater.from(context).inflate(R.layout.print_templete, null);//创建视图
		LinearLayout llTemplete = (LinearLayout) view.findViewById(R.id.container);

		for (PrintItemObj item : data) {
			if (item.getFontSize()<=0) {
				listener.onError(AidlErrorCode.Printer.ERROR_PRINT_ILLIGALARGUMENT);
				return;
			}
			int fontSize = getFontSize(item.getFontSize());//获得文本字体的大小
			TextView tView = new TextView(context);
			tView.setLayoutParams(new LayoutParams(768,LayoutParams.WRAP_CONTENT));//动态设置布局的宽度和高度
			LakalaDebug.LogD("printText  item.getText()=", item.getText()+item.getFontSize());
			LakalaDebug.LogD("printText  item.getText()=", item.getText()+fontSize);
			SpannableString spannableString = new SpannableString(item.getText());//使用SpannableString设置复合文本
			spannableString.setSpan(new AbsoluteSizeSpan(fontSize*4), 0, spannableString.toString()
					.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);//绝对大小，（文本字体）
			tView.setText(spannableString);
			tView.setTextColor(Color.BLACK);
			//tView.setTextSize(fontSize);

//			Typeface fontFace = Typeface.createFromAsset(context.getAssets(), "fonts/Redocn001.ttf");
//			tView.setTypeface(fontFace);


			tView.setPadding(item.getMarginLeft(), 0, 0, 0);
			ALIGN align = item.getAlign();//每一个item的所在位置
			if (align == ALIGN.LEFT) {
				tView.setGravity(Gravity.LEFT);
			} else if (align == ALIGN.RIGHT) {
				tView.setGravity(Gravity.RIGHT);
			} else if (align == ALIGN.CENTER) {
				tView.setGravity(Gravity.CENTER_HORIZONTAL);
			}
			if (item.isUnderline()) {
				tView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); // 设置下划线
				tView.getPaint().setAntiAlias(true);// 防止边缘锯齿
			}
			if (item.isBold()) {
				tView.getPaint().setFakeBoldText(true);
			}
			llTemplete.addView(tView);
		}
/**
 * Thread.interrupt()方法不会中断一个正在运行的线程。它的作用是，在线程受到阻塞时抛出一个中断信号，这样线程就得以退出阻塞的状态
 * 如果线程被Object.wait, Thread.join和Thread.sleep三种方法之一阻塞，
 * 那么，它将接收到一个中断异常（InterruptedException），从而提早地终结被阻塞状态。
 * */
		if (printThread != null) {
			printThread.interrupt();//不会中断一个正在运行的线程
		}
		printThread = new PrintThread(listener, convertViewToBitmap(llTemplete));//把视图转化成图片
		printThread.start();
	}

	@Override
	//打印图片    //leftoffset 左偏移量
	public void printBmp(int leftoffset, int width, int height,
						 final Bitmap picture, final AidlPrinterListener listener)
			throws RemoteException {

		LakalaDebug.LogD(TAG,"=====printBmp=====");

		new Thread(new Runnable() {
			@Override
			public void run() {
				toPrinter(listener, picture,(short) 0);
			}

		}).start();
	}

	@Override
	public void printBarCode(final int width, final int height, final int leftoffset,
							 final int barcodetype, final String barcode, final AidlPrinterListener listener)
			throws RemoteException {
		LakalaDebug.LogD(TAG,"=====printBarCode=====");

		LakalaDebug.LogD("Printer","printBarCode"+String.format("w:%d,h:%d,offset:%d,type:%d",width,height,leftoffset,barcodetype)+"--"+barcode);
//        new Thread(new Runnable() {
//			@Override
//			public void run() {
//				try {//, 380, 56
//					Bitmap bitmap=Utils.CreateOneDCode(300,height,leftoffset,barcodetype,barcode);
//					toPrinter(listener, bitmap,(short)0);
//				} catch (WriterException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//					try {
//						listener.onError(AidlErrorCode.Printer.ERROR_PRINT_BITMAP_OTHER);
//					} catch (RemoteException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//					return;
//				}
//			}
//
//		}).start();

	}
	@Override
	public void setPrinterGray(int gray) throws RemoteException {//设置打印机的灰度值
		LakalaDebug.LogD("Printer","setPrinterGray");
		LakalaDebug.LogD("gray",gray);
		int hxgary=0;
		switch (gray) {//gray 自动配置灰度，打印机的灰度值
			case 1:
				hxgary=3;
				break;
			case 2:
				hxgary=4;
				break;
			case 3:
				hxgary=5;
				break;
			case 4:
				hxgary=6;
				break;
			default:
				hxgary=3;
				break;
		}
		int ret = spiPrinter.Printer_setGray(hxgary);
	}

	private synchronized void toPrinter(AidlPrinterListener listener, Bitmap bitmap, short indent) {

		LakalaDebug.LogD("Printer","toPrinter");
		try {
			int initRet =spiPrinter.Printer_init();//打印初始化
			LakalaDebug.LogD("toPrinter", "initRet="+initRet);
			if (initRet!= SpiPrinter.PRINTER_OK) {
				try {
					Thread.sleep(500);
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
				initRet =spiPrinter.Printer_init();//初始化打印机
				if (initRet!= SpiPrinter.PRINTER_OK) {
					listener.onError(AidlErrorCode.Printer.ERROR_PRINT_UNKNOWN);
					return;
				}

			}
			/**
			 *先打印图片（printBtm）
			 *去打印：把图片转化成黑白色
			 *再去压缩图片
			 * */
			show = convertToBlackWhite(compMat(bitmap));//在打印图片中       先把图片彩色转换成黑白色，然后压缩图片
			int ret = spiPrinter.Printer_Image(show, indent);//参数含义：1，图片对象：2，对齐方式
			LakalaDebug.LogD("toPrinter", "ret="+ret);
			if (ret!=0) {
				listener.onError(converPrintImageStatus(ret));//转换打印图片时的状态
				return;
			}
			ret= spiPrinter.Printer_cutPaper();
			if (ret!=0) {
				listener.onError(converPrintCupPaperStatus(ret));//转换打印机切纸的状态
				return;
			}
			listener.onPrintFinish();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	private Bitmap convertViewToBitmap(View view) {//把view 转化成图片
//绘制view 设置view的宽度和高度
		view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
				MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

		view.buildDrawingCache();//开启view的绘制缓存
		Bitmap bitmap = view.getDrawingCache();
		return bitmap;
	}

	private int converPrintStatus(int printStatus) {//打印机的状态
		LakalaDebug.LogD("Printer","converPrintStatus");
		int ret = PrinterConstant.PrinterState.PRINTER_STATE_UNKNOWN;
		switch (printStatus) {
			case SpiPrinter.PRINTER_OK:
				ret = PrinterConstant.PrinterState.PRINTER_STATE_NORMAL;
				break;
			case SpiPrinter.PRINTER_ERROR_NOPAPER: // 无纸
				ret = PrinterConstant.PrinterState.PRINTER_STATE_NOPAPER;
				break;
			case SpiPrinter.PRINTER_ERROR_HOT: // 过热
				ret = PrinterConstant.PrinterState.PRINTER_STATE_HIGHTEMP;
				break;
			default://
				ret = PrinterConstant.PrinterState.PRINTER_STATE_UNKNOWN;
				break;
		}
		return ret;

	}

	private int converPrintImageStatus(int printStatus) {//打印图片的状态
		LakalaDebug.LogD("Printer","converPrintStatus");
		int ret = AidlErrorCode.Printer.ERROR_PRINT_UNKNOWN;
		switch (printStatus) {
			case SpiPrinter.PRINTER_ERROR_BMP_NULL: // bmp参数为空
				ret = AidlErrorCode.Printer.ERROR_PRINT_BITMAP_OTHER;
				break;
			case SpiPrinter.PRINTER_ERROR_NOPAPER: // 缺纸
				ret = AidlErrorCode.Printer.ERROR_PRINT_NOPAPER;
				break;
			case SpiPrinter.PRINTER_ERROR_HOT: //过热
				ret = AidlErrorCode.Printer.ERROR_PRINT_HOT;
				break;
			case SpiPrinter.PRINTER_ERROR_IMG_MODE: //对齐方式参数错误
				ret = AidlErrorCode.Printer.ERROR_PRINT_BITMAP_OTHER;
				break;
			default://
				ret = AidlErrorCode.Printer.ERROR_PRINT_UNKNOWN;
				break;
		}
		return ret;

	}


	private int converPrintCupPaperStatus(int printStatus) {//切纸时的状态
		LakalaDebug.LogD("Printer","converPrintStatus");
		int ret = AidlErrorCode.Printer.ERROR_PRINT_UNKNOWN;
		switch (printStatus) {
			case SpiPrinter.PRINTER_ERROR_NOPAPER: // 无纸
				ret = AidlErrorCode.Printer.ERROR_PRINT_NOPAPER;
				break;
			case SpiPrinter.PRINTER_ERROR_HOT: // 过热
				ret = AidlErrorCode.Printer.ERROR_PRINT_HOT;
				break;
			default:// 鏈煡寮傚父
				ret = AidlErrorCode.Printer.ERROR_PRINT_UNKNOWN;
				break;
		}
		return ret;

	}
	class PrintThread extends Thread {
		private AidlPrinterListener listener;
		private Bitmap bitmap;

		public PrintThread(AidlPrinterListener listener, Bitmap bitmap) {
			this.listener = listener;
			this.bitmap = bitmap;
		}
		@Override
		public void run() {
			try {
				printBmp(5, 376, 900, bitmap, listener);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			super.run();
		}

	}
	private Bitmap convertToBlackWhite(Bitmap bmp) {//把彩色转化成黑白色
		int width = bmp.getWidth();
		int height = bmp.getHeight();
		int[] pixels = new int[width * height]; //通过位图的大小创建像素点数组

		bmp.getPixels(pixels, 0, width, 0, 0, width, height);//获取像素
		int alpha = 0xFF << 24;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int grey = pixels[width * i + j];
				//分离三原色
				int red = ((grey & 0x00FF0000) >> 16);
				int green = ((grey & 0x0000FF00) >> 8);
				int blue = (grey & 0x000000FF);
				int avg = ((red + green + blue)/3);
				if(avg > 150)
					avg = 0xFFFFFFFF;
				else
					avg = 0xFF000000;
				//转换成灰色像素
				grey = avg ;
				pixels[width * i + j] = grey ;
			}
		}
		//新建图片
		Bitmap newBmp = Bitmap.createBitmap(width, height, Config.RGB_565);
		//设置图片数据
		newBmp.setPixels(pixels, 0, width, 0, 0, width, height);
//		Bitmap resizeBmp = ThumbnailUtils.extractThumbnail(newBmp, width, height);
		return newBmp;
	}
	private Bitmap compMat(Bitmap image){//图片压缩处理
		if (image.getWidth()<=384) {
			return image;
		}
		float b=(384f)/(image.getWidth());
		Matrix matrix = new Matrix();//想要取得缩放图片的参数
		matrix.setScale(b, b);
		Bitmap bm = Bitmap.createBitmap(image, 0, 0, image.getWidth(),//得到新的图片
				image.getHeight(), matrix, true);
		return bm;
	}
	private int getFontSize(int size){
		LakalaDebug.LogD("getFontSize",size);
		int fontSize =0;
		if (size>0&&size<=8) {
			fontSize=(size/4+3)*2;
		}else if (size>8&&size<=24) {
			fontSize = (size/4+3)*2;
		}else {
			fontSize = 10*2;
		}
		LakalaDebug.LogD("getFontSize 2",size);
		return fontSize;
	}

}
