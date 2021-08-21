package com.zzl.video.tool.test;

import com.zzl.video.tool.VideoHandle;

public class Test {
	public static void main(String[] args) {
		
		String ffmpegExePath = "D:\\BaiduNetdiskDownload\\19微服务项目学成在线\\day13 在线学习 HLS - 副本\\资料\\ffmpeg-20180227-fa0c9d6-win64-static\\ffmpeg-20180227-fa0c9d6-win64-static\\bin\\ffmpeg.exe";//ffmpeg的安装位置
		String videoPath1 = "D:\\BaiduNetdiskDownload\\19微服务项目学成在线\\day13 在线学习 HLS - 副本\\资料\\lucene.avi";
		String videoPath2 = "D:\\BaiduNetdiskDownload\\19微服务项目学成在线\\day13 在线学习 HLS - 副本\\资料\\lucene.avi";

		
		try {
			VideoHandle videoHandle =  new VideoHandle(ffmpegExePath);
//			System.out.println(videoHandle.checkVideDuration(videoPath1, videoPath2));
//			System.out.println(videoHandle.getVideoTime(videoPath1, ffmpegExePath));
			
//			String success = videoHandle.generateMp4(videoPath2,
			String M3U8FolderPath = "D:\\BaiduNetdiskDownload\\19微服务项目学成在线\\day13 在线学习 HLS - 副本\\资料\\hls";
			String M3U8Name = "lucene.m3u8";
//			System.out.println(success);
			
//			System.out.println(videoHandle.getTsList(M3U8FolderPath, M3U8Name));
			
			String m3u8FolderPath = "D:\\BaiduNetdiskDownload\\19微服务项目学成在线\\day13 在线学习 HLS - 副本\\资料\\ffmpeg-20180227-fa0c9d6-win64-static\\ffmpeg-20180227-fa0c9d6-win64-static\\bin\\123";
			String videoPath = "D:\\BaiduNetdiskDownload\\19微服务项目学成在线\\day13 在线学习 HLS - 副本\\资料\\ffmpeg-20180227-fa0c9d6-win64-static\\ffmpeg-20180227-fa0c9d6-win64-static\\bin\\lucene.mp4";
			String m3u8Name = "zyf.m3u8";
			System.out.println(videoHandle.generateM3U8(m3u8FolderPath, videoPath, m3u8Name));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
