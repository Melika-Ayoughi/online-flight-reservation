package controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by Ali_Iman on 3/6/17.
 */
public class ResultsServletTest {
    ResultsServlet resultsServlet;
    HttpServletRequest httpServletRequest;
    HttpServletResponse httpServletResponse;

    @Before
    public void setUp() throws Exception {
        httpServletRequest = new HttpServletRequest() {
            public String getAuthType() {
                return null;
            }

            public Cookie[] getCookies() {
                return new Cookie[0];
            }

            public long getDateHeader(String s) {
                return 0;
            }

            public String getHeader(String s) {
                return null;
            }

            public Enumeration<String> getHeaders(String s) {
                return null;
            }

            public Enumeration<String> getHeaderNames() {
                return null;
            }

            public int getIntHeader(String s) {
                return 0;
            }

            public String getMethod() {
                return null;
            }

            public String getPathInfo() {
                return null;
            }

            public String getPathTranslated() {
                return null;
            }

            public String getContextPath() {
                return null;
            }

            public String getQueryString() {
                return null;
            }

            public String getRemoteUser() {
                return null;
            }

            public boolean isUserInRole(String s) {
                return false;
            }

            public Principal getUserPrincipal() {
                return null;
            }

            public String getRequestedSessionId() {
                return null;
            }

            public String getRequestURI() {
                return null;
            }

            public StringBuffer getRequestURL() {
                return null;
            }

            public String getServletPath() {
                return null;
            }

            public HttpSession getSession(boolean b) {
                return null;
            }

            public HttpSession getSession() {
                return null;
            }

            public String changeSessionId() {
                return null;
            }

            public boolean isRequestedSessionIdValid() {
                return false;
            }

            public boolean isRequestedSessionIdFromCookie() {
                return false;
            }

            public boolean isRequestedSessionIdFromURL() {
                return false;
            }

            public boolean isRequestedSessionIdFromUrl() {
                return false;
            }

            public boolean authenticate(HttpServletResponse httpServletResponse) throws IOException, ServletException {
                return false;
            }

            public void login(String s, String s1) throws ServletException {

            }

            public void logout() throws ServletException {

            }

            public Collection<Part> getParts() throws IOException, ServletException {
                return null;
            }

            public Part getPart(String s) throws IOException, ServletException {
                return null;
            }

            public <T extends HttpUpgradeHandler> T upgrade(Class<T> aClass) throws IOException, ServletException {
                return null;
            }

            public Object getAttribute(String s) {
                return null;
            }

            public Enumeration<String> getAttributeNames() {
                return null;
            }

            public String getCharacterEncoding() {
                return null;
            }

            public void setCharacterEncoding(String s) throws UnsupportedEncodingException {

            }

            public int getContentLength() {
                return 0;
            }

            public long getContentLengthLong() {
                return 0;
            }

            public String getContentType() {
                return null;
            }

            public ServletInputStream getInputStream() throws IOException {
                return null;
            }

            public String getParameter(String s) {
                return null;
            }

            public Enumeration<String> getParameterNames() {
                return null;
            }

            public String[] getParameterValues(String s) {
                return new String[0];
            }

            public Map<String, String[]> getParameterMap() {
                return null;
            }

            public String getProtocol() {
                return null;
            }

            public String getScheme() {
                return null;
            }

            public String getServerName() {
                return null;
            }

            public int getServerPort() {
                return 0;
            }

            public BufferedReader getReader() throws IOException {
                return null;
            }

            public String getRemoteAddr() {
                return null;
            }

            public String getRemoteHost() {
                return null;
            }

            public void setAttribute(String s, Object o) {

            }

            public void removeAttribute(String s) {

            }

            public Locale getLocale() {
                return null;
            }

            public Enumeration<Locale> getLocales() {
                return null;
            }

            public boolean isSecure() {
                return false;
            }

            public RequestDispatcher getRequestDispatcher(String s) {
                return null;
            }

            public String getRealPath(String s) {
                return null;
            }

            public int getRemotePort() {
                return 0;
            }

            public String getLocalName() {
                return null;
            }

            public String getLocalAddr() {
                return null;
            }

            public int getLocalPort() {
                return 0;
            }

            public ServletContext getServletContext() {
                return null;
            }

            public AsyncContext startAsync() throws IllegalStateException {
                return null;
            }

            public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
                return null;
            }

            public boolean isAsyncStarted() {
                return false;
            }

            public boolean isAsyncSupported() {
                return false;
            }

            public AsyncContext getAsyncContext() {
                return null;
            }

            public DispatcherType getDispatcherType() {
                return null;
            }
        };
        httpServletResponse = new HttpServletResponse() {
            public void addCookie(Cookie cookie) {

            }

            public boolean containsHeader(String s) {
                return false;
            }

            public String encodeURL(String s) {
                return null;
            }

            public String encodeRedirectURL(String s) {
                return null;
            }

            public String encodeUrl(String s) {
                return null;
            }

            public String encodeRedirectUrl(String s) {
                return null;
            }

            public void sendError(int i, String s) throws IOException {

            }

            public void sendError(int i) throws IOException {

            }

            public void sendRedirect(String s) throws IOException {

            }

            public void setDateHeader(String s, long l) {

            }

            public void addDateHeader(String s, long l) {

            }

            public void setHeader(String s, String s1) {

            }

            public void addHeader(String s, String s1) {

            }

            public void setIntHeader(String s, int i) {

            }

            public void addIntHeader(String s, int i) {

            }

            public void setStatus(int i) {

            }

            public void setStatus(int i, String s) {

            }

            public int getStatus() {
                return 0;
            }

            public String getHeader(String s) {
                return null;
            }

            public Collection<String> getHeaders(String s) {
                return null;
            }

            public Collection<String> getHeaderNames() {
                return null;
            }

            public String getCharacterEncoding() {
                return null;
            }

            public String getContentType() {
                return null;
            }

            public ServletOutputStream getOutputStream() throws IOException {
                return null;
            }

            public PrintWriter getWriter() throws IOException {
                return null;
            }

            public void setCharacterEncoding(String s) {

            }

            public void setContentLength(int i) {

            }

            public void setContentLengthLong(long l) {

            }

            public void setContentType(String s) {

            }

            public void setBufferSize(int i) {

            }

            public int getBufferSize() {
                return 0;
            }

            public void flushBuffer() throws IOException {

            }

            public void resetBuffer() {

            }

            public boolean isCommitted() {
                return false;
            }

            public void reset() {

            }

            public void setLocale(Locale locale) {

            }

            public Locale getLocale() {
                return null;
            }
        };
        resultsServlet = new ResultsServlet();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void doPost() throws Exception {
        httpServletRequest.setAttribute("src","THR");
        httpServletRequest.setAttribute("dest","MHD");
        httpServletRequest.setAttribute("departureDate","05Feb");
        httpServletRequest.setAttribute("returnDate","05Feb");
        httpServletRequest.setAttribute("adult-count","1");
        httpServletRequest.setAttribute("child-count","0");
        httpServletRequest.setAttribute("infant-count","0");
        resultsServlet.doPost(httpServletRequest, httpServletResponse);
        System.out.println(httpServletResponse);
    }

}