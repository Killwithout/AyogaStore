package com.cjk.core.service.staticpage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 生成静态页实现类
 * @author cjk
 *
 */

public class StaticPageServiceImpl implements StaticPageService,ServletContextAware{
	
	private Configuration conf;

	public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {
		this.conf = freeMarkerConfigurer.getConfiguration();
	}

	
	/**
	 * 静态化方法  在上架的同时生成；
	 */
	public void productIndex(Map<String , Object> root , Integer id){
		//设置模板目录  以web-inf为相对路径   “web-inf/tfl/” 在配置文件
		//输出流  写出去（硬盘） UTF-8
		Writer out = null; 
		try {
			//获取模板  读进来（内存）UTF-8
			Template template = conf.getTemplate("productDetail.html");
			//获取html路径
			String path = getPath("/html/product/" + id + ".html");//278.html
			
			File f = new File(path);
			File parentFile = f.getParentFile();
			if(!parentFile.exists()){
				parentFile.mkdirs();
			}
			
			//输出流 
			out = new OutputStreamWriter(new FileOutputStream(f),"utf-8");
			//处理模板
			template.process(root, out);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	//利用上下文获取路径
	public String getPath(String name){
		
		return servletContext.getRealPath(name);
	}
	
	private ServletContext servletContext;
	public void setServletContext(ServletContext servletContext) {
		// TODO Auto-generated method stub
		this.servletContext=servletContext; 
	}



}
