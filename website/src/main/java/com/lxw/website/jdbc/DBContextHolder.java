package com.lxw.website.jdbc;

import com.lxw.website.enums.DataTypeEnum;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class DBContextHolder {
    private static  final ThreadLocal<DataTypeEnum> context =new ThreadLocal<>();
    //多读数据源  从该处轮询
    //private static final AtomicInteger counter = new AtomicInteger(-1);


    public static void set(DataTypeEnum dbType) {
        context.set(dbType);
    }

    public static DataTypeEnum get() {
        return context.get();
    }

    public static void write() {
        set(DataTypeEnum.WRITE);
        log.info("切换到write!");
    }

    public static void read() {
        //  轮询
       /* int index = counter.getAndIncrement() % 2;
        if (counter.get() > 9999) {
            counter.set(-1);
        }
        if (index == 0) {
            set(DataTypeEnum.WRITE);
            System.out.println("切换到slave1");
        }else {
            set(DataTypeEnum.READ);
            System.out.println("切换到slave2");
        }*/
        set(DataTypeEnum.READ);
        log.info("切换到read!");
    }

}
