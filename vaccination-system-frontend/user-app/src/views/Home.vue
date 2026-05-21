<template>
  <div class="home-container">
    <el-container>
      <el-header>
        <div class="header-content">
          <span class="title">疫苗接种系统</span>
          <div class="user-info">
            <span>{{ user?.realName }}</span>
            <el-button type="text" @click="router.push('/profile')">个人中心</el-button>
            <el-button type="text" @click="handleLogout">退出</el-button>
          </div>
        </div>
      </el-header>
      <el-main>
        <div class="menu-grid">
          <div class="menu-item" @click="router.push('/appointment')">
            <el-icon size="40"><Calendar /></el-icon>
            <span>预约接种</span>
          </div>
          <div class="menu-item" @click="router.push('/my-appointments')">
            <el-icon size="40"><Document /></el-icon>
            <span>我的预约</span>
          </div>
          <div class="menu-item" @click="router.push('/records')">
            <el-icon size="40"><Files /></el-icon>
            <span>接种记录</span>
          </div>
          <div class="menu-item" @click="router.push('/family')">
            <el-icon size="40"><User /></el-icon>
            <span>家庭成员</span>
          </div>
        </div>
        <div class="announcement">
          <h3>接种须知</h3>
          <ul>
            <li>请携带本人身份证或相关证件前往接种点</li>
            <li>接种前请如实告知健康状况</li>
            <li>接种后请在现场观察30分钟</li>
            <li>如有不良反应请及时就医</li>
          </ul>
        </div>
      </el-main>
    </el-container>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { Calendar, Document, Files, User } from '@element-plus/icons-vue'
import { ElMessageBox } from 'element-plus'
import api from '../api'

const router = useRouter()
const user = ref(JSON.parse(localStorage.getItem('user')))

const handleLogout = () => {
  ElMessageBox.confirm('确定要退出登录吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    localStorage.removeItem('token')
    localStorage.removeItem('user')
    router.push('/login')
  }).catch(() => {
    // 取消退出，不做任何操作
  })
}
</script>

<style scoped>
.home-container {
  min-height: 100vh;
}
.el-header {
  background: #409eff;
  color: #fff;
  line-height: 60px;
}
.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.title {
  font-size: 20px;
  font-weight: bold;
}
.user-info .el-button {
  color: #fff;
}
.menu-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
  padding: 20px;
}
.menu-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 30px;
  background: #fff;
  border-radius: 10px;
  box-shadow: 0 2px 10px rgba(0,0,0,0.1);
  cursor: pointer;
  transition: transform 0.2s;
}
.menu-item:hover {
  transform: translateY(-5px);
}
.menu-item span {
  margin-top: 10px;
  font-size: 16px;
  color: #333;
}
.announcement {
  margin: 20px;
  padding: 20px;
  background: #fff;
  border-radius: 10px;
}
.announcement h3 {
  margin-bottom: 10px;
  color: #409eff;
}
.announcement ul {
  padding-left: 20px;
  color: #666;
}
</style>
