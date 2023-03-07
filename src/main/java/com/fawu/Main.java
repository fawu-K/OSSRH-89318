package com.fawu;


import com.kang.EnableAutoDB;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 在springBoot项目的启动类上添加{@link EnableAutoDB}注解，即可启动该模块
 * {@link EnableAutoDB}注解有两个参数，意义很明显。
 * {@link EnableAutoDB.tableToEntity}表示通过数据表生成实体类。默认false，不开启
 * {@link EnableAutoDB.entityToTable}表示通过实体类生成数据表。默认true，开启
 *
 * @author K.faWu
 * @program lowcode-dome
 * @date 2023-02-20 09:20
 **/
@SpringBootApplication
@EnableAutoDB(tableToEntity = true)
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
