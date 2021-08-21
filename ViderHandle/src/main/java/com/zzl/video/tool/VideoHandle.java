package com.zzl.video.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 基于ffmpeg的视频处理工具类
 * @param desPath
 * @param videoName
 * @return
 */
public class VideoHandle {
	private static final String M3U8_EXT_X_ENDLIST = "#EXT-X-ENDLIST";
	private String ffmpegPath;
	
	public VideoHandle(String ffmpegPath) {
		this.ffmpegPath = ffmpegPath;
	}
	
	
	 
	 /**
	  * 生成 m3u8文件 耗时操作
	  * @param m3u8FolderPath 放置 m3u8文件的路径
	  * @param videoPath 原视频路径包括名字
	  * @param m3u8Name 此名字用处：1 作为 ts文件的名字前缀（ 不包含扩展名）；
	  * 						 2 作为m3u8文件的名字例子（ 包含扩展名）
	  * 	例子： zyf.m3u8 将生成1个名字为zyf.m3u8的文件和一系列zyf_xxxxx.ts的
	  * 	视频文件，xxxxx按序生成
	  * @return
	  */
	 public String generateM3U8(String m3u8FolderPath, String videoPath, String m3u8Name){
		 createPath(m3u8FolderPath);
		 /*
	        ffmpeg 
	        	-i  
	        	lucene.mp4
	        	-hls_time 10
	        	-hls_list_size 0
	        	-hls_segment_filename 
	        	./123/lucene_%05d.ts 
	        	./123/lucene.m3u8
	     */
	        List<String> commend = new ArrayList<String>();
	        commend.add(ffmpegPath);
	        commend.add("-i");
	        commend.add(videoPath);
	        commend.add("-hls_time");
	        commend.add("10");
	        commend.add("-hls_list_size");
	        commend.add("0");
	        commend.add("-hls_segment_filename");
	        commend.add(m3u8FolderPath + File.separator + m3u8Name.substring(0,m3u8Name.lastIndexOf(".")) + "_%05d.ts");
	        commend.add(m3u8FolderPath + File.separator + m3u8Name );
	        String outstring = null;
	        try {
	            ProcessBuilder builder = new ProcessBuilder();
	            builder.command(commend);
	            //将标准输入流和错误输入流合并，通过标准输入流程读取信息
	            builder.redirectErrorStream(true);
	            Process p = builder.start();
	            outstring = waitFor(p);
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	        //通过查看m3u8列表判断是否成功
	        List<String> tsList = getTsList(m3u8FolderPath, m3u8Name);
	        if(tsList == null){
	            return outstring;
	        }
	        return "success";
	    }
	
	
	 /**
	  * 将视频转换成MP4格式
	  * *******耗时操作
	  * @param sourcePath 视频源文件
	  * @param videoName 目标视频文件名路径
	  * @return
	  */
	 public String generateMp4(String sourcePath, String videoName){
        /*
        ffmpeg.exe -i  video.avi -c:v libx264 -s 1280x720 -pix_fmt yuv420p -b:a 63k -b:v 753k -r 18 .\lucene.mp4
         */
        List<String> commend = new ArrayList<String>();
        commend.add(ffmpegPath);
        commend.add("-i");
        commend.add(sourcePath);
        commend.add("-c:v");
        commend.add("libx264");
        commend.add("-y");//覆盖输出文件
        commend.add("-s");
        commend.add(VideoContants.RESOLUTION_1280x720);
        commend.add("-pix_fmt");
        commend.add("yuv420p");
        commend.add("-b:a");
        commend.add("63k");
        commend.add("-b:v");
        commend.add("753k");
        commend.add("-r");
        commend.add("18");
        commend.add(videoName);
        String outstring = null;
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commend);
            //将标准输入流和错误输入流合并，通过标准输入流程读取信息
            builder.redirectErrorStream(true);
            Process p = builder.start();
            outstring = waitFor(p);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Boolean videoDur = this.checkVideDuration(sourcePath, videoName);
        if(!videoDur){
            return outstring;
        }else{
            return "success";
        }
    }
	
