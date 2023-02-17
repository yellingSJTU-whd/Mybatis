package org.example;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

public class DBMSTest {

    @Test
    void test_connection() throws IOException {
        var builder = new SqlSessionFactoryBuilder();
        var factory = builder.build(Resources.getResourceAsStream("mybatis-config.xml"), "MySQL");

        try (var session = factory.openSession();) {
            session.insert("insertCar",
                    Map.of("carNum", 1003,
                            "brand", "BYD",
                            "guidePrice", 30.0,
                            "produceTime", "2012-12-12",
                            "carType", "新能源"));
            session.commit();
        }
    }
}
