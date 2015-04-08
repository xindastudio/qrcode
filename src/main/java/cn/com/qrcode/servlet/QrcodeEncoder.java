package cn.com.qrcode.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.qrcode.util.Parser;
import cn.com.qrcode.util.Utils;

/**
 * Servlet implementation class QrcodeEncoder
 */
public class QrcodeEncoder extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public QrcodeEncoder() {
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
		String content = Parser.parseString(param(request, "content"), "");
		String charset = Parser.parseString(param(request, "charset"), "UTF-8");
		Integer width = Parser.parseInteger(param(request, "width"), 200);
		Integer height = Parser.parseInteger(param(request, "height"), 200);
		Integer margin = Parser.parseInteger(param(request, "margin"), 1);
		String format = Parser.parseString(param(request, "format"), "png");
		if (content.length() < 1) {
			return;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Utils.encode(content, charset, width, height, margin, format, baos);
		byte[] b = baos.toByteArray();

		response.setContentType("image/png");
		response.setContentLength(b.length);
		response.setHeader("Cache-Control", "public");
		response.getOutputStream().write(b);
	}

	private String param(HttpServletRequest request, String name) {
		return request.getParameter(name);
	}

}