	/**
	 * 检查视频时间是否一致
	 * @param video1
	 * @param video2
	 * @param ffmpegPath
	 * @return
	 */
    public Boolean checkVideDuration(String video1,String video2) {
        String video1Duration = getVideoTime(video1, ffmpegPath);
        
        String video2Duration = getVideoTime(video2, ffmpegPath);
        if(Objects.isNull(video1Duration) ||
        		video1Duration.trim().length() == 0 ||
        		Objects.isNull(video2Duration) ||
        		video2Duration.trim().length() == 0){
            return false;
        }
        if(video2Duration.equals(video1Duration)){
            return true;
        }
        return false;
    }
    
    /**
     * 
     *   desc 获取视频时间
     * @param video_path
     * @param ffmpegPath
     * @return
     */
    public String getVideoTime(String video_path, String ffmpegPath) {
        /*
        ffmpeg -i  lucene.mp4
         */
        List<String> commend = new ArrayList<String>();
        commend.add(ffmpegPath);
        commend.add("-i");
        commend.add(video_path);
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commend);
            //将标准输入流和错误输入流合并，通过标准输入流程读取信息
            builder.redirectErrorStream(true);
            Process p = builder.start();
            String outstring = waitFor(p);
            System.out.println(outstring);
            int start = outstring.trim().indexOf("Duration: ");
            if(start>=0){
                int end = outstring.trim().indexOf(", start:");
                if(end>=0){
                    String time = outstring.substring(start+10,end);
                    if(time!=null && !time.equals("")){
                        return time.trim();
                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    private void createPath(String m3u8Path){
        File m3u8dir = new File(m3u8Path);
        if(!m3u8dir.exists()){
            m3u8dir.mkdirs();
        }
	  }
	  	
	  	/**
	  	 *      检查视频处理是否完成
	  	 * @param M3U8FolderPath 
	  	 * @param M3U8Name
	  	 * @return
	  	 */
	    public List<String> getTsList(String M3U8FolderPath, String M3U8Name) {
	        List<String> fileList = new ArrayList<>();
	        List<String> tsList = new ArrayList<>();
	        String m3u8file_path =M3U8FolderPath + File.separator + M3U8Name;
	        String str = null;
	        
	        try( BufferedReader br = new BufferedReader(new FileReader(m3u8file_path)) ){
	            while ((str = br.readLine()) != null) {
	                if(str.endsWith(".ts")){
	                    tsList.add(str);
	                }
	                if (str.contains(M3U8_EXT_X_ENDLIST)) {
			            fileList.addAll(tsList);
			            return fileList;
			        }
	            }
	        }catch (Exception e) {
	        	e.printStackTrace();
			}
	        return null;
	    }

     @SuppressWarnings("static-access")
	private String waitFor(Process p) throws Exception {
        int exitValue = -1;
        try(InputStream  in = p.getInputStream();
                InputStream error = p.getErrorStream()){
            StringBuffer outputString = new StringBuffer();
            boolean finished = false;
            int maxRetry = 600;//每次休眠1秒，最长执行时间10分种
            int retry = 0;
            while (!finished) {
                if (retry > maxRetry) {
                    return "error";
                }
                try {
                    while (in.available() > 0) {
                        Character c = new Character((char) in.read());
                        outputString.append(c);
                    }
                    while (error.available() > 0) {
                        Character c = new Character((char) in.read());
                        outputString.append(c);
                    }
                    //进程未结束时调用exitValue将抛出异常
                    exitValue = p.exitValue();
                    finished = true;

                } catch (IllegalThreadStateException e) {
                    Thread.currentThread().sleep(1000);//休眠1秒
                    retry++;
                }
            }
            return outputString.toString();
        }catch (Exception e) {
        	throw new Exception("命令行出错");
		}
    }
}
