package com.youngbook.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * Created by Lee on 2016/5/25.
 */

public class BaseDao {

    private JdbcTemplate jdbcTemplate;


    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void select() {
        Integer count = (Integer) this.jdbcTemplate.queryForObject("select count(*) from crm_customerpersonal", Integer.class);

        System.out.println(count);
    }

    public DataSource getDataSource() {
        return jdbcTemplate.getDataSource();
    }

}
