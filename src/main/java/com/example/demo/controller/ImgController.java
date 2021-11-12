package com.example.demo.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/img")
public class ImgController {
	
	private void reponseImgFromPath(HttpServletResponse response,String path,String imgname) throws FileNotFoundException, IOException
	{
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		response.setHeader("Pragma", "no-cache");
		response.setContentType("image/jpeg");
		
		String serverImgSource="usr/serverImg/goods/";
		// 获得的系统的根目录
		File fileParent = new File(File.separator);
		String photoName = imgname;
		File file = new File(fileParent, serverImgSource + photoName);
		
 
		BufferedImage bi = ImageIO.read(new FileInputStream(file));
		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(bi, "jpg", out);
		try {
			out.flush();
		} finally {
			out.close();
		}
	}
	
	@GetMapping("/public/goodsImg/{imgname}")
    public String goodsImg(@PathVariable String imgname,HttpServletRequest request, HttpServletResponse response) throws IOException {
    	reponseImgFromPath(response,"usr/serverImg/goods/",imgname);
		return null;
    }
	
	@GetMapping("/public/homeImg")
	public String homeImg(HttpServletResponse response) throws FileNotFoundException, IOException {
		reponseImgFromPath(response,"usr/serverImg/","homeImg.png");
		return null;
	}
}
