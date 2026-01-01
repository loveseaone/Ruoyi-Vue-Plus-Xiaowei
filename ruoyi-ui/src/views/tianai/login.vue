<template>
  <div class="tianai-login">
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form">
      <h3 class="title">{{title}}</h3>
      <el-form-item prop="username">
        <el-input
          v-model="loginForm.username"
          type="text"
          auto-complete="off"
          placeholder="账号"
        >
          <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input
          v-model="loginForm.password"
          type="password"
          auto-complete="off"
          placeholder="密码"
          @keyup.enter.native="handleLogin"
        >
          <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>
      
      <!-- Tianai验证码区域 -->
      <el-form-item v-if="tianaiCaptchaEnabled">
        <div v-if="captchaType === 'SLIDER'" class="captcha-container">
          <div class="captcha-description">请拖动滑块完成验证</div>
          <div ref="sliderCaptcha" class="slider-captcha"></div>
        </div>
        <div v-else-if="captchaType === 'ROTATE'" class="captcha-container">
          <div class="captcha-description">请旋转图片完成验证</div>
          <div ref="rotateCaptcha" class="rotate-captcha"></div>
        </div>
        <div v-else class="captcha-container">
          <div class="captcha-description">请选择适当的验证方式</div>
        </div>
      </el-form-item>
      
      <el-checkbox v-model="loginForm.rememberMe" style="margin:0px 0px 25px 0px;">记住密码</el-checkbox>
      <el-form-item style="width:100%;">
        <el-button
          :loading="loading"
          size="medium"
          type="primary"
          style="width:100%;"
          @click.native.prevent="handleLogin"
        >
          <span v-if="!loading">登 录</span>
          <span v-else>登 录 中...</span>
        </el-button>
        <div style="float: right;" v-if="register">
          <router-link class="link-type" :to="'/register'">立即注册</router-link>
        </div>
      </el-form-item>
    </el-form>
    <!--  底部  -->
    <div class="el-login-footer">
      <span>Copyright © 2018-2025 ruoyi.vip All Rights Reserved.</span>
    </div>
  </div>
</template>

<script>
import { getCodeImg } from "@/api/login"
import { generateTianaiCaptcha, validateTianaiCaptcha } from "@/api/tianaiCaptcha"
import Cookies from "js-cookie"
import { encrypt, decrypt } from '@/utils/jsencrypt'

export default {
  name: "TianaiLogin",
  data() {
    return {
      title: process.env.VUE_APP_TITLE,
      loginForm: {
        username: "admin",
        password: "admin123",
        rememberMe: false,
        captchaId: "",
        track: ""
      },
      loginRules: {
        username: [
          { required: true, trigger: "blur", message: "请输入您的账号" }
        ],
        password: [
          { required: true, trigger: "blur", message: "请输入您的密码" }
        ]
      },
      loading: false,
      // Tianai验证码开关
      tianaiCaptchaEnabled: true,
      // 验证码类型
      captchaType: "SLIDER", // SLIDER, ROTATE
      // 注册开关
      register: false,
      redirect: undefined
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  created() {
    this.initTianaiCaptcha()
    this.getCookie()
  },
  methods: {
    // 初始化Tianai验证码
    initTianaiCaptcha() {
      if (!this.tianaiCaptchaEnabled) return
      
      generateTianaiCaptcha(this.captchaType).then(response => {
        const data = response.data
        this.loginForm.captchaId = data.id
        // TODO: 根据返回的数据渲染验证码
        this.renderCaptcha(data)
      }).catch(() => {
        this.tianaiCaptchaEnabled = false
      })
    },
    
    // 渲染验证码
    renderCaptcha(data) {
      // TODO: 实际渲染验证码逻辑
      console.log("渲染验证码:", data)
    },
    
    getCookie() {
      const username = Cookies.get("username")
      const password = Cookies.get("password")
      const rememberMe = Cookies.get('rememberMe')
      this.loginForm = {
        username: username === undefined ? this.loginForm.username : username,
        password: password === undefined ? this.loginForm.password : decrypt(password),
        rememberMe: rememberMe === undefined ? false : Boolean(rememberMe)
      }
    },
    
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          // 如果启用了Tianai验证码，需要先验证
          if (this.tianaiCaptchaEnabled) {
            if (!this.loginForm.captchaId || !this.loginForm.track) {
              this.$message.error("请先完成安全验证")
              return
            }
            
            // 可以在这里先调用后台验证接口确认验证结果
            // validateTianaiCaptcha(this.loginForm.captchaId, this.loginForm.track)
          }
          
          this.loading = true
          if (this.loginForm.rememberMe) {
            Cookies.set("username", this.loginForm.username, { expires: 30 })
            Cookies.set("password", encrypt(this.loginForm.password), { expires: 30 })
            Cookies.set('rememberMe', this.loginForm.rememberMe, { expires: 30 })
          } else {
            Cookies.remove("username")
            Cookies.remove("password")
            Cookies.remove('rememberMe')
          }
          
          this.$store.dispatch("Login", this.loginForm).then(() => {
            this.$router.push({ path: this.redirect || "/" }).catch(()=>{})
          }).catch(() => {
            this.loading = false
            // 验证失败后重新初始化验证码
            if (this.tianaiCaptchaEnabled) {
              this.initTianaiCaptcha()
            }
          })
        }
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss">
.tianai-login {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background-image: url("../assets/images/login-background.jpg");
  background-size: cover;
}

.title {
  margin: 0px auto 30px auto;
  text-align: center;
  color: #707070;
}

.login-form {
  border-radius: 6px;
  background: #ffffff;
  width: 400px;
  padding: 25px 25px 5px 25px;
  z-index: 1;
  
  .el-input {
    height: 38px;
    input {
      height: 38px;
    }
  }
  
  .input-icon {
    height: 39px;
    width: 14px;
    margin-left: 2px;
  }
}

.login-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}

.captcha-container {
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  padding: 10px;
  min-height: 100px;
  
  .captcha-description {
    text-align: center;
    margin-bottom: 10px;
    color: #606266;
  }
}

.el-login-footer {
  height: 40px;
  line-height: 40px;
  position: fixed;
  bottom: 0;
  width: 100%;
  text-align: center;
  color: #fff;
  font-family: Arial;
  font-size: 12px;
  letter-spacing: 1px;
}

.link-type {
  color: #409EFF;
  text-decoration: none;
}
</style>