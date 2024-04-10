package com.teaching.common.exception;

import com.alibaba.fastjson.JSONObject;
import com.teaching.common.core.CommonCode;
import com.teaching.common.core.ICodeMSG;
import com.teaching.common.core.RequestContextSession;
import com.teaching.common.core.RequestContextThreadHolder;
import com.teaching.common.core.ResultReply;
import com.teaching.common.helper.BytesHelper;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Objects;

/**
 * @Description：
 * @Author：sacher
 * @Create：2023/5/5 11:30
 **/
@RestControllerAdvice
@Controller
@AutoConfigureBefore(ErrorMvcAutoConfiguration.class)
public class GlobalExceptionHandler implements ErrorController {
    public static final String ERROR_PATH = "/error";
    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @RequestMapping(ERROR_PATH)
    void errorNotfound(HttpServletResponse response) throws IOException {
        OutputStream os = null;
        try {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            os = response.getOutputStream();
            os.write(JSONObject.toJSONBytes(ResultReply.onFail(CommonCode.NOT_FOUND)));
        }finally {
            BytesHelper.close(os);
        }
    }

    @ExceptionHandler(Throwable.class)
    void errorThrowable(HttpServletResponse response, Throwable cause) throws IOException {
        responseErrorWrite(response, cause);
    }

    public static void responseErrorWrite(HttpServletResponse response, Throwable cause) throws IOException {
        if(!(cause.getCause() instanceof ServiceException)){
            LOG.error(cause.getMessage(),cause);
        }
        ResultReply<Void> resultReply = getErrorResult(cause.getCause());
        OutputStream os = null;
        try {
            RequestContextSession session = RequestContextThreadHolder.get().getSession();
            if(Objects.nonNull(session)){
                session.responseBody = JSONObject.toJSONString(resultReply);
            }
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            os = response.getOutputStream();
            os.write(JSONObject.toJSONBytes(resultReply));
        }finally {
            BytesHelper.close(os);
        }
    }

    public static ResultReply<Void> getErrorResult(Throwable cause) {
        ResultReply<Void> resultReply;
        if(cause instanceof ServiceException e){
            resultReply = ResultReply.onFail(e.getCode(),e.getMsg());
        }else {
            resultReply = ResultReply.onFail();
        }
        return resultReply;
    }
}
