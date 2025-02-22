package ru.akvine.dbvisor.services.impl;

import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.akvine.dbvisor.enums.DatabaseType;
import ru.akvine.dbvisor.exceptions.tech.InitMapperException;
import ru.akvine.dbvisor.services.MapperService;
import ru.akvine.dbvisor.services.mappers.CommonMapper;
import ru.akvine.dbvisor.utils.Asserts;
import ru.akvine.dbvisor.utils.MyBatisUtils;

import javax.sql.DataSource;

@Service
@RequiredArgsConstructor
public class MapperServiceImpl implements MapperService {
    @Value("${mappers.xml.files.classpath}")
    private String mappersXmlFilesClasspath;

    @Override
    public <T extends CommonMapper> T getMapper(DataSource dataSource, DatabaseType databaseType) {
        Asserts.isNotNull(dataSource);
        Asserts.isNotNull(databaseType);

        Class<? extends CommonMapper> mapperInterface = databaseType.getMapper();
        MapperFactoryBean<T> factoryBean = new MapperFactoryBean(mapperInterface);
        String classpath = String.join(
                "/",
                mappersXmlFilesClasspath,
                mapperInterface.getSimpleName() + ".xml");

        SqlSessionFactoryBean sqlSessionFactoryBean = MyBatisUtils.create(classpath);

        sqlSessionFactoryBean.setDataSource(dataSource);
        try {
            factoryBean.setSqlSessionFactory(sqlSessionFactoryBean.getObject());
            return factoryBean.getObject();
        } catch (Exception exception) {
            String errorMessage = String.format(
                    "Error while init mapper with interface = [%s] at classPath = [%s]. Message = [%s]",
                    mapperInterface.getSimpleName(),
                    classpath,
                    exception.getMessage()
            );
            throw new InitMapperException(errorMessage);
        }
    }
}
