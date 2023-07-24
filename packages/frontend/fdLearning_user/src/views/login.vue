<template>
  <div class="login">
    <div class="login-content">
      <div class="login-left"></div>
      <div class="login-right">
        <div class="login-right-inner">
          <div class="login-title">复旦继续教育学院智慧云课堂平台</div>
          <div class="login-subtitle">账户密码登录</div>
          <el-form
            ref="ruleFormRef"
            :model="ruleForm"
            status-icon
            :rules="rules"
            class="logo-ruleForm"
          >
            <el-form-item prop="userName">
              <el-input v-model.number="ruleForm.userName" placeholder="账户" />
            </el-form-item>
            <el-form-item prop="password">
              <el-input
                placeholder="密码"
                v-model="ruleForm.password"
                type="password"
                autocomplete="off"
              />
            </el-form-item>

            <el-form-item>
              <div class="submit" @click="submit">登录</div>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, reactive } from "vue";
import { useRouter } from "vue-router";
import type { FormInstance, FormRules } from "element-plus";
export default defineComponent({
  name: "loginView",
  setup() {
    const router = useRouter();
    const ruleForm = reactive({
      userName: "",
      password: "",
    });
    const rules = reactive<FormRules<typeof ruleForm>>({
      userName: [
        {
          required: true,
          message: "Please input Activity name",
          trigger: "blur",
        },
        { min: 3, max: 5, message: "Length should be 3 to 5", trigger: "blur" },
      ],
      password: [
        {
          required: true,
          message: "Please select Activity zone",
          trigger: "change",
        },
      ],
    });

    const submit = () => {
      router.push("/");
    };
    return {
      rules,
      ruleForm,
      submit,
    };
  },
});
</script>

<style lang="less" scoped>
.login {
  width: 100%;
  height: 100%;
  background-color: #def1fd;
  display: flex;
  align-items: center;
  justify-content: center;
  .login-content {
    width: 70%;
    height: 400px;
    display: flex;
    border-radius: 50px;
    background-color: #fcfbfd;
    .login-left {
      width: 60%;
      height: 100%;
      background-color: #91d0fd;
      border-radius: 50px;
    }
    .login-right {
      width: 40%;
      height: 100%;
      display: flex;
      align-items: center;
      justify-content: center;

      .login-right-inner {
        padding: 30px 20px;
        box-sizing: border-box;
        box-shadow: 2px 2px 3px 1px rgba(59, 161, 255, 0.4);
        .login-title {
          color: #3ba1ff;
          font-size: 18px;
          text-align: center;
        }
        .login-subtitle {
          color: #3ba1ff;
          margin-top: 20px;
        }
        .logo-ruleForm {
          margin-top: 20px;
          .submit {
            background-color: #3ba1ff;
            color: #fff;
            margin: 0 auto;
            width: 80%;
            line-height: 35px;
            border-radius: 30px;
            text-align: center;
          }
        }
      }
    }
  }
}
</style>

