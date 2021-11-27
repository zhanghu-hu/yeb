package com.zh.server.config.database;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @description 多数据源配置（gis）
 * @author ZH
 * @date 2021-8-15
 */
@Configuration
@MapperScan(basePackages = "com.zh.server.mapper.gis",sqlSessionFactoryRef = "sqlSessionFactory")
public class GISDatabase {

    @Bean("gisDatasource")
    @ConfigurationProperties(prefix = "spring.datasource.db2")
    public DataSource getGISDatasource(){
        return DataSourceBuilder.create().build();
    }

    @Bean("sqlSessionFactory")//与MapperScan配置项要对应
    public SqlSessionFactory sqlSessionFactory(@Qualifier("gisDatasource")DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean =new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/gis/*.xml"));
        return bean.getObject();
    }

    @Bean("sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory")SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
