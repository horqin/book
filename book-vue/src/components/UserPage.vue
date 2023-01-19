<script setup lang="ts">
import {computed, reactive, ref} from "vue";
import {message} from "ant-design-vue";

import router from "../router";
import User from "../domain/User";
import {login, register} from "../api/user";

// 用户
const user = reactive<User>({
  username: "",
  password: ""
})

// 状态
const status = ref("登录")
const next_status = computed(() => {
  switch (status.value) {
    case "登录":
      return "注册"
    case "注册":
      return "登录"
  }
  return ""
})
function status_next(): void {
  switch (status.value) {
    case "登录":
      status.value = next_status.value
      break
    case "注册":
      status.value = next_status.value
      break
  }
}
async function status_entry() {
  switch (status.value) {
    case "登录":
      await (async () => {
        const r = await login(user)
        if (r.code === 200) {
          message.info("登录成功")
          // 跳转页面
          await router.replace({name: "book"})
        } else {
          message.warn(r.msg)
        }
      })()
      break
    case "注册":
      await (async () => {
        const r = await register(user)
        if (r.code === 200) {
          message.info("注册成功")
          // 切换状态
          status_next()
        } else {
          message.warn(r.msg)
        }
      })()
      break
  }
}
</script>

<template>
  <a-page-header title="用户管理">
    <template #extra>
      <a-button type="primary" @click="status_next">{{ next_status }}</a-button>
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
        <a-button @click="status_entry">{{ status }}</a-button>
      </a-form-item>
    </a-form>
  </div>
</template>
