//package com.asia_eagle.money.erqing.activity;
//
//import java.util.List;
//
//import com.hisense.pos.spiprinter.SpiPrinter;
//import com.lkl.cloudpos.aidl.printer.AidlPrinterListener;
//import com.lkl.cloudpos.aidl.printer.PrintItemObj;
//import com.lkl.cloudpos.aidl.printer.PrintItemObj.ALIGN;
//import com.lkl.cloudpos.data.AidlErrorCode;
//import com.lkl.cloudpos.data.PrinterConstant;
//
//import android.content.Context;
//import android.content.res.ColorStateList;
//import android.graphics.Bitmap;
//import android.graphics.Bitmap.Config;
//import android.graphics.Color;
//import android.graphics.Matrix;
//import android.graphics.Paint;
//import android.graphics.Typeface;
//import android.os.RemoteException;
//import android.text.Spannable;
//import android.text.SpannableString;
//import android.text.style.AbsoluteSizeSpan;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.MeasureSpec;
//import android.view.ViewGroup.LayoutParams;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//public class Printer extends com.lkl.cloudpos.aidl.printer.AidlPrinter.Stub {
//	private final String TAG = this.getClass().getSimpleName();
//	//private static final int DEFAULT_FONT_SIZE = 24;
//	private static Printer printer;
//	private SpiPrinter spiPrinter;
//	private Context context;
//	private PrintThread printThread;
//	public static Bitmap show;
//	private Printer(Context context) {
//		this.context = context;
//		spiPrinter=new SpiPrinter();
//	}
//
//	public synchronized static Printer getInstance(Context context) {
//		if (printer == null) {
//			printer = new Printer(context);
//		}
//		LakalaDebug.LogD("Printer","getInstance");
//		return printer;
//	}
//
//	@Override
//	public int getPrinterState() throws RemoteException {
//		spiPrinter.Printer_init();
//		LakalaDebug.LogD(TAG,"=====getPrinterState=====");
//		int ret = spiPrinter.Printer_getStatus();
//		LakalaDebug.LogD("ret",ret+"");
//		return converPrintStatus(ret);//0x03;
//	}
//
//	@Override
//
//	public void printText(List<PrintItemObj> data, AidlPrinterListener listener)
//			throws RemoteException {
//		LakalaDebug.LogD(TAG,"=====printText=====");
//		if(null == listener){
//			return ;
//		}
//		if(null == data){
//			listener.onError(AidlErrorCode.Printer.ERROR_PRINT_ILLIGALARGUMENT);
//		}
//		LakalaDebug.LogD("Printer","printText");
//		View view = LayoutInflater.from(context).inflate(R.layout.print_templete, null);
//		LinearLayout llTemplete = (LinearLayout) view.findViewById(R.id.container);
//
//		for (PrintItemObj item : data) {
//			if (item.getFontSize()<=0) {
//				listener.onError(AidlErrorCode.Printer.ERROR_PRINT_ILLIGALARGUMENT);
//				return;
//			}
//			int fontSize = getFontSize(item.getFontSize());
//			TextView tView = new TextView(context);
//			tView.setLayoutParams(new LayoutParams(768,LayoutParams.WRAP_CONTENT));
//			LakalaDebug.LogD("printText  item.getText()=", item.getText()+item.getFontSize());
//			LakalaDebug.LogD("printText  item.getText()=", item.getText()+fontSize);
//			SpannableString spannableString = new SpannableString(item.getText());
//			spannableString.setSpan(new AbsoluteSizeSpan(fontSize*4), 0, spannableString.toString()
//					.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//			tView.setText(spannableString);
//			tView.setTextColor(Color.BLACK);
//			//tView.setTextSize(fontSize);
//
////			Typeface fontFace = Typeface.createFromAsset(context.getAssets(), "fonts/Redocn001.ttf");
////			tView.setTypeface(fontFace);
//
//
//			tView.setPadding(item.getMarginLeft(), 0, 0, 0);
//			ALIGN align = item.getAlign();
//			if (align == ALIGN.LEFT) {
//				tView.setGravity(Gravity.LEFT);
//			} else if (align == ALIGN.RIGHT) {
//				tView.setGravity(Gravity.RIGHT);
//			} else if (align == ALIGN.CENTER) {
//				tView.setGravity(Gravity.CENTER_HORIZONTAL);
//			}
//			if (item.isUnderline()) {
//				tView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
//				tView.getPaint().setAntiAlias(true);
//			}
//			if (item.isBold()) {
//				tView.getPaint().setFakeBoldText(true);
//			}
//			llTemplete.addView(tView);
//		}
//
//		if (printThread != null) {
//			printThread.interrupt();
//		}
//		printThread = new PrintThread(listener, convertViewToBitmap(llTemplete));
//		printThread.start();
//	}
//
//	@Override
//
//	public void printBmp(int leftoffset, int width, int height,
//			final Bitmap picture, final AidlPrinterListener listener)
//			throws RemoteException {
//
//		LakalaDebug.LogD(TAG,"=====printBmp=====");
//
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				toPrinter(listener, picture,(short) 0);
//			}
//
//		}).start();
//	}
//
//	@Override
//	public void printBarCode(final int width, final int height, final int leftoffset,
//			final int barcodetype, final String barcode, final AidlPrinterListener listener)
//			throws RemoteException {
//		LakalaDebug.LogD(TAG,"=====printBarCode=====");
//
//		LakalaDebug.LogD("Printer","printBarCode"+String.format("w:%d,h:%d,offset:%d,type:%d",width,height,leftoffset,barcodetype)+"--"+barcode);
////        new Thread(new Runnable() {
////			@Override
////			public void run() {
////				try {//, 380, 56
////					Bitmap bitmap=Utils.CreateOneDCode(300,height,leftoffset,barcodetype,barcode);
////					toPrinter(listener, bitmap,(short)0);
////				} catch (WriterException e) {
////					// TODO Auto-generated catch block
////					e.printStackTrace();
////					try {
////						listener.onError(AidlErrorCode.Printer.ERROR_PRINT_BITMAP_OTHER);
////					} catch (RemoteException e1) {
////						// TODO Auto-generated catch block
////						e1.printStackTrace();
////					}
////					return;
////				}
////			}
////
////		}).start();
//
//	}
//	@Override
//	public void setPrinterGray(int gray) throws RemoteException {
//		LakalaDebug.LogD("Printer","setPrinterGray");
//		LakalaDebug.LogD("gray",gray);
//		int hxgary=0;
//		switch (gray) {//gray �Զ����ûҶȣ���ӡ��ĻҶ�ֵ
//		case 1:
//			hxgary=3;
//			break;
//		case 2:
//			hxgary=4;
//			break;
//		case 3:
//			hxgary=5;
//			break;
//		case 4:
//			hxgary=6;
//			break;
//		default:
//			hxgary=3;
//			break;
//		}
//		int ret = spiPrinter.Printer_setGray(hxgary);
//	}
//
//	private synchronized void toPrinter(AidlPrinterListener listener,Bitmap bitmap,short indent) {
//
//		LakalaDebug.LogD("Printer","toPrinter");
//		try {
//			int initRet =spiPrinter.Printer_init();
//			LakalaDebug.LogD("toPrinter", "initRet="+initRet);
//			if (initRet!=SpiPrinter.PRINTER_OK) {
//				try {
//					Thread.sleep(500);
//				}
//				catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				initRet =spiPrinter.Printer_init();//��ʼ����ӡ��
//				if (initRet!=SpiPrinter.PRINTER_OK) {
//					listener.onError(AidlErrorCode.Printer.ERROR_PRINT_UNKNOWN);
//					return;
//				}
//
//			}
//
//			show = convertToBlackWhite(compMat(bitmap));
//			int ret = spiPrinter.Printer_Image(show, indent);
//			LakalaDebug.LogD("toPrinter", "ret="+ret);
//			if (ret!=0) {
//				listener.onError(converPrintImageStatus(ret));
//				return;
//			}
//			ret= spiPrinter.Printer_cutPaper();
//			if (ret!=0) {
//				listener.onError(converPrintCupPaperStatus(ret));
//				return;
//			}
//			listener.onPrintFinish();
//		} catch (RemoteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
//
//
//	private Bitmap convertViewToBitmap(View view) {
//
//		view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
//				MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
//		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
//
//		view.buildDrawingCache();//����view�Ļ��ƻ���
//		Bitmap bitmap = view.getDrawingCache();
//		return bitmap;
//	}
//
//	private int converPrintStatus(int printStatus) {//��ӡ���״̬
//		LakalaDebug.LogD("Printer","converPrintStatus");
//		int ret = PrinterConstant.PrinterState.PRINTER_STATE_UNKNOWN;
//		switch (printStatus) {
//		case SpiPrinter.PRINTER_OK:
//			ret = PrinterConstant.PrinterState.PRINTER_STATE_NORMAL;
//			break;
//		case SpiPrinter.PRINTER_ERROR_NOPAPER: // ��ֽ
//			ret = PrinterConstant.PrinterState.PRINTER_STATE_NOPAPER;
//			break;
//		case SpiPrinter.PRINTER_ERROR_HOT: // ����
//			ret = PrinterConstant.PrinterState.PRINTER_STATE_HIGHTEMP;
//			break;
//		default://
//			ret = PrinterConstant.PrinterState.PRINTER_STATE_UNKNOWN;
//			break;
//		}
//		return ret;
//
//	}
//
//	private int converPrintImageStatus(int printStatus) {//��ӡͼƬ��״̬
//		LakalaDebug.LogD("Printer","converPrintStatus");
//		int ret = AidlErrorCode.Printer.ERROR_PRINT_UNKNOWN;
//		switch (printStatus) {
//		case SpiPrinter.PRINTER_ERROR_BMP_NULL: // bmp����Ϊ��
//			ret = AidlErrorCode.Printer.ERROR_PRINT_BITMAP_OTHER;
//			break;
//		case SpiPrinter.PRINTER_ERROR_NOPAPER: // ȱֽ
//			ret = AidlErrorCode.Printer.ERROR_PRINT_NOPAPER;
//			break;
//		case SpiPrinter.PRINTER_ERROR_HOT: //����
//			ret = AidlErrorCode.Printer.ERROR_PRINT_HOT;
//			break;
//		case SpiPrinter.PRINTER_ERROR_IMG_MODE: //���뷽ʽ�������
//			ret = AidlErrorCode.Printer.ERROR_PRINT_BITMAP_OTHER;
//			break;
//		default://
//			ret = AidlErrorCode.Printer.ERROR_PRINT_UNKNOWN;
//			break;
//		}
//		return ret;
//
//	}
//
//
//	private int converPrintCupPaperStatus(int printStatus) {//��ֽʱ��״̬
//		LakalaDebug.LogD("Printer","converPrintStatus");
//		int ret = AidlErrorCode.Printer.ERROR_PRINT_UNKNOWN;
//		switch (printStatus) {
//		case SpiPrinter.PRINTER_ERROR_NOPAPER: // ��ֽ
//			ret = AidlErrorCode.Printer.ERROR_PRINT_NOPAPER;
//			break;
//		case SpiPrinter.PRINTER_ERROR_HOT: // ����
//			ret = AidlErrorCode.Printer.ERROR_PRINT_HOT;
//			break;
//		default:// 未知异常
//			ret = AidlErrorCode.Printer.ERROR_PRINT_UNKNOWN;
//			break;
//		}
//		return ret;
//
//	}
//	class PrintThread extends Thread {
//		private AidlPrinterListener listener;
//		private Bitmap bitmap;
//
//		public PrintThread(AidlPrinterListener listener, Bitmap bitmap) {
//			this.listener = listener;
//			this.bitmap = bitmap;
//		}
//		@Override
//		public void run() {
//			try {
//				printBmp(5, 376, 900, bitmap, listener);
//			} catch (RemoteException e) {
//				e.printStackTrace();
//			}
//			super.run();
//		}
//
//	}
//	private Bitmap convertToBlackWhite(Bitmap bmp) {
//		int width = bmp.getWidth();
//		int height = bmp.getHeight();
//		int[] pixels = new int[width * height];
//
//		bmp.getPixels(pixels, 0, width, 0, 0, width, height);
//		int alpha = 0xFF << 24;
//		for (int i = 0; i < height; i++) {
//			for (int j = 0; j < width; j++) {
//				int grey = pixels[width * i + j];
//
//				int red = ((grey & 0x00FF0000) >> 16);
//				int green = ((grey & 0x0000FF00) >> 8);
//				int blue = (grey & 0x000000FF);
//				int avg = ((red + green + blue)/3);
//				if(avg > 150)
//					avg = 0xFFFFFFFF;
//				else
//					avg = 0xFF000000;
//				//ת���ɻ�ɫ����
//				grey = avg ;
//				pixels[width * i + j] = grey ;
//			}
//		}
//		//�½�ͼƬ
//		Bitmap newBmp = Bitmap.createBitmap(width, height, Config.RGB_565);
//		//����ͼƬ���
//		newBmp.setPixels(pixels, 0, width, 0, 0, width, height);
////		Bitmap resizeBmp = ThumbnailUtils.extractThumbnail(newBmp, width, height);
//		return newBmp;
//	}
//	private Bitmap compMat(Bitmap image){
//		if (image.getWidth()<=384) {
//			return image;
//		}
//		float b=(384f)/(image.getWidth());
//		Matrix matrix = new Matrix();
//		matrix.setScale(b, b);
//		Bitmap bm = Bitmap.createBitmap(image, 0, 0, image.getWidth(),//�õ��µ�ͼƬ
//				image.getHeight(), matrix, true);
//		return bm;
//	}
//	private int getFontSize(int size){
//		LakalaDebug.LogD("getFontSize",size);
//		int fontSize =0;
//		if (size>0&&size<=8) {
//			fontSize=(size/4+3)*2;
//		}else if (size>8&&size<=24) {
//			fontSize = (size/4+3)*2;
//		}else {
//			fontSize = 10*2;
//		}
//		LakalaDebug.LogD("getFontSize 2",size);
//		return fontSize;
//	}
//
//}
