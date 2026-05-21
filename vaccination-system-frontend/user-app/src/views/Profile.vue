<template>
  <div class="profile-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>个人中心</span>
          <el-button text @click="router.push('/')">返回首页</el-button>
        </div>
      </template>

      <el-descriptions :column="1" border>
        <el-descriptions-item label="用户名">{{ user?.username }}</el-descriptions-item>
        <el-descriptions-item label="真实姓名">{{ user?.realName }}</el-descriptions-item>
        <el-descriptions-item label="手机号">{{ user?.phone }}</el-descriptions-item>
        <el-descriptions-item label="身份证号">{{ user?.idCard }}</el-descriptions-item>
        <el-descriptions-item label="角色">{{ roleText }}</el-descriptions-item>
      </el-descriptions>

      <el-divider />

      <h4>修改密码</h4>
      <el-form :model="passwordForm" :rules="passwordRules" ref="passwordFormRef" label-width="100px">
        <el-form-item label="原密码" prop="oldPassword">
          <el-input v-model="passwordForm.oldPassword" type="password" />
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input v-model="passwordForm.newPassword" type="password" />
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input v-model="passwordForm.confirmPassword" type="password" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleChangePassword">修改密码</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, computed } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import api from '../api'

const router = useRouter()
const user = ref(JSON.parse(localStorage.getItem('user')))
const passwordFormRef = ref()

const roleText = computed(() => {
  return ['普通用户', '医护人员', '管理员'][user.value?.role || 0]
})

const passwordForm = reactive({
  oldPassword: '',
  newPassword: '',
  confirmPassword: ''
})

const validateConfirm = (rule, value, callback) => {
  if (value !== passwordForm.newPassword) {
    callback(new Error('两次输入的密码不一致'))
  } else {
    callback()
  }
}

const passwordRules = {
  oldPassword: [{ required: true, message: '请输入原密码', trigger: 'blur' }],
  newPassword: [{ required: true, min: 6, message: '密码至少6位', trigger: 'blur' }],
  confirmPassword: [{ required: true, validator: validateConfirm, trigger: 'blur' }]
}

const handleChangePassword = async () => {
  await passwordFormRef.value.validate()
  await api.changePassword(passwordForm.oldPassword, passwordForm.newPassword)
  ElMessage.success('密码修改成功，请重新登录')
  localStorage.removeItem('token')
  localStorage.removeItem('user')
  router.push('/login')
}
</script>

<style scoped>
.profile-container {
  padding: 20px;
  max-width: 600px;
  margin: 0 auto;
}
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
h4 {
  margin: 20px 0 10px;
}
</style>
