package com.ece.parkisonditributed.back;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class SpringJdbcConfig {

    /*private static String driverclassname;
    @Value("${jdbc.driverclassname}")
    public void setdriverclassname(String driverclassname) { SpringJdbcConfig.driverclassname = driverclassname;}

    private static String Url;
    @Value("${jdbc.Url}")
    public void setUrl(String Url) { SpringJdbcConfig.Url = Url;}

    private static String Username;
    @Value("${jdbc.Username}")
    public void setUsername(String Username) { SpringJdbcConfig.Username = Username;}

    private static String Password;
    @Value("${jdbc.Password}")
    public void setPassword(String Password) { SpringJdbcConfig.Password = Password;}*/


    /*public DataSource mysqlDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverclassname);
        dataSource.setUrl(Url);
        dataSource.setUsername(Username);
        dataSource.setPassword(Password);

        return dataSource;
    }*/

    public DataSource mysqlDataSource(String driver, String target, String usr, String pass) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(target);
        dataSource.setUsername(usr);
        dataSource.setPassword(pass);

        return dataSource;
    }

}
