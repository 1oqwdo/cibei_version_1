<template>
  <div class="min-h-screen bg-gray-50 flex items-center justify-center px-4">
    <div class="w-full max-w-md bg-white rounded-2xl shadow-md p-8">

      <!-- 标题 -->
      <h1 class="text-2xl font-bold text-gray-800 mb-2">创建账号</h1>
      <p class="text-sm text-gray-400 mb-8">注册后开始你的自由之旅</p>

      <form @submit.prevent="handleSubmit" novalidate>

        <!-- 手机号 -->
        <div class="mb-4">
          <label class="block text-sm font-medium text-gray-700 mb-1">手机号</label>
          <input
            v-model="form.phone"
            type="tel"
            maxlength="11"
            placeholder="请输入手机号"
            class="input-base"
            :class="{ 'border-red-400': errors.phone }"
            @blur="validatePhone"
          />
          <p v-if="errors.phone" class="err-msg">{{ errors.phone }}</p>
        </div>

        <!-- 验证码 -->
        <div class="mb-4">
          <label class="block text-sm font-medium text-gray-700 mb-1">验证码</label>
          <div class="flex gap-2">
            <input
              v-model="form.code"
              type="text"
              maxlength="6"
              placeholder="请输入验证码"
              class="input-base flex-1"
              :class="{ 'border-red-400': errors.code }"
              @blur="validateCode"
            />
            <button
              type="button"
              class="shrink-0 px-4 py-2 rounded-lg text-sm font-medium transition-colors
                     bg-indigo-600 text-white hover:bg-indigo-700 disabled:bg-gray-300 disabled:cursor-not-allowed"
              :disabled="codeCooldown > 0"
              @click="handleSendCode"
            >
              {{ codeCooldown > 0 ? `${codeCooldown}s 后重发` : '获取验证码' }}
            </button>
          </div>
          <p v-if="errors.code" class="err-msg">{{ errors.code }}</p>
        </div>

        <!-- 密码 -->
        <div class="mb-4">
          <label class="block text-sm font-medium text-gray-700 mb-1">密码</label>
          <input
            v-model="form.password"
            type="password"
            placeholder="6-20位字符"
            class="input-base"
            :class="{ 'border-red-400': errors.password }"
            @blur="validatePassword"
          />
          <p v-if="errors.password" class="err-msg">{{ errors.password }}</p>
        </div>

        <!-- 确认密码 -->
        <div class="mb-5">
          <label class="block text-sm font-medium text-gray-700 mb-1">确认密码</label>
          <input
            v-model="form.confirmPassword"
            type="password"
            placeholder="再次输入密码"
            class="input-base"
            :class="{ 'border-red-400': errors.confirmPassword }"
            @blur="validateConfirmPassword"
          />
          <p v-if="errors.confirmPassword" class="err-msg">{{ errors.confirmPassword }}</p>
        </div>

        <!-- 协议勾选 -->
        <div class="mb-6">
          <label class="flex items-start gap-2 cursor-pointer select-none">
            <input
              v-model="form.agreed"
              type="checkbox"
              class="mt-0.5 h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-500"
              @change="errors.agreed = ''"
            />
            <span class="text-sm text-gray-600">
              我已阅读并同意
              <a href="#" class="text-indigo-600 hover:underline">《用户协议》</a>
              和
              <a href="#" class="text-indigo-600 hover:underline">《隐私政策》</a>
            </span>
          </label>
          <p v-if="errors.agreed" class="err-msg mt-1">{{ errors.agreed }}</p>
        </div>

        <!-- 注册按钮 -->
        <button
          type="submit"
          class="w-full py-3 rounded-xl bg-indigo-600 text-white font-semibold text-base
                 hover:bg-indigo-700 active:scale-95 transition-all disabled:bg-gray-300 disabled:cursor-not-allowed"
          :disabled="loading"
        >
          <span v-if="loading" class="inline-flex items-center gap-2">
            <svg class="animate-spin h-4 w-4" viewBox="0 0 24 24" fill="none">
              <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"/>
              <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8v8z"/>
            </svg>
            注册中...
          </span>
          <span v-else>注册</span>
        </button>

        <!-- 全局错误提示 -->
        <p v-if="globalError" class="mt-4 text-center text-sm text-red-500">{{ globalError }}</p>

        <!-- 成功提示 -->
        <div v-if="success"
          class="mt-4 p-3 rounded-lg bg-green-50 border border-green-200 text-green-700 text-sm text-center">
          注册成功！欢迎加入 🎉
        </div>

      </form>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { sendCode, register } from '../api/auth.js'

const form = reactive({
  phone: '',
  code: '',
  password: '',
  confirmPassword: '',
  agreed: false
})

const errors = reactive({
  phone: '',
  code: '',
  password: '',
  confirmPassword: '',
  agreed: ''
})

const loading = ref(false)
const success = ref(false)
const globalError = ref('')
const codeCooldown = ref(0)

// ── 单字段校验 ──────────────────────────────────────────
function validatePhone() {
  if (!form.phone) {
    errors.phone = '手机号不能为空'
  } else if (!/^1[3-9]\d{9}$/.test(form.phone)) {
    errors.phone = '手机号格式不正确'
  } else {
    errors.phone = ''
  }
}

function validateCode() {
  if (!form.code) {
    errors.code = '验证码不能为空'
  } else if (!/^\d{6}$/.test(form.code)) {
    errors.code = '验证码为6位数字'
  } else {
    errors.code = ''
  }
}

function validatePassword() {
  if (!form.password) {
    errors.password = '密码不能为空'
  } else if (form.password.length < 6 || form.password.length > 20) {
    errors.password = '密码长度须在6-20位之间'
  } else {
    errors.password = ''
  }
}

function validateConfirmPassword() {
  if (!form.confirmPassword) {
    errors.confirmPassword = '请再次输入密码'
  } else if (form.confirmPassword !== form.password) {
    errors.confirmPassword = '两次密码输入不一致'
  } else {
    errors.confirmPassword = ''
  }
}

function validateAll() {
  validatePhone()
  validateCode()
  validatePassword()
  validateConfirmPassword()
  if (!form.agreed) errors.agreed = '请阅读并同意用户协议和隐私政策'
  return !Object.values(errors).some(e => e)
}

// ── 发送验证码 ──────────────────────────────────────────
async function handleSendCode() {
  validatePhone()
  if (errors.phone) return

  try {
    await sendCode(form.phone)
    startCooldown()
  } catch (e) {
    globalError.value = e.response?.data?.message || '发送失败，请稍后重试'
  }
}

function startCooldown() {
  codeCooldown.value = 60
  const timer = setInterval(() => {
    codeCooldown.value--
    if (codeCooldown.value <= 0) clearInterval(timer)
  }, 1000)
}

// ── 提交注册 ──────────────────────────────────────────
async function handleSubmit() {
  globalError.value = ''
  if (!validateAll()) return

  loading.value = true
  try {
    await register({
      phone: form.phone,
      code: form.code,
      password: form.password,
      confirmPassword: form.confirmPassword,
      agreed: form.agreed
    })
    success.value = true
  } catch (e) {
    globalError.value = e.response?.data?.message || '注册失败，请稍后重试'
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.input-base {
  @apply w-full px-4 py-2.5 border border-gray-300 rounded-lg text-sm
         focus:outline-none focus:ring-2 focus:ring-indigo-400 focus:border-transparent
         transition-colors;
}
.err-msg {
  @apply mt-1 text-xs text-red-500;
}
</style>
