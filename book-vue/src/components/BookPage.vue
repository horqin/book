<script setup lang="ts">
import {onMounted, reactive, ref} from "vue";
import {message} from "ant-design-vue";

import router from "../router";
import {logout} from "../api/user";
import Book from "../domain/Book";
import {create, deleteById, updateById, readBatchByPage} from "../api/book";
import BookReview from "../domain/BookReview";
import {createReview, createReviewReplyById, readByBookId} from "../api/bookReview";

// 搜索
const keyword = ref("")
const page = reactive({
  current: 1,
  size: 5,
  pages: 0,
  total: 0,
  records: Array<Book>()
})
const list = reactive({
  records: Array<BookReview>()
})

// 状态
const visibleInsert = ref(false)
const visibleDelete = ref(false)
const visibleUpdate = ref(false)
const visibleReview = ref(false)

// 书籍
const bookId = ref(0)
const book = reactive({
  title: '',
  authors: '',
  publisher: ''
})

// 评论
const bookReviewId = ref(0)
const bookReview = reactive({
  review: ''
})
const bookReviewReplyId = ref("")
const bookReviewReply = reactive({
  reply: ''
})

onMounted(onSearch)

async function onLogout() {
  logout()
  message.info("登出成功")
  // 跳转页面
  await router.replace("/user")
}

async function onSearch() {
  const r = await readBatchByPage(keyword.value, page.current, page.size)
  if (r.code === 200) {
    page.current = r.data.current
    page.size = r.data.size
    page.pages = r.data.pages
    page.total = r.data.total
    page.records = r.data.records
  } else {
    message.warn("检索失败")
  }
}

async function onSearchWithKeyword() {
  page.current = 1
  page.size = 5
  await onSearch()
}

const onShowUpdate = (id: number, record: Book) => {
  bookId.value = id
  book.title = record.title
  book.authors = record.authors
  book.publisher = record.publisher
  visibleUpdate.value = true
}

const onShowDelete = (id: number, record: Book) => {
  bookId.value = id
  book.title = record.title
  book.authors = record.authors
  book.publisher = record.publisher
  visibleDelete.value = true
}

const onShowInsert = () => {
  book.title = ""
  book.authors = ""
  book.publisher = ""
  visibleInsert.value = true
}

async function onUpdate() {
  const r = await updateById(bookId.value, {
    title: book.title,
    authors: book.authors,
    publisher: book.publisher
  })
  if (r.code == 200) {
    message.info("修改成功")
    // 更新搜索结果
    await onSearch();
  } else {
    message.warn(r.msg)
  }
  visibleUpdate.value = false
}

async function onDelete() {
  const r = await deleteById(bookId.value)
  if (r.code == 200) {
    message.info("删除成功")
    // 更新搜索结果
    await onSearch()
  } else {
    message.warn(r.msg)
  }
  visibleDelete.value = false
}

async function onInsert() {
  const r = await create({
    title: book.title,
    authors: book.authors,
    publisher: book.publisher
  })
  if (r.code == 200) {
    message.info("新增成功")
    // 更新搜索结果
    await onSearch()
  } else {
    message.warn(r.msg)
  }
  visibleInsert.value = false
}

const onShowReview = async (bookId: number) => {
  bookReviewId.value = bookId
  visibleReview.value = true
  await onReviewSearch()
}

const onShowReviewReply = async (id: string) => {
  bookReviewReplyId.value = id
}

async function onReviewSearch() {
  const r = await readByBookId(bookReviewId.value)
  if (r.code == 200) {
    list.records = r.data
  } else {
    message.warn("检索失败")
  }
}

async function onReviewInsert() {
  const r = await createReview({
    bookId: bookReviewId.value,
    review: bookReview.review
  })
  bookReview.review = ""
  if (r.code == 200) {
    message.info("评论成功")
    // 更新搜索结果
    await onReviewSearch()
  } else {
    message.warn(r.msg)
  }
}

async function onReviewReplyInsert(id: string) {
  const r = await createReviewReplyById(id, {
    reply: bookReviewReply.reply
  })
  bookReviewReply.reply = ""
  bookReviewReplyId.value = ""
  if (r.code == 200) {
    message.info("回复成功")
    // 更新搜索结果
    await onReviewSearch()
  } else {
    message.warn(r.msg)
  }
}
</script>

