// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';

/** 获取当前的用户 GET /api/auth/currentUser */
export async function currentUser(options?: { [key: string]: any }) {
  return request<API.CurrentUser>('/api/auth/currentUser', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 退出登录接口 POST /api/auth/logout */
export async function outLogin(options?: { [key: string]: any }) {
  return request<Record<string, any>>('/api/auth/logout', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 登录接口 POST /api/auth/login */
export async function login(body: API.LoginParams, options?: { [key: string]: any }) {
  return request<API.LoginResponse>('/api/auth/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}


/** 此处后端没有提供注释 GET /api/notices */
export async function getNotices(options?: { [key: string]: any }) {
  return request<API.NoticeIconList>('/api/notices', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 获取用户列表 POST /api/user/page */
export async function getUserListConditional(
  params: {
    current?: number;
    pageSize?: number;
    username?: string;
    userStatus?: number;
    roleIds?: number[];
  },
  options?: { [key: string]: any },
) {
  const response = await request<API.UserList>(`/api/user/page`, {
    method: 'POST',
    data:{
      ...params,
      roleIds: params.roleIds?.map(id => Number(id))
    },
    ...(options || {}),
  });
  return {
    ...response,
    data: response.records,
  };
}

/** 获取用户列表 GET /api/user/page */
export async function getUserList(
  params: {
    current?: number;
    pageSize?: number;
  },
  options?: { [key: string]: any },
) {
  const response = await request<API.UserList>(`/api/user/page/${params.current}&${params.pageSize}`, {
    method: 'GET',
    ...(options || {}),
  });
  return {
    ...response,
    data: response.records,
  };
}

/** 新建规则 PUT /api/rule */
export async function updateUser(body: API.UserListItem, options?: { [key: string]: any }) {
  return request<API.UserListItem>('/api/user/update', {
    method: 'PUT',
    data: body,
    ...(options || {}),
  });
}

/** 新建规则 POST /api/rule */
export async function addUser(options?: { [key: string]: any }) {
  return request<API.UserListItem>('/api/user/add', {
    method: 'POST',
    ...(options || {}),
  });
}

/** 删除规则 DELETE /api/rule */
export async function removeUser(options?: { [key: string]: any }) {
  return request<Record<string, any>>('/api/user/delete', {
    method: 'DELETE',
    ...(options || {}),
  });
}



/** 获取规则列表 GET /api/rule */
export async function rule(
  params: {
    // query
    /** 当前的页码 */
    current?: number;
    /** 页面的容量 */
    pageSize?: number;
  },
  options?: { [key: string]: any },
) {
  return request<API.RuleList>('/api/rule', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** 新建规则 PUT /api/rule */
export async function updateRule(options?: { [key: string]: any }) {
  return request<API.RuleListItem>('/api/rule', {
    method: 'PUT',
    ...(options || {}),
  });
}

/** 新建规则 POST /api/rule */
export async function addRule(options?: { [key: string]: any }) {
  return request<API.RuleListItem>('/api/rule', {
    method: 'POST',
    ...(options || {}),
  });
}

/** 删除规则 DELETE /api/rule */
export async function removeRule(options?: { [key: string]: any }) {
  return request<Record<string, any>>('/api/rule', {
    method: 'DELETE',
    ...(options || {}),
  });
}
