package com.agent.czb.core;

import lombok.Data;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/**
 * @author linkai
 * @since 2017/8/25
 */
public class DataBaseCheckTest {

    private Connection conn;

    @Before
    public void before() throws SQLException, ClassNotFoundException {
        String driverClassName = "org.mariadb.jdbc.Driver";
        String url = "jdbc:mariadb://139.196.174.197:3306/czb";
        String username = "root";
        String password = "Tingba1968";
        this.conn = MetaDataUtils.getConnection(driverClassName, url, username, password);
    }

    @Test
    public void name() throws Exception {
        File file = new File("/Users/linkai/workspaces/idea-projects/idea-czb/czb-itr/stopbar-itr-core/src/main/resources/mapper");
        List<File> files = new ArrayList<>();
        findFile(files, file);
        for (File f : files) {
            DataModel baenName = getBaenName(f);
            System.out.println("--------------------- " + baenName.getTableName() + "    " +baenName.getC().getName() + " ---------------------");
            Set<String> tableFields = getTableFields(baenName.getTableName());
            for (String field : baenName.getFields()) {
                if (!tableFields.contains(field)) {
                    System.out.println(field);
                }
            }
        }
    }

    private Set<String> getTableFields(String tableName) throws SQLException {
        Set<String> temp = new HashSet<>();
        List<Map<String, String>> columnsMetaData = MetaDataUtils.getColumnsMetaData(conn, tableName);
        if (columnsMetaData.isEmpty()) {
            System.err.println("==========================asdfasdfasfsadsa");
        }
        for (Map<String, String> columnsMetaDatum : columnsMetaData) {
            temp.add(columnsMetaDatum.get(MetaDataUtils.COLUMN_NAME));
        }
        return temp;
    }

    private DataModel getBaenName(File file) throws IOException, ParserConfigurationException, SAXException, ClassNotFoundException {
        DataModel dataModel = new DataModel();
        String tableName = tableName(file.getName());
        String type = null;
        String xml = FileUtils.readFileToString(file);
        Element element = XmlUtil.xmlToElement(xml);
        List<String> fields = new ArrayList<>();

        List<Node> nodes = XmlUtil.elementListByPath(element, "/mapper/resultMap");
        for (Node node : nodes) {
            type = XmlUtil.xmlToAttribute((Element) node, "type");
            NodeList childNodes = node.getChildNodes();
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node item = childNodes.item(i);
                if (item.getNodeType() == Node.ELEMENT_NODE) {
                    String column = XmlUtil.xmlToAttribute(item, "column");
                    fields.add(column);
                }
            }
            break;
        }
        dataModel.setTableName(tableName);
        dataModel.setC(Class.forName(type));
        dataModel.setFields(fields);

        return dataModel;
    }

    private String tableName(String s) {
        return Underline2Camel.camel2Underline(s.replace("Mapper.xml", ""));
    }

    private void findFile(List<File> files, File directory) {
        if (directory.isDirectory()) {
            File[] files1 = directory.listFiles();
            if (files1 != null) {
                for (File file : files1) {
                    findFile(files, file);
                }
            }
        } else {
            files.add(directory);
        }
    }

    @Data
    public static class DataModel {
        private String tableName;
        private Class<?> c;
        private List<String> fields;
    }
}