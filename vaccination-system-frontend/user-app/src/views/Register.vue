<template>
  <div class="register-container">
    <div class="register-box">
      <h2>用户注册</h2>
      <el-form :model="form" :rules="rules" ref="formRef">
        <el-form-item prop="username">
          <el-input v-model="form.username" placeholder="用户名" prefix-icon="User" />
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="form.password" type="password" placeholder="密码" prefix-icon="Lock" />
        </el-form-item>
        <el-form-item prop="phone">
          <el-input v-model="form.phone" placeholder="手机号" prefix-icon="Phone" />
        </el-form-item>
        <el-form-item prop="realName">
          <el-input v-model="form.realName" placeholder="真实姓名" prefix-icon="UserFilled" />
        </el-form-item>
        <el-form-item prop="idCard">
          <el-input v-model="form.idCard" placeholder="身份证号" prefix-icon="Postcard" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleRegister" :loading="loading" style="width: 100%">注册</el-button>
        </el-form-item>
      </el-form>
      <div class="login-link">
        已有账号？<router-link to="/login">返回登录</router-link>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import api from '../api'

const router = useRouter()
const formRef = ref()
const loading = ref(false)

const form = reactive({
  username: '',
  password: '',
  phone: '',
  realName: '',
  idCard: ''
})

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, min: 6, message: '密码至少6位', trigger: 'blur' }],
  phone: [{ required: true, pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' }],
  realName: [{ required: true, message: '请输入真实姓名', trigger: 'blur' }],
  idCard: [{ required: true, pattern: /^\d{17}[\dXx]$/, message: '请输入正确的身份证号', trigger: 'blur' }]
}

const handleRegister = async () => {
  await formRef.value.validate()
  loading.value = true
  try {
    await api.register(form)
    ElMessage.success('注册成功')
    router.push('/login')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}
.register-box {
  width: 350px;
  padding: 30px;
  background: #fff;
  border-radius: 10px;
  box-shadow: 0 10px 30px rgba(0,0,0,0.2);
}
.register-box h2 {
  text-align: center;
  margin-bottom: 20px;
  color: #333;
}
.login-link {
  text-align: center;
  margin-top: 10px;
  color: #666;
}
</style>
