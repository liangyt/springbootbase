package com.liangyt.common.view;

import com.liangyt.common.rest.MessageReturn;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 描述：
 *
 * @author tony
 * @创建时间 2017-08-17 17:01
 */
@SuppressWarnings("unused")
public class BaseController {
    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }

    /**
     * 统一处理hibernate validator 不通过的数据  (BindException)
     *
     * @param ex 异常
     * @return 组装异常结果
     * {
     *     code: 0,
     *     message: "失败",
     *     data: {
     *         name: "姓名不能为空",
     *         ...
     *     }
     * }
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object validatorException(MethodArgumentNotValidException ex) {

        List<FieldError> errors = ex.getBindingResult().getFieldErrors();

        MessageReturn mr = MessageReturn.fail();
        for (FieldError fe : errors) {
            mr.add(fe.getField(), fe.getDefaultMessage());
        }

        return mr;
    }

    @ExceptionHandler({Exception.class})
    public Object exception(Exception ex) {
        return MessageReturn.fail("系统异常");
    }
}
