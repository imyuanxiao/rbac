import {FormattedMessage} from "@@/exports";
import React from "react";

export const orgEnum = {
  1:  {
    text: (
      <FormattedMessage
        id="pages.searchTable.org.org1"
        defaultMessage="总公司"
      />
    ),
  },
  2: {
    text: (
      <FormattedMessage
        id="pages.searchTable.org.org2"
        defaultMessage="分公司1"
      />
    ),
  },
  3: {
    text: (
      <FormattedMessage
        id="pages.searchTable.org.org3"
        defaultMessage="分公司2"
      />
    ),
  },
}

export const userStatusEnum = {
    0: {
      text: (
        <FormattedMessage
          id="pages.searchTable.nameStatus.inUse"
          defaultMessage="正常"
        />
      ),
      status: 'Success',
    },
    1: {
      text: (
        <FormattedMessage
          id="pages.searchTable.nameStatus.disabled"
          defaultMessage="停用" />
      ),
      status: 'Error',
    },
    2: {
      text: (
        <FormattedMessage id="pages.searchTable.nameStatus.deleted"
                          defaultMessage="注销" />
      ),
      status: 'Default',
    },
}

export const userStatusOptions = [
  {
    value: 0,
    label: (
      <FormattedMessage
        id="pages.searchTable.nameStatus.inUse"
        defaultMessage="正常"
      />
    ),
  },
  {
    value: 1,
    label: (
      <FormattedMessage
        id="pages.searchTable.nameStatus.disabled"
        defaultMessage="停用"
      />
    ),
  },
  {
    value: 2,
    label: (
      <FormattedMessage
        id="pages.searchTable.nameStatus.deleted"
        defaultMessage="注销"
      />
    ),
  },
];

export const loginHistoryEnum = {
  0: {
    text: (
      <FormattedMessage
        id="pages.searchTable.loginHistory.login"
        defaultMessage="登录"
      />
    ),
  },
  1: {
    text: (
      <FormattedMessage
        id="pages.searchTable.loginHistory.logout"
        defaultMessage="登出"
      />
    ),
  }
}

const convertPermissionTree = (tree, result = {}) => {
  tree.forEach((node) => {
    result[node.value] = {
      text: node.title,
    };

    if (node.children && node.children.length > 0) {
      convertPermissionTree(node.children, result);
    }
  });

  return result;
};

export const permissionTreeEnum = [
  {
    title: (
      <FormattedMessage
        id="type.permission.canUserAdmin"
        defaultMessage="用户管理"
      />
    ),
    value: 1000,
    children: [
      {
        title: (
          <FormattedMessage
            id="type.permission.canAddUser"
            defaultMessage="新增用户"
          />
        ),
        value: 1001,
      },
      {
        title: (
          <FormattedMessage
            id="type.permission.canDeleteUser"
            defaultMessage="删除用户"
          />
        ),
        value: 1002,
      },
      {
        title: (
          <FormattedMessage
            id="type.permission.canEditUser"
            defaultMessage="编辑用户"
          />
        ),
        value: 1003,
      },
      {
        title: (
          <FormattedMessage
            id="type.permission.canGetUserById"
            defaultMessage="通过ID获取用户信息"
          />
        ),
        value: 1004,
      },
      {
        title: (
          <FormattedMessage
            id="type.permission.canGetUserPermissionsById"
            defaultMessage="通过ID获取用户权限"
          />
        ),
        value: 1005,
      },
      {
        title: (
          <FormattedMessage
            id="type.permission.getUserPageByConditions"
            defaultMessage="查看用户信息"
          />
        ),
        value: 1006,
      },
      {
        title: (
          <FormattedMessage
            id="type.permission.getLoginHistoryByConditions"
            defaultMessage="查看登录历史"
          />
        ),
        value: 1007,
      },
    ],
  },
  {
    title: (
      <FormattedMessage
        id="type.permission.canRoleAdmin"
        defaultMessage="角色管理"
      />
    ),
    value: 3000,
    children: [
      {
        title: (
          <FormattedMessage
            id="type.permission.canAddRole"
            defaultMessage="新增角色"
          />
        ),
        value: 3001,
      },
      {
        title: (
          <FormattedMessage
            id="type.permission.canDeleteRole"
            defaultMessage="删除角色"
          />
        ),
        value: 3002,
      },
      {
        title: (
          <FormattedMessage
            id="type.permission.canEditRole"
            defaultMessage="编辑角色"
          />
        ),
        value: 3003,
      },
      {
        title: (
          <FormattedMessage
            id="type.permission.canRolePage"
            defaultMessage="查看角色信息"
          />
        ),
        value: 3004,
      },
    ],
  },
  {
    title: (
      <FormattedMessage
        id="type.permission.canPermissionAdmin"
        defaultMessage="权限管理"
      />
    ),
    value: 4000,
  },
  {
    title: (
      <FormattedMessage
        id="type.permission.canDataAdmin"
        defaultMessage="数据管理"
      />
    ),
    value: 5000,
  }
]

export const permissionEnum = convertPermissionTree(permissionTreeEnum);
