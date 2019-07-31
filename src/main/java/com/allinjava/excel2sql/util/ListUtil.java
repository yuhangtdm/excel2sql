package com.allinjava.excel2sql.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author za-yuhang   2019/7/31 17:22
 */
public class ListUtil {

    /**
     * 判断list中有无重复数据
     * @return true 有  false 无
     */
    public static boolean  containRepetition(List<String> excelHeadList) {
        Set<String> strings = new HashSet<>(excelHeadList);
        if (strings.size()==excelHeadList.size()){
            return false;
        }
        return true;
    }


    /**
     * 判断两个list的元素是否全部一致
     */
    public static boolean compareList(List<?> list1, List<?> list2){
        boolean f1 = list1.stream().allMatch(s -> {
            if (!list2.contains(s)){
                return false;
            }
            return true;
        });

        boolean f2 = list2.stream().allMatch(s -> {
            if (!list1.contains(s)){
                return false;
            }
            return true;
        });

        return f1 && f2;
    }

}
