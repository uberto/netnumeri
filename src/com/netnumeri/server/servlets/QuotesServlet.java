package com.netnumeri.server.servlets;

import com.netnumeri.shared.finance.utils.YahooUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class QuotesServlet extends HttpServlet {


    public QuotesServlet() {
        super();
    }

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        export(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        export(request, response);
    }

    private void export(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String ticker = request.getParameter("name");

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -1);

        String s = null;
        try {
            s = YahooUtils.proxyYahooData(ticker, cal.getTime(), new Date());
        } catch (Exception e) {
        }

        ServletOutputStream sos = response.getOutputStream();
        sos.print(s);

    }

}
