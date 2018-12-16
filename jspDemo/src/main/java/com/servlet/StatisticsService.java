package com.servlet;


import com.bean.Statistics;
import com.dao.StatisticsDao;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

public class StatisticsService extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        StatisticsDao staDao = new StatisticsDao();
        List<Statistics> statisticList =  staDao.query();
        resp.setContentType("text/html;charset=utf-8");
        Collections.sort(statisticList);
        JSONArray jsonArray = JSONArray.fromObject(statisticList);
        System.out.println(jsonArray.toString());
        PrintWriter writer = resp.getWriter();
        //why following statement is necessary? please read source code!!
        writer.println(jsonArray);
        writer.flush();
        //关闭输出流
        writer.close();
    }
}
