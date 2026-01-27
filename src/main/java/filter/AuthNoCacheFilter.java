package filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/*")
public class AuthNoCacheFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        // contextPathを除いたパス（例：/MyApp/LoginServlet -> /LoginServlet）
        String path = request.getRequestURI().substring(request.getContextPath().length());

        // ===== 1) 静的ファイルは除外（CSS/JS/画像等）=====
        if (path.startsWith("/css/") ||
            path.startsWith("/js/") ||
            path.startsWith("/images/") ||
            path.startsWith("/uploads/") ||
            path.startsWith("/favicon")) {
            chain.doFilter(req, res);
            return;
        }

        // ===== 2) 認証不要（ログイン前でもOK）=====
        // ここが抜けるとログインできなくなる
        boolean isPublic =
                path.equals("/LoginServlet") ||
                path.equals("/CreateServlet") ||
                path.equals("/LogoutServlet") ||
                path.startsWith("/view/auth/"); // login.jsp / create.jsp など

        if (isPublic) {
            chain.doFilter(req, res);
            return;
        }

        // ===== 3) ここから先はログイン必須 =====
        // 戻る対策（キャッシュ禁止）
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        HttpSession session = request.getSession(false);
        boolean loggedIn = (session != null && session.getAttribute("loginUserId") != null);

        if (!loggedIn) {
            response.sendRedirect(request.getContextPath() + "/view/auth/login.jsp");
            return;
        }

        chain.doFilter(req, res);
    }
}
