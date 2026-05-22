<template>
  <div class="home-container">
    <el-container>
      <el-header>
        <div class="header-content">
          <span class="title">疫苗接种系统</span>
          <div class="user-info">
            <span>{{ user?.realName }} ({{ roleText }})</span>
            <el-button type="text" @click="router.push('/profile')">个人中心</el-button>
            <el-button type="text" @click="handleLogout">退出</el-button>
          </div>
        </div>
      </el-header>
      <el-main>
        <div class="menu-grid">
          <!-- 疫苗列表 - 所有角色 -->
          <div class="menu-item" @click="router.push('/vaccine')">
            <el-icon size="40"><FirstAidKit /></el-icon>
            <span>疫苗列表</span>
            <p class="menu-desc">查看可接种疫苗信息</p>
          </div>

          <!-- 批次管理 - 管理员 -->
          <div class="menu-item" @click="router.push('/vaccine-batch')" v-if="isAdmin">
            <el-icon size="40"><Box /></el-icon>
            <span>批次管理</span>
            <p class="menu-desc">管理疫苗批次库存</p>
          </div>

          <!-- 预约接种 - 普通用户 -->
          <div class="menu-item" @click="router.push('/appointment')" v-if="isUser">
            <el-icon size="40"><Calendar /></el-icon>
            <span>预约接种</span>
            <p class="menu-desc">在线预约疫苗接种</p>
          </div>

          <!-- 我的预约 - 普通用户 -->
          <div class="menu-item" @click="router.push('/my-appointments')" v-if="isUser">
            <el-icon size="40"><Document /></el-icon>
            <span>我的预约</span>
            <p class="menu-desc">查看和管理预约</p>
          </div>

          <!-- 预约管理 - 医生、管理员 -->
          <div class="menu-item" @click="router.push('/appointment-list')" v-if="isDoctorOrAdmin">
            <el-icon size="40"><Document /></el-icon>
            <span>预约管理</span>
            <p class="menu-desc">管理所有预约记录</p>
          </div>

          <!-- 接种记录 - 所有角色 -->
          <div class="menu-item" @click="router.push('/records')">
            <el-icon size="40"><Files /></el-icon>
            <span>接种记录</span>
            <p class="menu-desc">查看历史接种记录</p>
          </div>

          <!-- 家庭成员 - 普通用户 -->
          <div class="menu-item" @click="router.push('/family')" v-if="isUser">
            <el-icon size="40"><User /></el-icon>
            <span>家庭成员</span>
            <p class="menu-desc">管理家庭成员信息</p>
          </div>

          <!-- 接种提醒 - 普通用户（新增） -->
          <div class="menu-item" @click="showReminders" v-if="isUser">
            <el-icon size="40"><Bell /></el-icon>
            <span>接种提醒</span>
            <p class="menu-desc">查看待接种提醒</p>
          </div>

          <!-- 用户管理 - 管理员 -->
          <div class="menu-item" @click="router.push('/user-list')" v-if="isAdmin">
            <el-icon size="40"><UserFilled /></el-icon>
            <span>用户管理</span>
            <p class="menu-desc">管理系统用户</p>
          </div>

          <!-- 统计报表 - 管理员（新增） -->
          <div class="menu-item" @click="router.push('/statistics')" v-if="isAdmin">
            <el-icon size="40"><DataAnalysis /></el-icon>
            <span>统计报表</span>
            <p class="menu-desc">查看接种统计数据</p>
          </div>
        </div>

        <!-- 接种须知公告区域 -->
        <div class="announcement">
          <h3><el-icon><BellFilled /></el-icon> 接种须知</h3>
          <ul>
            <li>请携带本人身份证或相关证件前往接种点</li>
            <li>接种前请如实告知健康状况</li>
            <li>接种后请在现场观察30分钟</li>
            <li>如有不良反应请及时就医</li>
          </ul>
        </div>

        <!-- 接种提醒对话框 -->
        <el-dialog v-model="reminderVisible" title="接种提醒" width="500px">
          <el-alert type="warning" :closable="false" show-icon style="margin-bottom: 15px">
            <template #title>您有以下待接种的疫苗</template>
          </el-alert>
          <el-table :data="reminders" stripe size="small">
            <el-table-column prop="vaccineName" label="疫苗名称" />
            <el-table-column prop="memberName" label="接种对象" />
            <el-table-column prop="nextDate" label="建议接种日期" />
          </el-table>
        </el-dialog>
      </el-main>
    </el-container>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { Calendar, Document, Files, User, FirstAidKit, Box, UserFilled, Bell, BellFilled, DataAnalysis } from '@element-plus/icons-vue'
import { ElMessageBox } from 'element-plus'
import api from '../api'

const router = useRouter()
const user = ref(JSON.parse(localStorage.getItem('user')))

const reminderVisible = ref(false)
const reminders = ref([
  { vaccineName: '新冠疫苗（第二针）', memberName: '本人', nextDate: '2026-06-10' },
  { vaccineName: '乙肝疫苗', memberName: '张小明', nextDate: '2026-06-08' },
  { vaccineName: '流感疫苗', memberName: '李小红', nextDate: '2026-05-20' }
])

const showReminders = () => {
  reminderVisible.value = true
}

const roleText = computed(() => {
  const role = user.value?.role
  if (role === 2) return '管理员'
  if (role === 1) return '医生'
  return '用户'
})

const isUser = computed(() => user.value?.role === 0)
const isAdmin = computed(() => user.value?.role === 2)
const isDoctorOrAdmin = computed(() => user.value?.role === 1 || user.value?.role === 2)

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
  })
}
</script>

<style scoped>
.home-container {
  min-height: 100vh;
}
.el-header {
  background: linear-gradient(135deg, #409eff 0%, #66b1ff 100%);
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
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
  padding: 20px;
}
.menu-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 25px;
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 4px 15px rgba(0,0,0,0.1);
  cursor: pointer;
  transition: all 0.3s;
}
.menu-item:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 25px rgba(0,0,0,0.15);
}
.menu-item .el-icon {
  color: #409eff;
}
.menu-item span {
  margin-top: 12px;
  font-size: 16px;
  font-weight: 500;
  color: #333;
}
.menu-desc {
  margin-top: 8px;
  font-size: 12px;
  color: #999;
  text-align: center;
}
.announcement {
  margin: 20px;
  padding: 20px;
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 4px 15px rgba(0,0,0,0.1);
}
.announcement h3 {
  margin-bottom: 15px;
  color: #409eff;
  display: flex;
  align-items: center;
  gap: 8px;
}
.announcement ul {
  padding-left: 20px;
  color: #666;
}
.announcement li {
  margin: 8px 0;
}
</style>