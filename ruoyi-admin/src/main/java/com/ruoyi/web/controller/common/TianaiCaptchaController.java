package com.ruoyi.web.controller.common;

import cloud.tianai.captcha.application.ImageCaptchaApplication;
import cloud.tianai.captcha.application.vo.ImageCaptchaVO;
import cloud.tianai.captcha.common.response.ApiResponse;
import cloud.tianai.captcha.validator.common.model.dto.ImageCaptchaTrack;
import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.domain.AjaxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Tianai验证码控制器
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/tianai/captcha")
public class TianaiCaptchaController {

    private static final Logger logger = LoggerFactory.getLogger(TianaiCaptchaController.class);
    @Resource
    ImageCaptchaApplication imageCaptchaApplication;
    /**
     * 生成验证码
     */
    @GetMapping("/generate")
    @Anonymous
    public AjaxResult generateCaptcha(@RequestParam(required = false, defaultValue = "SLIDER") String type) {
        logger.info("请求生成Tianai验证码，类型: {}", type);
        ApiResponse<ImageCaptchaVO> captchaResponse = imageCaptchaApplication.generateCaptcha(type);
        ImageCaptchaVO captcha = captchaResponse.getData();
        return AjaxResult.success(captcha);
    }

    /**
     * 验证验证码
     */
    @PostMapping("/validate")
    @Anonymous
    public AjaxResult validateCaptcha(@RequestBody CaptchaTrackDto captchaTrackDto) {
        logger.info("验证Tianai验证码，ID: {}", captchaTrackDto.getId());
        ApiResponse<?> matching = imageCaptchaApplication.matching(captchaTrackDto.getId(), captchaTrackDto.getData());
        return AjaxResult.success(matching.isSuccess());
    }

    class CaptchaTrackDto {

        private String id;

        private ImageCaptchaTrack data;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public ImageCaptchaTrack getData() {
            return data;
        }

        public void setData(ImageCaptchaTrack data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "CaptchaTrackDto{" +
                    "id='" + id + '\'' +
                    ", data=" + data +
                    '}';
        }

    }
}