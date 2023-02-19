package org.example;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DBMSTest {

    @Test
    void testConnection() throws IOException {
        var builder = new SqlSessionFactoryBuilder();
        var factory = builder.build(Resources.getResourceAsStream("mybatis-config.xml"), "MySQL");

        try (var session = factory.openSession()) {
            session.insert("car.insertCar",
                    Map.of("carNum", 1003,
                            "brand", "BYD",
                            "guidePrice", 30.0,
                            "produceTime", "2012-12-12",
                            "carType", "新能源"));
            session.commit();
        }
    }

    @Test
    void testParsingXML() throws DocumentException {
        var reader = new SAXReader();
        var document = reader.read(ClassLoader.getSystemResourceAsStream("mybatis-config.xml"));
        var xPath = "configuration/environments";
        Element ele = (Element) document.selectSingleNode(xPath);
        String defaultEnvironmentId = ele.attributeValue("default");
        if (defaultEnvironmentId!=null) {
            xPath = xPath + "/environment[@id='"+defaultEnvironmentId+"']";
            System.out.println(xPath);
            Element defaultEnv = (Element) document.selectSingleNode(xPath);
            assertEquals(defaultEnvironmentId, defaultEnv.attributeValue("id"));
        }
    }
}
