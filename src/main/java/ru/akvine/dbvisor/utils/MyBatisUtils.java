package ru.akvine.dbvisor.utils;

import lombok.experimental.UtilityClass;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@UtilityClass
public class MyBatisUtils {

    /**
     * Возвращает instance SqlSessionFactory с привязкой к реализации mybatis interface
     *
     * @param classPath путь до директории с .xml описанием myBatis
     * @return instance SqlSessionFactoryBean
     */
    public SqlSessionFactoryBean create(String classPath) {
        Asserts.isNotNull(classPath);

        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResource(classPath));
        return sqlSessionFactoryBean;
    }
}
