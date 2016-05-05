package cn.edu.pku.wechat.tools;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Bitmap.CompressFormat;

public class ImageDispose {
	/**
     * @param 将字节数组转换为ImageView可调用的Bitmap对象
     * @param bytes
     * @param opts
     * @return Bitmap
     */
	public static Bitmap getPicFromBytes(byte[] bytes,BitmapFactory.Options opts) {
       if (bytes != null)
	        if (opts != null)
               return BitmapFactory.decodeByteArray(bytes, 0, bytes.length,opts);
	        else
               return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
       return null;
	}
	
	/**
     * @param 将图片内容解析成字节数组
     * @param inStream
     * @return byte[]
     * @throws Exception
 	*/
	public static byte[] readStream(InputStream inStream) throws Exception {
       byte[] buffer = new byte[1024];
       int len = -1;
       ByteArrayOutputStream outStream = new ByteArrayOutputStream();
       while ((len = inStream.read(buffer)) != -1) {
           outStream.write(buffer, 0, len);
       }
       byte[] data = outStream.toByteArray();
       outStream.close();
       inStream.close();
       return data;
	
	}
	
	/**
     * @param 图片缩放
     * @param bitmap 对象
     * @param w 要缩放的宽度
     * @param h 要缩放的高度
     * @return newBmp 新 Bitmap对象
     */
    public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h){
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            Matrix matrix = new Matrix();
            float scaleWidth = ((float) w / width);
            float scaleHeight = ((float) h / height);
            matrix.postScale(scaleWidth, scaleHeight);
            Bitmap newBmp = Bitmap.createBitmap(bitmap, 0, 0, width, height,matrix, true);
            return newBmp;
    }
    /**
     * 把Bitmap转Byte
     */

     public static byte[] Bitmap2Bytes(Bitmap bm){
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
         return baos.toByteArray();
     }


     /**
      * 把字节数组保存为一个文件
      */
     public static File getFileFromBytes(byte[] b, String outputFile) {
         BufferedOutputStream stream = null;
         File file = null;
         try {
             file = new File(outputFile);
             FileOutputStream fstream = new FileOutputStream(file);
             stream = new BufferedOutputStream(fstream);
             stream.write(b);
         } catch (Exception e) {
             e.printStackTrace();
         } finally {
             if (stream != null) {
                 try {
                     stream.close();
                 } catch (IOException e1) {
                     e1.printStackTrace();
                 }
             }
         }
         return file;
     }
     /**
  	 * 把bitmap压缩成jpeg格式并且保存成一个图片文件
  	 * file 目标文件
  	 * bitmap 图片对象
  	 * Exception 
  	 * @throws Exception 
  	 */
  	public static void save(File file,Bitmap bitmap) throws Exception{
  		//父目录是否存在
  		if(!file.getParentFile().exists()){
  			file.getParentFile().mkdirs();
  		}
  		//文件是否存在
  		if(!file.exists()){
  			file.createNewFile();
  		}
  		//把bitmap压缩成JPEG格式输出
  		bitmap.compress(CompressFormat.JPEG, 100, new FileOutputStream(file));
  	}
  	
  	/**
	 * 从某一个路径中读取一个Bitmap对象
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static Bitmap loadBitmap(String path) throws Exception{
		File file=new File(path);
		if(!file.exists()){
			return null;
		}
		Bitmap bitmap=BitmapFactory.decodeFile(path);
		return bitmap;
	}

	/**
    
	   * @param bitmap      原图
	   * @param edgeLength  希望得到的正方形部分的边长
	   * @return  缩放截取正中部分后的位图。
	   */
	  public static Bitmap centerSquareScaleBitmap(Bitmap bitmap, int edgeLength)
	  {
	   if(null == bitmap || edgeLength <= 0)
	   {
	    return  null;
	   }
	                                                                                 
	   Bitmap result = bitmap;
	   int widthOrg = bitmap.getWidth();
	   int heightOrg = bitmap.getHeight();
	                                                                                 
	   if(widthOrg > edgeLength && heightOrg > edgeLength)
	   {
	    //压缩到一个最小长度是edgeLength的bitmap
	    int longerEdge = edgeLength * Math.max(widthOrg, heightOrg) / Math.min(widthOrg, heightOrg);
	    int scaledWidth = widthOrg > heightOrg ? longerEdge : edgeLength;
	    int scaledHeight = widthOrg > heightOrg ? edgeLength : longerEdge;
	    Bitmap scaledBitmap;
	                                                                                  
	          try{
	           scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true);
	          }
	          catch(Exception e){
	           return null;
	          }
	                                                                                       
	       //从图中截取正中间的正方形部分。
	       int xTopLeft = (scaledWidth - edgeLength) / 2;
	       int yTopLeft = (scaledHeight - edgeLength) / 2;
	                                                                                     
	       try{
	        result = Bitmap.createBitmap(scaledBitmap, xTopLeft, yTopLeft, edgeLength, edgeLength);
	        scaledBitmap.recycle();
	       }
	       catch(Exception e){
	        return null;
	       }       
	   }
	                                                                                      
	   return result;
	  }
	 

	  

}
