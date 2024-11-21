package utils;

import java.io.PrintWriter;

import jakarta.servlet.http.HttpServletResponse;

public class JSFunction {
	public static void alertLocation(HttpServletResponse resp, String msg, String url) {
		try {
			resp.setContentType("text/html;charset=UTF-8");
			PrintWriter writer = resp.getWriter();
			//경고창을 띄우고 특정페이지로 이동할 수 있는 JS코드 작성
			String script = ""
					+"<script>"
					+"    alert('" + msg + "');"
					+ "         location.href='" + url + "';"
					+"</script>";
			writer.print(script);
		}catch(Exception e) {}
	}
}
