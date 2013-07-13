package com.founder.common.data;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.util.EncodingUtils;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

public  class FileStoreTools{

	

	/**

	 * 

	 *TODO：保存文件 根目录

	 *Author：Andy.Liu

	 *Create Time：2012-7-10 上午08:54:14

	 *TAG：@return

	 *Return：String

	 */

	public static String getSDPath(){

		boolean hasSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);

		if(hasSDCard){

			return Environment.getExternalStorageDirectory().toString();

		}else

			return null;
			//return Environment.getDownloadCacheDirectory().toString();

	}
	
	public static String getFilePath()
	{
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd/HHmmss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	

	/**

	 * 

	 *TODO：保存文件

	 *Author：Andy.Liu

	 *Create Time：2012-7-10 上午08:42:40

	 *TAG：@param str	文件的内容

	 *TAG：@param filePath	保存路径

	 *Return：void

	 */

	public static void saveFile(String str, String filePath, String fileName){

		 File fp = new File(filePath);
		 if (fp.exists() == false)
			fp.mkdirs();
		 File file=new File(filePath,fileName);  
	        
			try {
				FileOutputStream fos;
				fos = new FileOutputStream(file);
				fos.write(str.getBytes());  
				fos.close();  

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  

	}
	
	public static String readFileSdcard(String fileName){

        String res=""; 

        try{ 

         FileInputStream fin = new FileInputStream(fileName); 

         int length = fin.available(); 

         byte [] buffer = new byte[length]; 

         fin.read(buffer);     

         res = EncodingUtils.getString(buffer, "UTF-8"); 

         fin.close();     

        } 

        catch(Exception e){ 

         e.printStackTrace(); 

        } 

        return res; 

   }



	public static String readFile(String filePath){

		FileInputStream fis = null;

		byte[] mByte = new byte[512];

		try {

			fis = new FileInputStream(new File(filePath));

			fis.read(mByte);

		} catch (FileNotFoundException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}finally{

				try {

					if(null!=fis)

					fis.close();

				} catch (IOException e) {

					e.printStackTrace();

				}

		}

		return new String(mByte).toString();

	}
	
	/**
     * Save Bitmap to a file.保存图片到SD卡。
     * 
     * @param bitmap
     * @param file
     * @return error message if the saving is failed. null if the saving is
     *         successful.
     * @throws IOException
     */
    public static void saveBitmapToFile(Bitmap bitmap, String _file)
            throws IOException {
        BufferedOutputStream os = null;
        try {
            File file = new File(_file);
            // String _filePath_file.replace(File.separatorChar +
            // file.getName(), "");
            int end = _file.lastIndexOf(File.separator);
            String _filePath = _file.substring(0, end);
            File filePath = new File(_filePath);
            if (!filePath.exists()) {
                filePath.mkdirs();
            }
            file.createNewFile();
            os = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    Log.e("Error", e.getMessage(), e);
                }
            }
        }
    }
    


}
