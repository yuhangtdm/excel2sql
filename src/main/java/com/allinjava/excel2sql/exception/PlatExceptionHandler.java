package com.allinjava.excel2sql.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@ControllerAdvice
@Slf4j
public class PlatExceptionHandler  {

    /**
     * 代码中抛出的自定义业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public ModelAndView businessHandle(BusinessException ex, Model model){
        model.addAttribute("msg",ex.getMsg());
        return new ModelAndView("error");
    }

    /**
     * form提交实体的校验出现的异常
     */
    @ExceptionHandler(BindException.class)
    public ModelAndView bind(BindException e, Model model){
        log.error("bind exception",e);
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        StringBuffer sb=new StringBuffer("参数错误:");
        for (FieldError fieldError : fieldErrors) {
            sb.append(fieldError.getDefaultMessage()).append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        model.addAttribute("msg",sb.toString());
        return new ModelAndView("error");
    }


    @ExceptionHandler(Exception.class)
    public ModelAndView exception(Exception ex, Model model){
        log.error("exception,",ex);
        model.addAttribute("msg","服务器冒烟了,"+ex.getMessage());
        return new ModelAndView("error");
    }

}
