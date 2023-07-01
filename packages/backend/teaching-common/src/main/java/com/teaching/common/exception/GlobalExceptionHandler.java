package com.teaching.common.exception;

import com.alibaba.fastjson.JSONObject;
import com.teaching.common.core.CommonCode;
import com.teaching.common.core.ICodeMSG;
import com.teaching.common.core.RequestContextSession;
import com.teaching.common.core.RequestContextThreadHolder;
import com.teaching.common.core.ResultReply;
import com.teaching.common.helper.BytesHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.servlet.error.ErrorController;
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

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

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
        if(!(cause instanceof ServiceException)){
            LOG.error(cause.getMessage(),cause);
        }
        responseErrorWrite(response, cause);
    }

    private static void responseErrorWrite(HttpServletResponse response, Throwable cause) throws IOException {
        ResultReply<Void> resultReply = getErrorResult(cause);
        OutputStream os = null;
        try {
            RequestContextSession session = RequestContextThreadHolder.get().getSession();
            session.responseBody = JSONObject.toJSONString(resultReply);
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
        if(cause instanceof ServiceException){
            ServiceException e = (ServiceException) cause;
            resultReply = ResultReply.onFail(e.getCode(),e.getMsg());
        }else if(cause instanceof HttpRequestMethodNotSupportedException){
            HttpRequestMethodNotSupportedException e = (HttpRequestMethodNotSupportedException) cause;
            final String method = e.getMethod();
            resultReply = ResultReply.onFail(ICodeMSG.create(405, String.format("接口不支持%s请求方式", method)));
        }else if(cause instanceof HttpMediaTypeNotSupportedException){
            HttpMediaTypeNotSupportedException e = (HttpMediaTypeNotSupportedException) cause;
            final MediaType media = e.getContentType();
            List<MediaType> types = e.getSupportedMediaTypes();
            resultReply = ResultReply.onFail((ICodeMSG.create(415, String.format("接口不支持 Content-Type: [%s] ，请使用 %s", media, types))));
        }else if(cause instanceof MethodArgumentNotValidException){
            MethodArgumentNotValidException e = (MethodArgumentNotValidException) cause;
            StringBuilder sb = new StringBuilder();
            List<ObjectError> errors = e.getBindingResult().getAllErrors();
            for (ObjectError oe : errors) {
                sb.append(String.format(" | %s", oe.getDefaultMessage()));
            }
            resultReply = ResultReply.onFail((ICodeMSG.create(402, sb.substring(3))));
        }else if(cause instanceof BindException){
            BindException e = (BindException) cause;
            String message = e.getAllErrors().get(0).getDefaultMessage();
            resultReply = ResultReply.onFail((ICodeMSG.create(402, message)));
        }else if(cause instanceof HttpMessageNotReadableException){
            resultReply = ResultReply.onFail(ICodeMSG.create(419, "请求BODY体内容缺失"));
        }else {
            resultReply = ResultReply.onFail();
        }
        return resultReply;
    }

}
