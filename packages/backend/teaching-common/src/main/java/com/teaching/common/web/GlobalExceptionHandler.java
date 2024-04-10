package com.teaching.common.web;

import com.alibaba.fastjson.JSONObject;
import com.teaching.common.core.CommonCode;
import com.teaching.common.core.ICodeMSG;
import com.teaching.common.core.RequestContextSession;
import com.teaching.common.core.RequestContextThreadHolder;
import com.teaching.common.core.ResultReply;
import com.teaching.common.exception.ServiceException;
import com.teaching.common.helper.BeanHelper;
import com.teaching.common.helper.BytesHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Objects;

/**
 * @Description：
 * @Author：sacher
 * @Create：2024/4/9 20:24
 **/
public class GlobalExceptionHandler implements HandlerExceptionResolver {
    private static final Logger LOG = LoggerFactory.getLogger(com.teaching.common.exception.GlobalExceptionHandler.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if(!(ex instanceof ServiceException)){
            LOG.error(ex.getMessage(),ex);
        }
        ModelAndView modelAndView = new ModelAndView();
        MappingJackson2JsonView fastJsonJsonView = new MappingJackson2JsonView();
        fastJsonJsonView.setAttributesMap(BeanHelper.bean2Map(getErrorResult(ex)));
        modelAndView.setView(fastJsonJsonView);
        return modelAndView;
    }

    private static ResultReply<Void> getErrorResult(Exception ex) {
        ResultReply<Void> resultReply;
        if(ex instanceof ServiceException e){
            resultReply = ResultReply.onFail(e.getCode(),e.getMsg());
        } else if(ex instanceof NoResourceFoundException){
            resultReply = ResultReply.onFail(CommonCode.NOT_FOUND);
        }else if(ex instanceof HttpRequestMethodNotSupportedException e){
            final String method = e.getMethod();
            resultReply = ResultReply.onFail(ICodeMSG.create(405, String.format("接口不支持%s请求方式", method)));
        }else if(ex instanceof HttpMediaTypeNotSupportedException e){
            final MediaType media = e.getContentType();
            List<MediaType> types = e.getSupportedMediaTypes();
            resultReply = ResultReply.onFail((ICodeMSG.create(415, String.format("接口不支持 Content-Type: [%s] ，请使用 %s", media, types))));
        }else if(ex instanceof MethodArgumentNotValidException e){
            StringBuilder sb = new StringBuilder();
            List<ObjectError> errors = e.getBindingResult().getAllErrors();
            for (ObjectError oe : errors) {
                sb.append(String.format(" | %s", oe.getDefaultMessage()));
            }
            resultReply = ResultReply.onFail((ICodeMSG.create(402, sb.substring(3))));
        }else if(ex instanceof BindException e){
            String message = e.getAllErrors().get(0).getDefaultMessage();
            resultReply = ResultReply.onFail((ICodeMSG.create(402, message)));
        }else if(ex instanceof HttpMessageNotReadableException){
            resultReply = ResultReply.onFail(ICodeMSG.create(419, "请求BODY体内容缺失"));
        }else {
            resultReply = ResultReply.onFail();
        }
        RequestContextSession session = RequestContextThreadHolder.get().getSession();
        if(Objects.nonNull(session)){
            session.responseBody = JSONObject.toJSONString(resultReply);
        }
        return resultReply;
    }
    public static void responseErrorWrite(HttpServletResponse response, Exception ex) throws IOException {
        if(!(ex instanceof ServiceException)){
            LOG.error(ex.getMessage(),ex);
        }
        ResultReply<Void> resultReply = getErrorResult(ex);
        OutputStream os = null;
        try {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            os = response.getOutputStream();
            os.write(JSONObject.toJSONBytes(resultReply));
        }finally {
            BytesHelper.close(os);
        }
    }
}
