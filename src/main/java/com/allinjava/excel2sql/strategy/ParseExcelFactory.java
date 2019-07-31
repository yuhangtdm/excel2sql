package com.allinjava.excel2sql.strategy;

import com.allinjava.excel2sql.strategy.impl.PoiExcelStrategy;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

/**
 * 工厂设计模式
 * @author yuhang   2019/7/27 10:32
 */
@Component
public class ParseExcelFactory implements InitializingBean {

    private static final Map<String, ParseExcel> PARSE_EXCEL_MAP = new HashMap(16);

    @Autowired
    private PoiExcelStrategy poiExcelStrategy;

    @Override
    public void afterPropertiesSet() {
        PARSE_EXCEL_MAP.put("poi",poiExcelStrategy);
    }

    /**
     * 获取实例 获取不到默认使用poi的策略
     */
    public ParseExcel getInstance(String strategy){
        return PARSE_EXCEL_MAP.getOrDefault(strategy,poiExcelStrategy);
    }
}
