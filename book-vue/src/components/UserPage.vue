<script setup lang="ts">
import {reactive, ref} from "vue";
import {message} from "ant-design-vue";

import {login, register} from "../api/user";
import router from "../router";

// 状态
const status = ref(true)

// 数据
const user = reactive({
  username: "",
  password: ""
})

async function onClick() {
  if (status.value) {
    // 登录
    const r = await login(user)
    if (r.code == 200) {
      // 成功，跳转 BookPage
      message.info("登录成功")
      await router.replace({name: "book"})
    } else {
      // 失败
      message.warn(r.msg)
    }
  } else {
    // 注册
    const r = await register(user)
    if (r.code == 200) {
      // 成功
      message.info("注册成功")
      status.value = true
    } else {
      // 失败
      message.warn(r.msg)
    }
  }
}
</script>

<template>
  <a-page-header title="用户管理">
    <template #extra>
      <a-button type="primary" @click="status = !status">{{ status ? "注册" : "登录" }}</a-button>
    </template>
  </a-page-header>

  <div class="custom">
    <a-form>
      <a-form-item label="用户名：">
        <a-input v-model:value="user.username">{{ user.username }}}</a-input>
      </a-form-item>
      <a-form-item label="密　码：">
        <a-input-password v-model:value="user.password">{{ user.password }}}</a-input-password>
      </a-form-item>
      <a-form-item style="text-align: center">
        <a-button @click="onClick()">{{ status ? "登录" : "注册" }}</a-button>
      </a-form-item>
    </a-form>
  </div>
</template>
