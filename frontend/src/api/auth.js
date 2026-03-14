import axios from 'axios'

const http = axios.create({ baseURL: '/api' })

/**
 * 发送短信验证码
 * @param {string} phone
 */
export function sendCode(phone) {
  return http.post('/auth/send-code', { phone })
}

/**
 * 用户注册
 * @param {{ phone, code, password, confirmPassword, agreed }} data
 */
export function register(data) {
  return http.post('/auth/register', data)
}
