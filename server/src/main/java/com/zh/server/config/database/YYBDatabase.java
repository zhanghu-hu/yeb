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
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @description 多数据源配置YYB
 * @author ZH
 * @date 2021-8-13
 */
@Configuration
@MapperScan(basePackages = "com.zh.server.Mapper.yyb",sqlSessionFactoryRef = "yybSqlSessionFactory")
public class YYBDatabase {

    @Primary //必加项  告诉spring默认的数据源
    @Bean("yybDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.db1") //读取application.yml中的配置参数映射成为一个对象
    public DataSource getYYBDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean("yybSqlSessionFactory")//与MapperScan配置项要对应
    public SqlSessionFactory yybSqlSessionFactory(@Qualifier("yybDataSource") DataSource dataSource) throws Exception{
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        //mapper的xml形式文件必须要配置，不然报错：no statement（这种错误也有可能是mapper的xml中，namespace与项目的路径不一致导致的）
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/yyb/*.xml"));
        return bean.getObject();
    }

    @Primary
    @Bean("yybSqlSessionTemplate")
    public SqlSessionTemplate yybSqlSessionTemplate(@Qualifier("yybSqlSessionFactory")SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
