package com.mood.SqlSession;

import com.mood.config.Function;
import com.mood.config.MapperBean;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MoodSqlSessionFactory {

    //加载
    private static final ClassLoader classLoader = ClassLoader.getSystemClassLoader();

    //初始化
    public Connection bulider(String path) {
        try {
            InputStream stream = classLoader.getResourceAsStream(path);

            SAXReader reader = new SAXReader();
            Document document = reader.read(stream);
            Element root = document.getRootElement();
            return evalDataSource(root);
        } catch (DocumentException e) {
            throw new RuntimeException("error occured while evaling xml " + path);
        }

    }

    private Connection evalDataSource(Element node) {
        if (!"database".equals(node.getName())) {
            throw new RuntimeException("root should be <database>");
        }
        String driverClassName = null;
        String url = null;
        String username = null;
        String password = null;
        //获取属性节点
        for (Object item : node.elements("property")) {
            Element i = (Element) item;
            String value = getValue(i);
            String name = i.attributeValue("name");
            if (name == null || value == null) {
                throw new RuntimeException("[database]: <property> should contain name and value");
            }
            //赋值
            switch (name) {
                case "url":
                    url = value;
                    break;
                case "username":
                    username = value;
                    break;
                case "password":
                    password = value;
                    break;
                case "driverClassName":
                    driverClassName = value;
                    break;
                default:
                    throw new RuntimeException("[database]: <property> unknown name");
            }
        }

        Connection connection = null;
        try {
            Class.forName(driverClassName);
            //建立数据库链接
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    //获取property属性的值,如果有value值,则读取 没有设置value,则读取内容
    private String getValue(Element node) {
        return node.hasContent() ? node.getText() : node.attributeValue("value");
    }

    public MapperBean getXMLMapperBean(String path) {
        MapperBean bean = new MapperBean();
        InputStream resourceAsStream = classLoader.getResourceAsStream(path);
        SAXReader saxReader = new SAXReader();
        Document document = null;
        try {
            document = saxReader.read(resourceAsStream);
            Element rootElement = document.getRootElement();
            bean.setInterfaceName(rootElement.attributeValue("nameSpace").trim());
            List<Function> list = new ArrayList<Function>(); //用来存储方法的List
            for(Iterator rootIter = rootElement.elementIterator(); rootIter.hasNext();) {//遍历根节点下所有子节点
                Function fun = new Function();    //用来存储一条方法的信息
                Element e = (Element) rootIter.next();
                String sqltype = e.getName().trim();
                String funcName = e.attributeValue("id").trim();
                String sql = e.getText().trim();
                String resultType = e.attributeValue("resultType").trim();
                fun.setSqltype(sqltype);
                fun.setFuncName(funcName);
                Object newInstance=null;
                try {
                    newInstance = Class.forName(resultType).newInstance();
                } catch (InstantiationException e1) {
                    e1.printStackTrace();
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
                fun.setResultType(newInstance);
                fun.setSql(sql);
                list.add(fun);
            }
            bean.setList(list);
        } catch (DocumentException e) {
            e.printStackTrace();
        }


        return bean;
    }

    public static void main(String[] args) {
        MoodSqlSessionFactory myConfiguration = new MoodSqlSessionFactory();
       // Connection con = myConfiguration.bulider("config.xml");
        MapperBean xmlMapperBean = myConfiguration.getXMLMapperBean("UserMapper.xml");
        System.out.println(xmlMapperBean);
    }
}
