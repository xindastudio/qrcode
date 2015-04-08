package cn.com.qrcode.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.com.qrcode.util.Parser;
import cn.com.qrcode.util.Utils;

/**
 * Servlet implementation class QrcodeDecoder
 */
public class QrcodeDecoder extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public QrcodeDecoder() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String url = Parser.parseString(param(request, "url"), "");
		String charset = "UTF-8";
		InputStream input = null;
		if (url.length() > 0) {
			charset = Parser.parseString(param(request, "charset"), charset);
			input = new URL(url).openStream();
		} else {
			// Create a factory for disk-based file items
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// Configure a repository (to ensure a secure temp location is used)
			ServletContext servletContext = this.getServletConfig()
					.getServletContext();
			File repository = (File) servletContext
					.getAttribute("javax.servlet.context.tempdir");
			factory.setRepository(repository);
			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);
			// Parse the request
			List<FileItem> items;
			try {
				items = upload.parseRequest(request);
			} catch (FileUploadException e) {
				e.printStackTrace();
				items = new ArrayList<FileItem>();
			}
			String name = null;
			for (FileItem item : items) {
				if (item.isFormField()) {
					name = item.getFieldName();
					if ("charset".equals(name)) {
						charset = Parser.parseString(item.getString(), charset);
					}
				} else {
					input = item.getInputStream();
				}
			}
		}
		if (null == input) {
			return;
		}
		String content = Utils.decode(charset, input);
		response.setContentType("text/plain");
		response.setCharacterEncoding(charset);
		PrintWriter pw = response.getWriter();
		pw.write(content);
	}

	private String param(HttpServletRequest request, String name) {
		return request.getParameter(name);
	}

}
