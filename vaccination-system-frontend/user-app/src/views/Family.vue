<template>
  <div class="family-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>家庭成员管理</span>
          <el-button type="primary" size="small" @click="showAddDialog">添加成员</el-button>
        </div>
      </template>

      <el-table :data="familyMembers" stripe>
        <el-table-column prop="name" label="姓名" width="100" />
        <el-table-column prop="relation" label="关系" width="80" />
        <el-table-column prop="gender" label="性别" width="60">
          <template #default="{ row }">
            {{ row.gender === 0 ? '男' : '女' }}
          </template>
        </el-table-column>
        <el-table-column prop="birthDate" label="出生日期" width="120" />
        <el-table-column prop="phone" label="手机号" width="120" />
        <el-table-column prop="idCard" label="身份证号" />
        <el-table-column label="操作" width="150">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="showEditDialog(row)">编辑</el-button>
            <el-button type="danger" size="small" @click="handleDelete(row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="isEdit ? '编辑成员' : '添加成员'" width="400px">
      <el-form :model="memberForm" :rules="memberRules" ref="memberFormRef" label-width="80px">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="memberForm.name" />
        </el-form-item>
        <el-form-item label="关系" prop="relation">
          <el-select v-model="memberForm.relation" style="width: 100%">
            <el-option label="本人" value="本人" />
            <el-option label="子女" value="子女" />
            <el-option label="配偶" value="配偶" />
            <el-option label="父母" value="父母" />
          </el-select>
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-radio-group v-model="memberForm.gender">
            <el-radio :value="0">男</el-radio>
            <el-radio :value="1">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="出生日期" prop="birthDate">
          <el-date-picker v-model="memberForm.birthDate" type="date" style="width: 100%" />
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="memberForm.phone" />
        </el-form-item>
        <el-form-item label="身份证号" prop="idCard">
          <el-input v-model="memberForm.idCard" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSave">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import api from '../api'

const router = useRouter()
const familyMembers = ref([])
const dialogVisible = ref(false)
const isEdit = ref(false)
const memberFormRef = ref()

const memberForm = reactive({
  id: null,
  name: '',
  relation: '本人',
  gender: 0,
  birthDate: null,
  phone: '',
  idCard: ''
})

const memberRules = {
  name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
  relation: [{ required: true, message: '请选择关系', trigger: 'change' }]
}

const showAddDialog = () => {
  isEdit.value = false
  Object.assign(memberForm, { id: null, name: '', relation: '本人', gender: 0, birthDate: null, phone: '', idCard: '' })
  dialogVisible.value = true
}

const showEditDialog = (row) => {
  isEdit.value = true
  Object.assign(memberForm, row)
  dialogVisible.value = true
}

const handleSave = async () => {
  await memberFormRef.value.validate()
  const data = { ...memberForm, birthDate: memberForm.birthDate?.toISOString?.()?.split('T')[0] }
  if (isEdit.value) {
    await api.updateFamilyMember(data)
    ElMessage.success('修改成功')
  } else {
    await api.addFamilyMember(data)
    ElMessage.success('添加成功')
  }
  dialogVisible.value = false
  loadData()
}

const handleDelete = async (id) => {
  await ElMessageBox.confirm('确定删除该家庭成员吗？', '提示', { type: 'warning' })
  await api.deleteFamilyMember(id)
  ElMessage.success('删除成功')
  loadData()
}

const loadData = async () => {
  const res = await api.getFamilyMembers()
  familyMembers.value = res.data
}

onMounted(loadData)
</script>

<style scoped>
.family-container {
  padding: 20px;
}
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
