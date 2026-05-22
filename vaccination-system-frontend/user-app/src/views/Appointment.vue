<template>
  <div class="appointment-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>预约接种</span>
        </div>
      </template>

      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="接种对象" prop="familyMemberId">
          <el-select v-model="form.familyMemberId" placeholder="请选择" style="width: 100%">
            <el-option label="本人" :value="null" />
            <el-option v-for="member in familyMembers" :key="member.id" :label="member.name" :value="member.id" />
          </el-select>
        </el-form-item>

        <el-form-item label="选择疫苗" prop="vaccineId">
          <el-select v-model="form.vaccineId" placeholder="请选择疫苗" style="width: 100%">
            <el-option v-for="v in vaccines" :key="v.id" :label="`${v.name} - ${v.type === 0 ? '免费' : `${v.price}元`}`" :value="v.id" />
          </el-select>
        </el-form-item>

        <el-form-item label="预约日期" prop="appointmentDate">
          <el-date-picker v-model="form.appointmentDate" type="date" placeholder="选择日期" style="width: 100%" :disabled-date="disabledDate" @change="loadSlots" />
        </el-form-item>

        <el-form-item label="预约时段" prop="appointmentTime">
          <el-select v-model="form.appointmentTime" placeholder="请先选择日期" style="width: 100%">
            <el-option v-for="slot in slots" :key="slot.id" :label="`${slot.startTime} - ${slot.endTime} (${slot.currentCount}/${slot.maxCount})`" :value="slot.startTime" :disabled="slot.currentCount >= slot.maxCount" />
          </el-select>
        </el-form-item>

        <el-form-item label="备注">
          <el-input v-model="form.remark" type="textarea" :rows="3" placeholder="如有特殊情况请备注" />
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="handleSubmit" :loading="loading">提交预约</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import api from '../api'

const router = useRouter()
const formRef = ref()
const loading = ref(false)
const vaccines = ref([])
const familyMembers = ref([])
const slots = ref([])

const form = reactive({
  familyMemberId: null,
  vaccineId: null,
  appointmentDate: null,
  appointmentTime: null,
  remark: ''
})

const rules = {
  vaccineId: [{ required: true, message: '请选择疫苗', trigger: 'change' }],
  appointmentDate: [{ required: true, message: '请选择预约日期', trigger: 'change' }],
  appointmentTime: [{ required: true, message: '请选择预约时段', trigger: 'change' }]
}

const disabledDate = (date) => {
  return date < new Date()
}

const loadSlots = async () => {
  if (!form.appointmentDate) return
  const dateStr = form.appointmentDate.toISOString().split('T')[0]
  const res = await api.getSlots(dateStr)
  slots.value = res.data
}

onMounted(async () => {
  // 需要从疫苗模块获取疫苗列表，从家庭成员模块获取家庭成员列表
  // 这里暂时使用模拟数据
})

const handleSubmit = async () => {
  await formRef.value.validate()
  loading.value = true
  try {
    const data = {
      ...form,
      appointmentDate: form.appointmentDate.toISOString().split('T')[0],
      appointmentTime: form.appointmentTime
    }
    await api.createAppointment(data)
    ElMessage.success('预约成功')
    router.push('/my-appointments')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.appointment-container {
  padding: 20px;
}
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