<template>
  <a-page-header title="文献管理">
    <template #extra>
      <a-button type="primary" @click="onLogout">登出</a-button>
    </template>
  </a-page-header>

  <div class="custom">
    <a-input-search v-model:value="keyword" @search="onSearchWithKeyword"/>

    <a-button type="primary" @click="onShowInsert()">新增</a-button>

    <a-table :columns="[
        { title: '篇名', dataIndex: 'title' },
        { title: '作者', dataIndex: 'authors' },
        { title: '刊名', dataIndex: 'publisher' },
        { title: '操作', dataIndex: 'id', slots: { customRender: 'name' }, align: 'center', width: '300px'},
      ]" :rowKey='record=>record.id' :data-source="page.records" :pagination="false">
      <template #name="{ record }">
        <div style="text-align: center">
          <a-button type="primary" @click="onShowDelete(record.id, record)">删除</a-button>
          <a-button type="primary" @click="onShowUpdate(record.id, record)" style="margin-left: 20px">修改</a-button>
          <a-button type="primary" @click="onShowReview(record.id)" style="margin-left: 20px">评论</a-button>
        </div>
      </template>
    </a-table>

    <a-pagination v-model:current="page.current" v-model:pageSize="page.size" :total="page.total"
                  @showSizeChange="onSearch" @change="onSearch"/>

    <a-input-search v-model:value="bookReview.review" @search="onReviewInsert" v-show="visibleReview">
      <template #enterButton>
        <a-button>提交</a-button>
      </template>
    </a-input-search>

    <a-list item-layout="horizontal" :data-source="list.records"
            v-show="visibleReview">
      <template #renderItem="{ item }">
        <a-list-item>
          <a-list-item-meta>
            <template #title>
              <div>{{ item.username }}</div>
              <a-button size="small" @click="onShowReviewReply(item.id)">回复</a-button>
            </template>
          </a-list-item-meta>
          {{item.review}}
          <div v-for="r of item.replies" style="margin-left: 20px">
            <a-list-item-meta>
              <template #title>
                <div>{{ r.username }}</div>
              </template>
            </a-list-item-meta>
            {{r.reply}}
          </div>

          <a-input-search v-model:value="bookReviewReply.reply" @search="onReviewReplyInsert(item.id)"
                          v-show="bookReviewReplyId === item.id">
            <template #enterButton>
              <a-button>提交</a-button>
            </template>
          </a-input-search>
        </a-list-item>
      </template>
    </a-list>

    <a-layout-footer :style="{ textAlign: 'center' }">
      FOOTER
    </a-layout-footer>
  </div>

  <a-modal v-model:visible="visibleUpdate" title="修改" @ok="onUpdate">
    <a-form>
      <a-form-item label="篇名：">
        <a-input v-model:value="book.title">{{ book.title }}}</a-input>
      </a-form-item>
      <a-form-item label="作者：">
        <a-input v-model:value="book.authors">{{ book.authors }}}</a-input>
      </a-form-item>
      <a-form-item label="刊名：">
        <a-input v-model:value="book.publisher">{{ book.publisher }}}</a-input>
      </a-form-item>
    </a-form>
  </a-modal>

  <a-modal v-model:visible="visibleDelete" title="删除" @ok="onDelete">
    <a-form>
      <a-form-item label="篇名：">
        <a-input v-model:value="book.title" :disabled="true">{{ book.title }}}</a-input>
      </a-form-item>
      <a-form-item label="作者：">
        <a-input v-model:value="book.authors" :disabled="true">{{ book.authors }}}</a-input>
      </a-form-item>
      <a-form-item label="刊名：">
        <a-input v-model:value="book.publisher" :disabled="true">{{ book.publisher }}}</a-input>
      </a-form-item>
    </a-form>
  </a-modal>

  <a-modal v-model:visible="visibleInsert" title="新增" @ok="onInsert">
    <a-form>
      <a-form-item label="篇名：">
        <a-input v-model:value="book.title">{{ book.title }}}</a-input>
      </a-form-item>
      <a-form-item label="作者：">
        <a-input v-model:value="book.authors">{{ book.authors }}}</a-input>
      </a-form-item>
      <a-form-item label="刊名：">
        <a-input v-model:value="book.publisher">{{ book.publisher }}}</a-input>
      </a-form-item>
    </a-form>
  </a-modal>
</template>
